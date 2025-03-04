package dev.homework.restclientapp.dto.response;


import dev.homework.restclientapp.dto.response.general.VehicleDataResponse;
import dev.homework.restclientapp.dto.response.general.VehicleMainRecord;
import dev.homework.restclientapp.dto.response.singleVehicle.VehicleByIdRecords;
import dev.homework.restclientapp.dto.response.singleVehicle.VehicleByIdResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CarDetailsMapper {

    public VehicleByIdRecords mapToVehicleDetails(VehicleByIdResponse vehicleByIdResponse) {

        Map<String, Object> attributes = vehicleByIdResponse.getData().getAttributes();

        return new VehicleByIdRecords(
                (String) attributes.get("marka"),
                (String) attributes.get("model"),
                (String) attributes.get("rodzaj-pojazdu"),
                (String) attributes.get("podrodzaj-pojazdu"),
                (String) attributes.get("pochodzenie-pojazdu"),
                (String) attributes.get("rok-produkcji"),
                (String) attributes.get("data-pierwszej-rejestracji-w-kraju"),
                (String) attributes.get("data-ostatniej-rejestracji-w-kraju"),
                getDoubleValue(attributes.get("pojemnosc-skokowa-silnika")),
                getIntValue(attributes.get("masa-wlasna")),
                getIntValue(attributes.get("dopuszczalna-masa-calkowita")),
                getIntValue(attributes.get("liczba-osi")),
                (String) attributes.get("rejestracja-wojewodztwo"),
                (String) attributes.get("rejestracja-gmina"),
                (String) attributes.get("rejestracja-powiat")
        );
    }

    public List<VehicleMainRecord> mapToVehicleMainInfo(List<VehicleDataResponse> vehicleDataResponses) {

        return vehicleDataResponses
                .stream()
                .map(car -> {

                    Map<String, Object> attributes = car.getAttributes();
                    return new VehicleMainRecord(
                            car.getId(),
                            (String) attributes.get("marka"),
                            (String) attributes.get("model"),
                            (String) attributes.get("data-pierwszej-rejestracji"),
                            (String) attributes.get("rok-produkcji")

                    );
                })
                .collect(Collectors.toList());
    }

    private int getIntValue(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return 0;
    }

    private double getDoubleValue(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        return 0.0;
    }

}
