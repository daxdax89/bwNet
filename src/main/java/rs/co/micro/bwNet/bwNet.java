package rs.co.micro.bwNet;

import com.vaadin.annotations.PreserveOnRefresh;
import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import javax.servlet.http.Cookie;

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
public class bwNet extends UI implements View {

    Navigator navigator = new Navigator(this, this);
    configure cf = new configure();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Cookie kolac = new Cookie("test", "cookie found");
        VaadinService.getCurrentResponse().addCookie(kolac);
        System.out.println("OVO JE KOLAC BRE!" + kolac);

        if (!configure.load()) {
            navigator.addView("configure", new configure());
            navigator.navigateTo("configure");
        } else {
            navigator.addView("login", new login());
            navigator.navigateTo("login");
        }

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        navigator = event.getNavigator();
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = bwNet.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
