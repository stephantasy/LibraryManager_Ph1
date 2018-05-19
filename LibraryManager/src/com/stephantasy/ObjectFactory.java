package com.stephantasy;

/**
 * Design Pattern FACTORY
 */
public class ObjectFactory {
    /**
     * Renvoie un Auteur ou un Livre
     * @param type : Objet voulu
     * @param data : Paramètres pour le constructeur de l'objet
     * @return : L'objet demandé
     */
    public Object getObject(String type, String[] data) throws BadDataForThisConstructor{
        try {
            if(type.equalsIgnoreCase("AUTEUR")){
                return new Auteur(data);
            }
            else if(type.equalsIgnoreCase("LIVRE")){
                return new Livre(data);
            }
        }catch (Exception e){
            throw new BadDataForThisConstructor(e.getMessage());
        }
        return null;
    }
}
