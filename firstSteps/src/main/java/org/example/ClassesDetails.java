package org.example;

import java.util.HashMap;
import java.util.Map;

public class ClassesDetails {

    private static void printClassInfo(Class<?> ...classes){
        for (var clazz :classes){

            System.out.println(String.format("class name : %s, class package name : %s, canonical name: %s",clazz.getSimpleName(),clazz.getPackageName(),clazz.getCanonicalName()));

            Class<?>[] implementedInterfaces = clazz.getInterfaces();

            for(var implementedInterface: implementedInterfaces ){
                System.out.println(String.format("class %s implements : %s",clazz.getSimpleName(),implementedInterface.getSimpleName()));

            }


            System.out.println("Is array: "+ clazz.isArray());
            System.out.println("Is enum: "+ clazz.isEnum());
            System.out.println("Is primitive: "+ clazz.isPrimitive());
            System.out.println("Is interface: "+ clazz.isInterface());
            System.out.println("Is anonymous: "+ clazz.isAnonymousClass());
            System.out.println("--------------------------------------------------------------------------");
        }
    }

    private static class Square implements Drawable{
        @Override
        public int getNumberCorners() {
            return 4;
        }
    }

    private static interface Drawable{
        int getNumberCorners();
    }

    private enum Color{
        BLUE,RED,GREEN
    }


    public static void main(String[] args) throws ClassNotFoundException , NoSuchMethodException {

        Map<String,Integer> mapObj = new HashMap<>();

         // create an anonymous class from runnable

//        System.out.println(mapObj.getClass().getTypeName());

        Runnable runnable = () -> System.out.println("Hello from anonymous class");

        printClassInfo(String.class,Object.class,Square.class,Drawable.class,Color.class,mapObj.getClass(),Class.forName("org.example.ClassesDetails$Drawable"),boolean.class,Boolean.class,Integer.class,runnable.getClass(),int[].class);

        var consts=String.class.getDeclaredConstructors();


    }


}