package com.tui.proof.ws.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class BaseModel implements Serializable {

    private String id;
    private String userName;
    private LocalDateTime dateCreated;
}
