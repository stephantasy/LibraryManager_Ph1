package com.stephantasy;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class Main {

    public static void main(String[] args) {

        /*
         * TESTS FOURNIS PAR LE CLIENT
          */
        Signatures BdDonnees = new Bdd();


        try {
            BdDonnees.lireBddAut("Auteurs.txt");
        } catch (IOException e) {
            System.out.println(e);
        }

        try {
            BdDonnees.lireBddLivre("Livres.txt");
        } catch (IOException e) {
            System.out.println(e);
        }


/*
        Auteur unAuteur = new Auteur("KING, STEPHEN", 1,"USA");
        BdDonnees.addAuteur(unAuteur); //code existe déjà, doit être ignoré

        unAuteur = new Auteur("KING, STEPHEN", 321,"USA");
        BdDonnees.addAuteur(unAuteur);

        Livre unLivre = new Livre("Carrie", 222, 0,"Roman",645, 7.99);
        BdDonnees.addLivre(unLivre); //auteur inexistant, à ignorer

        unLivre = new Livre("Carrie", 222, 321,"Roman",645, 7.99);
        BdDonnees.addLivre(unLivre);

        unLivre = new Livre("LISTE MORTELLE", 232, 154,"Roman",439, 17.99);
        BdDonnees.addLivre(unLivre);

        // Pour afficher les livres d'un auteur par son nom
        unAuteur = BdDonnees.getAuteur("VERNE, JULES");
        Collection oeuvres = BdDonnees.getColLivresAut(unAuteur);
        // prévoyez la méthode getNom dans Auteur
        if(oeuvres != null)
            System.out.println("Les oeuvres de " + unAuteur.getNom() + "\n"+ oeuvres);

        //Pour afficher les livres d'un auteur par son code
        unAuteur = BdDonnees.getAuteur(39);
        oeuvres = BdDonnees.getColLivresAut(unAuteur);
        if(oeuvres != null)
        System.out.println("Les oeuvres de " + unAuteur.getNom() + "\n"+ oeuvres);

        //afficher un livre selon son titre et afficher aussi le nom l'auteur (pas si évident)
        unLivre = BdDonnees.getLivre("ROBINSON CRUSOE");
        if(unLivre != null)
        {  int codeNum = unLivre.getCodeAuteur();  //prévoyez cette méthode dans Livre
        System.out.println(unLivre + " de " + BdDonnees.getAuteur(codeNum).getNom());
        }

        //Créer les fichiers de rapports
        try {
            BdDonnees.rapportParAuteurs();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BdDonnees.rapportParLivres();
        } catch (IOException e) {
            e.printStackTrace();
        }

*/

        //Prévoyez un toString dans Bdd pour faire afficher en ordre de saisie des auteurs
        System.out.println(BdDonnees);

    }
}
