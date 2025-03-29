package dev.homework.restclientapp.vaadin.layout;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import dev.homework.restclientapp.dto.request.VehicleRequest;
import dev.homework.restclientapp.service.VehicleService;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@Getter
public class PaginationLayout extends HorizontalLayout {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final VehicleRequest vehicleRequest;
    private final VehicleService vehicleService;
    private final SearchFormLayout searchFormLayout;

    private final Button prevButton = new Button("Poprzednia strona");
    private final Button nextButton = new Button("Następna strona");


    public PaginationLayout(VehicleRequest vehicleRequest, VehicleService vehicleService, SearchFormLayout searchFormLayout) {
        this.vehicleRequest = vehicleRequest;
        this.vehicleService = vehicleService;
        this.searchFormLayout = searchFormLayout;

        prevButton.addClickListener(e -> vehicleService.navigateToPreviousPage());
        nextButton.addClickListener(e -> vehicleService.navigateToNextPage());

        prevButton.setEnabled(vehicleService.getPageNo() > 1);
        add(createPaginationLayout());
    }

    private HorizontalLayout createPaginationLayout() {
        Select<Integer> pageLimitSelect = new Select<>();
        pageLimitSelect.setItems(100, 200, 300, 400, 500);
        pageLimitSelect.setValue(100);
        pageLimitSelect.addValueChangeListener(event -> vehicleService.updatePageSize(event.getValue()));

        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(pageLimitSelect, "Rozmiar");
        formLayout.setSizeFull();

        Div numberOfRecords = new Div(
                new Text("Liczba rekordów: " + vehicleService.getCachedData().size()));


        HorizontalLayout paginationLayout = new HorizontalLayout(prevButton, nextButton, formLayout, numberOfRecords);
        paginationLayout.setSizeFull();
        return paginationLayout;
    }

}
