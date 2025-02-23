package dev.homework.restclientapp.dto.responce.car;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Vehicle {

    @JsonProperty("id")
    private String id;

    @JsonProperty("marka")
    private String mark;

    @JsonProperty("model")
    private String model;

    @JsonProperty("data-pierwszej-rejestracjiwkraju")
    private String dateOfFirstRegistration;

    @JsonProperty("rok-produkcji")
    private String yearOfProduction;

    public Vehicle(String id, String mark, String model, String dateOfFirstRegistration, String yearOfProduction) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.dateOfFirstRegistration = dateOfFirstRegistration;
        this.yearOfProduction = yearOfProduction;
    }
}
