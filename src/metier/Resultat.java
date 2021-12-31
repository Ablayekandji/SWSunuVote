package metier;

import java.util.HashMap;

public class Resultat {
    private String nom;
    private String region;
    private String depart;
    private String arrondi;
    private String repart;

    public Resultat(String nom, String region, String depart, String arrondi, String repart) {
        this.nom = nom;
        this.depart = depart;
        this.arrondi = arrondi;
        this.repart = repart;
        this.region=region;
    }

    public Resultat() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getArrondi() {
        return arrondi;
    }

    public void setArrondi(String arrondi) {
        this.arrondi = arrondi;
    }

    public String getRepart() {
        return repart;
    }

    public void setRepart(String repart) {
        this.repart = repart;
    }

}
