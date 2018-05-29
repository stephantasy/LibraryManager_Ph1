package com.stephantasy;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;


public class Bdd implements Signatures{

    private static final String m_outPathAuteur = "RapportAuteurs.txt";
    private static final String m_outPathLivres = "RapportLivres.txt";

    private Map<Auteur, TreeSet<Livre>> mapBiblio = new HashMap<>();    // La HashMap donne les meilleurs résultats de rapidité

    private int m_lineNumberError = 0;   // Numéro de la ligne dans le fichier ayant provoqué l'erreur

    // Liste des auteurs dans l'ordre alphabétique
    private Set<Auteur> listeAuteurs = new TreeSet<>(new AuteurTriAlphabetique());
    private boolean isListeAuteursUpToDate = false;

    // Liste des livres ; On utilise une ArrayList pour l'utilisation de BinarySearch
    private List<Livre> listeLivres = new ArrayList<>();
    private boolean isListeLivresUpToDate = false;
    private boolean isListeLivresSorted = false;

    Bdd(){
        /* Constructeur vide demandé par le client */
    }

    /**
     * Permet de lire un fichier d’auteurs passé en argument de créer la map et ajouter les auteurs à la map.
     *   => La partie valeur associée à chaque auteur est une nouvelle collection vide.
     * @param nomFichier : Nom du fichier
     * @throws IOException : Erreur lors de la lecture
     */
    @Override
    public void lireBddAut(String nomFichier) throws IOException {

        // Utilisation d'un Try-With-Resource Statement pour fermer automatiquement le Stream
        try (Stream<String> stream = Files.lines( Paths.get(nomFichier), StandardCharsets.UTF_8))
        {
            m_lineNumberError = 0;
            stream.forEach(s -> addAuteur(getObject("AUTEUR", s)));
        }
        catch (IOException e)
        {
            throw new IOException("Erreur lors de la lecture du fichier " + nomFichier);
        }
    }

    /**
     * ajoute un auteur, passé en argument, dans la map.  La partie valeur associée est une nouvelle collection vide.
     *   => Si l’auteur s’y trouve déjà, l’ajout est ignoré (pas de remplacement).
     * @param a : Auteur à ajouter
     */
    @Override
    public void addAuteur(Auteur a) {
        // On a que la clé, donc la valeur est nulle
        mapBiblio.put(a, new TreeSet<>());
    }

    /**
     * permet de lire les livres à partir d'un fichier passé en argument.
     * o	Chaque livre est ajouté à la collection associé à son auteur.
     * o	Un livre dont l’auteur n’est pas déjà dans la map est ignoré silencieusement.
     * @param nomFichier Nom du fichier
     * @throws IOException  Erreur lors de la lecture du fichier
     */
    @Override
    public void lireBddLivre(String nomFichier) throws IOException {

        // Utilisation d'un Try-With-Resource Statement pour fermer automatiquement le Stream
        try (Stream<String> stream = Files.lines( Paths.get(nomFichier), StandardCharsets.UTF_8))
        {
            m_lineNumberError = 0;
            stream.forEach(s -> addLivre(getObject("LIVRE", s)));
        }
        catch (IOException e)
        {
            throw new IOException("Erreur lors de la lecture du fichier " + nomFichier);
        }
    }

    /**
     * ajoute un objet livre, passé en argument, à la collection de l’auteur dont le code est aussi passé en argument.
     * o	Si l’auteur n’est pas déjà dans la map, l’ajout du livre est ignoré.
     * @param l Livre à ajouter
     */
    @Override
    @SuppressWarnings("unchecked")
    public void addLivre(Livre l) {
        for(Map.Entry<Auteur, TreeSet<Livre>> entry : mapBiblio.entrySet()) {
            Auteur auteur = entry.getKey();

            // L'auteur est déjà présent, on lui ajoute le livre
            if(auteur.getCode() == l.getCodeAuteur()){
                TreeSet livres = entry.getValue();
                livres.add(l);
            }
        }
    }

    /**
     * Retourne l’objet Auteur, se trouvant dans la map associé à un auteur dont l’Auteur
     * (une référence sur un objet considéré égal) est passé en paramètre.
     * @param nom Nom de l'auteur
     * @return L'auteur associé au nom
     */
    @Override
    public Auteur getAuteur(String nom) {
        Set<Auteur> auteurs = mapBiblio.keySet();
        for (Auteur a : auteurs) {
            if(a.getNom().equalsIgnoreCase(nom)){
                return a;
            }
        }
        return null;
    }

    /**
     * Surdéfinition de la précédente mais cette fois, c’est le code numérique de l’auteur qui est passé en paramètre.
     * @param codeAuteur Code de l'auteur
     * @return  L'auteur associé au code
     */
    @Override
    public Auteur getAuteur(int codeAuteur) {
        Auteur auteurTemp = new Auteur("", codeAuteur, "");
        if(mapBiblio.containsKey(auteurTemp)) {
            Set<Auteur> auteurs = mapBiblio.keySet();
            for (Auteur a : auteurs) {
                if(a.getCode() == codeAuteur){
                    return a;
                }
            }
        }
        return null;
    }

    /**
     * Retourne l’objet livre, dont le titre est passé en argument.
     * @param titre Titre du livre
     * @return Le livre correspondant au titre
     */
    @Override
    public Livre getLivre(String titre) {
        if(!isListeLivresUpToDate){
            listeLivresUpdater();
        }
        if(!isListeLivresSorted){
            listeLivresSorter();
        }
        int index = Collections.binarySearch(listeLivres, new Livre(titre, -1, -1, "", -1, -1));
        if(index >= 0) {
            return listeLivres.get(index);
        }
        return null;
    }

