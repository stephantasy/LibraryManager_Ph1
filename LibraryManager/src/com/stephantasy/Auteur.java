package com.stephantasy;

// Note : la classe est Immutable car elle sert de clé à une Map !
public final class Auteur implements Comparable<Auteur>{
    private final int code;
    private final String nom;
    private final String pays;

    public Auteur(String nom, int code, String pays)  {
        this.nom = nom;
        this.code = code;
        this.pays = pays;
    }

    public Auteur(String[] data) throws BadDataForThisConstructor {
        //this(data[1], Integer.parseInt(data[0]), data[2]);
        try{
            this.nom = data[1];
            this.code = Integer.parseInt(data[0]);
            this.pays = data[2];
        }catch (Exception e){
            throw new BadDataForThisConstructor("Erreur dans les données du constructeur : Auteur : " + e);
        }
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
        return code == ((Auteur)obj).code;
    }

    @Override
    public int hashCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.format("%3d\t%-30s\t%s", code, nom, pays);
    }

    @Override
    public int compareTo(Auteur o) {
        if(code == o.code)
            return 0;
        else if(code > o.code)
            return 1;
        else
            return -1;
    }
}
