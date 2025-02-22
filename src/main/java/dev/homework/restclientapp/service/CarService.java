package dev.homework.restclientapp.service;

import dev.homework.restclientapp.dto.request.CarSearchRequestDto;
import dev.homework.restclientapp.dto.responce.car.Car;
import dev.homework.restclientapp.dto.responce.car.CarDataResponse;
import dev.homework.restclientapp.dto.responce.car.CarResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class CarService {
    private static final Logger logger = LoggerFactory.getLogger(CarService.class);
    public final String BASE_URL = "https://api.cepik.gov.pl";
    public final String URI_CARS = "/pojazdy?wojewodztwo=%s&data-od=%s&data-do=%s";
    private final RestClient restClient;

    public CarService() {
        restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }

    public List<CarDataResponse> getCarsData(CarSearchRequestDto carRequestDto) {
        logger.info("Fetching vehicles from API: {}", BASE_URL + URI_CARS);
        String uri = String.format(URI_CARS,
                carRequestDto.getWojewodztwo(), carRequestDto.getDataOd(), carRequestDto.getDataDo());

        return Objects.requireNonNull(restClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<CarResponse>() {
                }).getBody()).getData();

    }

    public List<Car> getCarByModel(CarSearchRequestDto carRequestDto) {

        List<CarDataResponse> carsData = getCarsData(carRequestDto);

        return carsData
                .stream()
                .map(car -> {

                    Map<String, Object> attributes = car.getAttributes();
                    return new Car(
                            car.getId(),
                            (String) attributes.get("marka"),
                            (String) attributes.get("model"),
                            (String) attributes.get("data-pierwszej-rejestracji"),
                            (String) attributes.get("rok-produkcji")

                    );
                })
                .collect(Collectors.toList());
    }
}