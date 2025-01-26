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
    private JdbcTemplate db;

    private Logger logger = LoggerFactory.getLogger(HandlelisteController.class);

    @PostMapping("/lagreHandleliste")
    public String lagreHandleliste(Handleliste handleliste) {
        String regexpVare = "^[A-Za-z0-9\s]{1,50}$";

        boolean ok = handleliste.getVare().matches(regexpVare);

        if (ok) {
            try {
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

    @GetMapping("/hentHandleliste")
    public List<Handleliste> hentHandleliste() {
        String sql = "SELECT * FROM handleliste";
        return db.query(sql, new BeanPropertyRowMapper<>(Handleliste.class));
    }

    @PostMapping("/oppdaterHandleliste")
    public String oppdaterHandleliste(Handleliste handleliste) {
        try {
            String sql = "UPDATE handleliste SET ok = ? WHERE id = ?";
            db.update(sql, handleliste.isOk(), handleliste.getId());
            return "Handleliste oppdatert";
        } catch (Exception e) {
            logger.error("Feil ved oppdatering av vare: " + e);
            return "Feil ved oppdatering av vare";
        }
    }

    @PostMapping("/slettHandleliste")
    public String slettHandleliste(int id) {
        try {
            String sql = "DELETE FROM handleliste WHERE id = ?";
            db.update(sql, id);
            return "Vare slettet";
        } catch (Exception e) {
            logger.error("Feil ved sletting av vare: " + e);
            return "Feil ved sletting av vare";
        }
    }
}