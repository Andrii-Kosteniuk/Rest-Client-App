package dev.homework.restclientapp.dto.response;


import dev.homework.restclientapp.dto.response.allVehicles.VehicleDataResponse;
import dev.homework.restclientapp.dto.response.allVehicles.VehicleMainRecord;
import dev.homework.restclientapp.dto.response.cepik.CepikData;
import dev.homework.restclientapp.dto.response.singleVehicle.VehicleByIdRecords;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Component
public class CarDetailsMapper {

    public VehicleByIdRecords mapToVehicleDetails(CepikData<VehicleByIdRecords> cepikData) {

        VehicleByIdRecords attributes = cepikData.getAttributes();

        return new VehicleByIdRecords(
                attributes.getMark(),
                attributes.getModel(),
                attributes.getType(),
                attributes.getSubtype(),
                attributes.getOriginOfCar(),
                attributes.getDateOfManufacture(),
                attributes.getDateOfFirstRegistrationInPoland(),
                attributes.getDateOfLastRegistrationInPoland(),
                attributes.getEngineDisplacement(),
                attributes.getNetWeight(),
                attributes.getPermissibleGrossVehicleWeight(),
                attributes.getNumberOfAxles(),
                attributes.getRegistrationProvince(),
                attributes.getRegistrationDistrict(),
                attributes.getRegistrationCounty()
        );
    }

    public List<VehicleMainRecord> mapToVehicleMainInfo(List<VehicleDataResponse> vehicleDataResponses) {

        return vehicleDataResponses
                .stream()
                .map(response -> {

                    Map<String, Object> attributes = response.getAttributes();
                    return new VehicleMainRecord(
                            response.getId(),
                            (String) attributes.get("marka"),
                            (String) attributes.get("model"),
                            (String) attributes.get("data-pierwszej-rejestracji"),
                            (String) attributes.get("rok-produkcji")

                    );
                })
                .collect(Collectors.toList());
    }
}
