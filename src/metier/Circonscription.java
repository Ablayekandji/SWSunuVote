package metier;

public class Circonscription {
    private String nom;
    private String region;
    private String departement;
    private String arrondissement;
    //private int bureau;
    private int nbrebureau;

    //pour renseigner une circonscription
    public Circonscription(String nom, String region, String departement, String arrondissement, int nbrebureau) {
        this.nom = nom;
        this.region = region;
        this.departement = departement;
        this.arrondissement = arrondissement;
        this.nbrebureau = nbrebureau;
    }

    //pour attribuer une circonscription pour un electeur
    public Circonscription(String nom, String region, String departement, String arrondissement) {
        this.nom = nom;
        this.region = region;
        this.departement = departement;
        this.arrondissement = arrondissement;
    }


    public Circonscription() {
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

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getArrondissement() {
        return arrondissement;
    }

    public void setArrondissement(String arrondissement) {
        this.arrondissement = arrondissement;
    }

    public int getNbrebureau() {
        return nbrebureau;
    }

    public void setNbrebureau(int nbrebureau) {
        this.nbrebureau = nbrebureau;
    }
}
