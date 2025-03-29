package dev.homework.restclientapp.vaadin.layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import dev.homework.restclientapp.dto.request.VehicleRequest;
import dev.homework.restclientapp.dto.response.general.VehicleMainRecord;
import dev.homework.restclientapp.service.VehicleService;
import dev.homework.restclientapp.vaadin.notification.NoDataNotification;
import dev.homework.restclientapp.vaadin.view.VehicleInformationView;
import dev.homework.restclientapp.vaadin.view.VehicleView;

import java.util.Optional;
import java.util.function.Consumer;


@Route(value = "/vehicles-filter", layout = MainApplicationLayout.class)
public class FilterLayout extends VerticalLayout {
    private final VehicleView vehicleView;

    public FilterLayout(VehicleService vehicleService, VehicleInformationView vehicleInformationView, VehicleRequest vehicleRequest, SearchFormLayout searchFormLayout) {
        this.vehicleView = new VehicleView(vehicleService);

        Button backButton = new Button("Powrót", new Icon(VaadinIcon.ARROW_LEFT),
                event -> UI.getCurrent().navigate(SearchFormLayout.class));

        GridListDataView<VehicleMainRecord> listOfData = vehicleView.getGridListData();
        if (listOfData.getItemCount() == 0) {
            NoDataNotification.showNoDataNotification();
        }

        VehicleFilter vehicleFilter = new VehicleFilter(listOfData);

        addHeaderRow(vehicleView.getMarkColumn(), vehicleFilter, vehicleView.getModelColumn(), vehicleView.getYearColumn(), vehicleView.getRegistrationDate());
        Grid<VehicleMainRecord> vehicleMainRecordGrid = vehicleView.getVehicleMainRecordGrid();

        vehicleMainRecordGrid.setTooltipGenerator(info -> """
                Kliknij prawym przyciskiem myszy, aby zobaczyć pełne informacje
                """);

        GridContextMenu<VehicleMainRecord> menu = vehicleMainRecordGrid.addContextMenu();

        menu.addItem("Zobacz szegółowe informacje", event -> {
            Optional<VehicleMainRecord> selectedCar = event.getItem();

            selectedCar.ifPresent(vehicleMainRecord ->
                    vehicleInformationView.showDialogWithVehicleDetails(vehicleMainRecord.getId()));
        });


        HorizontalLayout paginationControls = new PaginationLayout(vehicleRequest, vehicleService, searchFormLayout);
        paginationControls.setSizeFull();

        add(backButton, vehicleMainRecordGrid, paginationControls);
    }

    private static Component createFilterHeader(String labelText, Consumer<String> filter) {
        NativeLabel label = new NativeLabel(labelText);
        label.getStyle().set("padding-top", "var(--lumo-space-m)")
                .set("font-size", "var(--lumo-font-size-xs)");

        TextField textField = new TextField();
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidthFull();
        textField.getStyle().set("max-width", "100%");
        textField.addValueChangeListener(
                e -> filter.accept(e.getValue()));

        VerticalLayout layout = new VerticalLayout(label, textField);
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");
        return layout;
    }

    private void addHeaderRow(Grid.Column<VehicleMainRecord> markColumn, VehicleFilter vehicleFilter, Grid.Column<VehicleMainRecord> modelColumn, Grid.Column<VehicleMainRecord> yearColumn, Grid.Column<VehicleMainRecord> registrationDate) {
        HeaderRow headerRow = vehicleView.getVehicleMainRecordGrid().appendHeaderRow();

        headerRow.getCell(markColumn).setComponent(
                createFilterHeader("Marka", vehicleFilter::setMark));
        headerRow.getCell(modelColumn).setComponent(
                createFilterHeader("Model", vehicleFilter::setModel));
        headerRow.getCell(yearColumn).setComponent(
                createFilterHeader("Rok produkcji", vehicleFilter::setYear));
        headerRow.getCell(registrationDate).setComponent(
                createFilterHeader("Data pierwszej rejestracji ", vehicleFilter::setDate));
    }

    private static class VehicleFilter {

        private final GridListDataView<VehicleMainRecord> dataView;

        private String mark;
        private String model;
        private String year;
        private String date;

        public VehicleFilter(GridListDataView<VehicleMainRecord> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }

        public void setMark(String mark) {
            this.mark = mark;
            this.dataView.refreshAll();
        }

        public void setModel(String model) {
            this.model = model;
            this.dataView.refreshAll();
        }

        public void setYear(String year) {
            this.year = year;
            this.dataView.refreshAll();
        }

        public void setDate(String date) {
            this.date = date;
            this.dataView.refreshAll();
        }

        public boolean test(VehicleMainRecord vehicle) {
            boolean matchesMark = matches(vehicle.getMarka(), mark);
            boolean matchesModel = matches(vehicle.getModel(), model);
            boolean matchesYearOfManufacture = matches(vehicle.getRokProdukcji(), year);
            boolean matchesRegistrationDate = matches(vehicle.getDataPierwszejRejestracji(), date);

            return matchesMark && matchesModel && matchesYearOfManufacture && matchesRegistrationDate;

        }

        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                   || value.toLowerCase().contains(searchTerm.toLowerCase());
        }

    }

}
