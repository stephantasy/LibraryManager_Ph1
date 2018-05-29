package com.stephantasy;

import java.util.Comparator;
/**
 * Tri permettant de récupérer la Map dans l'ordre alphabétique des auteurs
 */
public class AuteurTriAlphabetique implements Comparator<Auteur> {
    @Override
    public int compare(Auteur a1, Auteur a2) {
        String nom1 = a1.getNom();
        String nom2 = a2.getNom();
        return nom1.trim().compareToIgnoreCase(nom2);
    }
}
