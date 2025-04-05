package dev.homework.restclientapp.vaadin.notification;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import dev.homework.restclientapp.vaadin.component.CloseButtonComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public  class ErrorAndExceptionNotification extends Div {

    private static final Logger log = LoggerFactory.getLogger(ErrorAndExceptionNotification.class);

    public static void showNotificationException(Exception ex) {
        UI ui = UI.getCurrent();
        if (ui != null) {
            ui.access(() -> {
                Notification errorNotification = new Notification();
                errorNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);

                Div text = new Div(
                        new Paragraph(ex.getMessage()),
                        new Paragraph("Spróbuj przeładować stronę, proszę!"));

                Button closeNotificationButton = CloseButtonComponent.closeNotification(errorNotification);
                HorizontalLayout layout = new HorizontalLayout(text, closeNotificationButton);
                layout.setAlignItems(FlexComponent.Alignment.CENTER);

                errorNotification.add(layout);

                errorNotification.open();
            });
        } else {
            log.error("UI instance is not available. Cannot show notification.");
        }

    }

}
