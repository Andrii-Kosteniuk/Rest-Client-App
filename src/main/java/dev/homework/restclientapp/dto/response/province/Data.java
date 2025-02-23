package dev.homework.restclientapp.dto.response.province;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Data {

    @JsonProperty("attributes")
    private Attributes attributes;

}
