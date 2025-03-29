package dev.homework.restclientapp.service.api;

import dev.homework.restclientapp.dto.response.province.CepikResponse;
import io.netty.handler.timeout.ReadTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ProvinceApiService {
    private static final Logger logger = LoggerFactory.getLogger(ProvinceApiService.class);
    public final String BASE_URL = "https://api.cepik.gov.pl/slowniki/wojewodztwa";
    public final String URI_PROVINCES = "";
    private final RestClient restClient;


    public ProvinceApiService() {
        restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }

    @Retryable(
            retryFor = {ReadTimeoutException.class},
            backoff = @Backoff(maxDelay = 5000)
    )
    public ResponseEntity<CepikResponse> getCepikProvincesResponse() {
        logger.info("Fetching API response from remote server...");
        try {
        return restClient.get()
                .uri(URI_PROVINCES)
                .retrieve()
                .toEntity(CepikResponse.class);
    }catch (ReadTimeoutException e) {
            logger.error("Error while fetching data from API", e);
           throw e;
        }
    }
}