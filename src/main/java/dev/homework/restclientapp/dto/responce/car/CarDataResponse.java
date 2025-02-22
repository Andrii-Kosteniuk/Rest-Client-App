package dev.homework.restclientapp.dto.responce.car;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class CarDataResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private String type;

   @JsonProperty("attributes")
    private Map<String, Object> attributes;
}
