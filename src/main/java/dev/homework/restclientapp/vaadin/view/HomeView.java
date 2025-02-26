package dev.homework.restclientapp.vaadin.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Route;
import dev.homework.restclientapp.vaadin.layout.MainApplicationLayout;

@Route(value = "", layout = MainApplicationLayout.class)
public class HomeView extends Div {
    public static final String TITLE = "Vehicle information application";

    public HomeView() {
        addClassName("home-view");

        H1 title = new H1(TITLE);
        title.getStyle().setTextAlign(Style.TextAlign.CENTER);
        add(title);

    }
}
