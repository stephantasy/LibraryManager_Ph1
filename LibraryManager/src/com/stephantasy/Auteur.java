package com.stephantasy;

public class Auteur {
    private int code;
    private String nom;
    private String pays;

    public Auteur(String nom, int code, String pays) {
        this.nom = nom;
        this.code = code;
        this.pays = pays;
    }


    public int getCode() {
        return code;
    }

    public String getNom() {
        return nom;
    }

    public String getPays() {
        return pays;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return code;
    }
}
