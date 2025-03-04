package dev.homework.restclientapp.dto.response.province;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CepikResponse {

    @JsonProperty("data")
    private CepikData data;

}
