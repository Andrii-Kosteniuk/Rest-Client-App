package dev.homework.restclientapp.service;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.theme.lumo.LumoUtility;
import dev.homework.restclientapp.dto.request.VehicleRequest;
import dev.homework.restclientapp.dto.response.general.VehicleMainRecord;
import dev.homework.restclientapp.dto.response.singleVehicle.VehicleByIdRecord;
import dev.homework.restclientapp.service.api.ProvinceApiService;
import dev.homework.restclientapp.service.api.VehicleApiService;
import dev.homework.restclientapp.util.DataValidation;
import dev.homework.restclientapp.vaadin.notification.FormValidationNotification;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
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

    private boolean isFetchingDetails = false;

    private static void printFieldsAndInformation(VehicleByIdRecord carDetails, VerticalLayout infoLayout) {
        Field[] fields = carDetails.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(carDetails);
                if (value != null) {
                    infoLayout.add(new Span(field.getName() + ": " + value));
                }
            } catch (IllegalAccessException e) {
                e.getCause();
            }
        }
    }

    private void fetchDataFromApi() {
        Boolean registeredCheckBoxValue = registeredCheckBox.getValue();
        vehicleRequest.setRegistered(registeredCheckBoxValue);
        logger.debug("Fetching registered vehicles {}", registeredCheckBoxValue);

        List<VehicleMainRecord> vehicleMainRecords = vehicleAPIService.getVehicleMainInfo(vehicleRequest);
        mainRecordGrid.setItems(vehicleMainRecords);
        logger.info("Fetching data from API and setting items to main records");
    }

    public void submitForm() {
        if (dataValidation.binder.isValid()) {
            fillFormFieldsForSearchVehicles();
            fetchDataFromApi();
            logger.info("Form  submitted");
        } else {
            formValidationNotification.showInvalidDateNotification();
            logger.error("Form Validation Error");
        }
    }

    private void fillFormFieldsForSearchVehicles() {
        vehicleRequest.setProvinceName(provinceService.getProvinceKey(selectProvince.getValue()));

        LocalDate dateFrom = datePickerFrom.getValue();
        LocalDate dateTo = datePickerTo.getValue();

        vehicleRequest.setDateFrom(DATE_FORMATTER.format(dateFrom));
        vehicleRequest.setDateTo(DATE_FORMATTER.format(dateTo));
    }

    public void showTableVehicleInformation() {
        mainRecordGrid.setColumns("marka", "model", "dataPierwszejRejestracji", "rokProdukcji");
        logger.info("Showing table vehicle information");

        mainRecordGrid.addItemClickListener(event -> {
            VehicleMainRecord selectedCar = event.getItem();
            if (selectedCar != null) {
                showCarDetails(selectedCar.getId());
                logger.info("Selected car {}:", selectedCar);
            }
        });
    }

    public void defineProvinces(ProvinceApiService provinceApiService) {
        logger.info("Trying to get provinces");
        List<String> allProvinceNames = provinceApiService.getAllProvinceNames();
            selectProvince.setItems(allProvinceNames);
            logger.info("Provinces were retrieved successfully");
    }

    private void showCarDetails(String vehicleId) {
        if (isFetchingDetails) {
            return;
        }
        isFetchingDetails = true;

        VehicleByIdRecord vehicleDetails = vehicleAPIService.getCarDetails(vehicleId);

        Dialog detailsDialog = new Dialog();
        detailsDialog.setHeaderTitle("Vehicle details");
        detailsDialog.setWidth("600px");

        Div detailsContainer = new Div();
        detailsContainer.getStyle()
                .setDisplay(Style.Display.GRID)
                .setDisplay(Style.Display.TABLE_COLUMN).set("gap", "10px")
                .setPadding("10px");

        VerticalLayout infoLayout = new VerticalLayout();
        infoLayout.getStyle().setBoxShadow(LumoUtility.BoxShadow.MEDIUM);
        printFieldsAndInformation(vehicleDetails, infoLayout);

        detailsDialog.add(infoLayout);

        Button closeButton = new Button("Close", e -> {
            detailsDialog.close();
            isFetchingDetails = false;
        });

        detailsDialog.getFooter().add(closeButton);

        detailsDialog.open();
    }
}
