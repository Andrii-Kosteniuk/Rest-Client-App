package dev.homework.restclientapp.vaadin.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import dev.homework.restclientapp.vaadin.view.AboutView;
import dev.homework.restclientapp.vaadin.view.ContactView;

import java.util.List;


public class MainApplicationLayout extends AppLayout {

    public MainApplicationLayout() {

        setPrimarySection(Section.DRAWER);
        addNavbarContent();
        addDrawerContent();

    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();

        List<Tab> tabsList = defineTabs();
        tabsList.forEach(tabs::add);

        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        return tabs;
    }

    private List<Tab> defineTabs() {
        Tab homeTab = new Tab(new RouterLink("Home", HomeLayout.class));
        Tab vehicleTab = new Tab(new RouterLink("Find Vehicles", SearchFormLayout.class));
        Tab aboutTab = new Tab(new RouterLink("About", AboutView.class));
        Tab contactTab = new Tab(new RouterLink("Contact", ContactView.class));

        return List.of(homeTab, vehicleTab, aboutTab, contactTab);
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

        var appName = new Span("What do you want to do?");
        appName.addClassNames(LumoUtility.AlignItems.CENTER, LumoUtility.Display.FLEX,
                LumoUtility.FontSize.LARGE, LumoUtility.FontWeight.SEMIBOLD,
                LumoUtility.Height.XLARGE, LumoUtility.Padding.Horizontal.MEDIUM);

        addToDrawer(appName, tabs);

    }

}