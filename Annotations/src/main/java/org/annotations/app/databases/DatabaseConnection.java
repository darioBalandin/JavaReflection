package org.annotations.app.databases;

import org.annotations.annotations.InitializerClass;
import org.annotations.annotations.InitializerMethod;
import org.annotations.annotations.Priority;
import org.annotations.app.chain.Chain;

@InitializerClass
@Priority(order = 3)
public class DatabaseConnection implements Chain {

    private Chain chain;
    @InitializerMethod
    public void connectToDatabase1() {
        System.out.println("Connecting to db 1");
    }

    @InitializerMethod
    public void connectToDatabase2() {
        System.out.println("Connecting to db 2");
    }


    @Override
    public void setNextLimit(Chain nextChain) {
        this.chain= nextChain;
    }
}
