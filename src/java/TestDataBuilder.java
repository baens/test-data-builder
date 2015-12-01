package net.baens.testdatabuilder;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.sql.Timestamp;
import java.util.*;

public class TestDataBuilder
{
    private static Map<Class,Map<Integer,Integer>> _lookups = new HashMap<>();
    private static Map<Class,Object> _instances =new HashMap<>();

    public static <T> Builder<T> create(Class<T> type){
        return new Builder(type,_lookups);
    }

    public static <T> T fieldOf(Class<T> type) {
        T instance = (T)_instances.get(type);

        if(instance != null)
            return instance;

        Constructor constructor = type.getConstructors()[0];

        Object[] parameters = Utils.createParameters(constructor);

        Map<Integer,Integer> parameterLookups = new HashMap<>();
        for(int i =0; i < parameters.length; i++){
            parameterLookups.put(System.identityHashCode(parameters[i]), i);
            parameterLookups.put(parameters[i].hashCode(),i);
        }

        try {
            instance = (T)constructor.newInstance(parameters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        _instances.put(type,instance);

        _lookups.put(type,parameterLookups);

        return instance;
    }
}
