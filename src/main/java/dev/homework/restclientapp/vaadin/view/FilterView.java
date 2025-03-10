package dev.homework.restclientapp.vaadin.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import dev.homework.restclientapp.dto.response.general.VehicleMainRecord;
import dev.homework.restclientapp.service.VehicleService;
import dev.homework.restclientapp.vaadin.layout.MainApplicationLayout;

import java.util.List;
import java.util.function.Consumer;


@Route(value = "/vehicles-filter", layout = MainApplicationLayout.class)
public class FilterView extends VerticalLayout {


    public FilterView(VehicleService vehicleService, VehicleInformationView vehicleInformationView) {
        Grid<VehicleMainRecord> vehicleMainRecordGrid = new Grid<>(VehicleMainRecord.class, false);
        Grid.Column<VehicleMainRecord> markColumn = vehicleMainRecordGrid.addColumn(VehicleMainRecord::getMarka);
        Grid.Column<VehicleMainRecord> modelColumn = vehicleMainRecordGrid.addColumn(VehicleMainRecord::getModel);
        Grid.Column<VehicleMainRecord> yearColumn = vehicleMainRecordGrid.addColumn(VehicleMainRecord::getRokProdukcji);
        Grid.Column<VehicleMainRecord> registrationDate = vehicleMainRecordGrid.addColumn(VehicleMainRecord::getDataPierwszejRejestracji);
        int page = vehicleService.getCurrentPage();

        vehicleMainRecordGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        Button backButton = new Button("Back", new Icon(VaadinIcon.ARROW_LEFT),
                event -> UI.getCurrent().navigate(SearchCarView.class));

        HorizontalLayout paginationControls = getHorizontalLayout(vehicleService, page);
        paginationControls.setSizeFull();

        List<VehicleMainRecord> vehicleMainRecords = vehicleService.getCachedData();
        GridListDataView<VehicleMainRecord> dataView = vehicleMainRecordGrid.setItems(vehicleMainRecords);


        VehicleFilter vehicleFilter = new VehicleFilter(dataView);
        vehicleMainRecordGrid.getHeaderRows().clear();

        HeaderRow headerRow = vehicleMainRecordGrid.appendHeaderRow();

        headerRow.getCell(markColumn).setComponent(
                createFilterHeader("Marka", vehicleFilter::setMark));
        headerRow.getCell(modelColumn).setComponent(
                createFilterHeader("Model", vehicleFilter::setModel));
        headerRow.getCell(yearColumn).setComponent(
                createFilterHeader("Rok produkcji", vehicleFilter::setYear));
        headerRow.getCell(registrationDate).setComponent(
                createFilterHeader("Data pierwszej rejestracji ", vehicleFilter::setDate));

        add(backButton, vehicleMainRecordGrid, paginationControls);

        vehicleMainRecordGrid.addItemClickListener(event -> {
            VehicleMainRecord selectedCar = event.getItem();
            if (selectedCar != null) {
                vehicleInformationView.showDialogWithVehicleDetails(selectedCar.getId());
            }
        });
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

    private HorizontalLayout getHorizontalLayout(VehicleService vehicleService, int page) {
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

        pageLimitSelect.setValue(100);
        pageLimitSelect.setItems(100, 200, 300, 400, 500);

        pageLimitSelect.addValueChangeListener(event -> {
            Integer size = event.getValue();
            vehicleService.setPageSize(size);
            vehicleService.submitForm();
        });

        return new HorizontalLayout(prevButton, nextButton, formLayout);
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
