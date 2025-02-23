package dev.homework.restclientapp.vaadin.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route("")
public class MainApplicationLayout extends AppLayout {

    public MainApplicationLayout() {
        DrawerToggle toggle = new DrawerToggle();

        H1 mainTitle = new H1("Vehicle pocket");
        mainTitle.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        Tabs tabs = getTabs();

        addToDrawer(tabs);
        addToNavbar(toggle, mainTitle);

        setPrimarySection(Section.DRAWER);
    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();

        Button vehicleFindButton = new Button("Find Vehicles", event -> getUI().ifPresent(ui -> ui.navigate("/vehicles-search")));
        vehicleFindButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        Tab findVehicleTab = new Tab(vehicleFindButton);

        List<Tab> tabsList = defineTabs();
        tabsList.forEach(tabs::add);

        tabs.add(findVehicleTab);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.setHeight("240px");
        tabs.setWidth("240px");
        return tabs;
    }

    private List<Tab> defineTabs() {
        List<Tab> tabs = new ArrayList<>();
        tabs.add(new Tab("Home"));
        tabs.add(new Tab("About"));
        tabs.add(new Tab("Contact"));
        return tabs;
    }
}