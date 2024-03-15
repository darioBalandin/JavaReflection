package org.dario.configloader;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class ArrayFlattening {

    public static void main(String[] args) {

        int [] result = concat(int.class, 1, 2, 3, new int[] {4, 5, 6}, 7);

    }


    public static <T> T concat(Class<?> type, Object... arguments) {
        if (arguments.length == 0) {
            return null;
        }
        List<Object> objectList = new ArrayList<>();

        for (Object o : arguments) {
            if (o.getClass().isArray()) {
                int length = Array.getLength(o);
                for (int i = 0; i < length; i++) {
                    objectList.add(Array.get(o, i));
                }
            } else {
                objectList.add(o);
            }
        }

        Object array = Array.newInstance(type, objectList.size());

        for (int i = 0; i < objectList.size(); i++) {
            Array.set(array, i, objectList.get(i));
        }
        return (T) array;
    }
}
