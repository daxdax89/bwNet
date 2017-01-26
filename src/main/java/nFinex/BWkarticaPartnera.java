package nFinex;

import com.vaadin.annotations.Title;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.sql.SQLException;
import java.util.Locale;
import rs.co.micro.micro.BwTable;

/**
 *
 * @author damir
 */
@Title("Kartica Partnera")
public class BWkarticaPartnera extends VerticalLayout implements View {

    Navigator navigator;

    public BWkarticaPartnera() throws SQLException {
        setResponsive(true);
        setSpacing(true);
        setMargin(false);
        HorizontalLayout topMenu = new HorizontalLayout();
        topMenu.setSizeFull();

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

        Label naslov = new Label("Kartica Partnera");
        naslov.setId("naslovKarticaPartnera");
        Label konto = new Label("Šifra konta:");
        konto.setId("konto");
        Label partner = new Label("Šifra partnera:");
        partner.setId("partner");
        HorizontalLayout prvi = new HorizontalLayout();
        prvi.setId("prvi");
        prvi.setSpacing(true);
        prvi.setResponsive(true);
        HorizontalLayout drugi = new HorizontalLayout();
        drugi.setId("drugi");
        drugi.setSpacing(true);
        drugi.setResponsive(true);

        TextField kontoField = new TextField();
        kontoField.setId("kontoField");
        kontoField.setInputPrompt("Pretraži");
        kontoField.focus();

        TextField partnerField = new TextField();
        partnerField.setId("partnerField");
        partnerField.setInputPrompt("Pretraži");
        TextField kontoResult = new TextField();
        kontoResult.setEnabled(false);
        TextField partnerResult = new TextField();
        partnerResult.setEnabled(false);

        Table podaci = new Table();
        podaci.addContainerProperty("Sifra partnera", String.class, null);
        try {
            SimpleJDBCConnectionPool connectionPool = new SimpleJDBCConnectionPool("org.postgresql.Driver", "jdbc:postgresql://10.1.2.3:5432/BW6", "postgres", "superset");
            SQLContainer container = new SQLContainer(new FreeformQuery(
                    "(\n"
                    + "SELECT FinKonto, FinPartner::TEXT, FinNalog, FinDatNal, FinVezni, FinDokument, FinDatDok, PartPreduzece, PartMesto, PartAdresa, PartDefTelefon, PartDefRacun, kontonaziv, KontoPartner, FinDuguje, FinPotrazuje, 0::numeric AS FinSaldo, FinZatDug, FinZatPot, SUBSTR('OZD'::text, FinStatZatvor::int4 + 1, 1) AS FinStatZatvor, (FinDuguje - FinZatDug) AS FinOtDug, (FinPotrazuje - FinZatPot) AS FinOtPot, 0::numeric AS FinOtSaldo, FinZatDug, FinZatPot, 0::numeric AS FinZatSaldo, 0::numeric AS ZatIznos, FinOpis, FinDatDos, FinStavka, FinTrosak, FinGodina, FinDevDug, FinDevPot, FinMoneta, partpib, partmaticnibroj, kontoprenos, kontodevizni, COALESCE(findelbroj, 0) AS findelbroj FROM BUSINESS.finex t1 \n"
                    + "LEFT OUTER JOIN (SELECT kontobroj, kontonaziv[1] AS kontonaziv, kontopartner, kontoprenos, kontodevizni FROM BUSINESS.kplan_1) t2 on t2.kontobroj = t1.finkonto \n"
                    + "LEFT OUTER JOIN (SELECT partsifra, partfirma, partpreduzece, partmesto, partposta, partadresa, partmaticnibroj, partdefracun, partdeftelefon, CASE WHEN LENGTH(TRIM(partpib)) = 0 THEN partmaticnibroj ELSE partpib END AS partpib, parttip, partpdvstatus, partstatus, partkategorija FROM BUSINESS.partneri) t3 ON t1.FinPartner = t3.partsifra  \n"
                    + "WHERE  finnalog = '1' AND  FinKonto = '2040' AND FinPartner = 1058  AND FinGodina >= 2016 AND FinDatNal BETWEEN '2016-01-01' AND '2017-01-11')\n"
                    + "UNION ALL\n"
                    + "(\n"
                    + "SELECT FinKonto, FinPartner::TEXT, FinNalog, FinDatNal, FinVezni, FinDokument, FinDatDok, PartPreduzece, PartMesto, PartAdresa, PartDefTelefon, PartDefRacun, kontonaziv, KontoPartner, FinDuguje, FinPotrazuje, 0::numeric AS FinSaldo, FinZatDug, FinZatPot, SUBSTR('OZD'::text, FinStatZatvor::int4 + 1, 1) AS FinStatZatvor, (FinDuguje - FinZatDug) AS FinOtDug, (FinPotrazuje - FinZatPot) AS FinOtPot, 0::numeric AS FinOtSaldo, FinZatDug, FinZatPot, 0::numeric AS FinZatSaldo, 0::numeric AS ZatIznos, FinOpis, FinDatDos, FinStavka, FinTrosak, FinGodina, FinDevDug, FinDevPot, FinMoneta, partpib, partmaticnibroj, kontoprenos, kontodevizni, COALESCE(findelbroj, 0) AS findelbroj FROM BUSINESS.finex t1 \n"
                    + "LEFT OUTER JOIN (SELECT kontobroj, kontonaziv[1] AS kontonaziv, kontopartner, kontoprenos, kontodevizni FROM BUSINESS.kplan_1) t2 on t2.kontobroj = t1.finkonto \n"
                    + "LEFT OUTER JOIN (SELECT partsifra, partfirma, partpreduzece, partmesto, partposta, partadresa, partmaticnibroj, partdefracun, partdeftelefon, CASE WHEN LENGTH(TRIM(partpib)) = 0 THEN partmaticnibroj ELSE partpib END AS partpib, parttip, partpdvstatus, partstatus, partkategorija FROM BUSINESS.partneri) t3 ON t1.FinPartner = t3.partsifra\n"
                    + "WHERE  finnalog <> '1' AND  FinKonto = '2040' AND FinPartner = 1058  AND FinGodina >= 2016 AND FinDatNal BETWEEN '2016-01-01' AND '2017-01-11')", connectionPool));
            podaci = new Table("", container);
            podaci.setSortEnabled(true);
            System.out.println(container);
            System.out.println("radi");

        } catch (SQLException e) {
            System.out.println("Zajebao si");
        }
        
        //Dodeljivanje imena kolona
        podaci.setColumnHeader("finkonto", "Šifra konta");
        podaci.setColumnHeader("finpartner", "Šifra partnera");
        podaci.setColumnHeader("finnalog", "Šifra partnera");
        podaci.setColumnHeader("findatnal", "Datum naloga");
        podaci.setColumnHeader("finvezni", "Šifra partnera");
        podaci.setColumnHeader("findokument", "Šifra dokumenta");
        podaci.setColumnHeader("findatdok", "Šifra partnera");
        podaci.setColumnHeader("partpreduzece", "Šifra partnera");
        podaci.setColumnHeader("partmesto", "Mesto partnera");
        podaci.setColumnHeader("partadresa", "Adresa partnera");
        podaci.setColumnHeader("partdeftelefon", "Telefon partnera");
        podaci.setColumnHeader("partdefracun", "Račun partnera");
        podaci.setColumnHeader("kontonaziv", "Konto naziv");
        podaci.setColumnHeader("kontopartner", "Konto partnera");
        podaci.setColumnHeader("finduguje", "Dugovanje partnera");
        podaci.setColumnHeader("finpotrazuje", "Potraživanje partnera");
        podaci.setColumnHeader("finnalog", "Šifra partnera");
        podaci.setColumnHeader("finnalog", "Šifra partnera");
        podaci.setColumnHeader("finnalog", "Šifra partnera");
        podaci.setColumnHeader("finnalog", "Šifra partnera");
        podaci.setColumnHeader("finnalog", "Šifra partnera");
        podaci.setColumnHeader("finnalog", "Šifra partnera");
        podaci.setColumnHeader("finnalog", "Šifra partnera");
        podaci.setColumnHeader("finnalog", "Šifra partnera");
        podaci.setColumnHeader("finnalog", "Šifra partnera");
        podaci.setColumnHeader("finnalog", "Šifra partnera");
        podaci.setColumnHeader("finnalog", "Šifra partnera");
        podaci.setColumnHeader("finnalog", "Šifra partnera");
        podaci.setColumnHeader("finnalog", "Šifra partnera");
        podaci.setColumnHeader("finnalog", "Šifra partnera");
        podaci.setColumnHeader("finnalog", "Šifra partnera");
        podaci.setColumnHeader("finnalog", "Šifra partnera");
                
//        podaci.addContainerProperty("Šifra partnera", String.class, null);
//podaci.addContainerProperty("Godine", Integer.class, null);
//podaci.addContainerProperty("Grad", String.class, null);
//// Show exactly the currently contained rows (items)
//        podaci.setPageLength(podaci.size());
        podaci.setId("tabela");
        podaci.setColumnAlignments(new Table.Align[]{Table.Align.LEFT, Table.Align.RIGHT, Table.Align.LEFT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT,
            Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT,
            Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT,
            Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT});
        podaci.setSizeFull();
        podaci.addStyleName("example-table");
        podaci.setLocale(Locale.ITALY);
        podaci.setSortEnabled(true);
        podaci.setWidth("98%");
//Table.sort(Object[] propertyId, boolean[] ascending);

        BwTable tabelaNasa = new BwTable();

        //Dodavanje komponenti
        topMenu.addComponents(navMeni, logout);
        addComponents(topMenu, naslov, prvi, drugi, podaci, tabelaNasa);

        //Dodavanje listenera
        kontoField.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(final FieldEvents.TextChangeEvent event) {
                Notification not = new Notification("Dobro");
                not.setPosition(Position.BOTTOM_CENTER);
                not.show(Page.getCurrent());
            }
        });
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        navigator = event.getNavigator();
    }

}