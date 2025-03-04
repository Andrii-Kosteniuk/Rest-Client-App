package dev.homework.restclientapp.dto.response.province;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class ProvinceRecord {

    @JsonProperty("klucz-slownika")
    private String provinceKey;

    @JsonProperty("wartosc-slownika")
    private String provinceName;
}
