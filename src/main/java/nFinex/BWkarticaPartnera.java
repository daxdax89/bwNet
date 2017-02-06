package nFinex;

import com.vaadin.annotations.Title;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.sql.SQLException;
import rs.co.micro.micro.BwLogout;
import rs.co.micro.micro.BwNavMeni;
import rs.co.micro.micro.BwTable;

/**
 *
 * @author damir
 */
@Title("Kartica Partnera")
public class BWkarticaPartnera extends VerticalLayout implements View {

    Navigator navigator;
    BwTable tablica = new BwTable();
    BwNavMeni navMeni = new BwNavMeni();
    BwLogout logout = new BwLogout();

    public BWkarticaPartnera() throws SQLException {
        setResponsive(true);
        setSpacing(true);
        setMargin(false);
        HorizontalLayout topMenu = new HorizontalLayout();
        topMenu.setSizeFull();

        Label naslov = new Label("Kartica Partnera");
        naslov.setId("naslovKarticaPartnera");

        Label konto = new Label("Šifra konta:");
        konto.setId("konto");
        Label partner = new Label("Šifra partnera:");
        partner.setId("partner");
        HorizontalLayout prvi = new HorizontalLayout();
        prvi.setId("prvi");
        prvi.setSpacing(true);
        prvi.setResponsive(true);
        HorizontalLayout drugi = new HorizontalLayout();
        drugi.setId("drugi");
        drugi.setSpacing(true);
        drugi.setResponsive(true);

        TextField kontoField = new TextField();
        kontoField.setId("kontoField");
        kontoField.setInputPrompt("Pretraži");
        kontoField.focus();

        TextField partnerField = new TextField();
        partnerField.setId("partnerField");
        partnerField.setInputPrompt("Pretraži");
        TextField kontoResult = new TextField();
        kontoResult.setEnabled(false);
        TextField partnerResult = new TextField();
        partnerResult.setEnabled(false);

        //Dodavanje listenera
        kontoField.addTextChangeListener(new FieldEvents.TextChangeListener() {

            @Override
            public void textChange(final FieldEvents.TextChangeEvent event) {
                String value = event.getText();
                addComponents(topMenu, naslov, prvi, drugi, tablica);
                Notification n = new Notification("Ukucali ste " + event.getText());
                n.setPosition(Position.MIDDLE_CENTER);
                n.show(Page.getCurrent());
//                System.out.println(value);
            }
        });

        partnerField.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                Notification not = new Notification("Dobro je bre");
                not.setPosition(Position.BOTTOM_CENTER);
                not.show(Page.getCurrent());
            }
        });

        //Dodavanje komponenti
        topMenu.addComponents(navMeni, logout);
        prvi.addComponents(konto, kontoField, kontoResult);
        drugi.addComponents(partner, partnerField, partnerResult);
        if (kontoField.isEmpty()) {
            addComponents(topMenu, naslov, prvi, drugi);
        } else {
        }

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        navigator = event.getNavigator();
    }
}
