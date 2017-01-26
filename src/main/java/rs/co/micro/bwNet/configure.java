package rs.co.micro.bwNet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author damir
 */
@Theme("mytheme")
@Title("Configure")
public class configure extends VerticalLayout implements View {

    private Navigator navigator;

    //Promenjive iz fajla
    private static final String FILENAME = "b2b.properties";
    private static String bazaMicro;
    private static String serverAdresa;
    private static String portNumber;
    private static String username;
    private static String pass;

    //Podaci o prijavljenom korrisniku
    private static String loginName;
    private static String loginFullName;
    private static String loginPass;
    private static String lozdatum;
    private static byte[] fileBytes = null;

    //Va\e'a konekcija na konfigursanu bazu
    private static Connection con = null;

    private static File f;

    public configure() {
        setMargin(true);
        setSpacing(true);
        setResponsive(true);

//        VerticalLayout box = new VerticalLayout();
//        box.setSpacing(true);
//        box.setMargin(true);
//        box.setResponsive(true);
//        box.addStyleName("centriraj belaBoja boxBorder");
//        box.setWidthUndefined();
        Label ikona = new Label();
        ikona.setContentMode(ContentMode.HTML);
        ikona.setValue("<p id='ikonica' style='text-align:center;'></p>");

        Label naslov = new Label("Podešavanje");
        Label naslova = new Label("konekcije");
        naslov.setStyleName("naslov");
        naslova.setStyleName("naslov");

        final TextField polje1 = new TextField();
        polje1.setId("polje1");
        polje1.setCaption("Host (IP) adresa:");
        polje1.setIcon(FontAwesome.SERVER);
        polje1.focus();

        final TextField polje2 = new TextField();
        polje2.setCaption("Broj porta:");
        polje2.setIcon(FontAwesome.HASHTAG);

        final TextField polje3 = new TextField();
        polje3.setCaption("Baza:");
        polje3.setIcon(FontAwesome.DATABASE);

        final TextField polje4 = new TextField();
        polje4.setCaption("BwAdmin nalog:");
        polje4.setIcon(FontAwesome.USER);

        final PasswordField polje5 = new PasswordField();
        polje5.setCaption("BwAdmin šifra:");
        polje5.setIcon(FontAwesome.LOCK);

//        box.addComponents(polje1, polje2, polje3, polje4, polje5);
        Button button = new Button("OK");
        button.addStyleName(ValoTheme.BUTTON_PRIMARY);
        button.addStyleName(ValoTheme.BUTTON_LARGE);
        button.addStyleName("dugmence");
        button.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        button.setIcon(FontAwesome.CHECK_CIRCLE);
        button.addClickListener(e -> {
            bazaMicro = polje3.getValue();
            serverAdresa = polje1.getValue();
            portNumber = polje2.getValue();
            username = polje4.getValue();
            pass = polje5.getValue();
            save();
            try {
                con = Connect(serverAdresa, portNumber, bazaMicro, username, pass);
            } catch (SQLException ex) {
                Notification.show("Neuspela konekcija na bazu podataka sa snimljenim parametrima, molimo vas pokusajte ponovo");
                return;
            }
            navigator.addView("login", new login());
            navigator.navigateTo("login");
            if (!checkUser(username, pass)) {

            } else {
                navigator.addView("welcome", new welcome());
                navigator.navigateTo("welcome");
                System.out.println("USAO SAM!");
            }
        });

        addComponents(ikona, naslov, naslova, polje1, polje2, polje3, polje4, polje5, button);
        setComponentAlignment(ikona, Alignment.MIDDLE_CENTER);
        setComponentAlignment(naslov, Alignment.MIDDLE_CENTER);
        setComponentAlignment(naslova, Alignment.MIDDLE_CENTER);
        setComponentAlignment(polje1, Alignment.MIDDLE_CENTER);
        setComponentAlignment(polje2, Alignment.MIDDLE_CENTER);
        setComponentAlignment(polje3, Alignment.MIDDLE_CENTER);
        setComponentAlignment(polje4, Alignment.MIDDLE_CENTER);
        setComponentAlignment(polje5, Alignment.MIDDLE_CENTER);
        setComponentAlignment(button, Alignment.MIDDLE_CENTER);
    }

