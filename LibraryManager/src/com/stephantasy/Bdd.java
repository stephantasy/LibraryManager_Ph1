package com.stephantasy;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;


public class Bdd implements Signatures{

    private Map<Auteur, TreeSet<Livre>> mapBiblio = new TreeMap<>();

    private final String m_outPathAuteur = "RapportAuteurs.txt";
    private final String m_outPathLivres = "RapportLivres.txt";

    private int m_lineNumber = 0; // Pour tracer le numéro de ligne en erreur lors de la lecture d'un fichier
    /*private List <Map.Entry<Auteur, TreeSet<Livre>>> entryMapList;
    private boolean isEntryMapListUpToDate = false;
    private boolean isEntryMapListSorted = false;*/


    private List<Auteur> listeAuteurs = new ArrayList<>();
    private boolean isListeAuteursUpToDate = false;
    private boolean isListeAuteursSorted = false;

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
            m_lineNumber = 0;
            stream.forEach(s -> addToMap(getObject("AUTEUR", s)));
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
     * @param nomFichier
     * @throws IOException
     */
    @Override
    public void lireBddLivre(String nomFichier) throws IOException {

        // Utilisation d'un Try-With-Resource Statement pour fermer automatiquement le Stream
        try (Stream<String> stream = Files.lines( Paths.get(nomFichier), StandardCharsets.UTF_8))
        {
            m_lineNumber = 0;
            stream.forEach(s -> addToMap(getObject("LIVRE", s)));
        }
        catch (IOException e)
        {
            throw new IOException("Erreur lors de la lecture du fichier " + nomFichier);
        }
    }

    /**
     * ajoute un objet livre, passé en argument, à la collection de l’auteur dont le code est aussi passé en argument.
     * o	Si l’auteur n’est pas déjà dans la map, l’ajout du livre est ignoré.
     * @param l
     */
    @Override
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
     * @param nom
     * @return
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
     * @param codeAuteur
     * @return
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
     * @param titre
     * @return
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
     * @param codeLivre
     * @return
     */
    @Override
    public Livre getLivre(int codeLivre) {
        if(!isListeLivresUpToDate){
            listeLivresUpdater();
        }
        for(Iterator<Livre> i = listeLivres.iterator(); i.hasNext() ; ){
            Livre livreTemp = i.next();
            if(livreTemp.getCode() == codeLivre){
                return livreTemp;
            }
        }
        return null;
    }

    /**
     * Reçoit en paramètre un auteur et retourne sa collection de livre (valeur associée dans la Map)
     * @param unAuteur
     * @return
     */
    @Override
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
     * @throws IOException
     */
    @Override
    public void rapportParAuteurs() throws IOException {
        // On crée le fichier
        Files.write(Paths.get(m_outPathAuteur), getMapToString().getBytes());
    }

    /**
     * Crée un fichier parLivre.txt contenant la liste des livres et des auteurs
     * par ordre alphabétique des titres de livre.
     * @throws IOException
     */
    @Override
    public void rapportParLivres() throws IOException {
        String str = "";

        // On veut les auteurs dans l'ordre d'inserstion
        if(!isListeLivresSorted){
            listeLivresSorter();
        }

        // On compile les données
        for(Livre l : listeLivres) {
            str += l.toString() + "\t" +getAuteur(l.getCodeAuteur()).getNom() + "\n";
        }

        // On crée le fichier
        Files.write(Paths.get(m_outPathLivres), str.getBytes());
    }



    public String toString0() {
        String str = "";
        for (Map.Entry<Auteur, TreeSet<Livre>> entry : mapBiblio.entrySet()) {
            TreeSet livres = entry.getValue();
            str += entry.getKey() + " :";
            for(Object l : livres){
                str += "\n\t" + l;
            }
            str += "\n";
        }
        return str;
    }


    /**
     * Redéfinition de toString
     * @return Renvoie la liste des auteurs en ordre croissant, ainsi que leurs oeuvres.
     */
    @Override
    public String toString() {
        return getMapToString();
    }


    /* PRIVATE PART */


    /**
     * Ajout d'auteurs ou de livres à notre Map
     * @param obj
     * @param <T>
     */
    private <T> void addToMap(T obj) {
        if( obj instanceof Auteur) {
            addAuteur((Auteur) obj);
        }
        else if( obj instanceof Livre) {
            addLivre((Livre) obj);
        }
    }

    /**
     * Permet de récupérer un Auteur ou un Livre lors de la lecture des fichiers texte
     * @param type
     * @param data
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    private <T> T getObject(String type, String data) {
        m_lineNumber++;
        T t = null;
        String[] s = data.trim().split("\t");
        ObjectFactory of = new ObjectFactory();
        try {
            t = (T) of.getObject(type, s);
        } catch (BadDataForThisConstructor e) {
            System.out.println(e.getMessage() + " (Ligne " + m_lineNumber + ")");
        }
        return t;
    }

    // Map Entry List
    /*private List<Map.Entry<Auteur, TreeSet<Livre>>> getEntrySet(){
        entryMapList = new ArrayList<>(mapBiblio.entrySet());
        isEntryMapListUpToDate = true;
        return entryMapList;
    }

    // Sorted Map Entry List
    private void entryMapListSorter(){
        if(!isEntryMapListUpToDate){
            entryMapList = getEntrySet();
        }
        //Collections.sort(entryMapList, new BiblioMapTriAlpha());
        isEntryMapListSorted = true;
    }*/



    // On tri les auteurs en s'assurant d'abord que la liste soit à jour
    private void listeAuteursSorter() {
        if(!isListeAuteursUpToDate){
            listeAuteursUpdater();
        }
        Collections.sort(listeAuteurs, new BiblioMapTriAlpha());
        isListeAuteursSorted = true;
    }

    // On met la liste des auteurs à jour
    private void listeAuteursUpdater() {
        listeAuteurs.clear();
        Collection<Auteur> list = mapBiblio.keySet();
        for (Auteur a : list) {
            listeAuteurs.add(a);
        }
        isListeAuteursUpToDate = true;
    }


    // On tri les livres en s'assurant d'abord que la liste soit à jour
    private void listeLivresSorter() {
        if(!isListeLivresUpToDate){
            listeLivresUpdater();
        }
        Collections.sort(listeLivres);
        isListeLivresSorted = true;
    }

    // On met la liste à jour
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
        String str = "";

        // On veut les auteurs dans l'ordre d'inserstion
        if(!isListeAuteursSorted){
            listeAuteursSorter();
        }

        // On compile les données
        for(Auteur a : listeAuteurs) {
            str += a.getNom() + " :\n";

            Collection livres = getColLivresAut(a);
            if(!livres.isEmpty()) {
                for (Object l : livres) {
                    str += "\t" + l.toString() + "\n";
                }
            }
        }
        return str;
    }
}

