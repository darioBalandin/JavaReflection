package org.annotations.app.http;

import org.annotations.annotations.InitializerClass;
import org.annotations.annotations.InitializerMethod;

@InitializerClass
public class ServiceRegistry {

    @InitializerMethod
    public void registerService(){
        System.out.println("Service succesfully registered");
    }
}
