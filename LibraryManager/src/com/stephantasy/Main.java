package com.stephantasy;

import java.io.IOException;
import java.time.Instant;
import java.util.Collection;

public class Main {

    private static long lTotalTime;
    private static long lStartTime;

    public static void main(String[] args) {

        lStartTime = Instant.now().toEpochMilli();
        lTotalTime = lStartTime;

        doStuff();

        displayTime(true);



        // TODO :
        // - Faire test de performances avec des fichiers d'entrés BEAUCOUP plus long
        // - Ajouter une méthode "Map.Entry<Auteur, TreeSet<Livre>> getEntry()" et une variable Map.Entry<Auteur, TreeSet<Livre>> entry,
        // (on check avec un bool si on peut utiliser la variable au lien d'appeler la méthode), puis refaire le test
        // - Ajouter aussi une méthode de tri appelé seulement si dans le désordre
        // - Essayer des faire des recherches dans une "HashMap" et "LinkedHashMap" au-lieu d'une TreeMap (plus rapide)
    }

    private static void displayTime(boolean isTotal) {
        long lEndTime = Instant.now().toEpochMilli();
        long output;
        if(isTotal) {
            output = lEndTime - lTotalTime;
            System.out.println("Total time in milliseconds: " + output);
        }else{
            output = lEndTime - lStartTime;
            lStartTime = lEndTime;
            System.out.println("Elapsed time in milliseconds: " + output);
        }

    }

    private static void doStuff(){

        /*
         * TESTS FOURNIS PAR LE CLIENT
         */
        Signatures BdDonnees = new Bdd();

        System.out.println("========== Lecture des fichiers ==========");

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

        //System.out.println();
        displayTime(false);
        System.out.println("========== Manipulations ==========");

        Auteur unAuteur = new Auteur("KING, STEPHEN", 1,"USA");
        BdDonnees.addAuteur(unAuteur); //code existe déjà, doit être ignoré

        unAuteur = new Auteur("KING, STEPHEN", 321,"USA");
        BdDonnees.addAuteur(unAuteur);  // Ajout car code différent

        Livre unLivre = new Livre("Carrie", 222, 0,"Roman",645, 7.99);
        BdDonnees.addLivre(unLivre); // auteur inexistant, à ignorer

        unLivre = new Livre("Carrie", 222, 321,"Roman",645, 7.99);
        BdDonnees.addLivre(unLivre);    // Ajout car auteur existant

        unLivre = new Livre("LISTE MORTELLE", 232, 154,"Roman",439, 17.99);
        BdDonnees.addLivre(unLivre);    // ajout car auteur existant


        // Pour afficher les livres d'un auteur par son nom
        unAuteur = BdDonnees.getAuteur("VERNE, JULES");
        Collection oeuvres = BdDonnees.getColLivresAut(unAuteur);

        // prévoyez la méthode getNom dans Auteur
        if(!oeuvres.isEmpty()) {
            //System.out.println("\nLes oeuvres de " + unAuteur.getNom() + ":");
            for(Object l : oeuvres) {
                //System.out.println("\t" + l.toString());
            }
        }


        unLivre = new Livre("Carrie2", 55, 321,"Roman",645, 7.99);
        BdDonnees.addLivre(unLivre);    // Ajout car auteur existant


        //Pour afficher les livres d'un auteur par son code
        unAuteur = BdDonnees.getAuteur(39);
        oeuvres = BdDonnees.getColLivresAut(unAuteur);
        if(!oeuvres.isEmpty()) {
            //System.out.println("\nLes oeuvres de " + unAuteur.getNom() + ":");
            for(Object l : oeuvres) {
                //System.out.println("\t" + l.toString());
            }
        }

        //System.out.println();

        //afficher un livre selon son titre et afficher aussi le nom l'auteur (pas si évident)
        unLivre = BdDonnees.getLivre("ROBINSON CRUSOE");
        if(unLivre != null)
        {
            int codeNum = unLivre.getCodeAuteur();  //prévoyez cette méthode dans Livre
            //System.out.println(unLivre + " de " + BdDonnees.getAuteur(codeNum).getNom());
        }

        //afficher un livre selon son code et afficher aussi le nom l'auteur (pas si évident)
        unLivre = BdDonnees.getLivre(34);
        if(unLivre != null)
        {
            int codeNum = unLivre.getCodeAuteur();  //prévoyez cette méthode dans Livre
            //System.out.println(unLivre + " de " + BdDonnees.getAuteur(codeNum).getNom());
        }

        //String mapTemp = ((Bdd) BdDonnees).getMapInInsertOrder();

        //System.out.println();
        displayTime(false);
        System.out.println("========== Création des rapports ==========");


        //Créer les fichiers de rapports
        try {
            BdDonnees.rapportParAuteurs();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("rapportParAuteurs : ");
        displayTime(false);

        try {
            BdDonnees.rapportParLivres();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("rapportParLivres : ");
        displayTime(false);


        //Prévoyez un toString dans Bdd pour faire afficher en ordre de saisie des auteurs
        //
        //System.out.println(((Bdd) BdDonnees).toString2());

        System.out.println("******************************************************************");

        //System.out.println(BdDonnees);
    }
}
