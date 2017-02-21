/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.co.micro.bwNet;

import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author damir
 */
@Title("Main Window")

public class main extends VerticalLayout implements View {

//    private Navigator navigator;
    public Navigator navigator;

    public main() {

        //Layout
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.addStyleName("odvoji");
        layout.setSpacing(true);
        layout.setResponsive(true);
        layout.setId("oprem");

        //Prvo dugme
        Button conf = new Button("Configure");
        Button.ClickListener listener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                navigator.addView("configure", new configure());
                navigator.navigateTo("configure");
            }
        };
        conf.addClickListener(listener);

        //Drugo dugme
        Button log = new Button("Login");
        Button.ClickListener listener2 = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                navigator.addView("login", new login());
                navigator.navigateTo("login");
            }
        };
        log.addClickListener(listener2);

        //Trece dugme
        Button wel = new Button("Welcome");
        Button.ClickListener listener3 = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                navigator.addView("welcome", new welcome());
                navigator.navigateTo("welcome");
            }
        };
        wel.addClickListener(listener3);

        layout.addComponents(conf, log, wel);
        addComponent(layout);

    }

    @Override
    public void enter(ViewChangeEvent event) {
        navigator = event.getNavigator();
    }

}
