package dev.homework.restclientapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Address address;


    @Data
    @AllArgsConstructor
    public static class Address {

        private String zip;
        private String city;
        private String state;

    }

}
