package dev.homework.restclientapp.validation;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.binder.Binder;
import dev.homework.restclientapp.dto.request.VehicleRequest;
import dev.homework.restclientapp.vaadin.notification.FormValidationNotification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static org.reflections.Reflections.log;

@Component
public class DataValidation {

    public final Binder<VehicleRequest> binder = new Binder<>(VehicleRequest.class);

    public void validateProvinceNameIsNotEmpty(Select<String> provinces) {

        binder.forField(provinces)
                .asRequired("Pole nie może być puste!")
                .bind(VehicleRequest::getProvinceName, VehicleRequest::setProvinceName);

        provinces.addValueChangeListener(event -> binder.validate());

    }

    public void validateDateTypeIsNotEmpty(ComboBox<String> typeOfDate) {

        binder.forField(typeOfDate)
                .asRequired("Pole nie może być puste!")
                .bind(VehicleRequest::getTypeOfDate, VehicleRequest::setTypeOfDate);

        typeOfDate.addValueChangeListener(event -> binder.validate());

    }


    public void validateDates(DatePicker datePickerFrom, DatePicker datePickerTo) {

        validateDateFrom(datePickerFrom, datePickerTo);
        validateDateTo(datePickerFrom, datePickerTo);
    }

    private void validateDateFrom(DatePicker datePickerFrom, DatePicker datePickerTo) {
        binder.forField(datePickerFrom)
                .withValidator(date -> date == null || datePickerTo.getValue() == null || ! date.isAfter(datePickerTo.getValue()),
                        "Data od nie może być późniejsza niż data do!")
                .asRequired("Wybierz datę od!")
                .withValidator(date -> date == null || ! date.isAfter(LocalDate.now()),
                        "Data nie może być późniejsza niż dzisiejsza data!")
                .withConverter(LocalDate::toString, LocalDate::parse)
                .bind(VehicleRequest::getDateFrom, VehicleRequest::setDateFrom);

        datePickerFrom.addValueChangeListener(event -> binder.validate());

    }

    private void validateDateTo(DatePicker datePickerFrom, DatePicker datePickerTo) {
        binder.forField(datePickerTo)
                .withValidator(date -> date == null || datePickerFrom.getValue() == null || ! date.isBefore(datePickerFrom.getValue()),
                        "Data do nie może być wcześniejsza niż data od!")
                .withValidator(date -> date == null || ! date.isAfter(LocalDate.now()),
                        "Data nie może być późniejsza niż dzisiejsza data!")
                .withConverter(LocalDate::toString, LocalDate::parse)
                .bind(VehicleRequest::getDateTo, VehicleRequest::setDateTo);

        datePickerTo.addValueChangeListener(event -> binder.validate());
    }


    public void checkIfAllDateAreProvided(FormValidationNotification formValidationNotification) {
        if (! binder.isValid()) {
            formValidationNotification.showInvalidDateNotification();

            binder.validate().getValidationErrors().forEach(error ->
                    log.error("Field error: {}", error.getErrorMessage())
            );
        }
    }

}
