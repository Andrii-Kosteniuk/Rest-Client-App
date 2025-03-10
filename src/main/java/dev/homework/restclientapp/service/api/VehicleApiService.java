package dev.homework.restclientapp.service.api;

import dev.homework.restclientapp.dto.DataMapper;
import dev.homework.restclientapp.dto.request.VehicleRequest;
import dev.homework.restclientapp.dto.response.general.VehicleDataResponse;
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
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;

@Service
public class VehicleApiService {
    public final static String BASE_URL = "https://api.cepik.gov.pl/pojazdy";
    public final static String CEPIK_API_VEHICLES_URL = "?wojewodztwo=%s&data-od=%s&data-do=%s&page=%d&limit=%d&tylko-zarejestrowane=%S";
    private static final Logger logger = LoggerFactory.getLogger(VehicleApiService.class);
    private final RestClient restClient;
    private final DataMapper dataMapper;

    public VehicleApiService(DataMapper dataMapper) {
        this.dataMapper = dataMapper;

        restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }

    public List<VehicleMainRecord> getVehiclesData(VehicleRequest vehicleRequest) {

        final String uri = String.format(BASE_URL + CEPIK_API_VEHICLES_URL,
                vehicleRequest.getProvinceName(),
                vehicleRequest.getDateFrom(),
                vehicleRequest.getDateTo(),
                vehicleRequest.getPage(),
                vehicleRequest.getLimit(),
                vehicleRequest.isRegistered());

        logger.info("Fetching vehicles from API: {}{}", BASE_URL, uri);

        List<VehicleDataResponse> data = Objects.requireNonNull(restClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<VehicleResponse>() {
                }).getBody()).getData();

        return dataMapper.mapToVehicleMainInfo(data);

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