package com.tui.proof.ws.model.event;

import com.tui.proof.ws.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class EventModel extends BaseModel implements Serializable {

    private EventStatus status = EventStatus.CREATED;
    private Object source;
    private String msg;
    private String location;
}
