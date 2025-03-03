package dev.homework.restclientapp.service;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import dev.homework.restclientapp.dto.response.province.CepikResponse;
import dev.homework.restclientapp.dto.response.province.ProvinceRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
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

    private static void showNotificationErrorIfTimeOutExceptionOccur() {
        Notification notification = new Notification("Timeout exception occurred! Try to reload page, please:)", 5000);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.addClassNames();
        notification.open();
    }

    public List<String> getAllProvinceNames() {
        logger.info("Fetching province names from API: {}", BASE_URL + URI_PROVINCES);

        try {
            ResponseEntity<CepikResponse> response = restClient.get()
                    .uri(URI_PROVINCES)
                    .retrieve()
                    .toEntity(CepikResponse.class);

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

            UI.getCurrent().access(ProvinceService::showNotificationErrorIfTimeOutExceptionOccur);
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