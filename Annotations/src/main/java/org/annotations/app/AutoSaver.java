package org.annotations.app;

import org.annotations.annotations.InitializerClass;
import org.annotations.annotations.Priority;
import org.annotations.app.chain.Chain;

@InitializerClass
@Priority(order = 1)
public class AutoSaver implements Chain {

    private Chain chain;
    public void start() {
        System.out.println("AutoSaver started");
    }

    @Override
    public void setNextLimit(Chain nextChain) {
        this.chain= nextChain;

    }
}
