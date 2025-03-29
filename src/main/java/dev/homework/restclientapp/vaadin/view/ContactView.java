package dev.homework.restclientapp.vaadin.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.Route;
import dev.homework.restclientapp.domain.Person;
import dev.homework.restclientapp.vaadin.layout.MainApplicationLayout;

@Route(value = "/contact", layout = MainApplicationLayout.class)
public class ContactView extends Div {

    public ContactView() {
        VirtualList<Person> list = new VirtualList<>();
        Person.Address address = new Person.Address("53-437",
                "Wrocław",
                "Dolnośląskie");
        Person person = new Person("Andrii",
                "Kosteniuk",
                "andriy.kosteniuk@gmail.com",
                "(+48) 663-567-594", address);
        String authorFullName = person.getFirstName() + " " + person.getLastName();
        list.setItems(person);

        ComponentRenderer<Component, Person> authorCardRenderer = new ComponentRenderer<>(
                auth -> {
                    HorizontalLayout cardLayout = new HorizontalLayout();
                    cardLayout.setMargin(true);


                    Avatar avatar = new Avatar(authorFullName);
                    avatar.setHeight("64px");
                    avatar.setWidth("64px");

                    VerticalLayout infoLayout = new VerticalLayout();
                    infoLayout.setSpacing(false);
                    infoLayout.setPadding(false);
                    infoLayout.getElement().appendChild(ElementFactory.createStrong(authorFullName));

                    VerticalLayout contactLayout = new VerticalLayout();
                    contactLayout.setSpacing(false);
                    contactLayout.setPadding(false);
                    contactLayout.add(new Div(new Text(person.getEmail())));
                    contactLayout.add(new Div(new Text(person.getAddress().getZip())));
                    contactLayout.add(new Div(new Text(person.getAddress().getCity())));
                    contactLayout.add(new Div(new Text(person.getAddress().getState())));


                    infoLayout.add(new Details("Informacje kontaktowe", contactLayout));

                    cardLayout.add(avatar, infoLayout);
                    return cardLayout;
                });

        list.setRenderer(authorCardRenderer);
        add(list);
    }

}
