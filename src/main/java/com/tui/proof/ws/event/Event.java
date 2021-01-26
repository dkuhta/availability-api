package com.tui.proof.ws.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

@Getter
@Setter
public class Event<T> implements ResolvableTypeProvider {

    private String id;
    private String userName;
    private T source;

    public Event(String id, String userName, T object) {
        this.id = id;
        this.userName = userName;
        this.source = object;
    }

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(this.source));
    }
}
