package dev.homework.restclientapp.dto.response.province;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProvinceRecord {

    @JsonProperty("klucz-slownika")
    private String provinceKey;

    @JsonProperty("wartosc-slownika")
    private String provinceName;
}
