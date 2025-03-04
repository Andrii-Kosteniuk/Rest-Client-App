package dev.homework.restclientapp.service;

import dev.homework.restclientapp.dto.request.VehicleRequest;
import dev.homework.restclientapp.dto.response.CarDetailsMapper;
import dev.homework.restclientapp.dto.response.general.VehicleDataResponse;
import dev.homework.restclientapp.dto.response.general.VehicleMainRecord;
import dev.homework.restclientapp.dto.response.general.VehicleResponse;
import dev.homework.restclientapp.dto.response.singleVehicle.VehicleByIdRecords;
import dev.homework.restclientapp.dto.response.singleVehicle.VehicleByIdResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;

@Service
public class VehicleService {
    private static final Logger logger = LoggerFactory.getLogger(VehicleService.class);
    public final String BASE_URL = "https://api.cepik.gov.pl";
    public final String URI_VEHICLES = "/pojazdy?wojewodztwo=%s&data-od=%s&data-do=%s";
    private final RestClient restClient;
    private final CarDetailsMapper carDetailsMapper;

    public VehicleService(CarDetailsMapper carDetailsMapper) {
        this.carDetailsMapper = carDetailsMapper;

        restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }

    @Retryable(retryFor = ResourceAccessException.class, maxAttempts = 2)
    public List<VehicleDataResponse> getVehiclesData(VehicleRequest carRequestDto) {
        logger.info("Fetching vehicles from API: {}", BASE_URL + URI_VEHICLES);

        String uri = String.format(URI_VEHICLES,
                carRequestDto.getProvinceName(), carRequestDto.getDateFrom(), carRequestDto.getDateTo());

        return Objects.requireNonNull(restClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<VehicleResponse>() {
                }).getBody()).getData();

    }

    public List<VehicleMainRecord> getVehicleMainInfo(VehicleRequest vehicleRequest) {

        List<VehicleDataResponse> vehiclesData = getVehiclesData(vehicleRequest);
        logger.info("Fetching vehicles from API and setting them to VehicleMainRecord object");

        return carDetailsMapper.mapToVehicleMainInfo(vehiclesData);
    }

    @Retryable(retryFor = ResourceAccessException.class, maxAttempts = 2)
    public VehicleByIdRecords getCarDetails(String id) {
        final String URI_VEHICLE_BY_ID = "/pojazdy/%s".formatted(id);
        logger.info("Fetching vehicle by ID: {} from API: {}", id, BASE_URL + URI_VEHICLE_BY_ID);

        ResponseEntity<VehicleByIdResponse> response = restClient.get()
                .uri(URI_VEHICLE_BY_ID)
                .retrieve()
                .toEntity(VehicleByIdResponse.class);

        logger.info("Successfully retrieved data from API: {}", BASE_URL + URI_VEHICLE_BY_ID);

        return carDetailsMapper.mapToVehicleDetails(Objects.requireNonNull(response.getBody()));

    }
}