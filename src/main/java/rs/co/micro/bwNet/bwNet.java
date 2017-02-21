package rs.co.micro.bwNet;

import com.vaadin.annotations.PreserveOnRefresh;
import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nFinex.BWkarticaPartnera;
import nFinex.BWnFinex;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
@Title("BusinessWare Net")
@PreserveOnRefresh
public class bwNet extends UI{

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        try {
            Navigator navigator = new Navigator(this, this);
            
            //Login
            navigator.addView("login", new login());
            
            //Configure
            navigator.addView("configure", new configure());
            
            //Welcome
            navigator.addView("welcome", new welcome());
            
            //Finex
            navigator.addView("finex", new BWnFinex());
            
            
            //Kartica Partnera
            try {
                navigator.addView("karticaPartnera", new BWkarticaPartnera());
            } catch (SQLException ex) {
                System.out.println("Main klasa, nije uslo u karticu partnera");
            }
            
            if (!configure.load()) {
                navigator.navigateTo("configure");
            } else {
                navigator.navigateTo("login");
            }
        } catch (SQLException ex) {
            Logger.getLogger(bwNet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = bwNet.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
