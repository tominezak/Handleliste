package com.example.handleliste;

public class Handleliste {
        private int id;
        private String navn;

    public Handleliste(int id, String navn) {
        this.id = id;
        this.navn = navn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }
}
