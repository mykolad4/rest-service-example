package com.demchenko.restservice.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
public class User {
    private Integer id;
    @Size(min = 2, message = "Message should have at least 2 characters")
    private String name;
    @Past
    private Date birthDate;
}
