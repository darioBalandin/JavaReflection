package org.methods.test;

import org.methods.api.Product;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ProductTest {


    public static void main(String[] args) {


        Map<String, Method> stringMethodMap = mapMethodNametoMethod(Product.class);

        testGetters(Product.class);

    }

    public static void testGetters(Class<?> clazz) {

        Field[] declaredFields = clazz.getDeclaredFields();


        Map<String, Method> stringMethodMap = mapMethodNametoMethod(clazz);

        for (Field field : declaredFields) {

            String getterName = "get" + capitalizeFirstLetter(field.getName());

            if(!stringMethodMap.containsKey(getterName)){

                System.out.println("muy mal por no implementar: "+ getterName+ "();");
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
