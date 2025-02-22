package dev.homework.restclientapp.service;

import dev.homework.restclientapp.dto.responce.district.CepikResponse;
import dev.homework.restclientapp.dto.responce.district.DistrictRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class DistrictService {
    private static final Logger logger = LoggerFactory.getLogger(DistrictService.class);

    public final String BASE_URL = "https://api.cepik.gov.pl";
    public final String URI_DISTRICTS = "/slowniki/wojewodztwa";

    private final RestClient restClient;

    public DistrictService() {
        restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }

    public List<String> getAllDistrictNames() {
        logger.info("Fetching district names from API: {}", BASE_URL + URI_DISTRICTS);

        try {
            ResponseEntity<CepikResponse> response = restClient.get()
                    .uri(URI_DISTRICTS)
                    .retrieve()
                    .toEntity(CepikResponse.class);

            if (! Objects.isNull(response.getBody()) && ! Objects.isNull(response.getBody().getData())) {
                logger.info("Successfully retrieved data from API");
                return response.getBody().getData()
                        .getAttributes()
                        .getDistrictRecords()
                        .stream()
                        .map(DistrictRecord::getDistrictName)
                        .toList();
            } else {
                logger.warn("Received empty response from API");
            }

        } catch (RestClientException e) {
            logger.error("Error while fetching data from API", e);
        }
        return Collections.emptyList();
    }

    public String getDistrictKey(String districtName) {
        return switch (districtName) {
            case "DOLNOŚLĄSKIE" -> "02";
            case "KUJAWSKO-POMORSKIE" -> "04";
            case "LUBELSKIE" -> "06";
            case "LUBUSKIE" -> "08";
            case "ŁÓDZKIE" -> "10";
            case "MAŁOPOLSKIE" -> "12";
            case "MAZOWIECKIE" -> "14";
            case "OPOLSKIE" -> "16";
            case "PODKARPACKIE" -> "18";
            case "PODLASKIE" -> "20";
            case "POMORSKIE" -> "22";
            case "ŚLĄSKIE" -> "24";
            case "ŚWIĘTOKRZYSKIE" -> "26";
            case "WARMIŃSKO-MAZURSKIE" -> "28";
            case "WIELKOPOLSKIE" -> "30";
            case "ZACHODNIOPOMORSKIE" -> "32";
            default -> "XX";
        };
    }
}