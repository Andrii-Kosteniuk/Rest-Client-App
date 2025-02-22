package dev.homework.restclientapp.dto.responce.district;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Data {

    @JsonProperty("attributes")
    private Attributes attributes;

}
