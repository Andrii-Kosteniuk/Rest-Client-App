package dev.homework.restclientapp.util;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.binder.Binder;
import dev.homework.restclientapp.dto.request.VehicleRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class DataValidation {

    public static final Logger LOGGER = LoggerFactory.getLogger(DataValidation.class);
    public static final Binder<VehicleRequest> BINDER = new Binder<>();

    public static void validateDates(DatePicker datePickerFrom, DatePicker datePickerTo) {
        datePickerFrom.addValueChangeListener(event -> configureValidation(datePickerFrom, datePickerTo));
        datePickerTo.addValueChangeListener(event -> configureValidation(datePickerFrom, datePickerTo));
    }

    public static void configureValidation(DatePicker datePickerFrom, DatePicker datePickerTo) {

        LocalDate fromDate = datePickerFrom.getValue();
        LocalDate toDate = datePickerTo.getValue();

        datePickerFrom.setInvalid(false);
        datePickerTo.setInvalid(false);
        datePickerFrom.setErrorMessage(null);
        datePickerTo.setErrorMessage(null);

        if (fromDate != null && toDate != null) {
            if (fromDate.isAfter(toDate)) {
                datePickerFrom.setInvalid(true);
                datePickerFrom.setErrorMessage("Data od nie może być późniejsza niż data do!");
                LOGGER.warn(" {}", datePickerFrom.getErrorMessage());
            } else if (toDate.isBefore(fromDate)) {
                datePickerTo.setInvalid(true);
                datePickerTo.setErrorMessage("Data do nie może być wcześniejsza niż data od!");
                LOGGER.warn(" {}", datePickerTo.getErrorMessage());
            } else if (fromDate.isAfter(LocalDate.now())) {
                datePickerFrom.setInvalid(true);
                datePickerFrom.setErrorMessage("Data od nie może być późniejsza niż data dzisiejsza!");
                LOGGER.warn(" {}", datePickerFrom.getErrorMessage());
            } else if (toDate.isAfter(LocalDate.now())) {
                datePickerTo.setInvalid(true);
                datePickerTo.setErrorMessage("Data do nie może być późniejsza niż data dzisiejsza!");
                LOGGER.warn(" {}", datePickerTo.getErrorMessage());
            }
        }
    }

    public static void validateProvinceNameIsNotEmpty(Select<String> provinces) {

        BINDER.forField(provinces)
                .asRequired("Wybierz województwo!")
                .bind(VehicleRequest::getProvinceName, VehicleRequest::setProvinceName)
                .validate();
    }
}
