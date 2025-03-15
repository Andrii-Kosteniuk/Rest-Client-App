package dev.homework.restclientapp.dto.response.province;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProvinceAttribute {

    @JsonProperty("dostepne-rekordy-slownika")
    private List<ProvinceRecord> provinceRecords;

}
