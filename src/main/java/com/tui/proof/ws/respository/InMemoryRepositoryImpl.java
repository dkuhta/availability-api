package com.tui.proof.ws.respository;

import com.tui.proof.ws.exception.LogicalException;
import com.tui.proof.ws.model.BaseModel;
import com.tui.proof.ws.utils.SecurityUtils;

import javax.security.auth.login.LoginException;
import java.util.concurrent.ConcurrentHashMap;


public abstract class InMemoryRepositoryImpl<T extends BaseModel> {

    private final ConcurrentHashMap<String, T> memory = new ConcurrentHashMap<>();

    public void save(T model) {
        memory.put(model.getId(), model);
    }

    public T find(String id) {
        if (!memory.containsKey(id)) {
            throw new LogicalException(getNotFoundMsg(id));
        }

        T model = memory.get(id);
        validateUserAccess(model);
        return model;
    }

    protected String getNotFoundMsg(String id) {
        return String.format("Can't find %s with id=%s", getObjectName(), id);
    }

    protected abstract String getObjectName();

    protected void validateUserAccess(T model) {
        if (!SecurityUtils.getCurrentUsername().equals(model.getUserName())) {
            throw new SecurityException("Access denied");
        }
    }
}
