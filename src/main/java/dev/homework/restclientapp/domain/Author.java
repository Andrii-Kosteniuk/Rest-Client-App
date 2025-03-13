package dev.homework.restclientapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Author {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Address address;
}
