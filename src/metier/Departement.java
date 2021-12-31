package metier;

import java.util.LinkedList;

public class Departement {
    private String id;
    private String nomregion;
    private String nom;
    private int nbrelecteur;
    private int nbrebureau;
    private LinkedList listearrondissement;

    public Departement(String id, String idregion, String nom, int nbrelecteur, int nbrebureau, LinkedList listearrondissement) {
        this.id = id;
        this.nomregion = idregion;
        this.nom = nom;
        this.nbrelecteur = nbrelecteur;
        this.nbrebureau = nbrebureau;
        this.listearrondissement = listearrondissement;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdregion() {
        return nomregion;
    }

    public void setIdregion(String idregion) {
        this.nomregion = idregion;
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

    public LinkedList getListearrondissement() {
        return listearrondissement;
    }

    public void setListearrondissement(LinkedList listearrondissement) {
        this.listearrondissement = listearrondissement;
    }
}
