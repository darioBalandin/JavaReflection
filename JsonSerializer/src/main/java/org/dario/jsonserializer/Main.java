package org.dario.jsonserializer;

import org.dario.data.Address;
import org.dario.data.Company;
import org.dario.data.Person;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {


        Company company = new Company("Google", "san fran", new Address("Bronx ave", (short) 456));

        Address address = new Address("Pico cejo", (short) 48);

        String[] specs = {"guapo", "educado"};
        Person person2 = new Person("javier", true, 36, 37000f, address, company, specs, new Person[]{});

        Person person3 = new Person("jose", false, 38, 37000f, address, company, specs, new Person[]{});

        Person[] people = {person2, person3};
        Person person = new Person("dario", true, 34, 34000f, address, company, specs, people);

        String serialized = objectToJson(person, 0);
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
                stringBuilder.append(formatPrimitiveValue(field.get(instance), field.getType()));
            } else if (field.getType().equals(String.class)) {
                stringBuilder.append(formatStringValue(field.get(instance).toString()));

            } else if (field.getType().isArray()) {

                stringBuilder.append(arrayToJson(field.get(instance), indentSize + 1));


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

    private static String arrayToJson(Object arrayInstance, int indentSize) throws IllegalAccessException {

        StringBuilder stringBuilder = new StringBuilder();

        int length = Array.getLength(arrayInstance);


        Class<?> componentType = arrayInstance.getClass().getComponentType();

        stringBuilder.append("[");

        stringBuilder.append("\n");

        for (int i = 0; i < length; i++) {

            Object element = Array.get(arrayInstance, i);

            if (componentType.isPrimitive()) {
                stringBuilder.append(indent(indentSize + 1));
                stringBuilder.append(formatPrimitiveValue(element, componentType));
            } else if (componentType.equals(String.class)) {
                stringBuilder.append(indent(indentSize + 1));
                stringBuilder.append(formatStringValue(element.toString()));
            } else {
                stringBuilder.append(objectToJson(element, indentSize + 1));
            }

            if (i != length - 1) {
                stringBuilder.append(",");
            }
            stringBuilder.append("\n");

        }
        stringBuilder.append(indent(indentSize));
        stringBuilder.append("]");

        return stringBuilder.toString();

    }

    private static String indent(int indentSize) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < indentSize; i++) {
            stringBuilder.append("\t");
        }
        return stringBuilder.toString();
    }

    private static String formatPrimitiveValue(Object instance, Class<?> type) throws IllegalAccessException {
        if (type.equals(boolean.class)
                || type.equals(int.class)
                || type.equals(short.class)
                || type.equals(long.class)

        ) {
            return instance.toString();
        } else if (type.equals(double.class) || type.equals(float.class)) {
            return String.format("%.02f",
                    instance);

        }
        throw new RuntimeException(String.format("Type : %s not supported", type.getName()));

    }

    private static String formatStringValue(String value) {
        return String.format("\"%s\"", value);
    }
}