package com.stephantasy;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Tri permettant de récupérer la Map dans l'ordre alphabétique des auteurs
 */
public class BiblioMapTriAlpha implements Comparator<Auteur> {
    @Override
    public int compare(Auteur o1, Auteur o2) {
        String nom1 = o1.getNom();
        String nom2 = o2.getNom();
        return nom1.trim().compareToIgnoreCase(nom2);
    }
}
