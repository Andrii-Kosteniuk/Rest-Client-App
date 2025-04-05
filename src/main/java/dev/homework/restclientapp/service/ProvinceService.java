package dev.homework.restclientapp.service;

import com.vaadin.flow.component.select.Select;
import dev.homework.restclientapp.dto.DataMapper;
import dev.homework.restclientapp.dto.response.province.CepikResponse;
import dev.homework.restclientapp.service.api.ProvinceApiService;
import dev.homework.restclientapp.vaadin.notification.ErrorAndExceptionNotification;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProvinceService {

    Logger logger = LoggerFactory.getLogger(ProvinceService.class);

    private final ProvinceApiService provinceApiService;
    private final DataMapper dataMapper;

    @Cacheable(value = "provinceKeys", key = "#provinceName")
    public String getProvinceKey(String provinceName) {
        Map<String, String> allProvinceNamesWithKeys = getAllProvinceNamesWithKeys();
        logger.info("Retrieving provinceKey.... ");
        return allProvinceNamesWithKeys.entrySet()
                .stream()
                .filter(key -> key.getKey().equals(provinceName))
                .map(Map.Entry::getValue)
                .findFirst().orElse("XX");
    }

    public Map<String, String> getAllProvinceNamesWithKeys() {
        ResponseEntity<CepikResponse> response;
        try {
            response = provinceApiService.getCepikProvincesResponse();
            return dataMapper.mapToProvinceRecord(Objects.requireNonNull(response.getBody()));
        } catch (ReadTimeoutException e) {
            logger.error("Timeout error while fetching data from API", e);
            ErrorAndExceptionNotification.showNotificationException(e);
        } catch (Exception e) {
            logger.error("Unexpected error while fetching data from API", e);
            ErrorAndExceptionNotification.showNotificationException(e);
        }
        return Collections.emptyMap();
    }

    public void populateProvinceNameToSelect(Select<String> selectProvince) {
        selectProvince.setItems(getAllProvinceNamesWithKeys().keySet());
    }

}
