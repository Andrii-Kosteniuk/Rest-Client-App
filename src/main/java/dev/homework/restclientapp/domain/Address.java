package dev.homework.restclientapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {

    private  String zip;
    private  String city;
    private  String state;

}
