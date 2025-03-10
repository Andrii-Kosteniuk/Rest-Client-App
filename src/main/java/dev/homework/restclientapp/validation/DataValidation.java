package dev.homework.restclientapp.validation;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.binder.Binder;
import dev.homework.restclientapp.dto.request.VehicleRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataValidation {

    public final Binder<VehicleRequest> binder = new Binder<>(VehicleRequest.class);

    public void validateProvinceNameIsNotEmpty(Select<String> provinces) {

        binder.forField(provinces)
                .asRequired("Wybierz województwo!")
                .bind(VehicleRequest::getProvinceName, VehicleRequest::setProvinceName)
                .validate();
    }


    public void validateDates(DatePicker datePickerFrom, DatePicker datePickerTo) {

        validateDateFrom(datePickerFrom, datePickerTo);
        validateDateTo(datePickerFrom, datePickerTo);
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

    private void validateDateFrom(DatePicker datePickerFrom, DatePicker datePickerTo) {
        binder.forField(datePickerFrom)
                .withValidator(date -> date == null || datePickerTo.getValue() == null || ! date.isAfter(datePickerTo.getValue()),
                        "Data od nie może być późniejsza niż data do!")
                .withValidator(date -> date == null || ! date.isAfter(LocalDate.now()),
                        "Data nie może być późniejsza niż dzisiejsza data!")
                .withConverter(LocalDate::toString, LocalDate::parse)
                .bind(VehicleRequest::getDateFrom, VehicleRequest::setDateFrom);

        datePickerFrom.addValueChangeListener(event -> binder.validate());
    }


}
