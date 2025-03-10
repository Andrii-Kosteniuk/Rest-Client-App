package dev.homework.restclientapp.service;

import dev.homework.restclientapp.dto.request.VehicleRequest;
import dev.homework.restclientapp.dto.response.general.VehicleMainRecord;
import dev.homework.restclientapp.service.api.VehicleApiService;
import dev.homework.restclientapp.validation.DataValidation;
import dev.homework.restclientapp.vaadin.notification.FormValidationNotification;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static dev.homework.restclientapp.vaadin.view.SearchCarView.*;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private static final Logger logger = LoggerFactory.getLogger(VehicleService.class);
    private final VehicleRequest vehicleRequest;
    private final VehicleApiService vehicleAPIService;
    private final DataValidation dataValidation;
    private final ProvinceService provinceService;
    private final FormValidationNotification formValidationNotification;
    @Getter
    private List<VehicleMainRecord> cachedData = new ArrayList<>();
    @Getter
    @Setter
    private int currentPage = 1;
    @Getter
    @Setter
    private int pageSize = 100;

    @CacheEvict(value = "vehicles")
    public List<VehicleMainRecord> fetchDataFromApi() {
        logger.info("Fetching data from central API...");
        return vehicleAPIService.getVehiclesData(vehicleRequest);
    }


    public void submitForm() {
        if (dataValidation.binder.isValid()) {
            fillFormFieldsForSearchVehicles();
            logger.info("Form  submitted and data is passed forward to VehicleRequest DTO");
            this.cachedData = fetchDataFromApi();
        } else {
            formValidationNotification.showInvalidDateNotification();
            logger.error("Form Validation Error");
        }
    }

    public void fillFormFieldsForSearchVehicles() {
        vehicleRequest.setProvinceName(provinceService.getProvinceKey(selectProvince.getValue()));

        LocalDate dateFrom = datePickerFrom.getValue();
        LocalDate dateTo = datePickerTo.getValue();

        vehicleRequest.setDateFrom(DATE_FORMATTER.format(dateFrom));
        vehicleRequest.setDateTo(DATE_FORMATTER.format(dateTo));

        boolean registeredCheckBoxValue = registeredCheckBox.getValue();
        vehicleRequest.setRegistered(registeredCheckBoxValue);

        vehicleRequest.setPage(currentPage);
        vehicleRequest.setLimit(pageSize);

    }

}
