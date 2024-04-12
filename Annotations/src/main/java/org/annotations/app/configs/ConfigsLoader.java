package org.annotations.app.configs;


import org.annotations.annotations.InitializerClass;
import org.annotations.annotations.InitializerMethod;

@InitializerClass
public class ConfigsLoader {

    @InitializerMethod
    public void loadAllConfigs(){
        System.out.println("Loading all configs");
    }
}
