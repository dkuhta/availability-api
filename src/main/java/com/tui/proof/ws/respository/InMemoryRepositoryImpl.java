package com.tui.proof.ws.respository;

import com.tui.proof.ws.model.BaseModel;
import com.tui.proof.ws.utils.SecurityUtils;

import java.util.concurrent.ConcurrentHashMap;


public abstract class InMemoryRepositoryImpl<T extends BaseModel> {

    private final ConcurrentHashMap<String, T> memory = new ConcurrentHashMap<>();

    public void save(T model) {
        memory.put(model.getId(), model);
    }

    public T find(String id) {
        if (!memory.containsKey(id)) {
            throw new RuntimeException(String.format("Can't find object with id=%s", id));
        }

        T model = memory.get(id);
        validateUserAccess(model);
        return model;
    }

    protected void validateUserAccess(T model) {
        if (!SecurityUtils.getCurrentUsername().equals(model.getUserName())) {
            throw new SecurityException("Access denied");
        }
    }
}
