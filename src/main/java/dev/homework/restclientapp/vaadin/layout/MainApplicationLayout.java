package dev.homework.restclientapp.vaadin.layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;

@Route("/home")
public class MainApplicationLayout extends AppLayout {

    public MainApplicationLayout() {
        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("Car pocket");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        Tabs tabs = getTabs();

        addToDrawer(tabs);
        addToNavbar(toggle, title);

        setPrimarySection(Section.DRAWER);
    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();

        Component car = new Button("Find cars", event -> getUI().ifPresent(ui -> ui.navigate("/car-search")));
        Tab cars = new Tab(car);
        Tab about = new Tab("About");

        tabs.add(cars, about);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.setHeight("240px");
        tabs.setWidth("240px");
        return tabs;
    }
}