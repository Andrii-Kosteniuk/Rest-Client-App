package dev.homework.restclientapp.dto.response.allVehicles;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VehicleMainRecord {

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

}
