/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.co.micro.bwNet;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import java.sql.SQLException;
import nFinex.BWkarticaPartnera;

/**
 *
 * @author damir
 */
@Theme("mytheme")
public class navigationMenu extends VerticalLayout implements View {

    Navigator navigator;

    public navigationMenu() {

        //Main Panel
        setResponsive(true);
        setSpacing(true);
        setMargin(false);

        HorizontalLayout topMenu = new HorizontalLayout();
        topMenu.setSizeFull();

        //Logout Dugme
        MenuBar logout = new MenuBar();
        logout.setId("logoutDugme");
        logout.setSizeFull();
        logout.setResponsive(true);
        logout.setDescription("Izadjite iz programa");

        MenuBar.MenuItem logoutDugme = logout.addItem("Logout", FontAwesome.SIGN_OUT, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getSession().close();
                navigator.addView("login", new login());
                navigator.navigateTo("login");
            }
        });

        //Navigacioni meni
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

//        karticaPartnera.addItem("Tea",
//                FontAwesome.DROPBOX, mycommand);
//        karticaPartnera.addItem("Coffee",
//                FontAwesome.COFFEE, mycommand);
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

        topMenu.addComponents(navMeni, logout);
        addComponents(topMenu);

        // Kreira Kartica Partnera submeni i pokazuje Karticu 
        MenuBar.MenuItem karticaPartnera;
        karticaPartnera = uvidi.addItem("Kartica Partnera", null, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {

                try {
                    navigator.addView("BWKarticaPartnera", new BWkarticaPartnera());
                   
                } catch (SQLException ex) {
                    System.out.println("Greska (BWnFinex klasa)" + SQLException.class);
                }
                navigator.navigateTo("BWKarticaPartnera");
            }
        });

        System.out.println("ubacen meni");
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        navigator = event.getNavigator();
    }
}