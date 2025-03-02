package dev.homework.restclientapp.service;

import dev.homework.restclientapp.dto.response.cepik.CepikData;
import dev.homework.restclientapp.dto.response.cepik.CepikResponse;
import dev.homework.restclientapp.dto.response.province.ProvinceAttribute;
import dev.homework.restclientapp.dto.response.province.ProvinceRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class ProvinceService {
    private static final Logger logger = LoggerFactory.getLogger(ProvinceService.class);

    public final String BASE_URL = "https://api.cepik.gov.pl";
    public final String URI_PROVINCES = "/slowniki/wojewodztwa";

    private final RestClient restClient;

    public ProvinceService() {
        restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }

    @Retryable(retryFor = ResourceAccessException.class, maxAttempts = 2)
    public List<String> getAllProvinceNames() {
        logger.info("Fetching province names from API: {}", BASE_URL + URI_PROVINCES);

        try {
            ResponseEntity<CepikResponse<CepikData<ProvinceAttribute>>> response = restClient.get()
                    .uri(URI_PROVINCES)
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<>() {
                    });

            if (! Objects.isNull(response.getBody()) && ! Objects.isNull(response.getBody().getData())) {
                return response.getBody().getData()
                        .getAttributes()
                        .getProvinceRecords()
                        .stream()
                        .map(ProvinceRecord::getProvinceName)
                        .toList();
            } else {
                logger.warn("Received empty response from API");
            }

        } catch (ResourceAccessException e) {
            logger.error("Error while fetching data from API", e);
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }

    public String getProvinceKey(String ProvinceName) {
        return switch (ProvinceName) {
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