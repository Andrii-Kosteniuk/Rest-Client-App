package dev.homework.restclientapp.vaadin.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import dev.homework.restclientapp.vaadin.layout.MainApplicationLayout;

@Route(value = "/about", layout = MainApplicationLayout.class)
public class AboutView extends VerticalLayout {

    public AboutView() {
        Text mainInfo = new Text
                ("""
                         Centralna Ewidencja Pojazdów i Kierowców (CEPiK)\s
                         gromadzi dane o pojazdach zarejestrowanych w Polsce\s
                         oraz o osobach uprawnionych do kierowania pojazdami.
                        \s""");
        Paragraph mainInfoParagraph = new Paragraph();
        mainInfoParagraph.add(mainInfo);


        Text sourceText = new Text("Żródlo wytworzenia i pozyskania informacji jest: ");

        Anchor linkToCepik = new Anchor("https://www.gov.pl/web/cepik", "https://www.gov.pl/web/cepik");
        linkToCepik.setTarget("_blank");
        Span linkSpan = new Span(linkToCepik);

        Paragraph sourceTextParagraph = new Paragraph(sourceText);
        sourceTextParagraph.add(linkSpan);

        add(mainInfoParagraph, sourceTextParagraph);

    }
}
