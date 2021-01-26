package com.tui.proof.ws.service.event;

import com.tui.proof.ws.event.Event;
import com.tui.proof.ws.model.event.EventModel;
import com.tui.proof.ws.model.event.EventStatus;
import com.tui.proof.ws.respository.event.EventRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.time.LocalDateTime.now;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository repository;

    public EventServiceImpl(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public String generateId() {
        return UUID.randomUUID().toString();

    }

    @Override
    public EventModel create(Event<?> event) {
        EventModel model = new EventModel();
        model.setId(event.getId());
        model.setDateCreated(now());
        model.setSource(event.getSource());
        model.setUserName(event.getUserName());
        repository.save(model);
        return model;
    }

    @Override
    public EventModel get(String id) {
        return repository.find(id);
    }

    @Override
    public EventModel setComplete(String id, String location) {
        EventModel event = get(id);
        event.setStatus(EventStatus.COMPLETED);
        event.setLocation(location);
        repository.save(event);
        return event;
    }

    @Override
    public EventModel setFailed(String id, String msg) {
        EventModel event = get(id);
        event.setStatus(EventStatus.FAILED);
        event.setMsg(msg);
        repository.save(event);
        return event;
    }
}
