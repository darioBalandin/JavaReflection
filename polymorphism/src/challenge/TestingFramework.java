package challenge;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class TestingFramework {
    public static void main(String[] args) throws Throwable {
        runTestSuite(PaymentServiceTest.class);
    }

    public static void runTestSuite(Class<?> testClass) throws Throwable {

        AtomicReference<Method> beforeClass= new AtomicReference<>();
        AtomicReference<Method> afterClass=new AtomicReference<>();
        AtomicReference<Method> setupTest=new AtomicReference<>();

        Method[] declaredMethods = testClass.getMethods();

        List<Method> collected = Arrays.stream(testClass.getMethods())
                .filter(method -> method.getReturnType().equals(void.class))
                .filter(method -> method.getParameterCount() == 0)
                .peek(method -> {

                    if (method.getName().equals("beforeClass")) {
                        beforeClass.set(method);
                    }
                })
                .peek(method -> {

                    if (method.getName().equals("afterClass")) {
                        afterClass.set(method);
                    }
                })
                .peek(method -> {

                    if (method.getName().equals("setupTest")) {
                        setupTest.set(method);
                    }
                })
                .filter(method -> method.getName().startsWith("test"))
                .collect(Collectors.toList());

        if(beforeClass.get()!=null){
            beforeClass.get().invoke(null);
        }

        Constructor<?> constructor = testClass.getConstructor();
        final Object newInstance = constructor.newInstance();
        for (Method method:collected) {
            if(setupTest.get()!=null){
                setupTest.get().invoke(newInstance);
            }
            method.invoke(newInstance);
        }


        if(afterClass.get()!=null){
            afterClass.get().invoke(null);
        }
    }

}