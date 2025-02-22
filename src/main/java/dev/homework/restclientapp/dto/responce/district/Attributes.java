package dev.homework.restclientapp.dto.responce.district;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Attributes {

    @JsonProperty("dostepne-rekordy-slownika")
    private List<DistrictRecord> districtRecords;

    @JsonProperty("attributes")
    private Map<String, Object> attributes;
}
