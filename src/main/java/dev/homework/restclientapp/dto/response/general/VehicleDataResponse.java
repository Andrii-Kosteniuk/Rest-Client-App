package dev.homework.restclientapp.dto.response.general;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDataResponse {

    @JsonProperty("id")
    private String id;

   @JsonProperty("attributes")
    private Map<String, Object> attributes;
}
