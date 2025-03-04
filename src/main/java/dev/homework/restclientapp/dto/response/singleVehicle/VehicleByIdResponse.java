package dev.homework.restclientapp.dto.response.singleVehicle;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class VehicleByIdResponse {

    @JsonProperty("data")
    private VehicleData data;
}
