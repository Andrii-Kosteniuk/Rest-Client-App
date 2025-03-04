package dev.homework.restclientapp.vaadin.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;
import dev.homework.restclientapp.dto.request.VehicleRequest;
import dev.homework.restclientapp.dto.response.general.VehicleMainRecord;
import dev.homework.restclientapp.dto.response.singleVehicle.VehicleByIdRecords;
import dev.homework.restclientapp.service.ProvinceService;
import dev.homework.restclientapp.service.VehicleService;
import dev.homework.restclientapp.util.DataValidation;
import dev.homework.restclientapp.vaadin.layout.MainApplicationLayout;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Route(value = "/vehicles-search", layout = MainApplicationLayout.class)
public class SearchCarView extends Div {
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final VehicleService vehicleService;
    private final ProvinceService cepikService;
    private final Grid<VehicleMainRecord> mainRecordGrid = new Grid<>(VehicleMainRecord.class);
    private final Select<String> selectProvince = new Select<>();
    private final DatePicker datePickerFrom = new DatePicker("Data od");
    private final DatePicker datePickerTo = new DatePicker("Data do");
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

        DataValidation.validateDates(datePickerFrom, datePickerTo);
        DataValidation.validateProvinceNameIsNotEmpty(selectProvince);

        add(formLayout, mainRecordGrid);
        showTableVehicleInformation();

    }

    private static void showValidationErrorNotification() {
        Notification errorValidationNotification = new Notification();
        errorValidationNotification.addThemeVariants(NotificationVariant.LUMO_WARNING);

        Div text = new Div(new Text("Please, check if you are providing a correct date!"));

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.setAriaLabel("Close");
        closeButton.addClickListener(event -> errorValidationNotification.close());

        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        errorValidationNotification.add(layout);
        errorValidationNotification.open();
    }

    private void submitForm() {
        if (datePickerFrom.getErrorMessage() == null && datePickerTo.getErrorMessage() == null) {
            fillFormFieldsForSearchVehicles();
            fetchDataFromApi();
        } else {
            showValidationErrorNotification();
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
        mainRecordGrid.setColumns("marka", "model", "dataPierwszejRejestracji", "rokProdukcji");

        mainRecordGrid.addItemClickListener(event -> {
            VehicleMainRecord selectedCar = event.getItem();
            if (selectedCar != null) {
                showCarDetails(selectedCar.getId());
            }
        });
    }

    private void fillFormFieldsForSearchVehicles() {
        vehicleRequest.setProvinceName(cepikService.getProvinceKey(selectProvince.getValue()));

        LocalDate dateFrom = datePickerFrom.getValue();
        LocalDate dateTo = datePickerTo.getValue();

        vehicleRequest.setDateFrom(DATE_FORMATTER.format(dateFrom));
        vehicleRequest.setDateTo(DATE_FORMATTER.format(dateTo));

    }

    private void fetchDataFromApi() {
        List<VehicleMainRecord> vehicleMainRecords = vehicleService.getVehicleMainInfo(vehicleRequest);
        mainRecordGrid.setItems(vehicleMainRecords);
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

}
