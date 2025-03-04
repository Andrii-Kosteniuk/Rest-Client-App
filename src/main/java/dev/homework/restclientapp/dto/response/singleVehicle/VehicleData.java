package dev.homework.restclientapp.dto.response.singleVehicle;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class VehicleData {

    @JsonProperty("attributes")
    private Map<String, Object> attributes;
}
