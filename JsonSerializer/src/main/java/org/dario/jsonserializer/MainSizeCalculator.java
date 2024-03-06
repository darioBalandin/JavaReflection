package org.dario.jsonserializer;

import org.dario.data.AccountSummary;

public class MainSizeCalculator {

    public static void main(String[] args) throws IllegalAccessException {


        AccountSummary accountSummary = new AccountSummary("John", "Smith", (short) 20, 100_000);


        ObjectSizeCalculator objectSizeCalculator= new ObjectSizeCalculator();


        long sizeOfObject = objectSizeCalculator.sizeOfObject(accountSummary);

        System.out.println(sizeOfObject);
    }
}
