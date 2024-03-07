package org.arrays;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;

public class Main {


    public static void main(String[] args) {

        int[] arrint = {1, 2, 3, 4};

        double[][] twoDimensions = {{1.5, 2.5}, {2.3, 3.4, 5.4}};

        inspectArray(arrint);
        String inspected = inspectArrayValues(arrint);
        System.out.println(inspected);
        System.out.println("-------------------------------------\n");
        inspectArray(twoDimensions);
        String inspectArrayValues = inspectArrayValues(twoDimensions);
        System.out.println(inspectArrayValues);
    }

    public static String inspectArrayValues(Object arrayObject) {

        int length = Array.getLength(arrayObject);

//        System.out.println(String.format("Array lenght is : %d", length));

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("[");
        for (int i = 0; i < length; i++) {

            Object element = Array.get(arrayObject, i);

            if (element.getClass().isArray()) {
                stringBuilder.append(inspectArrayValues(element));
            } else {
                stringBuilder.append(element);

            }



            if (i < length - 1) {
                stringBuilder.append(",");

            }
        }

        stringBuilder.append("]");

        return stringBuilder.toString();

    }


    public static void inspectArray(Object arrayObject) {

        Class<?> clazz = arrayObject.getClass();

        System.out.println(String.format("Is array : %s", clazz.isArray()));

        Class<?> componentType = clazz.getComponentType();

        System.out.println(String.format("This is an array of type: %s", componentType.getTypeName()));


    }
}