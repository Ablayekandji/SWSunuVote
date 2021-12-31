package metier;

import java.util.LinkedList;

public class Region {
    private String id;
    private String nom;
    private int nbrelecteur;
    private int nbrebureau;
    private LinkedList listedepartement;

    public Region(String id, String nom, int nbrelecteur, int nbrebureau, LinkedList listedepartement) {
        this.id = id;
        this.nom = nom;
        this.nbrelecteur = nbrelecteur;
        this.nbrebureau = nbrebureau;
        //this.listedepartement = listedepartement;
        this.listedepartement = listedepartement;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNbrelecteur() {
        return nbrelecteur;
    }

    public void setNbrelecteur(int nbrelecteur) {
        this.nbrelecteur = nbrelecteur;
    }

    public int getNbrebureau() {
        return nbrebureau;
    }

    public void setNbrebureau(int nbrebureau) {
        this.nbrebureau = nbrebureau;
    }

    public LinkedList getListedepartement() {
        return listedepartement;
    }

    public void setListedepartement(LinkedList listedepartement) {
        this.listedepartement = listedepartement;
    }
}
