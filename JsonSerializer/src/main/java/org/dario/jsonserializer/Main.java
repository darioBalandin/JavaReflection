package org.dario.jsonserializer;

import org.dario.data.Address;
import org.dario.data.Company;
import org.dario.data.Person;

import java.lang.reflect.Field;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {


        Company company= new Company("Google", "san fran", new Address("Bronx ave", (short)456));

        Address address = new Address("Pico cejo", (short) 48);

        Person person = new Person("dario", true, 34, 34000f, address, company,List.of("guapo","educado"));

        String serialized = objectToJson(person,0);


        var bool= Collection.class.isAssignableFrom(List.class);



        System.out.println(serialized);

    }


    public static String objectToJson(Object instance, int indentSize) throws IllegalAccessException {
        Field[] fields = instance.getClass().getDeclaredFields();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(indent(indentSize));
        stringBuilder.append("{\n");
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);

            if (field.isSynthetic()) {
                continue;
            }
            stringBuilder.append(indent(indentSize + 1));
            stringBuilder.append(formatStringValue(field.getName()));
            stringBuilder.append(":");
            if (field.getType().isPrimitive()) {
                stringBuilder.append(formatPrimitiveValue(field, instance));
            } else if (field.getType().equals(String.class)) {
                stringBuilder.append(formatStringValue(field.get(instance).toString()));

            } else if (Collection.class.isAssignableFrom(instance.getClass())) {

                stringBuilder.append("[");

                String pueba= Arrays.toString(((Collection<?>) instance).toArray());

                //                StringBuilder arrayB = new StringBuilder();
//                for (var obj: ((Collection<?>) instance).toArray()){
//                    arrayB.append(objectToJson(obj,indentSize+1));
//
//                }
//                stringBuilder.append(arrayB.toString());
                stringBuilder.append("]");
//                var coll= ((Collection<?>) instance).toArray();

            } else {
                stringBuilder.append(objectToJson(field.get(instance), indentSize + 1));
            }
            if (i != fields.length - 1) {
                stringBuilder.append(",\n");
            }

        }

        stringBuilder.append("\n");
        stringBuilder.append(indent(indentSize));
        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    private static String indent(int indentSize) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < indentSize; i++) {
            stringBuilder.append("\t");
        }
        return stringBuilder.toString();
    }

    private static String formatPrimitiveValue(Field field, Object parentInstance) throws IllegalAccessException {
        if (field.getType().equals(boolean.class)
                || field.getType().equals(int.class)
                || field.getType().equals(short.class)
                || field.getType().equals(long.class)

        ) {
            return field.get(parentInstance).toString();
        } else if (field.getType().equals(double.class) || field.getType().equals(float.class)) {
            return String.format("%.02f",
                    field.get(parentInstance));

        }
        throw new RuntimeException(String.format("Type : %s not supported", field.getType().getName()));

    }

    private static String formatStringValue(String value) {
        return String.format("\"%s\"", value);
    }
}