    /**
     * Surdéfinition de la précédente mais cette fois, c’est le code numérique du livre qui est passé en paramètre.
     * @param codeLivre Code du livre
     * @return Le livre correspondant au code
     */
    @Override
    public Livre getLivre(int codeLivre) {
        if(!isListeLivresUpToDate){
            listeLivresUpdater();
        }
        for (Livre livreTemp : listeLivres) {
            if (livreTemp.getCode() == codeLivre) {
                return livreTemp;
            }
        }
        return null;
    }

    /**
     * Reçoit en paramètre un auteur et retourne sa collection de livre (valeur associée dans la Map)
     * @param unAuteur Auteur demandé
     * @return Une Collection contenant la liste des livres de l'auteur demandé (liste vide autrement)
     */
    @Override
    @SuppressWarnings("unchecked")
    public Collection getColLivresAut(Auteur unAuteur) {
        Collection collection = new TreeSet<Livre>();
        if(unAuteur != null) {
            collection.addAll(mapBiblio.get(unAuteur));
        }
        return collection;
    }

    /**
     * Crée un fichier parAuteur.txt contenant la liste des auteurs et de leurs livres
     * par ordre alphabétique des auteurs puis des livres.
     * @throws IOException Erreur lors de l'écriture dans le fichier
     */
    @Override
    public void rapportParAuteurs() throws IOException {
        // On crée le fichier
        Files.write(Paths.get(m_outPathAuteur), getMapToString().getBytes());
    }

    /**
     * Crée un fichier parLivre.txt contenant la liste des livres et des auteurs
     * par ordre alphabétique des titres de livre.
     * @throws IOException Erreur lors de l'écriture dans le fichier
     */
    @Override
    public void rapportParLivres() throws IOException {
        StringBuilder str = new StringBuilder();

        // On veut les auteurs dans l'ordre d'inserstion
        if(!isListeLivresSorted){
            listeLivresSorter();
        }

        // On compile les données
        for(Livre l : listeLivres) {
            str.append(l.toString()).append("\t").append(getAuteur(l.getCodeAuteur()).getNom()).append("\n");
        }

        // On crée le fichier
        Files.write(Paths.get(m_outPathLivres), str.toString().getBytes());
    }


    /**
     *
     * @return Renvoie une String avec le contenu de la Map dans l'ordre d'insertion des données
     */
    public String getMapInInsertOrder() {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<Auteur, TreeSet<Livre>> entry : mapBiblio.entrySet()) {
            TreeSet livres = entry.getValue();
            str.append(entry.getKey()).append(" :");
            StringBuilder strBuilder = new StringBuilder(str.toString());
            for(Object l : livres){
                strBuilder.append("\n\t").append(l);
            }
            str = new StringBuilder(strBuilder.toString());
            str.append("\n");
        }
        return str.toString();
    }


    /**
     * Redéfinition de toString
     * @return Renvoie la liste des auteurs en ordre croissant, ainsi que leurs oeuvres.
     */
    @Override
    public String toString() {
        return getMapToString();
    }



    /* ******************* */
    /* *   PRIVATE PART    */
    /* ******************* */


    /**
     * Permet de récupérer un Auteur ou un Livre lors de la lecture des fichiers texte
     * @param type Nom de l'objet demandé
     * @param data Données à passer au constructeur de l'objet
     * @param <T>  Type de l'objet demandé
     * @return  L'objet construit demandé
     */
    @SuppressWarnings("unchecked")
    private <T> T getObject(String type, String data) {
        m_lineNumberError++;
        T t = null;
        String[] s = data.trim().split("\t");
        ObjectFactory of = new ObjectFactory();
        try {
            t = (T) of.getObject(type, s);
        } catch (BadDataForThisConstructor e) {
            System.out.println(e.getMessage() + " (Ligne " + m_lineNumberError + ")");
        }
        return t;
    }

    /**
     * On met la liste des auteurs à jour
     */
    private void listeAuteursUpdater() {
        listeAuteurs.clear();
        listeAuteurs.addAll(mapBiblio.keySet());
        isListeAuteursUpToDate = true;
    }


    /**
     * On tri les livres en s'assurant d'abord que la liste soit à jour
     */
    private void listeLivresSorter() {
        if(!isListeLivresUpToDate){
            listeLivresUpdater();
        }
        Collections.sort(listeLivres);
        isListeLivresSorted = true;
    }

    /**
     * On met la liste des livres à jour
     */
    private void listeLivresUpdater() {
        listeLivres.clear();
        Collection<TreeSet<Livre>> list = mapBiblio.values();
        for (TreeSet<Livre> ts : list) {
            listeLivres.addAll(ts);
        }
        isListeLivresUpToDate = true;
    }

    /**
     *
     * @return Renvoie la liste des auteurs en ordre croissant, ainsi que leurs oeuvres.
     */
    private String getMapToString() {
        StringBuilder str = new StringBuilder();

        // On veut les auteurs dans l'ordre alphabétique
        if(!isListeAuteursUpToDate){
            listeAuteursUpdater();
        }

        // On compile les données
        for(Auteur a : listeAuteurs) {
            str.append(a.getNom()).append(" :\n");

            Collection livres = getColLivresAut(a);
            if(!livres.isEmpty()) {
                for (Object l : livres) {
                    str.append("\t").append(l.toString()).append("\n");
                }
            }
        }
        return str.toString();
    }
}

