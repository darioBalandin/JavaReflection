package org.dario.init;

import org.dario.game.Game;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException, ClassNotFoundException {



        Game game= (Game) createObjectRecursively(Class.forName("org.dario.game.internal.TicTacToeGame"));


        game.startGame();

    }

    @SuppressWarnings("unchecked")
    public static <T> T createObjectRecursively(Class<T> obj) throws InvocationTargetException, InstantiationException, IllegalAccessException {

        Constructor<?> constructor= getFirstConstructor(obj);

        List<Object> constructorParameters= new ArrayList<>();

        for(Class<?> param: constructor.getParameterTypes()){
            Object valueParameter= createObjectRecursively(param);
            constructorParameters.add(valueParameter);
        }

        constructor.setAccessible(true);
        return (T) constructor.newInstance(constructorParameters.toArray());
    }


    public static Constructor<?> getFirstConstructor(Class<?> clazz){

        Constructor<?>[] constructors = clazz.getDeclaredConstructors();

        if(constructors.length== 0){
            throw new IllegalStateException("No constructor found");

        }

        return constructors[0];
    }
}