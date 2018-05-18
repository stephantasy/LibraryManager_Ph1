package com.stephantasy;

public class Livre {
    private int code;
    private String titre;
    private String catagorie;
    private int codeAuteur;
    private double prix;
    private int nbPage;

    public Livre(String titre, int code, int codeAuteur, String catagorie, int nbPage, double prix) {
        this.titre = titre;
        this.code = code;
        this.codeAuteur = codeAuteur;
        this.catagorie = catagorie;
        this.nbPage = nbPage;
        this.prix = prix;
    }


    public int getCode() {
        return code;
    }

    public String getTitre() {
        return titre;
    }

    public String getCatagorie() {
        return catagorie;
    }

    public int getCodeAuteur() {
        return codeAuteur;
    }

    public double getPrix() {
        return prix;
    }

    public int getNbPage() {
        return nbPage;
    }
}
