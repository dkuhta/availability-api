package com.tui.proof.ws.respository;

import com.tui.proof.ws.model.BaseModel;


public interface InMemoryRepository<T extends BaseModel> {

    void save(T model);

    T find(String id);
}
