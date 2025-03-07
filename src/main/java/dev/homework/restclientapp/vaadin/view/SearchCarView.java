package dev.homework.restclientapp.vaadin.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;
import dev.homework.restclientapp.dto.response.general.VehicleMainRecord;
import dev.homework.restclientapp.service.VehicleService;
import dev.homework.restclientapp.service.api.ProvinceApiService;
import dev.homework.restclientapp.util.DataValidation;
import dev.homework.restclientapp.vaadin.layout.MainApplicationLayout;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Route(value = "/vehicles-search", layout = MainApplicationLayout.class)
public class SearchCarView extends Div {


    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static Select<String> selectProvince = new Select<>();
    public static DatePicker datePickerFrom = new DatePicker("Data od");
    public static DatePicker datePickerTo = new DatePicker("Data do", LocalDate.now());
    public static Grid<VehicleMainRecord> mainRecordGrid = new Grid<>(VehicleMainRecord.class);
    public static Checkbox registeredCheckBox = new Checkbox();

    public SearchCarView(ProvinceApiService provinceApiService, DataValidation dataValidation, VehicleService vehicleService) {
        FormLayout formLayout = new FormLayout();

        selectProvince.setLabel("Województwo");
        selectProvince.setPlaceholder("Wybierz województwo...");

        datePickerFrom.setTooltipText("Określa datę początkową okresu pierwszej lub ostatniej rejestracji w kraju");
        datePickerTo.setTooltipText("Określa koniec okresu, dla którego pojazdy mają być zwrócone.\n" +
                                    "Domyślną wartością jest data bieżąca");

        registeredCheckBox.setTooltipText("Mają zostać zwrócone dane tylko pojazdów zarejestrowanych");
        registeredCheckBox.setLabel("Tylko zarejestrowane");

        Button submitButton = new Button("Szukaj...", event -> vehicleService.submitForm());

        dataValidation.validateDates(datePickerFrom, datePickerTo);
        dataValidation.validateProvinceNameIsNotEmpty(selectProvince);

        add(formLayout, mainRecordGrid);

        setStylesToDivVehicleInfo(mainRecordGrid);
        setStylesToSearchForm(formLayout, submitButton);

        vehicleService.defineProvinces(provinceApiService);
        vehicleService.showTableVehicleInformation();
    }

    private void setStylesToSearchForm(FormLayout formLayout, Button button) {
        formLayout.getStyle().setMarginLeft("1rem");
        formLayout.getStyle().setMarginRight("1rem");

        formLayout.add(selectProvince, datePickerFrom, datePickerTo, registeredCheckBox, button);
        formLayout.setColspan(button, 3);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 3));

        button.getStyle().setBorderBottom("1rem");
    }

    private void setStylesToDivVehicleInfo(Grid<VehicleMainRecord> mainRecordGrid) {

        mainRecordGrid.getStyle().setMargin("0 1rem 0 1rem");
        mainRecordGrid.setMultiSort(true);
    }

}
