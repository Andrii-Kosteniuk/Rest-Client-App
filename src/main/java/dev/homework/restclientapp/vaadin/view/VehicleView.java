package dev.homework.restclientapp.vaadin.view;


import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import dev.homework.restclientapp.dto.response.general.VehicleMainRecord;
import dev.homework.restclientapp.service.VehicleService;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class VehicleView {
    private Grid<VehicleMainRecord> vehicleMainRecordGrid;
    private GridListDataView<VehicleMainRecord> gridListData;
    private Grid.Column<VehicleMainRecord> markColumn;
    private Grid.Column<VehicleMainRecord> modelColumn;
    private Grid.Column<VehicleMainRecord> yearColumn;
    private Grid.Column<VehicleMainRecord> registrationDate;


    public VehicleView(VehicleService vehicleService) {
        this.vehicleMainRecordGrid = new Grid<>();
        this.markColumn = vehicleMainRecordGrid.addColumn(VehicleMainRecord::getMarka);
        this.modelColumn = vehicleMainRecordGrid.addColumn(VehicleMainRecord::getModel);
        this.yearColumn = vehicleMainRecordGrid.addColumn(VehicleMainRecord::getRokProdukcji);
        this.registrationDate = vehicleMainRecordGrid.addColumn(VehicleMainRecord::getDataPierwszejRejestracji);

        vehicleMainRecordGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        List<VehicleMainRecord> vehicleMainRecords = vehicleService.getCachedData();
        gridListData = vehicleMainRecordGrid.setItems(vehicleMainRecords);

    }

}
