package org.arrays;

public class Main {



    public static void main(String[] args) {

        int [] arrint={1,2,3,4};

        double [][] twoDimensions={{1.5,2.5},{2.3,3.4,5.4}};

        inspectArray(arrint);
        System.out.println("-------------------------------------\n");
        inspectArray(twoDimensions);
    }


    public static void inspectArray(Object arrayObject){

        Class<?> clazz = arrayObject.getClass();

        System.out.println(String.format("Is array : %s",clazz.isArray()));

        Class<?> componentType = clazz.getComponentType();

        System.out.println(String.format("This is an array of type: %s",componentType.getTypeName()));

    }
}