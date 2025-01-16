package com.example.handleliste;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HandlelisteController {
    @Autowired
    private HttpSession session;

    @Autowired
    private JdbcTemplate db;

}
