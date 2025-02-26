package dev.homework.restclientapp.dto.response.allVehicles;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class VehicleResponse {

    @JsonProperty("data")
    private List<VehicleDataResponse> data;

    @JsonProperty("links")
    private Map<String, String> links;

    @JsonProperty("meta")
    private Map<String, Object> meta;

}
