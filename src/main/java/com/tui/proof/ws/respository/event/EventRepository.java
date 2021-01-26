package com.tui.proof.ws.respository.event;

import com.tui.proof.ws.model.event.EventModel;
import com.tui.proof.ws.respository.InMemoryRepositoryImpl;
import org.springframework.stereotype.Component;

@Component
public class EventRepository extends InMemoryRepositoryImpl<EventModel> {

}
