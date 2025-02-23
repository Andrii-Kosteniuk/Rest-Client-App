package dev.homework.restclientapp.vaadin.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import dev.homework.restclientapp.service.ProvinceService;
import dev.homework.restclientapp.service.VehicleService;
import dev.homework.restclientapp.vaadin.layout.FormLayoutSearchCar;

@Route("vehicle-info")
public class VehicleInfoView extends VerticalLayout implements  HasDynamicTitle {
    public static final String TITLE = "Vehicle Info";

    public VehicleInfoView(VehicleService vehicleService, ProvinceService provinceService) {
        FormLayoutSearchCar formLayoutSearchCar = new FormLayoutSearchCar(vehicleService, provinceService);
        String pageTitle = getPageTitle();
        var vehicleInfo = new VerticalLayout();
        var vehicleInfoTitle = new H1(pageTitle);

        vehicleInfo.add(vehicleInfoTitle, formLayoutSearchCar);
    }

    @Override
    public String getPageTitle() {
        return TITLE;
    }


}
