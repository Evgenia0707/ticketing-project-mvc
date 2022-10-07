package com.cydeo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMapService <T,ID>{//SpringBoot design  this automatically

    public Map<ID,T> map = new HashMap<>();//create Customer DB
    //implementation same - save -> why use Generic -
    T save(ID id, T object){//save to DB- ID-generic(can be Strong, Long)map give obj, role - ID unique
        map.put(id,object);
        return object;
    }

    List<T> findAll(){
        return new ArrayList<>(map.values());
    }

    T findById(ID id){
        return map.get(id);
    }

    void deleteById(ID id){
        map.remove(id);
    }

    void update(ID id, T object){
        map.put(id, object);
    }
}
