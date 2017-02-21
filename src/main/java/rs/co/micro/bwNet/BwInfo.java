package rs.co.micro.bwNet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author zoran 26.05.2016
 */
public class BwInfo extends HttpServlet {

    static boolean isInitialized = false;
    public static String InstallPaket[];
    static String licencedTo;
    static boolean isAgencijskaVerzija;
    static boolean isBWLite;
    static String[] podrzaniJezici;
    static String bwVersion;
    static Date bwVerDate;
    static int brojKorisnickihLicenci;
    static Date zadnjiDatum;
    static Date datumUpotrebe;
    static Date tekuciDatum;
    static int daniUpozorenja;
    static Statement statement;
    static ResultSet resultSet;
    static String serijskiBroj;
    static Connection con;

    public BwInfo(Connection c, String username) {
        con = c;
        try {
            statement = getStatement();
        } catch (SQLException ex) {
            System.out.println(" Nije kreiran Statement " + ex);
            return;
        }
//DatumUpotrebe
        try {
            resultSet = statement.executeQuery("SELECT RegString FROM micro.bwreg WHERE RegToken = 'DatumUpotrebe'");
            if (!resultSet.first()) {
                return;
            }
            if (resultSet.getString("RegString") == null) {
                System.out.println("BwInfo.setDatumUpotrebe ");
                return;
            }
            datumUpotrebe = StringToDate(resultSet.getString("RegString"));
        } catch (SQLException ex) {
            System.out.println("BwInfo.setDatumUpotrebe " + ex);
            return;
        }
//DaniUpozorenja
        try {
            resultSet = statement.executeQuery("SELECT RegString FROM micro.bwreg WHERE RegToken = 'DaniUpozorenja'");
            resultSet.first();

            if (resultSet.getString("RegString") == null) {
                System.out.println("BwInfo.setDaniUpozorenja ");
                return;
            }
            daniUpozorenja = Integer.parseInt(resultSet.getString("RegString"));
        } catch (SQLException se) {
            System.out.println("BwInfo.setDaniUpozorenja " + se);
            return;
        }

//ZadnjiDatum
        String zadDat = "";
        try {
            resultSet = statement.executeQuery("SELECT RegString FROM micro.bwreg WHERE RegToken = 'ZadnjiDatum'");
            resultSet.first();
            if (resultSet.getString("RegString") == null) {
                System.out.println("BwInfo.setZadnjiDatum ");
                return;
            }
            zadDat = resultSet.getString("RegString");
            zadnjiDatum = StringToDate(resultSet.getString("RegString"));
            Calendar cal = null;
            String tekDat = "";
            try {
                ResultSet rsNow = getStatement().executeQuery("SELECT TIMENOW() AS datum");
                rsNow.first();
                cal = Calendar.getInstance();
                cal.setTimeInMillis(rsNow.getTimestamp("datum").getTime());
                tekuciDatum = cal.getTime();
                tekDat = rsNow.getString("datum").replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "").substring(0, 12);
            } catch (SQLException ex) {
                System.out.println("setZadnjiDatum");
                return;
            }
            //Da li je tekuci datum veci od krajnjeg datuma upotrebe
            if (tekuciDatum.after(datumUpotrebe)) {
                Date krajPlusDani = dateAdd(new java.sql.Date(datumUpotrebe.getTime()), daniUpozorenja);
                if (krajPlusDani.before(tekuciDatum)) {
                    //BwError.displayError("0002");
                    System.exit(0);
                } else {
                    //BwError.displayError("0003");
                    return;
                }
            }

            try {
                getStatement().execute("UPDATE micro.bwreg set RegString = '" + tekDat.trim() + "' WHERE RegToken = 'ZadnjiDatum'");
            } catch (SQLException sex1) {
                System.out.println("BwInfo.setZadnjiDatum1 " + sex1);
                return;
            }
        } catch (SQLException sex) {
            System.out.println("BwInfo.setZadnjiDatum " + sex);
            return;
        }
//BWVersion
        try {
            resultSet = statement.executeQuery("SELECT RegString, ENCODE(regvalue, 'escape') AS regvalue FROM micro.bwreg where RegToken = 'BWVersion'");
            resultSet.first();
            if (resultSet.getString("RegString") == null) {
                //BwError.displayError("0000");
                return;
            }
            Integer major = new Integer(resultSet.getString("RegString").substring(0, 3));
            Integer minor = new Integer(resultSet.getString("RegString").substring(3, 6));
            Integer revision = new Integer(resultSet.getString("RegString").substring(6));
            bwVersion = major.toString() + "." + minor.toString() + "." + revision.toString();
            byte[] regvalue = resultSet.getBytes("regvalue");
            String regstr = new String(regvalue);
            bwVerDate = getDate(Integer.parseInt(regstr.split("-")[2]), Integer.parseInt(regstr.split("-")[1]), Integer.parseInt(regstr.split("-")[0]));
        } catch (SQLException ex) {
            System.out.println("BwInfo.setBwVersion " + ex);
            return;
        }

//DozvoljenoKorisnika
        try {
            resultSet = statement.executeQuery("SELECT RegString FROM micro.bwreg WHERE RegToken = 'DozvoljenoKorisnika'");
            resultSet.first();
            if (resultSet.getString("RegString") == null) {
                System.out.println("BwInfo.setBrojKorisnickihLicenci ");
                //BwError.displayError("0001");
                return;
            }
            brojKorisnickihLicenci = Integer.parseInt(resultSet.getString("RegString"));
        } catch (SQLException ex) {
            System.out.println("BwInfo.setBrojKorisnickihLicenci " + ex);
            return;
        }

//BWLite
        try {
            resultSet = statement.executeQuery("SELECT RegString FROM micro.bwreg WHERE RegToken = 'BWLite'");
            if (resultSet.first()) {
                if (resultSet.getString("RegString") == null) {
                    System.out.println("BwInfo.setIsBWLite ");
                    //BwError.displayError("0001");
                    return;
                }
                isBWLite = resultSet.getBoolean("RegString");
            }
        } catch (SQLException ex) {
            System.out.println("BwInfo.setIsBWLite " + ex);
            return;
        }

//DozvoliViseDir
        try {
            resultSet = statement.executeQuery(
                    "select RegString from micro.bwreg where RegToken = 'DozvoliViseDir'");
            resultSet.first();
            if (resultSet.getString("RegString") == null) {
                System.out.println("BwInfo.setIsAgencijskaVrezija ");
                //BwError.displayError("0001");
                return;
            }
            isAgencijskaVerzija = resultSet.getBoolean("RegString");
        } catch (SQLException ex) {
            System.out.println("BwInfo.setIsAgencijskaVrezija " + ex);
            return;
        }

//InstalPaket
        String upit = "SELECT lozlogin, lozime, convert_from(lozpsw, 'UTF-8') as lozpsw, lozexpir, lozdatprom, lozid, loznivo, "
                    + "lozdatum, lozvreme, lozfiskalid, lozenabled, lozslika, lozpotpis, lozlang, lozadresa, lozposta, lozmesto, lozlk, lozjmbg FROM micro.loza "
                    + "WHERE lower(lozlogin) = '" + username + "'";
        try {
            resultSet = statement.executeQuery(upit);
            resultSet.next();
            int id = resultSet.getInt("lozid");
            int nivo = resultSet.getInt("loznivo");
            resultSet.close();
            upit = "SELECT x.* FROM "
                    + "("
                    + "SELECT regtoken AS ord, regstring AS str FROM micro.bwreg WHERE regtoken ILIKE (CASE WHEN (SELECT COUNT(*) AS br FROM micro.bwreg WHERE regtoken ILIKE '" + id + "BwMnu__') <> 0 THEN '" + id + "' ELSE '0'  END) ||'BwMnu__' ORDER BY regtoken"
                    + ") as x "
                    + "INNER JOIN ((SELECT regstring FROM micro.bwreg WHERE regtoken ILIKE (CASE WHEN (SELECT COUNT(*) AS br FROM micro.bwreg WHERE regtoken ILIKE '" + id + "BwMnu__') <> 0 THEN '" + id + "' ELSE '0' END) ||'BwMnu__' ORDER BY regtoken) "
                    + "             INTERSECT "
                    + "            (SELECT regstring FROM micro.bwreg WHERE regtoken ILIKE (CASE WHEN (SELECT COUNT(*) AS br FROM micro.bwreg WHERE regtoken ilike '" + nivo + "BwMnuNivo%') <> 0 THEN '" + nivo + "'||'BwMnuNivo%' ELSE '0'||'BwMnu__' END) ORDER BY regtoken)"
                    + "           ) as x2 ON regstring = str "
                    + "WHERE str IN (SELECT regstring FROM micro.bwreg WHERE regtoken = 'InstalPaket') "
                    + "ORDER BY ord";
            //System.out.println("upit: " + upit);

            resultSet = statement.executeQuery(upit);

            if (resultSet.first()) {
                resultSet.last();
                InstallPaket = new String[resultSet.getRow()];
                resultSet.beforeFirst();
                int i = 0;
                while (resultSet.next()) {
                    InstallPaket[i] = resultSet.getString("str");
                    i++;
                    
                }
            }
        } catch (SQLException ex) {
            System.out.println("procitajPakete \n" + upit + "\n" + ex);
            return;
        }

//LicencedTo
        try {
            resultSet = statement.executeQuery("SELECT RegString FROM micro.bwreg WHERE RegToken = 'LicencedTo'");
            resultSet.first();
            licencedTo = resultSet.getString("RegString");
        } catch (SQLException ex) {
            System.out.println("BwInfo.procitajLicence " + ex);
            return;
        }

//SerijskiBroj
        try {
            resultSet = statement.executeQuery("SELECT RegString FROM micro.bwreg WHERE RegToken = 'SerijskiBroj'");
            resultSet.first();
            serijskiBroj = resultSet.getString("RegString");
        } catch (SQLException ex) {
            System.out.println("BwInfo.procitajSerijskiBroj " + ex);
            return;
        }
        isInitialized = true;
    }

    /**
     *  Vraća Statemant od tekuće konekcije
     * @return Statement na tekuću konekciju
     * @throws java.sql.SQLException
    */
    public static Statement getStatement() throws SQLException {
        return con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }
    
    /**
     * Vraća tekuću konekciju na bazu podataka
     * @return tekuća konekcija 
     */
    public static Connection getCurentConnection() {
        return con;
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet logout</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet logout at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/index.html");
    }

    /**
     * Konvertuje datum zadat kao string u datum
     *
     * @param dateString Datum zadat kao string u formatu "GGGGMMDDHHSS"
     * @return Konvertovani datum
     */
    public static Date StringToDate(String dateString) {
        int y = new Integer(dateString.substring(0, 4));
        int m = new Integer(dateString.substring(4, 6));
        int d = new Integer(dateString.substring(6, 8));
        int h = new Integer(dateString.substring(8, 10));
        int min = new Integer(dateString.substring(10, 12));

        try {
            Calendar convCal = Calendar.getInstance();
            convCal.set(y, m - 1, d, h, min);
            Date date = convCal.getTime();

            return date;
        } catch (Exception ex) {
            System.out.println("BwInfo.convertStringToDate() " + ex);
            return null;
        }

    }

    /**
     * Dodaje datumu broj dana valute
     *
     * @param datum
     * @param valuta
     * @return datum sa dodatom valutom
     */
    public static java.sql.Date dateAdd(java.sql.Date datum, int valuta) {
        Calendar c = Calendar.getInstance();
        c.setTime(datum);
        c.add(Calendar.DATE, valuta);
        return new java.sql.Date(c.getTime().getTime());
    }

    /**
     * Vraća datum konstruisan od dana, meseca i godine
     *
     * @param dan dan u mesecu
     * @param mesec mesec u godini
     * @param godina godina datuma
     * @return java.util.Date
     */
    public static Date getDate(int dan, int mesec, int godina) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.set(godina, mesec - 1, dan);
        Date datum = cal.getTime();
        return datum;
    }

    /**
     * Vraća tekući datum
     *
     * @return java.util.Date
     */
    public static java.util.Date getDate() {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        return cal.getTime();
    }

    /**
     * Vraća tekuće vreme
     *
     * @return vreme
     */
    public static java.sql.Time getSQLTime() {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        return new java.sql.Time(cal.getTime().getTime());
    }

    /**
     * Vraća tekuću godinu
     *
     * @return godina
     */
    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        return cal.get(Calendar.YEAR);
    }

    /**
     * Vraća godinu od zadatog datuma
     *
     * @param datum
     * @return godina
     */
    public static int getCurrentYear(java.sql.Date datum) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datum);
        return cal.get(Calendar.YEAR);
    }

    /**
     * Vraća tekući mesec
     *
     * @return mesec
     */
    public static int getCurrentMonth() {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * Vraća mesec od zadatog datuma
     *
     * @param datum
     * @return mesec
     */
    public static int getCurrentMonth(java.sql.Date datum) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datum);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * Vraća tekući dan
     *
     * @return dan
     */
    public static int getCurrentDay() {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Vraća dan u mesecu od zadatog datuma
     *
     * @param datum
     * @return dan
     */
    public static int getCurrentDay(java.sql.Date datum) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datum);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Vraća putanju do sistemskog privremnog direktorijuma
     *
     * @return
     */
    public static String getTmpDir() {
        String tempdir = System.getProperty("java.io.tmpdir");
        if (!(tempdir.endsWith("/") || tempdir.endsWith("\\"))) {
            tempdir = tempdir + System.getProperty("file.separator");
        }
        return tempdir;
    }
}
