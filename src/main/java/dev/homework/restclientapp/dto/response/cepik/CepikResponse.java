package dev.homework.restclientapp.dto.response.cepik;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CepikResponse <T> {

    @JsonProperty("data")
    private T data;

}