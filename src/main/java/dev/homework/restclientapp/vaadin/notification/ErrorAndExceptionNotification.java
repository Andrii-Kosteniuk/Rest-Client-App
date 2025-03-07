package dev.homework.restclientapp.vaadin.notification;


import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import org.springframework.stereotype.Component;

@Component
public class ErrorAndExceptionNotification {

    public void showNotificationErrorIfTimeOutExceptionOccur() {
        Div text = new Div(
                new Paragraph("Timeout exception occurred!"),
                new Paragraph("Try to reload page, please!"));
        Notification notification = new Notification(text);

        notification.setDuration(5000);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        notification.open();
    }

}
