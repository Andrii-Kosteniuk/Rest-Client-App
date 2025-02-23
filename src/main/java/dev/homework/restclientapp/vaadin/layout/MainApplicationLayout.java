package dev.homework.restclientapp.vaadin.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import dev.homework.restclientapp.service.ProvinceService;
import dev.homework.restclientapp.service.VehicleService;
import dev.homework.restclientapp.vaadin.view.VehicleInfoView;

import java.util.ArrayList;
import java.util.List;


@Route("")
public class MainApplicationLayout extends AppLayout {
    private final VehicleService vehicleService;
    private final ProvinceService provinceService;

    public MainApplicationLayout(VehicleService vehicleService, ProvinceService provinceService) {
        this.vehicleService = vehicleService;
        this.provinceService = provinceService;


        setPrimarySection(Section.DRAWER);
        VehicleInfoView vehicleInfoView = new VehicleInfoView(vehicleService, provinceService);
        addNavbarContent();
        addDrawerContent();

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.add(vehicleInfoView);

        setContent(contentLayout);

    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();

        Tab homeTab = new Tab("Home");
        Tab findVehicleTab = new Tab("Find Vehicles");

        tabs.addSelectedChangeListener(event ->
        {
            Tab selectedTab = event.getSelectedTab();
            if (selectedTab.equals(homeTab)) {
                switchToHome();
            } else if (selectedTab.equals(findVehicleTab)) {
                switchToSearchVehicles();
            }
        });

        Button vehicleFindButton = new Button("Find vehicles", event -> switchToSearchVehicles());
        vehicleFindButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        tabs.add(findVehicleTab);
        List<Tab> tabsList = defineTabs();
        tabsList.forEach(tabs::add);

        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.setHeight("240px");
        tabs.setWidth("240px");
        return tabs;
    }

    private List<Tab> defineTabs() {
        List<Tab> tabs = new ArrayList<>();
        tabs.add(new Tab("About"));
        tabs.add(new Tab("Contact"));

        return tabs;
    }

    private void addNavbarContent() {
        DrawerToggle toggle = new DrawerToggle();
        H1 mainViewTitle = new H1("Vehicle pocket");
        mainViewTitle.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");
        mainViewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE,
                LumoUtility.Flex.GROW);

        Header header = new Header(toggle, mainViewTitle);
        header.addClassNames(LumoUtility.AlignItems.CENTER, LumoUtility.Display.FLEX,
                LumoUtility.Padding.End.MEDIUM, LumoUtility.Width.FULL);

        addToNavbar(toggle, mainViewTitle);
    }

    private void addDrawerContent() {
        Tabs tabs = getTabs();

        var appName = new Span("Vehicle pocket");
        appName.addClassNames(LumoUtility.AlignItems.CENTER, LumoUtility.Display.FLEX,
                LumoUtility.FontSize.LARGE, LumoUtility.FontWeight.SEMIBOLD,
                LumoUtility.Height.XLARGE, LumoUtility.Padding.Horizontal.MEDIUM);

        addToDrawer(appName, tabs);

    }

    private void switchToSearchVehicles() {
        setContent(new FormLayoutSearchCar(vehicleService, provinceService));
    }

    private void switchToHome() {
        setContent(new VehicleInfoView(vehicleService, provinceService));
    }


}