    @Override

    public void enter(ViewChangeEvent event) {
        navigator = event.getNavigator();
    }

    public static String getProperty(String key) {
        String ret = "";
        switch (key) {
            case "bazaMicro":
                ret = bazaMicro;
                break;
            case "serverAdresa":
                ret = serverAdresa;
                break;
            case "portNumber":
                ret = portNumber;
                break;
            case "user":
                ret = username;
                break;
            case "pass":
                ret = pass;
                break;
            default:
                ret = "";
        }
        return ret;
    }

    private void save() {
        Writer output = null;
        try {
            output = new BufferedWriter(new FileWriter(FILENAME, false));
            output.write(encrypt("bazaMicro=" + bazaMicro + "\r\n"));
            output.write(encrypt("serverAdresa=" + serverAdresa + "\r\n"));
            output.write(encrypt("portNumber=" + portNumber + "\r\n"));
            output.write(encrypt("user=" + username + "\r\n"));
            output.write(encrypt("pass=" + pass + "\r\n"));
            output.close();
        } catch (Exception ex) {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException ex1) {
            }
        }
    }

    private static synchronized String encrypt(String str) throws Exception {
        return rot39(str);
    }

    private static synchronized String decrypt(String str) throws Exception {
        return rot39(str);
    }

    private static String rot39(String data) {
        int UPPER_LIMIT = 125;
        int LOWER_LIMIT = 48;
        int CHARMAP = 39;
        try {
            byte[] buffer = data.getBytes("UTF-8");
            for (int iData = 0; iData < buffer.length; iData++) {
                int iCode = buffer[iData];
                if ((iCode >= LOWER_LIMIT) && (iCode <= UPPER_LIMIT)) {
                    iCode += CHARMAP;
                    if (iCode > UPPER_LIMIT) {
                        iCode = iCode - UPPER_LIMIT + LOWER_LIMIT - 1;
                    }
                    buffer[iData] = (byte) iCode;
                }
            }
            return new String(buffer, "UTF-8");

        } catch (java.io.UnsupportedEncodingException e) {
            System.out.println("Unicode/ISO ****Up!");
            System.exit(-1);
            return "";
        }
    }

    public static boolean load() {

        System.out.println("Ušo sam u LOAD");
        f = new File(FILENAME);

        if (f.exists()) {
            try {
                System.out.println("FAJL = " + f.getCanonicalPath());
                try (BufferedReader input = new BufferedReader(new FileReader(f))) {
                    String line;
                    while ((line = input.readLine()) != null) {
                        try {
                            switch (decrypt(line).split("=")[0]) {
                                case "bazaMicro":
                                    bazaMicro = decrypt(line).split("=")[1];
                                    System.out.println(bazaMicro);
                                    break;
                                case "serverAdresa":
                                    serverAdresa = decrypt(line).split("=")[1];
                                    System.out.println(serverAdresa);
                                    break;
                                case "portNumber":
                                    portNumber = decrypt(line).split("=")[1];
                                    System.out.println(portNumber);
                                    break;
                                case "user":
                                    username = decrypt(line).split("=")[1];
                                    System.out.println(username);
                                    break;
                                case "pass":
                                    pass = decrypt(line).split("=")[1];
                                    System.out.println(pass);
                                    break;
                                default:
                                    System.out.println("Loša promenjiva = " + decrypt(line));
                            }
                            try {
                                con = Connect(serverAdresa, portNumber, bazaMicro, username, pass);
                            } catch (SQLException ex) {

                            }
                        } catch (Exception ex) {
                            Notification.show("Neuspela konekcija na bazu podataka sa snimljenim podešavanjima, molimo vas pokusajte ponovo");
                            return false;
                        }
                    }
                }
                return true;
            } catch (IOException ex) {
                System.out.println("Neuspelo čitanje fajla!\n" + ex);
                return false;
            }
        } else {
            System.out.println("Fajl ne postoji!");
            return false;
        }
    }

    /**
     * Konektuje se na bazu podacima iz b2b.properties fajla koji mora postojati
     * na sistemu
     *
     * @param serverIP - IP adresa servera;
     * @param serverPort - port za postgreSQL bazu podataka na koji instalirana
     * baza osluškuje;
     * @param serverDB - naziv baze podataka na koju se konektujemo;
     * @param serverUser - sistemski nalog na bazu podataka;
     * @param serverPass - lozinka vezana za sisitemski nalog.
     * @return - konekciju na radnu bazu podataka
     * @throws SQLException metod mora biti pozvan unutar try {} catch
     */
    public static java.sql.Connection Connect(String serverIP, String serverPort, String serverDB, String serverUser, String serverPass) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Class.forName " + ex);
            return null;
        }
        return DriverManager.getConnection("jdbc:postgresql" + "://" + serverIP + ":" + serverPort + "/" + serverDB, serverUser, serverPass);
    }

    public static boolean checkUser(String username, String pass) {
        boolean st = false;
        try {
            Statement stm = con.createStatement();
            String upit = "SELECT lozlogin, lozime, convert_from(lozpsw, 'UTF-8') AS lozpsw, lozexpir, lozdatprom, lozid, loznivo, "
                    + "lozdatum, lozvreme, lozfiskalid, lozenabled, lozslika, lozpotpis, lozlang, lozadresa, lozposta, lozmesto, lozlk, lozjmbg FROM micro.loza "
                    + "WHERE lower(lozlogin) = '" + username.toLowerCase() + "'";
            System.out.println(upit);
            ResultSet rs = stm.executeQuery(upit);
            if (rs.next()) {
                System.out.println(BwInfo.getSQLTime());
                System.out.println("Username = " + rs.getString("lozlogin").toLowerCase());
                loginPass = rs.getString("lozpsw");
                if (loginPass.equals(pass)) {
                    loginName = rs.getString("lozlogin");
                    loginFullName = rs.getString("lozime");
                    lozdatum = rs.getDate("lozdatum").toString();
                    fileBytes = rs.getBytes("lozslika");
                    rs.close();
//                con.close();
                    con = DriverManager.getConnection("jdbc:postgresql" + "://" + configure.getProperty("serverAdresa") + ":" + configure.getProperty("portNumber") + "/" + configure.getProperty("bazaMicro"), username.toLowerCase(), pass);
                    st = true;
                } else {
                    System.out.println("Loša lozinka!");
                }
            } else {
                System.out.println("Nisam se prijavio !");
            }
        } catch (SQLException e) {
            System.out.println("Greska! " + e);
        }
        return st;
    }

    /**
     * Vraca login name korisnika koji se prijavio
     *
     * @return login name
     */
    public static String getLozime() {
        return loginName;
    }

    /**
     * Vraca lozinku prijavljenog korisnika
     *
     * @return vraca lozinku
     */
    public static String getLozPass() {
        return loginPass;
    }

    /**
     * Vraca datum poslednje prijave korisnika
     *
     * @return
     */
    public static String getLozdatum() {
        return lozdatum;
    }

    /**
     * Vraca puno ime i iprezime prijavljenog korisnika
     *
     * @return puno ime i prezime
     */
    public static String getLozaFullName() {
        return loginFullName;
    }

    public static void setUsername(String name) {
        loginName = name;
    }

    public static void setUserPass(String password) {
        loginPass = password;
    }

    /**
     * Vraca vazecu konekciju na konfigurisanu bazu podataka
     *
     * @return konekcija na bazu
     */
    public static Connection getConnection() {
        return con;
    }
}
