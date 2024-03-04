package org.example;

import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

public class RecursiveInterfaces {

    public static Set<Class<?>> findAllImplementedInterfaces(Class<?> input) {
        Set<Class<?>> allImplementedInterfaces = new HashSet<>();

        Class<?>[] inputInterfaces = input.getInterfaces();
        for (Class<?> currentInterface : inputInterfaces) {
            allImplementedInterfaces.add(currentInterface);

            if(currentInterface.getInterfaces().length>0){
                allImplementedInterfaces.addAll(findAllImplementedInterfaces(currentInterface));
            }
            /** Complete this code **/
        }

        return allImplementedInterfaces;
    }

    public static void main(String[] args) {


        var sets= findAllImplementedInterfaces(ObjectOutputStream.class);

        System.out.println(sets);
    }
}
