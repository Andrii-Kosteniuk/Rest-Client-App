package dev.homework.restclientapp.dto.response.general;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleMainRecord {

    @JsonProperty("id")
    private String id;

    @JsonProperty("marka")
    private String marka;

    @JsonProperty("model")
    private String model;

    @JsonProperty("data-pierwszej-rejestracjiwkraju")
    private String dataPierwszejRejestracji;

    @JsonProperty("rok-produkcji")
    private String rokProdukcji;

}
