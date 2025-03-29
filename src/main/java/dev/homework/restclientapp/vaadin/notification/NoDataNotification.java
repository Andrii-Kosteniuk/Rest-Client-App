package dev.homework.restclientapp.vaadin.notification;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import dev.homework.restclientapp.vaadin.component.CloseButtonComponent;

public class NoDataNotification extends Div {

    public static void showNoDataNotification() {

            Notification noDataNotification = new Notification();

            Div text = new Div(new Text("Zgodnie z tymi kryteriami wyszukiwania dane nie są dostępne... Spróbuj wybrać inny okres wyszukiwania!"));

            Button closeNotification = CloseButtonComponent.closeNotification(noDataNotification);
            closeNotification.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);

            HorizontalLayout layout = new HorizontalLayout(text, closeNotification);
            layout.setAlignItems(FlexComponent.Alignment.CENTER);
            noDataNotification.add(layout);
            noDataNotification.open();

            noDataNotification.setPosition(Notification.Position.MIDDLE);
            noDataNotification.setDuration(7000);


    }
}
