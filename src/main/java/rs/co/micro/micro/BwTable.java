/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.co.micro.micro;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.ui.Table;
import java.sql.SQLException;
import java.util.Locale;

/**
 *
 * @author damir
 */
public class BwTable extends Table {

    public BwTable() throws SQLException{

        Table podaci2 = new Table();
        podaci2.addContainerProperty("Sifra partnera", String.class, null);
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
            podaci2 = new Table("", container);
            podaci2.setSortEnabled(true);
            System.out.println(container);
            System.out.println("radi");

        } catch (SQLException e) {
            System.out.println("Zajebao si");
        }

        //Dodeljivanje imena kolona
        podaci2.setColumnHeader("finkonto", "Šifra konta");
        podaci2.setColumnHeader("finpartner", "Šifra partnera");
        podaci2.setColumnHeader("finnalog", "Šifra partnera");
        podaci2.setColumnHeader("findatnal", "Datum naloga");
        podaci2.setColumnHeader("finvezni", "Šifra partnera");
        podaci2.setColumnHeader("findokument", "Šifra dokumenta");
        podaci2.setColumnHeader("findatdok", "Šifra partnera");
        podaci2.setColumnHeader("partpreduzece", "Šifra partnera");
        podaci2.setColumnHeader("partmesto", "Mesto partnera");
        podaci2.setColumnHeader("partadresa", "Adresa partnera");
        podaci2.setColumnHeader("partdeftelefon", "Telefon partnera");
        podaci2.setColumnHeader("partdefracun", "Račun partnera");
        podaci2.setColumnHeader("kontonaziv", "Konto naziv");
        podaci2.setColumnHeader("kontopartner", "Konto partnera");
        podaci2.setColumnHeader("finduguje", "Dugovanje partnera");
        podaci2.setColumnHeader("finpotrazuje", "Potraživanje partnera");
        podaci2.setColumnHeader("finnalog", "Šifra partnera");
        podaci2.setColumnHeader("finnalog", "Šifra partnera");
        podaci2.setColumnHeader("finnalog", "Šifra partnera");
        podaci2.setColumnHeader("finnalog", "Šifra partnera");
        podaci2.setColumnHeader("finnalog", "Šifra partnera");
        podaci2.setColumnHeader("finnalog", "Šifra partnera");
        podaci2.setColumnHeader("finnalog", "Šifra partnera");
        podaci2.setColumnHeader("finnalog", "Šifra partnera");
        podaci2.setColumnHeader("finnalog", "Šifra partnera");
        podaci2.setColumnHeader("finnalog", "Šifra partnera");
        podaci2.setColumnHeader("finnalog", "Šifra partnera");
        podaci2.setColumnHeader("finnalog", "Šifra partnera");
        podaci2.setColumnHeader("finnalog", "Šifra partnera");
        podaci2.setColumnHeader("finnalog", "Šifra partnera");
        podaci2.setColumnHeader("finnalog", "Šifra partnera");
        podaci2.setColumnHeader("finnalog", "Šifra partnera");

//        podaci.addContainerProperty("Šifra partnera", String.class, null);
//podaci.addContainerProperty("Godine", Integer.class, null);
//podaci.addContainerProperty("Grad", String.class, null);
//// Show exactly the currently contained rows (items)
//        podaci.setPageLength(podaci.size());
        podaci2.setId("tabela2");
        podaci2.setColumnAlignments(new Table.Align[]{Table.Align.LEFT, Table.Align.RIGHT, Table.Align.LEFT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT,
            Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT,
            Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT,
            Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT, Table.Align.RIGHT});
        podaci2.setSizeFull();
        podaci2.addStyleName("example-table2");
        podaci2.setLocale(Locale.ITALY);
        podaci2.setSortEnabled(true);
        podaci2.setWidth("98%");
        podaci2.setImmediate(true);
        System.out.println("Ubacena nasa tabela");
        
        
    }

}
