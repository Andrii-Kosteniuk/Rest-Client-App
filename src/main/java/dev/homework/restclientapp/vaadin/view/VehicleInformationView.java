package dev.homework.restclientapp.vaadin.view;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.theme.lumo.LumoUtility;
import dev.homework.restclientapp.dto.response.singleVehicle.VehicleByIdRecord;
import dev.homework.restclientapp.service.api.VehicleApiService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VehicleInformationView {

    private static final Logger logger = LoggerFactory.getLogger(VehicleInformationView.class);
    public static boolean isFetchingDetails = false;
    private final VehicleApiService vehicleAPIService;

    private static void fillTheInformationAboutVehicle(VehicleByIdRecord vehicle, FormLayout formLayout) {

        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0px", 2));
        addVehicleDetail(formLayout, "Marka:", vehicle.getMark());
        addVehicleDetail(formLayout, "Model:", vehicle.getModel());
        addVehicleDetail(formLayout, "Rodzaj pojazdu:", vehicle.getType());
        addVehicleDetail(formLayout, "Podrodzaj pojazdu:", vehicle.getSubtype());
        addVehicleDetail(formLayout, "Pochodzenie pojazdu:", vehicle.getOriginOfCar());
        addVehicleDetail(formLayout, "Rok produkcji:", vehicle.getDateOfManufacture());
        addVehicleDetail(formLayout, "Pierwsza rejestracja w PL:", vehicle.getDateOfFirstRegistrationInPoland());
        addVehicleDetail(formLayout, "Ostatnia rejestracja w PL:", vehicle.getDateOfLastRegistrationInPoland());
        addVehicleDetail(formLayout, "Pojemność silnika:", vehicle.getEngineDisplacement() + " cm³");
        addVehicleDetail(formLayout, "Masa własna:", vehicle.getNetWeight() + " kg");
        addVehicleDetail(formLayout, "DMC:", vehicle.getPermissibleGrossVehicleWeight() + " kg");
        addVehicleDetail(formLayout, "Liczba osi:", String.valueOf(vehicle.getNumberOfAxles()));
        addVehicleDetail(formLayout, "Województwo rejestracji:", vehicle.getRegistrationProvince());
        addVehicleDetail(formLayout, "Powiat rejestracji:", vehicle.getRegistrationCounty());
        addVehicleDetail(formLayout, "Gmina rejestracji:", vehicle.getRegistrationDistrict());

        logger.info("Data about '{}' is filled and returned", vehicle.getMark());
    }

    private static void addVehicleDetail(FormLayout formLayout, String label, String value) {
        NativeLabel fieldLabel = new NativeLabel(label);
        NativeLabel fieldValue = new NativeLabel(value != null ? value : "Brak danych");
        formLayout.add(fieldLabel, fieldValue);
    }

    public void showDialogWithVehicleDetails(String vehicleId) {
        if (isFetchingDetails) {
            return;
        }
        isFetchingDetails = true;

        VehicleByIdRecord vehicleDetails = vehicleAPIService.getCarDetails(vehicleId);

        Dialog detailsDialog = new Dialog();
        detailsDialog.setHeaderTitle("Szczegóły pojazdu");
        detailsDialog.setWidth("600px");

        Div detailsContainer = new Div();
        detailsContainer.getStyle()
                .setDisplay(Style.Display.GRID)
                .setPadding("10px");

        FormLayout formLayout = new FormLayout();
        formLayout.getStyle().setBoxShadow(LumoUtility.BoxShadow.MEDIUM);
        fillTheInformationAboutVehicle(vehicleDetails, formLayout);

        detailsDialog.add(formLayout);

        Button closeButton = new Button("Zamknij", e -> {
            detailsDialog.close();
            isFetchingDetails = false;
        });

        detailsDialog.getFooter().add(closeButton);

        detailsDialog.open();
    }

}
