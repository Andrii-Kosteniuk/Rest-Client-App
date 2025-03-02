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
import com.vaadin.flow.router.Route;
import dev.homework.restclientapp.dto.request.VehicleRequest;
import dev.homework.restclientapp.dto.response.allVehicles.VehicleMainRecord;
import dev.homework.restclientapp.dto.response.singleVehicle.VehicleByIdRecords;
import dev.homework.restclientapp.service.ProvinceService;
import dev.homework.restclientapp.service.VehicleService;
import dev.homework.restclientapp.vaadin.layout.MainApplicationLayout;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.ResourceAccessException;

import java.lang.reflect.Field;
import java.util.List;

@Route(value = "/vehicles-search", layout = MainApplicationLayout.class)
public class SearchCarView extends Div {
    private final VehicleService vehicleService;
    private final ProvinceService cepikService;
    private final Grid<VehicleMainRecord> carGrid = new Grid<>(VehicleMainRecord.class);
    private final Select<String> selectProvince = new Select<>();
    private final DatePicker dateFrom = new DatePicker("CepikData od");
    private final DatePicker dateTo = new DatePicker("CepikData do");

    VehicleRequest vehicleRequest = new VehicleRequest();

    public SearchCarView(VehicleService vehicleService, ProvinceService provinceService) {
        this.vehicleService = vehicleService;
        this.cepikService = provinceService;

        Button submitButton = new Button("Search", e -> searchVehicles());
        FormLayout formLayout = new FormLayout();


        defineProvinces(provinceService);

        formLayout.add(selectProvince, dateFrom, dateTo, submitButton);
        formLayout.setColspan(submitButton, 3);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 3));


        showTableVehicleInformation();

        add(formLayout, carGrid);
    }

    private void defineProvinces(ProvinceService provinceService) {
        List<String> allProvinceNames = provinceService.getAllProvinceNames();
        selectProvince.setItems(allProvinceNames);
        selectProvince.setPlaceholder("Wybierz województwo...");
        selectProvince.setLabel("Województwo");
    }

    private void showTableVehicleInformation() {
        carGrid.setColumns("mark", "model", "dateOfFirstRegistration", "yearOfProduction");
        carGrid.addItemClickListener(event -> {
            VehicleMainRecord selectedCar = event.getItem();
            if (selectedCar != null) {
                showCarDetails(selectedCar.getId());
            }
        });
    }

    private void searchVehicles() {
        vehicleRequest.setProvinceName(cepikService.getProvinceKey(selectProvince.getValue()));
        vehicleRequest.setDateFrom(dateFrom.getValue() != null ? dateFrom.getValue().toString().replace("-", "") : null);
        vehicleRequest.setDateTo(dateTo.getValue() != null ? dateTo.getValue().toString().replace("-", "") : null);

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

}
