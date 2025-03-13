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
import dev.homework.restclientapp.domain.Address;
import dev.homework.restclientapp.domain.Author;
import dev.homework.restclientapp.vaadin.layout.MainApplicationLayout;

@Route(value = "/contact", layout = MainApplicationLayout.class)
public class ContactView extends Div {

    public ContactView() {
        VirtualList<Author> list = new VirtualList<>();
        Address address = new Address("53-437",
                "Wrocław",
                "Dolnośląskie");
        Author authorInfo = new Author("Andrii",
                "Kosteniuk",
                "andriy.kosteniuk@gmail.com",
                "(+48) 663-567-594", address);
        list.setItems(authorInfo);

        ComponentRenderer<Component, Author> authorCardRenderer = new ComponentRenderer<>(
                author -> {
                    HorizontalLayout cardLayout = new HorizontalLayout();
                    cardLayout.setMargin(true);

                    String authorFullName = author.getFirstName() + " " + author.getLastName();
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
                    contactLayout.add(new Div(new Text(author.getEmail())));
                    contactLayout.add(new Div(new Text(author.getAddress().getZip())));
                    contactLayout.add(new Div(new Text(author.getAddress().getCity())));
                    contactLayout.add(new Div(new Text(author.getAddress().getState())));


                    infoLayout.add(new Details("Contact information", contactLayout));

                    cardLayout.add(avatar, infoLayout);
                    return cardLayout;
                });
        list.setRenderer(authorCardRenderer);
        add(list);
    }

}
