package org.annotations.app.databases;

import org.annotations.annotations.InitializerClass;
import org.annotations.annotations.InitializerMethod;
import org.annotations.annotations.Priority;
import org.annotations.annotations.RetryOperation;
import org.annotations.app.chain.Chain;

import java.io.IOException;

@InitializerClass
@Priority(order = 3)
public class DatabaseConnection implements Chain {

    private int failCounter = 5;

    private Chain chain;

    @RetryOperation(
            numberOfRetries = 10,
            retryExceptions = IOException.class,
            durationBetweenRetriesMs = 1000,
            failureMessage = "Connection to database 1 failed after retries"
    )
    @InitializerMethod
    public void connectToDatabase1() throws IOException {
        System.out.println("Connecting to db 1");
        if (failCounter > 0) {
            failCounter--;
            throw new IOException("Connection failed");
        }

        System.out.println("Connection to db 1 succeeded");
    }

    @InitializerMethod
    public void connectToDatabase2() {
        System.out.println("Connecting to db 2");
    }


    @Override
    public void setNextLimit(Chain nextChain) {
        this.chain = nextChain;
    }
}
