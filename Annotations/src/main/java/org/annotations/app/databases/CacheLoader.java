package org.annotations.app.databases;

import org.annotations.annotations.InitializerClass;
import org.annotations.annotations.InitializerMethod;
import org.annotations.annotations.Priority;
import org.annotations.app.chain.Chain;

@InitializerClass
@Priority(order = 1)
public class CacheLoader implements Chain {
    private Chain chain;
    @InitializerMethod
    public void loadCache(){
        System.out.println("Loading data from cache");
    }

    public void reloadCache(){
        System.out.println("Reload cache");
    }


    @Override
    public void setNextLimit(Chain nextChain) {
        this.chain= nextChain;

    }
}
