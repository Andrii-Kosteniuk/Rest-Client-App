package dev.homework.restclientapp.dto.response.province;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProvinceData {

    @JsonProperty("attributes")
    private ProvinceAttribute attributes;
}
