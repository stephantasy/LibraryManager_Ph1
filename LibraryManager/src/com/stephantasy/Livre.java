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

    public Livre(String[] data) throws BadDataForThisConstructor{
        try{
            //this(data[1], Integer.parseInt(data[0]), Integer.parseInt(data[3]), data[2], Integer.parseInt(data[5]), Double.parseDouble(data[4]));
            this.titre = data[1];
            this.code = Integer.parseInt(data[0]);
            this.codeAuteur = Integer.parseInt(data[3]);
            this.catagorie = data[2];
            this.nbPage = Integer.parseInt(data[5]);
            this.prix = Double.parseDouble(data[4]);
        }catch (Exception e){
            throw new BadDataForThisConstructor("Erreur dans les données du constructeur : Livre : " + e);
        }
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
