package org.dario.jsonserializer;

import java.lang.reflect.Array;

public class ArrayReader {

    public static void main(String[] args) {

        int [] input = new int[] {0, 10, 20, 30, 40};

        Object arrayElement = getArrayElement(input, 3);

        String[] names = new String[] {"John", "Michael", "Joe", "David"};

        Object arrayElement1 = getArrayElement(names, -1);

        System.out.println(arrayElement1);

    }


    public static Object getArrayElement(Object array, int index){

        int length = Array.getLength(array);

        if(index<0){
            return Array.get(array,length+index);
        }

        return Array.get(array,index);
    }
}
