package dev.homework.restclientapp.dto.response.singleVehicle;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleByIdResponse {

    @JsonProperty("data")
    private VehicleData data;
}
