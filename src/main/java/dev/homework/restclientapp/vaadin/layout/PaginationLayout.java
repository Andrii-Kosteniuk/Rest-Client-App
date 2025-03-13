package dev.homework.restclientapp.vaadin.layout;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import dev.homework.restclientapp.service.VehicleService;

public class PaginationLayout extends HorizontalLayout {


    public static HorizontalLayout getPagesAndSize(VehicleService vehicleService, int page) {
        Button prevButton = new Button("Previous", event -> {
            if (page > 1) {
                vehicleService.setCurrentPage(page - 1);
            }
            vehicleService.submitForm();
        });

        Button nextButton = new Button("Next", event -> {
            vehicleService.setCurrentPage(page + 1);
            vehicleService.submitForm();
        });

        Select<Integer> pageLimitSelect = new Select<>();

        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(pageLimitSelect, "Size");
        formLayout.setSizeFull();

        pageLimitSelect.setItems(100, 200, 300, 400, 500);
        pageLimitSelect.setValue(100);

        pageLimitSelect.addValueChangeListener(event -> {
            Integer size = event.getValue();
            vehicleService.setPageSize(size);
            vehicleService.submitForm();
        });

        return new HorizontalLayout(prevButton, nextButton, formLayout);
    }
}
