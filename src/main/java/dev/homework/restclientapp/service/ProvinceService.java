package dev.homework.restclientapp.service;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.server.Command;
import dev.homework.restclientapp.dto.DataMapper;
import dev.homework.restclientapp.dto.response.province.CepikResponse;
import dev.homework.restclientapp.service.api.ProvinceApiService;
import dev.homework.restclientapp.vaadin.notification.ErrorAndExceptionNotification;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProvinceService {

    private final ErrorAndExceptionNotification errorAndExceptionNotification;
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


    @CachePut(value = "provinces")
    public Map<String, String> getAllProvinceNamesWithKeys() {
        ResponseEntity<CepikResponse> response;
        try {
            response = provinceApiService.getCepikProvincesResponse();
            return dataMapper.mapToProvinceRecord(Objects.requireNonNull(response.getBody()));
        } catch (ConnectException e) {
            logger.error("Error while fetching data from API", e);
            UI.getCurrent().access((Command) () -> errorAndExceptionNotification.showNotificationErrorIfTimeOutExceptionOccur(e));
        }
       return Collections.emptyMap();
    }

    @Cacheable("provinces")
    public void populateProvinceNameToSelect(Select<String> selectProvince) {
        selectProvince.setItems(getAllProvinceNamesWithKeys().keySet());
    }

}
