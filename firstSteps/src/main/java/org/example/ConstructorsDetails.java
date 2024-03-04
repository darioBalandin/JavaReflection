package org.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class ConstructorsDetails {

    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {


//        printConstructorData(Person.class);
//
//        printConstructorData(Address.class);

        Person person = createInstanceWithArguments(Person.class, "caballero", 22);

        assert person != null;

        System.out.println("creating instance with arguments : " + person);
        System.out.println(person.getClass().getSimpleName());


        var constructor = PrivateConstructor.class.getDeclaredConstructor(int.class, int.class);

        constructor.setAccessible(true);
        var privConst = constructor.newInstance(1, 2);
    }

    @SuppressWarnings("unchecked")
    public static <T> T createInstanceWithArguments(Class<T> clazz, Object... args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        for (var constructor : clazz.getDeclaredConstructors()) {
            if (constructor.getParameterTypes().length == args.length) {
                return (T) constructor.newInstance(args);
            }
        }
        System.out.println("Not a suitable constructor found");
        return null;
    }


    public static void printConstructorData(Class<?> clazz) {

        Constructor<?>[] constructors = clazz.getDeclaredConstructors();

        System.out.printf("class %s has %d constructors\n\n", clazz.getSimpleName(), constructors.length);

        for (int i = 0; i < constructors.length; i++) {
            var parameterTypes = constructors[i].getParameterTypes();
            List<String> parameterTypeNames = Arrays.stream(parameterTypes)
                    .map(Class::getSimpleName)
                    .toList();

            System.out.println(parameterTypeNames);


        }

    }


    public static class Person {

        private final Address address;
        private final String name;
        private final int age;

        @Override
        public String toString() {
            return "Person{" +
                    "address=" + address +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }

        public Person(Address address, String name, int age) {
            this.address = address;
            this.name = name;
            this.age = age;
        }

        public Person(String name, int age) {
            this(null, name, age);
        }

        public Person(String name) {
            this(null, name, 0);
        }

        public Person() {
            this(null, "anonymous", 0);
        }
    }


    public static class Address {
        private String street;
        private int number;


        public Address(String street, int number) {
            this.street = street;
            this.number = number;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "street='" + street + '\'' +
                    ", number=" + number +
                    '}';
        }
    }
}
