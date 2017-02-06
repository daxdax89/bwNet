package rs.co.micro.micro;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nFinex.BWkarticaPartnera;

/**
 *
 * @author damir
 */
public class BwNavMeni extends CustomComponent implements View {

    Navigator navigator;

    public BwNavMeni() throws SQLException {
        MenuBar navMeni = new MenuBar();
        navMeni.setId("navMeni");
        navMeni.setSizeFull();
        navMeni.setResponsive(true);

        // A top-level menu item that opens a submenu
        MenuBar.MenuItem uvidi = navMeni.addItem("Uvidi", null, null);
        //Nesto poput click listenera
        MenuBar.Command mycommand = new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                Notification.show("radi");
            }
        };

        // Kreira Kartica Partnera submeni i pokazuje Karticu 
        MenuBar.MenuItem karticaPartnera = uvidi.addItem("Kartica Partnera", null, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                try {
                    getUI().getNavigator().addView("karticaPartnera", new BWkarticaPartnera());
                } catch (SQLException ex) {
                    System.out.println("nope");
                }
                getUI().getNavigator().navigateTo("karticaPartnera");
            }
        });

// Another top-level item
        MenuBar.MenuItem snacks = navMeni.addItem("Snacks", null, null);
        snacks.addItem("Weisswurst", null, mycommand);
        snacks.addItem("Bratwurst", null, mycommand);
        snacks.addItem("Currywurst", null, mycommand);
        snacks.addItem("Test1", null, mycommand);
        snacks.addItem("test2", null, mycommand);

// Yet another top-level item
        MenuBar.MenuItem servs = navMeni.addItem("Services", null, null);
        servs.addItem("Car Service", null, mycommand);

        System.out.println("Ubacen Navmeni");

        setCompositionRoot(navMeni);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        navigator = event.getNavigator();
    }
}
