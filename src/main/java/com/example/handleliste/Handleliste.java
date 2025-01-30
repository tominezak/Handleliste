package com.example.handleliste;

public class Handleliste {
    private int id;
    private String vare;
    private boolean ok;

    public Handleliste(int id, String vare, boolean ok) {
        this.id = id;
        this.vare = vare;
        this.ok = ok;
    }

    //Tom konstruktør
    public Handleliste(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVare() {
        return vare;
    }

    public void setVare(String vare) {
        this.vare = vare;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}

