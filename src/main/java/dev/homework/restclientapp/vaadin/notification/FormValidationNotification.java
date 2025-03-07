package dev.homework.restclientapp.vaadin.notification;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.springframework.stereotype.Component;

@Component
public class FormValidationNotification {

    public void showInvalidDateNotification() {
        Notification errorValidationNotification = new Notification();
        errorValidationNotification.addThemeVariants(NotificationVariant.LUMO_WARNING);

        Div text = new Div(new Text("Please, check if you are providing a correct date!"));

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.setAriaLabel("Close");
        closeButton.addClickListener(event -> errorValidationNotification.close());

        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        errorValidationNotification.setDuration(5000);
        errorValidationNotification.add(layout);
        errorValidationNotification.open();
    }
}
