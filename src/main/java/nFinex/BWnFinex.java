package nFinex;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import java.sql.SQLException;
import rs.co.micro.bwNet.BwInfo;

/**
 *
 * @author damir
 */
@Theme("mytheme")
@Title("nFinex")
public class BWnFinex extends VerticalLayout implements View {

    Navigator navigator;

    public BWnFinex() {

        //Main Panel
        setResponsive(true);
        setSpacing(true);
        setMargin(false);
        setSizeFull();

        HorizontalLayout topMenu = new HorizontalLayout();
        topMenu.setSizeFull();

        //Logout Dugme
        MenuBar logout = new MenuBar();
        logout.setId("logoutDugme");
        logout.setResponsive(true);
        logout.setWidth("100%");

        MenuBar.MenuItem logoutDugme = logout.addItem("Logout", FontAwesome.SIGN_OUT, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                try {
                    BwInfo.getCurentConnection().close();
                } catch (SQLException ex) {

                }
                getUI().getSession().close();
                navigator.navigateTo("login");
            }
        });

        //Navigacioni meni
        MenuBar navMeni = new MenuBar();
        navMeni.setId("navMeni");
        navMeni.setWidth("100%");
        navMeni.setResponsive(true);

        // A top-level menu item that opens a submenu
        MenuItem uvidi = navMeni.addItem("Uvidi", null, null);
        //Nesto poput click listenera
        MenuBar.Command mycommand = new MenuBar.Command() {
            @Override
            public void menuSelected(MenuItem selectedItem) {
                Notification.show("radi");
            }
        };

//        karticaPartnera.addItem("Tea",
//                FontAwesome.DROPBOX, mycommand);
//        karticaPartnera.addItem("Coffee",
//                FontAwesome.COFFEE, mycommand);
// Another top-level item
        MenuItem snacks = navMeni.addItem("Snacks", null, null);
        snacks.addItem("Weisswurst", null, mycommand);
        snacks.addItem("Bratwurst", null, mycommand);
        snacks.addItem("Currywurst", null, mycommand);
        snacks.addItem("Test1", null, mycommand);
        snacks.addItem("test2", null, mycommand);

// Yet another top-level item
        MenuItem servs = navMeni.addItem("Services", null, null);
        servs.addItem("Car Service", null, mycommand);

        Label paketNaslov = new Label("nFinex");
        paketNaslov.setId("naslovFinex");
        addComponents(topMenu, paketNaslov);

        topMenu.addComponents(navMeni, logout);
        addComponents(topMenu, paketNaslov);

        // Kreira Kartica Partnera submeni i pokazuje Karticu 
        MenuItem karticaPartnera = uvidi.addItem("Kartica Partnera", null, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuItem selectedItem) {
                try {
                    navigator.addView("BWKarticaPartnera", new BWkarticaPartnera());
                } catch (SQLException ex) {
                    System.out.println("Greska (BWnFiex klasa)" + SQLException.class);
                }
                navigator.navigateTo("BWKarticaPartnera");
            }
        });
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        navigator = event.getNavigator();
    }

}
