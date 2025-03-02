package dev.homework.restclientapp.vaadin.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import dev.homework.restclientapp.vaadin.layout.MainApplicationLayout;

@Route(value = "", layout = MainApplicationLayout.class)
public class HomeView extends Div {

    public HomeView() {
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
