package rs.co.micro.bwNet;

import com.vaadin.annotations.Title;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author damir
 */
@Title("Login")
public class login extends VerticalLayout implements View {

    private Navigator navigator;

    public login() {
        setMargin(true);
        setSpacing(true);
        setResponsive(true);
        setId("loginMain");

        Label naslov = new Label("Micro Business");
        naslov.setId("naslov2");
        naslov.setResponsive(true);
        Label podnaslov = new Label("computer engineering");
        podnaslov.setId("logPodnaslov");

        final TextField polje1 = new TextField();
        polje1.setId("logPolje1");
        polje1.setCaption("Korisničko ime:");
        polje1.setIcon(FontAwesome.USER);
        polje1.focus();

        final PasswordField polje2 = new PasswordField();
        polje2.setId("logPolje2");
        polje2.setCaption("Lozinka:");
        polje2.setIcon(FontAwesome.LOCK);

        Button button = new Button("OK");
        button.addStyleName("dugmence");
        button.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        button.setIcon(FontAwesome.CHECK_CIRCLE);
        button.addClickListener(e -> {
            configure.setUsername(polje1.getValue());
            configure.setUserPass(polje2.getValue());

            if (configure.checkUser(configure.getLozime(), configure.getLozPass())) {
                navigator.addView("welcome", new welcome());
                navigator.navigateTo("welcome");
            } else {
                Notification.show("Uneti podaci nisu tacni");
                polje1.focus();
            }
        });

        addComponents(naslov, podnaslov, polje1, polje2, button);
        setComponentAlignment(naslov, Alignment.MIDDLE_CENTER);
        setComponentAlignment(podnaslov, Alignment.MIDDLE_CENTER);
        setComponentAlignment(button, Alignment.MIDDLE_CENTER);
        setComponentAlignment(polje1, Alignment.MIDDLE_CENTER);
        setComponentAlignment(polje2, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        navigator = event.getNavigator();
    }

}
