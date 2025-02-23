package dev.homework.restclientapp.vaadin.layout;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import dev.homework.restclientapp.dto.request.SearchVehiclesRequest;
import dev.homework.restclientapp.dto.responce.car.Vehicle;
import dev.homework.restclientapp.service.VehicleService;
import dev.homework.restclientapp.service.ProvinceService;
import org.springframework.stereotype.Component;

import java.util.List;

@Route("/vehicles-search")
@PageTitle("Vehicle Filter Form")
@UIScope
@Component
public class FormLayoutSearchCar extends Div {
    private final VehicleService vehicleService;
    private final ProvinceService cepikService;

    private final DatePicker dateFrom = new DatePicker("Data od");
    private final DatePicker dateTo = new DatePicker("Data do");
    private final Grid<Vehicle> carGrid = new Grid<>(Vehicle.class);
    Select<String> selectWojewodztwo = new Select<>();

    public FormLayoutSearchCar(VehicleService vehicleService, ProvinceService provinceService) {
        this.vehicleService = vehicleService;
        this.cepikService = provinceService;

        Button home = new Button("Go home", event -> getUI().ifPresent(ui -> ui.navigate("")));
        Button submitButton = new Button("Search", e -> searchVehicles());
        FormLayout formLayout = new FormLayout();

        selectWojewodztwo.setPlaceholder("Wybierz województwo...");
        selectWojewodztwo.setLabel("Województwo");

        List<String> allProvinceNames = provinceService.getAllProvinceNames();


        selectWojewodztwo.setItems(allProvinceNames);

        formLayout.add(selectWojewodztwo, dateFrom, dateTo, submitButton);
        formLayout.setColspan(submitButton, 3);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 3));


        carGrid.setColumns("mark", "model", "dateOfFirstRegistration", "yearOfProduction");

        add(home, formLayout, carGrid);
    }

    private void searchVehicles() {
        SearchVehiclesRequest searchVehiclesRequest = new SearchVehiclesRequest();
        searchVehiclesRequest.setProvinceName(cepikService.getProvinceKey(selectWojewodztwo.getValue()));
        searchVehiclesRequest.setDateFrom(dateFrom.getValue() != null ? dateFrom.getValue().toString().replace("-", "") : null);
        searchVehiclesRequest.setDateTo(dateTo.getValue() != null ? dateTo.getValue().toString().replace("-", "") : null);

        List<Vehicle> vehicles = vehicleService.getCarByModel(searchVehiclesRequest);
        carGrid.setItems(vehicles);
    }


}
