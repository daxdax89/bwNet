package nFinex;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import java.sql.SQLException;
import rs.co.micro.micro.BwLogout;
import rs.co.micro.micro.BwNavMeni;

/**
 *
 * @author damir
 */
@Theme("mytheme")
@Title("nFinex")
public class BWnFinex extends VerticalLayout implements View {

    public BWnFinex() throws SQLException {
        BwLogout logout = new BwLogout();
        BwNavMeni navMeni = new BwNavMeni();

        //Main Panel
        setResponsive(true);
        setSpacing(true);
        setMargin(false);
        setSizeFull();

        HorizontalLayout topMenu = new HorizontalLayout();
        topMenu.setSizeFull();

        Label paketNaslov = new Label("nFinex");
        paketNaslov.setId("naslovFinex");

        topMenu.addComponents(navMeni, logout);
        addComponents(topMenu, paketNaslov);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
