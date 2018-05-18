package com.stephantasy;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Bdd implements Signatures{

    private Map<Auteur, Set<Livre>> map = new TreeMap<Auteur, Set<Livre>>();

    public Bdd(){
        // Constructeur vide
    }

    /**
     * Permet de lire un fichier d’auteurs passé en argument de créer la map et ajouter les auteurs à la map.
     *   => La partie valeur associée à chaque auteur est une nouvelle collection vide.
     * @param nomFichier
     * @throws IOException
     */
    @Override
    public void lireBddAut(String nomFichier) throws IOException {

    }

    /**
     * ajoute un auteur, passé en argument, dans la map.  La partie valeur associée est une nouvelle collection vide.
     *   => Si l’auteur s’y trouve déjà, l’ajout est ignoré (pas de remplacement).
     * @param a
     */
    @Override
    public void addAuteur(Auteur a) {

    }

    /**
     * permet de lire les livres à partir d'un fichier passé en argument.
     * o	Chaque livre est ajouté à la collection associé à son auteur.
     * o	Un livre dont l’auteur n’est pas déjà dans la map est ignoré silencieusement.
     * @param nomFichier
     * @throws IOException
     */
    @Override
    public void lireBddLivre(String nomFichier) throws IOException {

    }

    /**
     * ajoute un objet livre, passé en argument, à la collection de l’auteur dont le code est aussi passé en argument.
     * o	Si l’auteur n’est pas déjà dans la map, l’ajout du livre est ignoré.
     * @param l
     */
    @Override
    public void addLivre(Livre l) {

    }

    /**
     * Retourne l’objet Auteur, se trouvant dans la map associé à un auteur dont l’Auteur
     * (une référence sur un objet considéré égal) est passé en paramètre.
     * @param nom
     * @return
     */
    @Override
    public Auteur getAuteur(String nom) {
        return null;
    }

    /**
     * Surdéfinition de la précédente mais cette fois, c’est le code numérique de l’auteur qui est passé en paramètre.
     * @param codeAuteur
     * @return
     */
    @Override
    public Auteur getAuteur(int codeAuteur) {
        return null;
    }

    /**
     * Retourne l’objet livre, dont le titre est passé en argument.
     * @param titre
     * @return
     */
    @Override
    public Livre getLivre(String titre) {
        return null;
    }

    /**
     * Surdéfinition de la précédente mais cette fois, c’est le code numérique du livre qui est passé en paramètre.
     * @param codeLivre
     * @return
     */
    @Override
    public Livre getLivre(int codeLivre) {
        return null;
    }

    /**
     * Reçoit en paramètre un auteur et retourne sa collection de livre (valeur associée dans la Map)
     * @param unAuteur
     * @return
     */
    @Override
    public Collection getColLivresAut(Auteur unAuteur) {
        return null;
    }

    /**
     * Crée un fichier parAuteur.txt contenant la liste des auteurs et de leurs livres
     * par ordre alphabétique des auteurs puis des livres.
     * @throws IOException
     */
    @Override
    public void rapportParAuteurs() throws IOException {

    }

    /**
     * Crée un fichier parLivre.txt contenant la liste des livres et des auteurs
     * par ordre alphabétique des titres de livre.
     * @throws IOException
     */
    @Override
    public void rapportParLivres() throws IOException {

    }

    /**
     * Pour permettre l’affichage de la map en ordre croissant du nom des auteurs.
     * @return
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
