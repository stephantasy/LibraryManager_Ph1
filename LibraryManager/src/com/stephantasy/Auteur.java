package com.stephantasy;

// Note : la classe est Immutable car elle sert de clé à une Map !
public final class Auteur implements Comparable<Auteur>{
    private final int code;
    private final String nom;
    private final String pays;

    Auteur(String nom, int code, String pays)  {
        this.nom = nom;
        this.code = code;
        this.pays = pays;
    }

    Auteur(String[] data) throws BadDataForThisConstructor {
        //this(data[1], Integer.parseInt(data[0]), data[2]);
        try{
            this.nom = data[1];
            this.code = Integer.parseInt(data[0]);
            this.pays = data[2];
        }catch (Exception e){
            throw new BadDataForThisConstructor("Erreur dans les données du constructeur : Auteur : " + e);
        }
    }

    int getCode() {
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
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Auteur))return false;
        return code == ((Auteur)obj).code;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(code);
    }

    @Override
    public String toString() {
        return String.format("%6d\t%-30s\t%s", code, nom, pays);
    }

    @Override
    public int compareTo(Auteur o) {
        return Integer.compare(code, o.code);
    }
}
