package dev.homework.restclientapp.vaadin.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import dev.homework.restclientapp.dto.request.VehicleRequest;
import dev.homework.restclientapp.dto.response.general.VehicleMainRecord;
import dev.homework.restclientapp.dto.response.singleVehicle.VehicleByIdRecords;
import dev.homework.restclientapp.service.ProvinceService;
import dev.homework.restclientapp.service.VehicleService;
import dev.homework.restclientapp.vaadin.layout.MainApplicationLayout;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Route(value = "/vehicles-search", layout = MainApplicationLayout.class)
public class SearchCarView extends Div {
    private final VehicleService vehicleService;
    private final ProvinceService cepikService;
    private final Grid<VehicleMainRecord> carGrid = new Grid<>(VehicleMainRecord.class);
    private final Select<String> selectProvince = new Select<>();
    private final DatePicker datePickerFrom = new DatePicker("Data od");
    private final DatePicker datePickerTo = new DatePicker("Data do");
    private final Binder<VehicleRequest> binder = new Binder<>(VehicleRequest.class);


    VehicleRequest vehicleRequest = new VehicleRequest();

    public SearchCarView(VehicleService vehicleService, ProvinceService provinceService) {
        this.vehicleService = vehicleService;
        this.cepikService = provinceService;

        Button submitButton = new Button("Szukaj...", e -> submitForm());
        FormLayout formLayout = new FormLayout();

        defineProvinces(provinceService);

        formLayout.add(selectProvince, datePickerFrom, datePickerTo, submitButton);
        formLayout.setColspan(submitButton, 3);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 3));


        add(formLayout, carGrid);
        showTableVehicleInformation();

        configureValidation();
    }

    private void submitForm() {
        if (binder.validate().isOk()) {
            fillFormFieldsForSearchVehicles();

        }
    }

    private void defineProvinces(ProvinceService provinceService) {
        List<String> allProvinceNames = provinceService.getAllProvinceNames();

        if (allProvinceNames == null) {
            allProvinceNames = List.of("Brak danych");
        }

        selectProvince.setItems(allProvinceNames);
        selectProvince.setPlaceholder("Wybierz województwo...");
        selectProvince.setLabel("Województwo");
    }

    private void showTableVehicleInformation() {
        carGrid.setColumns("marka", "model", "dataPierwszejRejestracji", "rokProdukcji");
        carGrid.addItemClickListener(event -> {
            VehicleMainRecord selectedCar = event.getItem();
            if (selectedCar != null) {
                showCarDetails(selectedCar.getId());
            }
        });
    }

    private void fillFormFieldsForSearchVehicles() {
        vehicleRequest.setProvinceName(cepikService.getProvinceKey(selectProvince.getValue()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        String dateFrom = datePickerFrom.getValue().format(formatter);
        String dateTo = datePickerTo.getValue().format(formatter);

        vehicleRequest.setDateFrom(dateFrom);
        vehicleRequest.setDateTo(dateTo);

        List<VehicleMainRecord> vehicleMainRecords = vehicleService.getVehicleMainInfo(vehicleRequest);
        carGrid.setItems(vehicleMainRecords);
    }

    private void showCarDetails(String vehicleId) {

        VehicleByIdRecords carDetails = vehicleService.getCarDetails(vehicleId);

        Dialog detailsDialog = new Dialog();
        detailsDialog.setHeaderTitle("Vehicle Details");

        VerticalLayout infoLayout = new VerticalLayout();

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

        detailsDialog.add(infoLayout);

        Button closeButton = new Button("Close", e -> detailsDialog.close());
        detailsDialog.getFooter().add(closeButton);

        detailsDialog.open();
    }

    private void configureValidation() {
        binder.forField(selectProvince)
                .asRequired("Wybierz województwo!")
                .bind(VehicleRequest::getProvinceName, VehicleRequest::setProvinceName);

        binder.forField(datePickerFrom)
                .asRequired("Wybierz datę początkową!")
                .withConverter(LocalDate::toString, LocalDate::parse)
                .bind(VehicleRequest::getDateFrom, VehicleRequest::setDateFrom);

        binder.forField(datePickerTo)
                .asRequired("Wybierz datę końcową!")
                .withValidator(date -> {
                    LocalDate fromDate = datePickerFrom.getValue();
                    return fromDate == null || date == null || ! fromDate.isAfter(date);
                }, "Data od nie może być późniejsza niż data do!")
                .withConverter(LocalDate::toString, LocalDate::parse)
                .bind(VehicleRequest::getDateTo, VehicleRequest::setDateTo);

        binder.forField(datePickerTo)
                .withValidator(date -> date == null || ! date.isAfter(LocalDate.now()),
                        "Data nie może być późniejsza niż dzisiejsza data!")
                .withConverter(LocalDate::toString, LocalDate::parse)
                .bind(VehicleRequest::getDateTo, VehicleRequest::setDateTo);
    }
}
