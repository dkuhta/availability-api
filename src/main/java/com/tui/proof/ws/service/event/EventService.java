package com.tui.proof.ws.service.event;

import com.tui.proof.ws.event.Event;
import com.tui.proof.ws.model.event.EventModel;

public interface EventService {

    String generateId();

    EventModel create(Event<?> event);

    EventModel get(String id);

    EventModel setComplete(String id, String location);

    EventModel setFailed(String id, String msg);
}
