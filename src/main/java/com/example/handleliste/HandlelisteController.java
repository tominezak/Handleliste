package com.example.handleliste;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HandlelisteController {

    @Autowired

    //Aksesserer databasen
    private JdbcTemplate db;

    //Logger for logging av feil og informasjon
    private Logger logger = LoggerFactory.getLogger(HandlelisteController.class);

    //Metode for å lagre ny vare i handleliste
    @PostMapping("/lagreHandleliste")
    public String lagreHandleliste(Handleliste handleliste) {
        String regexpVare = "^[A-Za-z0-9\s]{1,50}$"; //Kun bokstaver, tall og mellomrom + maks 50 tegn

        boolean ok = handleliste.getVare().matches(regexpVare);

        //Går innn i if-setning dersom input er gyldig
        if (ok) {
            try {
                //SQL-spørring for å sette inn ny vare i databasem
                String sql = "INSERT INTO handleliste (vare, ok) VALUES (?, ?)";
                db.update(sql, handleliste.getVare(), handleliste.isOk());
                return "Vare er lagret";
            } catch (Exception e) {
                logger.error("Feil i insetting av ny vare: " + e);
                return "Feil ved lagring av vare";
            }
        }
        return "Feil i inputvalidering";
    }

    //Metode for å hente hele handlelisten
    @GetMapping("/hentHandleliste")
    public List<Handleliste> hentHandleliste() {
        //SQL-spørring som henter alle rader fra handleliste-tabellen
        String sql = "SELECT * FROM handleliste";
        //Utfører spørring og mapper resultatet til en liste av varer
        return db.query(sql, new BeanPropertyRowMapper<>(Handleliste.class));
    }

    //Metode for å oppdatere statusen (ok) til en vare i handlelisten
    @PostMapping("/oppdaterHandleliste")
    public String oppdaterHandleliste(Handleliste handleliste) {
        try {
            //SQL-spørring for å oppdatere statusen til en vare basert på ID
            String sql = "UPDATE handleliste SET ok = ? WHERE id = ?";
            db.update(sql, handleliste.isOk(), handleliste.getId());
            return "Handleliste oppdatert";
        } catch (Exception e) {
            logger.error("Feil ved oppdatering av vare: " + e);
            return "Feil ved oppdatering av vare";
        }
    }

    //Metode for å slette en vare fra handlelisten basert på ID
    @PostMapping("/slettHandleliste")
    public String slettHandleliste(int id) {
        try {
            String sql = "DELETE FROM handleliste WHERE id = ?";
            db.update(sql, id);//Kjører slettingen
            return "Vare slettet";
        } catch (Exception e) {
            logger.error("Feil ved sletting av vare: " + e);
            return "Feil ved sletting av vare";
        }
    }
}