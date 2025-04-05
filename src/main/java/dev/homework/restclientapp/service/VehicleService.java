package dev.homework.restclientapp.service;

import com.vaadin.flow.component.UI;
import dev.homework.restclientapp.dto.request.VehicleRequest;
import dev.homework.restclientapp.dto.response.general.VehicleMainRecord;
import dev.homework.restclientapp.service.api.VehicleApiService;
import dev.homework.restclientapp.vaadin.layout.FilterLayout;
import dev.homework.restclientapp.vaadin.layout.SearchFormLayout;
import dev.homework.restclientapp.validation.DataValidation;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class VehicleService {

    private final VehicleRequest vehicleRequest;
    private final VehicleApiService vehicleAPIService;
    private final DataValidation dataValidation;
    private final ProvinceService provinceService;
    private Logger logger = LoggerFactory.getLogger(VehicleService.class);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private List<VehicleMainRecord> cachedData;
    private int pageSize = 100;
    private int pageNo = 1;

    public VehicleService(VehicleRequest vehicleRequest, VehicleApiService vehicleAPIService, DataValidation dataValidation, ProvinceService provinceService) {
        this.vehicleRequest = vehicleRequest;
        this.vehicleAPIService = vehicleAPIService;
        this.dataValidation = dataValidation;
        this.provinceService = provinceService;
        this.cachedData = new ArrayList<>();
    }

    public List<VehicleMainRecord> fetchDataFromApi() {
        logger.info("Fetching data from central API...");
       return vehicleAPIService.getVehiclesData(vehicleRequest);
    }

    public void submitForm() {
        cachedData.clear();
        cachedData = fetchDataFromApi();

        logger.info("Form  submitted and data is passed forward to VehicleRequest DTO");
        UI.getCurrent().navigate(FilterLayout.class);
    }

    public void fillFormFieldsForSearchVehicles(SearchFormLayout form) {
        vehicleRequest.setProvinceName(provinceService.getProvinceKey(form.getSelectProvince().getValue()));
        vehicleRequest.setDateFrom(formatDate(form.getDatePickerFrom().getValue()));
        vehicleRequest.setDateTo(formatDate(form.getDatePickerTo().getValue()));
        vehicleRequest.setTypeOfDate(form.getTypeOfDate(form.getTypeOfDateComboBox().getValue()));
        vehicleRequest.setRegistered(form.getRegisteredCheckBox().getValue());
        vehicleRequest.setShowAllFields(form.getAllFieldsCheckBox().getValue());
        vehicleRequest.setPageNo(String.valueOf(pageNo));
        vehicleRequest.setLimit(String.valueOf(pageSize));
        vehicleRequest.setShowAllFields(form.getAllFieldsCheckBox().getValue());

        logger.info("Province name: {} --- Date from is: {} --- Date to is: {} --- Type of date is {}", vehicleRequest.getProvinceName(), vehicleRequest.getDateFrom(), vehicleRequest.getDateTo(), vehicleRequest.getTypeOfDate());

    }

    private String formatDate(LocalDate date) {
        return formatter.format(date);
    }

    public void updatePageSize(int newPageSize) {
        this.pageSize = newPageSize;
        vehicleRequest.setLimit(String.valueOf(newPageSize));
        logger.info("Updated page size to: {} and fetched new data", newPageSize);
        submitForm();

        UI.getCurrent().access(() -> UI.getCurrent().getPage().reload());

    }

    public void navigateToNextPage() {
        pageNo++;
        vehicleRequest.setPageNo(String.valueOf(pageNo));
        submitForm();
        UI.getCurrent().access(() -> UI.getCurrent().getPage().reload());

    }

    public void navigateToPreviousPage() {
        if (pageNo > 1) {
            pageNo--;
            vehicleRequest.setPageNo(String.valueOf(pageNo));
            submitForm();
            UI.getCurrent().access(() -> UI.getCurrent().getPage().reload());
        }

    }

}
