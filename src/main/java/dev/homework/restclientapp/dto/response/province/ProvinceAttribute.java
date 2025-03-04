package dev.homework.restclientapp.dto.response.province;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ProvinceAttribute {

    @JsonProperty("dostepne-rekordy-slownika")
    private List<ProvinceRecord> provinceRecords;

    @JsonProperty("attributes")
    private Map<String, Object> attributes;
}
