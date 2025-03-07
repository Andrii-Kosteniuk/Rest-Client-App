package dev.homework.restclientapp.service.api;

import com.vaadin.flow.component.UI;
import dev.homework.restclientapp.dto.DataMapper;
import dev.homework.restclientapp.dto.response.province.CepikResponse;
import dev.homework.restclientapp.vaadin.notification.ErrorAndExceptionNotification;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class ProvinceApiService {
    private static final Logger logger = LoggerFactory.getLogger(ProvinceApiService.class);
    public final String BASE_URL = "https://api.cepik.gov.pl";
    public final String URI_PROVINCES = "/slowniki/wojewodztwa";
    private final DataMapper dataMapper;
    private final RestClient restClient;
    private final ErrorAndExceptionNotification notification;


    public ProvinceApiService(DataMapper dataMapper, ErrorAndExceptionNotification notification) {
        this.dataMapper = dataMapper;
        this.notification = notification;
        restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }


    @CacheEvict(value = "provinces")
    public List<String> getAllProvinceNames() {
        logger.info("Fetching province names from API: {}", BASE_URL + URI_PROVINCES);
        ResponseEntity<CepikResponse> response = getCepikProvincesResponse();
        return dataMapper.mapToProvinceName(Objects.requireNonNull(response.getBody()));
    }

    @Retryable(retryFor = {ReadTimeoutException.class, IOException.class, ResourceAccessException.class},
            backoff = @Backoff(delay = 3000))
    private ResponseEntity<CepikResponse> getCepikProvincesResponse() {
        try {
            return restClient.get()
                    .uri(URI_PROVINCES)
                    .retrieve()
                    .toEntity(CepikResponse.class);
        } catch (TimeoutException | ResourceAccessException e) {
            logger.error("Error while fetching data from API", e);
            UI.getCurrent().access(notification::showNotificationErrorIfTimeOutExceptionOccur);
        }
        return ResponseEntity.noContent().build();
    }

}