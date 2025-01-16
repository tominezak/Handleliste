package com.example.handleliste;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class HandlelisteController {

    @Autowired
    private JdbcTemplate db;

    private Logger logger = LoggerFactory.getLogger(HandlelisteController.class);

    //Lagre et nytt produkt i handlelisten
    @PostMapping("/lagreProdukt")
    public boolean lagreProdukt(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sql = "INSERT INTO handleliste (navn) VALUES(?)";

        String produktNavn = request.getParameter("navn"); // Henter parameter fra forespørselen
        String regexpProdukt = "^[a-zA-Z0-9\\s]{1,50}$"; // Valideringsmønster

        // Validering av input
        boolean navnOK = produktNavn != null && produktNavn.matches(regexpProdukt);

        if (navnOK) {
            try {
                db.update(sql, produktNavn);
                return true; // Returnerer true hvis alt er OK
            } catch (Exception e) {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil ved lagring i databasen.");
                return false; // Returnerer false ved databasefeil
            }
        } else {
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Feil i input: Produktnavn må bestå av bokstaver, tall og mellomrom.");
            return false; // Returnerer false hvis input ikke valideres
        }
    }
}
