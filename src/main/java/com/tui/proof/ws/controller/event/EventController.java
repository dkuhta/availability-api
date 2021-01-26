package com.tui.proof.ws.controller.event;

import com.tui.proof.ws.model.event.EventModel;
import com.tui.proof.ws.model.event.EventStatus;
import com.tui.proof.ws.service.event.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpHeaders.LOCATION;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable("id") String id) {
        EventModel eventModel = eventService.get(id);

        if (EventStatus.COMPLETED.equals(eventModel.getStatus())) {
            return ResponseEntity
                    .status(HttpStatus.SEE_OTHER)
                    .header(LOCATION, eventModel.getLocation())
                    .build();
        } else {
            return ResponseEntity
                    .ok()
                    .body(eventModel);
        }
    }
}
