package dev.homework.restclientapp.vaadin.notification;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import dev.homework.restclientapp.vaadin.component.CloseButtonComponent;
import org.springframework.stereotype.Component;

@Component
public class FormValidationNotification extends Div  {

    public void showInvalidDateNotification() {
        Notification errorValidationNotification = new Notification();
        errorValidationNotification.addThemeVariants(NotificationVariant.LUMO_WARNING);

        Div notificationMessage = new Div(new Text("Please, check if you are providing a correct data!"));

        Button closeNotificationButton = CloseButtonComponent.closeNotification(errorValidationNotification);

        HorizontalLayout layout = new HorizontalLayout(notificationMessage, closeNotificationButton);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        errorValidationNotification.setDuration(5000);
        errorValidationNotification.add(layout);
        errorValidationNotification.open();
    }
}
