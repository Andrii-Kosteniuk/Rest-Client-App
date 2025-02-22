package dev.homework.restclientapp.dto.responce.car;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Car {

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

    public Car(String id, String marka, String model, String dataPierwszejRejestracji, String rokProdukcji) {
        this.id = id;
        this.marka = marka;
        this.model = model;
        this.dataPierwszejRejestracji = dataPierwszejRejestracji;
        this.rokProdukcji = rokProdukcji;
    }
}
