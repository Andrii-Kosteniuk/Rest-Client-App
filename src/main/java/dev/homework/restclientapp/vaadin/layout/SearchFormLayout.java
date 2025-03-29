package dev.homework.restclientapp.vaadin.layout;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import dev.homework.restclientapp.service.ProvinceService;
import dev.homework.restclientapp.service.VehicleService;
import dev.homework.restclientapp.vaadin.notification.FormValidationNotification;
import dev.homework.restclientapp.validation.DataValidation;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Route(value = "/vehicles-search", layout = MainApplicationLayout.class)
@Component
@Scope("prototype")
@Getter
public class SearchFormLayout extends HorizontalLayout {


    private static final Logger log = LoggerFactory.getLogger(SearchFormLayout.class);
    private final Select<String> selectProvince = new Select<>();
    private final DatePicker datePickerFrom = new DatePicker("Data od", LocalDate.now());
    private final DatePicker datePickerTo = new DatePicker("Data do", LocalDate.now());
    private final Checkbox registeredCheckBox = new Checkbox("Tylko zarejestrowane");
    private final Checkbox allFieldsCheckBox = new Checkbox("Pokaz wszystkie pola");
    private final ComboBox<String> typeOfDateComboBox = new ComboBox<>("Typ daty");
    private final ListDataProvider<String> dataProvider;
    private final ProvinceService provinceService;
    private final VehicleService vehicleService;
    private final FormValidationNotification formValidationNotification;
    private final DataValidation dataValidation;


    public SearchFormLayout(DataValidation dataValidation, ProvinceService provinceService, FormValidationNotification formValidationNotification, VehicleService vehicleService) {
        this.provinceService = provinceService;
        this.vehicleService = vehicleService;
        this.formValidationNotification = formValidationNotification;
        this.dataValidation = dataValidation;

        FormLayout formLayout = new FormLayout();
        formLayout.setSizeFull();

        selectProvince.setLabel("Województwo");
        selectProvince.setPlaceholder("Wybierz województwo...");

        datePickerFrom.setTooltipText("Określa datę początkową okresu pierwszej lub ostatniej rejestracji w kraju");
        datePickerTo.setTooltipText("Określa koniec okresu, dla którego pojazdy mają być zwrócone.\n" +
                                    "Domyślną wartością jest data bieżąca");

        registeredCheckBox.setTooltipText("Mają zostać zwrócone dane tylko pojazdów zarejestrowanych");

        dataProvider = new ListDataProvider<>(
                List.of("Data pierwszej rejestracji pojazdu w Polsce",
                        "Data ostatniej rejestracji pojazdu w Polsce"));


        typeOfDateComboBox.getStyle().setFontSize("14px");
        typeOfDateComboBox.setPlaceholder("Wybierz typ daty...");

        typeOfDateComboBox.setItems(dataProvider);
        typeOfDateComboBox.setRequired(true);


        Button submitButton = new Button("Szukaj...", event -> {

            vehicleService.fillFormFieldsForSearchVehicles(this);

            vehicleService.submitForm();

        });

        dataValidation.validateDates(datePickerFrom, datePickerTo);
        dataValidation.validateProvinceNameIsNotEmpty(selectProvince);
        dataValidation.validateDateTypeIsNotEmpty(typeOfDateComboBox);

        setStylesToSearchForm(formLayout, submitButton);


        formLayout.add(selectProvince,
                datePickerFrom,
                datePickerTo,
                registeredCheckBox,
                typeOfDateComboBox,
                allFieldsCheckBox,
                submitButton);

        provinceService.populateProvinceNameToSelect(selectProvince);

        add(formLayout);
    }



    private void setStylesToSearchForm(FormLayout formLayout, Button button) {
        formLayout.getStyle().setMarginLeft("1rem");
        formLayout.getStyle().setMarginRight("1rem");

        formLayout.setColspan(button, 3);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 3));

        button.getStyle().setBorderBottom("1rem");
    }

    public String getTypeOfDate(String typeOfDate) {
        return typeOfDate.equals("Data pierwszej rejestracji pojazdu w Polsce") ? "1" : "2";
    }
}
