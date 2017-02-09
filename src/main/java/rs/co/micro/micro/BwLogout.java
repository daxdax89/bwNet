package rs.co.micro.micro;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import org.vaadin.dialogs.ConfirmDialog;

/**
 *
 * @author damir
 */
public class BwLogout extends CustomComponent implements View {

    Navigator navigator;
    Notification n;

    public BwLogout() {
        MenuBar logout = new MenuBar();
        logout.setId("logoutDugme");
        logout.setSizeFull();
        logout.setResponsive(true);
        logout.setDescription("Izadjite iz programa");

        MenuBar.MenuItem kontakt = logout.addItem("Prijavi grešku", FontAwesome.MAIL_FORWARD, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {

            }
        });

        MenuBar.MenuItem logoutDugme = logout.addItem("Izađi", FontAwesome.SIGN_OUT, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {

// The quickest way to confirm
                ConfirmDialog cf = null;
                ConfirmDialog.show(UI.getCurrent(), "Molimo vas potvrdite:", "Da li ste sigurni?", "Da", "Ne", new ConfirmDialog.Listener() {
                    @Override
                    public void onClose(ConfirmDialog cd) {
                        if (cd.isConfirmed()) {
                            getUI().getNavigator().navigateTo("login");
                        } else {
                            cd.close();
                        }
                    }
                });
            }
        });
        System.out.println("Ubacen logout");
        setCompositionRoot(logout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        navigator = event.getNavigator();
    }
}
