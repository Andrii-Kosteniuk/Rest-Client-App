package dev.homework.restclientapp.dto.responce.district;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class DistrictRecord {

    @JsonProperty("klucz-slownika")
    private String districtKey;

    @JsonProperty("wartosc-slownika")
    private String districtName;
}
