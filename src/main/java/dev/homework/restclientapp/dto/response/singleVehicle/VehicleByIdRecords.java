package dev.homework.restclientapp.dto.response.singleVehicle;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VehicleByIdRecords {

    @JsonProperty("marka")
    private String mark;

    @JsonProperty("model")
    private String model;

    @JsonProperty("rodzaj-pojazdu")
    private String type;

    @JsonProperty("podrodzaj-pojazdu")
    private String subtype;

    @JsonProperty("pochodzenie-pojazdu")
    private String originOfCar;

    @JsonProperty("rok-produkcji")
    private String dateOfManufacture;

    @JsonProperty("data-pierwszej-rejestracji-w-kraju")
    private String dateOfFirstRegistrationInPoland;

    @JsonProperty("data-ostatniej-rejestracji-w-kraju")
    private String dateOfLastRegistrationInPoland;

    @JsonProperty("pojemnosc-skokowa-silnika")
    private int engineDisplacement;

        @JsonProperty("masa-wlasna")
    private String netWeight;

    @JsonProperty("dopuszczalna-masa-calkowita")
    private String permissibleGrossVehicleWeight;

    @JsonProperty("liczba-osi")
    private int numberOfAxles;

    @JsonProperty("rejestracja-wojewodztwo")
    private String registrationProvince;

    @JsonProperty("rejestracja-gmina")
    private String registrationDistrict;

    @JsonProperty("rejestracja-powiat")
    private String registrationCounty;

}
