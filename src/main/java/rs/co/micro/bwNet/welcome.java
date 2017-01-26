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

//        VerticalLayout boxWelcome = new VerticalLayout();
//        boxWelcome.setId("loginBox");
//        boxWelcome.setSpacing(true);
//        boxWelcome.setMargin(true);
//        boxWelcome.setResponsive(true);
//        boxWelcome.addStyleName("belaBoja boxBorder");
//        boxWelcome.setWidthUndefined();
//        Label dobrodosli = new Label("Dobrodosli " + configure.getLozaFullName());
//        Label datum = new Label("Vasa zadnja poseta je bila " + configure.getLozdatum());
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
                    navigator.addView("finex", new BWnFinex());
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
//        Button test = new Button("Test");
//        test.setIcon(FontAwesome.MONEY);
//        test.setWidth("10em");
//        test.addStyleName(ValoTheme.BUTTON_FRI ENDLY);
//        test.addStyleName("nalevi");

//        addComponents(boxWelcome);
//        setComponentAlignment(boxWelcome, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        navigator = event.getNavigator();
    }
}

//Button test = new Button("Budzet");
//test.setIcon(FontAwesome.MONEY);
//test.setWidth("10em");
//test.addStyleName(ValoTheme.BUTTON_FRIENDLY);
//test.addStyleName("nalevi");
//Button test2 = new Button("Commerce");
//test2.setIcon(FontAwesome.PIE_CHART);
//test2.setWidth("10em");
//test2.addStyleName(ValoTheme.BUTTON_DANGER);
//test2.addStyleName("nalevi");
//Button test3 = new Button("CourtFlow");
//test3.setIcon(FontAwesome.BOOK);
//test3.setWidth("10em");
//test3.addStyleName(ValoTheme.BUTTON_PRIMARY);
//test3.addStyleName("nalevi");
//Button test4 = new Button("Finex");
//test4.setIcon(FontAwesome.MONEY);
//test4.setWidth("10em");
//test4.addStyleName(ValoTheme.BUTTON_BORDERLESS);
//test4.addStyleName("nalevi");
//Button test5 = new Button("FinOp");
//test5.setIcon(FontAwesome.USD);
//test5.setWidth("10em");
//test5.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
//test5.addStyleName("nalevi");
//Button test6 = new Button("Kamate");
//test6.setIcon(FontAwesome.LINE_CHART);
//test6.setWidth("10em");
//test6.addStyleName(ValoTheme.BUTTON_HUGE);
//test6.addStyleName("nalevi");
//Button test7 = new Button("Kinder");
//test7.setIcon(FontAwesome.GITHUB_ALT);
//test7.setWidth("10em");
//test7.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
//test7.addStyleName("nalevi");
//Button test8 = new Button("Komuna");
//test8.setIcon(FontAwesome.TINT);
//test8.setWidth("10em");
//test8.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_TOP);
//test8.addStyleName("nalevi");
//Button test9 = new Button("Osa");
//test9.setIcon(FontAwesome.AREA_CHART);
//test9.setWidth("10em");
//test9.addStyleName(ValoTheme.BUTTON_LARGE);
//test9.addStyleName("nalevi");
//Button test10 = new Button("Sitin");
//test10.setIcon(FontAwesome.BARCODE);
//test10.setWidth("10em");
//test10.addStyleName(ValoTheme.BUTTON_LINK);
//test10.addStyleName("nalevi");
//Button test11 = new Button("Trade");
//test11.setIcon(FontAwesome.SHOPPING_CART);
//test11.setWidth("10em");
//test11.addStyleName(ValoTheme.BUTTON_QUIET);
//test11.addStyleName("nalevi");
//Button test12 = new Button("Wizard");
//test12.setIcon(FontAwesome.MAGIC);
//test12.setWidth("10em");
//test12.addStyleName(ValoTheme.BUTTON_SMALL);
//test12.addStyleName("nalevi");
//Button test13 = new Button("Workflow");
//test13.setIcon(FontAwesome.INDUSTRY);
//test13.setWidth("10em");
//test13.addStyleName(ValoTheme.BUTTON_TINY);
//test13.addStyleName("nalevi");
