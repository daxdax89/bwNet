package rs.co.micro.bwNet;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nFinex.BWnFinex;

/**
 *
 * @author damir
 */
@Theme("mytheme")
public class welcome extends VerticalLayout implements View {

    private Navigator navigator;

    public welcome() {
        setMargin(true);
        setSpacing(true);
        setResponsive(true);

        BwInfo bwi = new BwInfo(configure.getConnection(), configure.getLozime());

        if (BwInfo.isInitialized) {
            Notification sample = new Notification("Dobrodosli " + configure.getLozime());
            sample.setDelayMsec(4000);
            sample.setPosition(Position.BOTTOM_CENTER);
            sample.show(Page.getCurrent());

            //Listeneri 
            Button.ClickListener finexL = new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    try {
                        navigator.addView("finex", new BWnFinex());
                    } catch (SQLException ex) {
                        Logger.getLogger(welcome.class.getName()).log(Level.SEVERE, null, ex);
                    }
//                    Page.getCurrent().open("http://localhost:8080/bwNet/#!finex", "nFinex");
                    navigator.navigateTo("finex");
                }
            };

            for (int i = 0; i < BwInfo.InstallPaket.length; i++) {
                System.out.println("Paket = " + BwInfo.InstallPaket[i]);
                Button paketButton = new Button(BwInfo.InstallPaket[i]);
                paketButton.setWidth("10em");
                paketButton.addStyleName("dugmence");
                paketButton.addStyleName("nalevi");
                addComponent(paketButton);
                setComponentAlignment(paketButton, Alignment.MIDDLE_CENTER);

                ///Dodeljivanje ikona i listenera dugmicima
                String paket = paketButton.getCaption();
                switch (paket) {
                    case "Finex":
                        paketButton.setIcon(FontAwesome.MONEY);
                        paketButton.addClickListener(finexL);
                        paketButton.setId("finexDugme");
                        break;
                    case "Magic":
                        paketButton.setIcon(FontAwesome.ARCHIVE);
                        break;
                    case "Trade":
                        paketButton.setIcon(FontAwesome.SHOPPING_CART);
                        break;
                    case "Wizard":
                        paketButton.setIcon(FontAwesome.MAGIC);
                        break;
                    case "Sitin":
                        paketButton.setIcon(FontAwesome.CUBES);
                        break;
                    case "Commerce":
                        paketButton.setIcon(FontAwesome.PIE_CHART);
                        break;
                    case "Kamate":
                        paketButton.setIcon(FontAwesome.LINE_CHART);
                        break;
                    case "WorkFlow":
                        paketButton.setIcon(FontAwesome.INDUSTRY);
                        break;
                    case "Komuna":
                        paketButton.setIcon(FontAwesome.TINT);
                        break;
                    case "Kinder":
                        paketButton.setIcon(FontAwesome.GITHUB_ALT);
                        break;
                    case "CourtFlow":
                        paketButton.setIcon(FontAwesome.GAVEL);
                        break;
                    case "FinOp":
                        paketButton.setIcon(FontAwesome.USD);
                        break;
                    case "Safe":
                        paketButton.setIcon(FontAwesome.BARCODE);
                        break;
                    case "OSA":
                        paketButton.setIcon(FontAwesome.AREA_CHART);
                        break;
                    case "Budget":
                        paketButton.setIcon(FontAwesome.MONEY);
                        break;
                    case "PayRoll":
                        paketButton.setIcon(FontAwesome.CREDIT_CARD_ALT);
                        break;
                    default:
                        paketButton.setIcon(FontAwesome.EXCLAMATION);
                }
            }
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        navigator = event.getNavigator();
    }
}