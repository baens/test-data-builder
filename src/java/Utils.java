package net.baens.testdatabuilder;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.sql.Timestamp;
import java.util.*;


class Utils {
    public static Object[] createParameters(Constructor constructor) {
        Object[] parameters;
        Class[] parameterTypes = constructor.getParameterTypes();
        parameters = new Object[parameterTypes.length];

        for(int i = 0; i < parameters.length; i++) {
            parameters[i] = createDefault(parameterTypes[i],i);
        }
        return parameters;
    }

    private static Object createDefault(Class type,int index) {
        if(type.equals(int.class) || type.equals(long.class)) {
            return index;
        }else if(type.equals(double.class)){
            return (double)index;
        }else if(type.equals(char.class)){
            return (char)index;
        }else if(type.equals(String.class)) {
            return new String("default");
        }else if(type.equals(java.sql.Timestamp.class)) {
            return new Timestamp(0);
        }else if(type.equals(java.sql.Date.class)){
            return new java.sql.Date(0);
        }else if(type.equals(UUID.class)) {
            return UUID.randomUUID();
        }else if(type.isEnum()) {
            return EnumSet.allOf(type).toArray()[0];
        }else if(type.equals(Iterable.class)) {
            return Arrays.asList();
        }else if(type.equals(boolean.class)){
            return index % 2 == 0;
        }else if(type.isArray()){
            return Array.newInstance(type.getComponentType(),0);
        }else{
            try {
                return type.newInstance();
            } catch(Exception e){
                throw new RuntimeException(e);
            }
        }
    }
}
