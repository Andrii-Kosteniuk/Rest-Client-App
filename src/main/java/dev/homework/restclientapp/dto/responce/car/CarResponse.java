package dev.homework.restclientapp.dto.responce.car;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CarResponse {

    @JsonProperty("data")
    private List<CarDataResponse> data;
    @JsonProperty("links")
    private Map<String, String> links;
    @JsonProperty("meta")
    private Map<String, Object> meta;

}
