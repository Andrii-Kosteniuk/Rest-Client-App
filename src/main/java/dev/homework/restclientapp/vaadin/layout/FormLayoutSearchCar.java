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
import dev.homework.restclientapp.dto.request.CarSearchRequestDto;
import dev.homework.restclientapp.dto.responce.car.Car;
import dev.homework.restclientapp.service.CarService;
import dev.homework.restclientapp.service.DistrictService;
import org.springframework.stereotype.Component;

import java.util.List;

@Route("/car-search")
@PageTitle("Car Filter Form")
@UIScope
@Component
public class FormLayoutSearchCar extends Div {
    private final CarService carService;
    private final DistrictService cepikService;

    private final DatePicker dataOd = new DatePicker("Data od");
    private final DatePicker dataDo = new DatePicker("Data do");
    private final Grid<Car> carGrid = new Grid<>(Car.class);
    Select<String> selectWojewodztwo = new Select<>();

    public FormLayoutSearchCar(CarService carService, DistrictService cepikService) {
        this.carService = carService;
        this.cepikService = cepikService;

        Button home = new Button("Go home", event -> getUI().ifPresent(ui -> ui.navigate("/home")));
        Button submitButton = new Button("Search", e -> searchCars());
        FormLayout formLayout = new FormLayout();

        selectWojewodztwo.setPlaceholder("Wybierz województwo");

        List<String> allDistrictNames = cepikService.getAllDistrictNames();


        selectWojewodztwo.setItems(allDistrictNames);

        formLayout.add(selectWojewodztwo, dataOd, dataDo, submitButton);
        formLayout.setColspan(submitButton, 3);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 3));


        carGrid.setColumns("marka", "model", "dataPierwszejRejestracji", "rokProdukcji");

        add(home, formLayout, carGrid);
    }

    private void searchCars() {
        CarSearchRequestDto carSearchRequestDto = new CarSearchRequestDto();
        carSearchRequestDto.setWojewodztwo(cepikService.getDistrictKey(selectWojewodztwo.getValue()));
        carSearchRequestDto.setDataOd(dataOd.getValue() != null ? dataOd.getValue().toString().replace("-", "") : null);
        carSearchRequestDto.setDataDo(dataDo.getValue() != null ? dataDo.getValue().toString().replace("-", "") : null);

        List<Car> cars = carService.getCarByModel(carSearchRequestDto);
        carGrid.setItems(cars);
    }


}
