package org.dario.configloader;

import org.dario.data.GameConfig;
import org.dario.data.UserInterfaceConfig;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {

    public static final Path PATH_USER_INTERFACE_CONFIG = Path.of("C:\\Users\\DarioGarciaBalandin\\Desktop\\Repositories\\JavaReflection\\Deserializer_ConfigFile\\src\\main\\resources\\user-interface.cfg");
    public static final Path PATH_GAME = Path.of("C:\\Users\\DarioGarciaBalandin\\Desktop\\Repositories\\JavaReflection\\Deserializer_ConfigFile\\src\\main\\resources\\game-properties.cfg");

    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {


        UserInterfaceConfig userInterfaceConfig= createConfigObject(UserInterfaceConfig.class, PATH_USER_INTERFACE_CONFIG);


        GameConfig gameConfig= createConfigObject(GameConfig.class, PATH_GAME);


        System.out.println("Hello world!");
    }


    public static <T> T createConfigObject(Class<T> clazz, Path path) throws IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Scanner scanner = new Scanner(path);

        Constructor<T> declaredConstructor = clazz.getDeclaredConstructor();

        declaredConstructor.setAccessible(true);

        T configInstance = (T) declaredConstructor.newInstance();

        while (scanner.hasNextLine()) {

            String configLine = scanner.nextLine();

            String[] split = configLine.split("=");
            String propertyName = split[0];
            String propertyValue = split[1];

            Field field;

            try {
                field = clazz.getDeclaredField(propertyName);
            } catch (NoSuchFieldException e) {
                System.out.println(e.toString());
                continue;
            }
            field.setAccessible(true);

            Object parsedValue = parseValue(field.getType(), propertyValue);

            field.set(configInstance, parsedValue);

        }

        return configInstance;


    }

    private static Object parseValue(Class<?> type, String propertyValue) {

        if (type.equals(int.class)) {
            return Integer.parseInt(propertyValue);
        } else if (type.equals(short.class)) {
            return Short.parseShort(propertyValue);
        } else if (type.equals(long.class)) {
            return Long.parseLong(propertyValue);
        } else if (type.equals(boolean.class)) {
            return Boolean.parseBoolean(propertyValue);
        } else if (type.equals(double.class)) {
            return Double.parseDouble(propertyValue);
        } else if (type.equals(float.class)) {
            return Float.parseFloat(propertyValue);
        } else if (type.equals(String.class)) {
            return propertyValue;

        }
        throw new RuntimeException(String.format("Type : %s not supported",type.getTypeName()));
    }
}