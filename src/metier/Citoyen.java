package metier;

public class Citoyen {
    private String NIN;
    private String nom;
    private String prenom;

    public Citoyen(String NIN, String nom, String prenom) {
        this.NIN = NIN;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Citoyen() {
    }

    public String getNIN() {
        return NIN;
    }

    public void setNIN(String NIN) {
        this.NIN = NIN;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
