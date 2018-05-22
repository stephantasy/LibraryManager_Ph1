package com.stephantasy;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeSet;

/**
 * Tri permettant de récupérer la Map dans l'ordre alphabétique des auteurs
 */
public class BiblioMapTriAlpha implements Comparator<Map.Entry<Auteur, TreeSet<Livre>>> {
    @Override
    public int compare(Map.Entry<Auteur, TreeSet<Livre>> o1, Map.Entry<Auteur, TreeSet<Livre>> o2) {
        String nom1 = o1.getKey().getNom();
        String nom2 = o2.getKey().getNom();
        return nom1.trim().compareToIgnoreCase(nom2);
    }
}
