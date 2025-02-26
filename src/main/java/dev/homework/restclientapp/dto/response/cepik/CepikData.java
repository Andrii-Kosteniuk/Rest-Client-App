package dev.homework.restclientapp.dto.response.cepik;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CepikData <T> {

    @JsonProperty("id")
    private String id;

    @JsonProperty("attributes")
    private T attributes;

}
