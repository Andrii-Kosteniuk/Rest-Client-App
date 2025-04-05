package dev.homework.restclientapp.service.api;

import dev.homework.restclientapp.dto.DataMapper;
import dev.homework.restclientapp.dto.request.VehicleRequest;
import dev.homework.restclientapp.dto.response.general.VehicleMainRecord;
import dev.homework.restclientapp.dto.response.general.VehicleResponse;
import dev.homework.restclientapp.dto.response.singleVehicle.VehicleByIdRecord;
import dev.homework.restclientapp.dto.response.singleVehicle.VehicleByIdResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;

@Service
public class VehicleApiService {
    public final static String BASE_URL = "https://api.cepik.gov.pl/pojazdy";
    public final static String CEPIK_API_VEHICLES_URL =
            "?wojewodztwo=%s" +
            "&data-od=%s" +
            "&data-do=%s" +
            "&typ-daty=%s" +
            "&tylko-zarejestrowane=%s" +
            "&pokaz-wszystkie-pola=%s" +
            "&limit=%s" +
            "&page=%s";
    private static final Logger logger = LoggerFactory.getLogger(VehicleApiService.class);
    private final RestClient restClient;
    private final DataMapper dataMapper;

    public VehicleApiService(DataMapper dataMapper ) {
        this.dataMapper = dataMapper;

        restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }

    @CacheEvict(value = "vehicles", key = "#vehicleRequest.dateFrom")
    public List<VehicleMainRecord> getVehiclesData(VehicleRequest vehicleRequest) {

        final String uri = String.format(CEPIK_API_VEHICLES_URL,
                vehicleRequest.getProvinceName(),
                vehicleRequest.getDateFrom(),
                vehicleRequest.getDateTo(),
                vehicleRequest.getTypeOfDate(),
                vehicleRequest.isRegistered(),
                vehicleRequest.isShowAllFields(),
                vehicleRequest.getLimit(),
                vehicleRequest.getPageNo()
        );

        logger.info("Fetching vehicles from API: {}{}", BASE_URL, uri);

        try {
            ResponseEntity<VehicleResponse> response = restClient.get()
                    .uri(BASE_URL + uri)
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<>() {});

            if (response.getBody() == null || response.getBody().getData() == null) {
                logger.error("Received null response from API");
                return List.of();
            }

            return dataMapper.mapToVehicleMainInfo(response.getBody().getData());

        } catch (ResourceAccessException e) {
            logger.error("Error accessing API: {}{}", BASE_URL, uri, e);
            return List.of();
        } catch (Exception e) {
            logger.error("Unexpected error while fetching data: {}", e.getMessage(), e);
            return List.of();
        }

    }


    @CacheEvict(value = "vehicleDetails", key = "#id")
    public VehicleByIdRecord getCarDetails(String id) {
        final String URI_VEHICLE_BY_ID = "/%s".formatted(id);

        logger.info("Fetching vehicle by ID: {} from API: {}", id, BASE_URL + URI_VEHICLE_BY_ID);

        ResponseEntity<VehicleByIdResponse> response = restClient.get()
                .uri(URI_VEHICLE_BY_ID)
                .retrieve()
                .toEntity(VehicleByIdResponse.class);

        logger.info("Successfully retrieved data from API: {}", BASE_URL + URI_VEHICLE_BY_ID);

        return dataMapper.mapToVehicleDetails(Objects.requireNonNull(response.getBody()));

    }

}