package org.dario;

import java.lang.reflect.Field;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }



    public static String objectToJson(Object instance){
        Field [] fields = instance.getClass().getDeclaredFields();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        for(int i=0;i<fields.length;i++){
            Field field= fields[i];
            field.setAccessible(true);

            

        }

    }


    private static String formatPrimitiveValue(Field field,Object parentInstance) throws IllegalAccessException {
        if(field.getType().equals(boolean.class)
            || field.getType().equals(int.class)
                || field.getType().equals(short.class)
                || field.getType().equals(long.class)

        ){
            return field.get(parentInstance).toString();
        } else if (field.getType().equals(double.class) || field.getType().equals(float.class)) {
            return String.format("%.02f",
                    field.get(parentInstance));

        }
        throw new RuntimeException(String.format("Type : %s not supported",field.getType().getName()));

    }

    private static String formatStringValue(String value){
        return String.format("\"%s\"",value);
    }
}