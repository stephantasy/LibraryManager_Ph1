package com.stephantasy;

public class Livre implements Comparable<Livre> {
    private int code;
    private String titre;
    private String catagorie;
    private int codeAuteur;
    private double prix;
    private int nbPage;

    Livre(String titre, int code, int codeAuteur, String catagorie, int nbPage, double prix) {
        this.titre = titre;
        this.code = code;
        this.codeAuteur = codeAuteur;
        this.catagorie = catagorie;
        this.nbPage = nbPage;
        this.prix = prix;
    }

    Livre(String[] data) throws BadDataForThisConstructor{
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

    int getCode() {
        return code;
    }

    public String getTitre() {
        return titre;
    }

    public String getCatagorie() {
        return catagorie;
    }

    int getCodeAuteur() {
        return codeAuteur;
    }

    public double getPrix() {
        return prix;
    }

    public int getNbPage() {
        return nbPage;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Livre))return false;
        return titre.equalsIgnoreCase(((Livre)obj).titre);
    }

    @Override
    public int hashCode() {
        return titre.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%-42s %-12s %8s %6d", titre, catagorie, String.format("%1$,.2f", prix) + "$", code);
    }

    /**
     * On compare le nom du livre (supposé unique !)
     * @param o Livre à comparer
     * @return Entier
     */
    @Override
    public int compareTo(Livre o) {
        return titre.compareToIgnoreCase(o.titre);
    }
}
