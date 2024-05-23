package org.methods.test;

import org.methods.api.Address;
import org.methods.api.Product;

import java.awt.event.ItemListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductTest {


    public static void main(String[] args) {


        Map<String, Method> stringMethodMap = mapMethodNametoMethod(Product.class);
        Map<String, Method> stringMethodMap1 = filterObjectMethods(stringMethodMap);
        List<Field> allFields = getAllFields(ArrayList.class);
        testGetters(Product.class);

        testSetters(Product.class);
    }

    private static Map<String, Method> filterObjectMethods(Map<String, Method> stringMethodMap) {

        return stringMethodMap.entrySet().stream()
                .filter(e -> {
                    Class<?> aClass = e.getValue().getDeclaringClass();
                    return !e.getValue().getDeclaringClass().equals(Object.class);
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static void testSetters(Class<?> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        Map<String, Method> stringMethodMap = mapMethodNametoMethod(clazz);

        for (Field field : declaredFields) {
            String setterName = "set" + capitalizeFirstLetter(field.getName());

            Method method = null;
            try {
                method = clazz.getMethod(setterName, field.getType());

            } catch (Exception e) {
                e.getCause();
                throw new IllegalStateException("muy mal por no implementar: " + setterName + "();");
            }
            if (!method.getReturnType().equals(void.class)) {
                throw new IllegalStateException("Tipo de retorno erroneo : " + setterName + "();");
            }
        }
    }

    public static List<Field> getAllFields(Class<?> clazz){
        if (clazz==null || clazz.equals(Object.class)){
            return Collections.emptyList();
        }
        Field[] declaredFields = clazz.getDeclaredFields();
        List<Field> superClassFields = getAllFields(clazz.getSuperclass());

        List<Field> allFields = new ArrayList<>();
        allFields.addAll(List.of(declaredFields));
        allFields.addAll(superClassFields);

        return allFields;
    }

    public static void testGetters(Class<?> clazz) {

        Field[] declaredFields = clazz.getDeclaredFields();


        Map<String, Method> stringMethodMap = mapMethodNametoMethod(clazz);

        for (Field field : declaredFields) {

            String getterName = "get" + capitalizeFirstLetter(field.getName());

            if (!stringMethodMap.containsKey(getterName)) {

                throw new IllegalStateException("muy mal por no implementar: " + getterName + "();");
            }
            Method method = stringMethodMap.get(getterName);
            Class<?> returnType = method.getReturnType();
            Class<?> fieldType = field.getType();

            if (!returnType.equals(fieldType)) {
                throw new IllegalStateException("Tipo de retorno erroneo : " + getterName + "();");
            }
            if (method.getParameterCount() > 0) {
                throw new IllegalStateException("A getter should not have parameters: " + getterName + "();");
            }

        }

    }

    private static String capitalizeFirstLetter(String name) {
        String upperCase = name.substring(0, 1).toUpperCase();
        return upperCase.concat(name.substring(1));
    }


    private static Map<String, Method> mapMethodNametoMethod(Class<?> dataClass) {


        Method[] dataClassMethods = dataClass.getMethods();

        HashMap<String, Method> methodHashMap = new HashMap<>();

        for (Method method : dataClassMethods) {

            methodHashMap.put(method.getName(), method);
        }


        return methodHashMap;


    }

}
