package dev.homework.restclientapp.vaadin.layout;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainApplicationLayout.class)
public class HomeLayout extends HorizontalLayout {

    public HomeLayout() {
        addClassName("home-view");
        setSizeFull();

        getStyle().set("background-image", "url('images/car-silhouette.jpg')");
        getStyle().set("background-size", "cover");
        getStyle().set("background-position", "center");
        getStyle().set("display", "flex");
        getStyle().set("justify-content", "center");
        getStyle().setOpacity("0.6");

    }
}
