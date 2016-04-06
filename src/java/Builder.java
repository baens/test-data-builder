package net.baens.testdatabuilder;

import java.lang.reflect.Constructor;
import java.util.*;

public class Builder<T> {
    private final Class<T> _type;
    private final Object[] _parameters;
    private final Object[][] _varParameters;
    private final Constructor _constructor;
    private final Map<Class,Map<Integer,Integer>> _lookups;
    private boolean _hasVarParameters = false;

    public Builder(Class<T> type,Map<Class,Map<Integer,Integer>> lookups){
        _type = type;
        _constructor = type.getConstructors()[0];
        _parameters = Utils.createParameters(_constructor);
        _varParameters = new Object[_parameters.length][];
        _lookups = lookups;
    }

    public Builder<T> set(Object field, Object value){
        Map<Integer,Integer> lookUps = _lookups.get(_type);
        Integer item = lookUps.get(System.identityHashCode(field));
        if(item == null)
            item = lookUps.get(field.hashCode());

        _parameters[item] = value;
        return this;
    }

    public Builder<T> set(Object field, Object... values){
        if(values == null) return set(field,(Object)null);

        _hasVarParameters = true;
        Map<Integer,Integer> lookUps = _lookups.get(_type);
        _varParameters[lookUps.get(System.identityHashCode(field))] = values;
        return this;
    }

    public T build(){
        try {
            return (T) _constructor.newInstance(_parameters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Iterable<T> build(int count) {
        List<T> list = new LinkedList<>();
        for(int i = 0; i < count; i++){
            list.add(buildByIndex(i));
        }

        return list;
    }

    private T buildByIndex(int index){
        Object[] parameters;

        if(_hasVarParameters){
            parameters = new Object[_parameters.length];

            System.arraycopy(_parameters,0,parameters,0,_parameters.length);

            for(int i = 0; i < _varParameters.length; i++){
                if(_varParameters[i] == null) continue;

                parameters[i] = _varParameters[i][index % _varParameters[i].length];
            }
        }else{
            parameters = _parameters;
        }

        try {
            return (T) _constructor.newInstance(parameters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

