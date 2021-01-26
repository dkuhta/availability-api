package com.tui.proof.ws.dto.booking;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
public class Holder implements Serializable {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String address;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String country;

    @Email
    private String email;

    @NotEmpty
    private Set<String> telephones;
}
