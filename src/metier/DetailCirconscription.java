package metier;

import java.util.HashMap;
import java.util.Map;

public class DetailCirconscription extends Circonscription {
    private int nbreinscrits;
    private int nbrsuffrageexprime;
    private int suffragevalable;
    private int suffrageinvalide;
    private Map<String, Integer> repartition;

    public DetailCirconscription(String nom, String region, String departement, String arrondissement, int nbrebureau, int nbreinscrits, int nbrsuffrageexprime, int suffragevalable, int suffrageinvalide, Map<String, Integer> repartition) {
        super(nom,region,departement,arrondissement,nbrebureau);
        this.nbreinscrits = nbreinscrits;
        this.nbrsuffrageexprime = nbrsuffrageexprime;
        this.suffragevalable = suffragevalable;
        this.suffrageinvalide = suffrageinvalide;
        this.repartition = repartition;
    }

    public DetailCirconscription() {
    }

    public int getNbreinscrits() {
        return nbreinscrits;
    }

    public void setNbreinscrits(int nbreinscrits) {
        this.nbreinscrits = nbreinscrits;
    }

    public int getNbrsuffrageexprime() {
        return nbrsuffrageexprime;
    }

    public void setNbrsuffrageexprime(int nbrsuffrageexprime) {
        this.nbrsuffrageexprime = nbrsuffrageexprime;
    }

    public int getSuffragevalable() {
        return suffragevalable;
    }

    public void setSuffragevalable(int suffragevalable) {
        this.suffragevalable = suffragevalable;
    }

    public int getSuffrageinvalide() {
        return suffrageinvalide;
    }

    public void setSuffrageinvalide(int suffrageinvalide) {
        this.suffrageinvalide = suffrageinvalide;
    }

    public Map<String, Integer> getRepartition() {
        return repartition;
    }

    public void setRepartition(Map<String, Integer> repartition) {
        this.repartition = repartition;
    }
}
