package rs.co.micro.micro;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;

/**
 *
 * @author damir
 */
public class BwLogout extends CustomComponent{
    Navigator navigator;
    public BwLogout(){
        MenuBar logout = new MenuBar();
        logout.setId("logoutDugme");
        logout.setSizeFull();
        logout.setResponsive(true);
        logout.setDescription("Izadjite iz programa");

        MenuBar.MenuItem logoutDugme = logout.addItem("Logout", FontAwesome.SIGN_OUT, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getSession().close();
                navigator.navigateTo("login");
            }
        });
        
        System.out.println("Ubacen logout");
        
        setCompositionRoot(logout);
    }
}
