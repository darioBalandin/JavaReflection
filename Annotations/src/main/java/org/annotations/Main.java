package org.annotations;

import org.annotations.annotations.InitializerClass;
import org.annotations.annotations.InitializerMethod;
import org.annotations.annotations.Priority;
import org.annotations.annotations.RetryOperation;
import org.annotations.annotations.ScanPackages;
import org.annotations.app.chain.Chain;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@ScanPackages({"app", "app.databases", "app.configs", "app.http"})
public class Main {
    public static void main(String[] args) throws Exception {


        initializeClasses();

        Map<Chain, Integer> app = classPriorityMap("app", "app.databases");

        Chain chain = returnChain(app);


        System.out.println("Hello world!");
    }

    private static Chain returnChain(Map<Chain, Integer> app) {
        List<Chain> chainList = app.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .toList();
        for (int i = 0; i < chainList.size() - 1; i++) {
            chainList.get(i).setNextLimit(chainList.get(i + 1));
        }
        return chainList.getFirst();
    }


    public static Map<Chain, Integer> classPriorityMap(String... packageNames) throws Exception {

        List<Class<?>> allClasses = getAllClasses(packageNames);
        Map<Chain, Integer> classMap = new HashMap<>();
        for (Class<?> clazz : allClasses) {
            if (!clazz.isAnnotationPresent(InitializerClass.class) && !clazz.isAnnotationPresent(Priority.class)) {
                continue;
            }
            Priority priority = clazz.getAnnotation(Priority.class);
            Object instance = clazz.getDeclaredConstructor().newInstance();
            if (instance instanceof Chain chain) {
                classMap.put(chain, priority.order());
            }

        }
        return classMap;
    }


    public static void initializeClasses() throws Exception {

        ScanPackages object = Main.class.getAnnotation(ScanPackages.class);

        if (object == null || object.value().length == 0) {
            return;
        }

        String[] packageNames = object.value();
        List<Class<?>> classes = getAllClasses(packageNames);

        for (Class<?> clazz : classes) {
            if (!clazz.isAnnotationPresent(InitializerClass.class)) {
                continue;
            }
            List<Method> methods = getAllInitializingMethods(clazz);
            Object instance = clazz.getDeclaredConstructor().newInstance();
            for (Method method : methods) {

                callInitializingMethod(instance, method);
            }

        }
    }

    private static void callInitializingMethod(Object instance, Method method) throws Exception {

        RetryOperation retryOperation = method.getAnnotation(RetryOperation.class);

        if (retryOperation == null) {
            try {
                method.invoke(instance);

            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getTargetException());
            }
            return;
        }
        int numberOfRetries = retryOperation.numberOfRetries();
        while (true) {
            try {
                method.invoke(instance);
                break;
            } catch (InvocationTargetException e) {
                Throwable targetException = e.getTargetException();
                if (numberOfRetries > 0 && Set.of(retryOperation.retryExceptions()).contains(targetException.getClass())) {
                    numberOfRetries--;

                    System.out.println("Retrying ...");
                    Thread.sleep(retryOperation.durationBetweenRetriesMs());
                } else {
                    throw new Exception(retryOperation.failureMessage(), targetException);
                }
            }
        }

        long duration = retryOperation.durationBetweenRetriesMs();


    }

    private static List<Class<?>> getAllClasses(String... packageNames) throws URISyntaxException, IOException, ClassNotFoundException {

        List<Class<?>> allClasses = new ArrayList<>();

        for (String packageName : packageNames) {
            String packageRelativePath = packageName.replace('.', '/');
            URI packageUri = Main.class.getResource(packageRelativePath).toURI();
            if (packageUri.getScheme().equals("file")) {
                Path packageFullPath = Paths.get(packageUri);
                allClasses.addAll(getAllPackageClasses(packageFullPath, packageName));
            } else if (packageUri.getScheme().equals("jar")) {
                FileSystem fileSystem = FileSystems.newFileSystem(packageUri, Collections.emptyMap());
                Path packageFullPathInJar = fileSystem.getPath(packageRelativePath);
                allClasses.addAll(getAllPackageClasses(packageFullPathInJar, packageName));
                fileSystem.close();
            }
        }
        return allClasses;
    }


    private static List<Method> getAllInitializingMethods(Class<?> clazz) {
        List<Method> initializingMethods = new ArrayList<>();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(InitializerMethod.class)) {
                initializingMethods.add(method);
            }
        }

        return initializingMethods;

    }

    private static List<Class<?>> getAllPackageClasses(Path packagePath, String packageName) throws IOException, ClassNotFoundException {
        if (!Files.exists(packagePath)) {
            return Collections.emptyList();
        }

        List<Path> files = Files.list(packagePath)
                .filter(Files::isRegularFile)
                .toList();

        List<Class<?>> classes = new ArrayList<>();

        for (Path filePath : files) {
            String fileName = filePath.getFileName().toString();
            if (fileName.endsWith(".class")) {
                String classFullName = "org.annotations." + packageName + "." + fileName.replaceFirst("\\.class$", "");
                Class<?> clazz = Class.forName(classFullName);
                classes.add(clazz);
            }
        }
        return classes;

    }
}