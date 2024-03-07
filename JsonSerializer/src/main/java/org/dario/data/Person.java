package org.dario.data;

import java.util.List;

public class Person {

    private final String name;
    private  final boolean employed;
    private final int age;
    private final float salary;
    private final Address address;
    private final Company company;

    private final String[] specs;

    private final Person[] friends;

    public Person(String name, boolean employed, int age, float salary, Address address, Company company, String[] specs, Person[] friends) {
        this.name = name;
        this.employed = employed;
        this.age = age;
        this.salary = salary;
        this.address = address;
        this.company = company;
        this.specs = specs;
        this.friends = friends;
    }
}
