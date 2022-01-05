package metier;

import javax.management.Query;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class Electeur {
    private String NIN;
    private String prenom;
    private String nom;
    private String datenaissance;
    private String adresse;
    private int numbureavote;
    private Circonscription circonscription = new Circonscription("","","","");
    private  String QUERY ;

    public Electeur(String NIN, String prenom, String nom, String datenaissance, String adresse, Circonscription circonscription) {
        this.NIN = NIN;
        this.prenom = prenom;
        this.nom = nom;
        this.datenaissance = datenaissance;
        this.adresse = adresse;
        this.circonscription =circonscription;
        //ici normalement je dois aller au niveau  de la base de donnees recuperer le nombre de bureau au niveau du circonscription
        //pour pouvoir lui donner un numero de bureau de vote
        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetSW?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");
            QUERY= "select  *from circonscription where nom =? and region =? and departement =? and arrondissement =?";
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
           preparedStatement.setString(1,this.circonscription.getNom());
            preparedStatement.setString(2,this.circonscription.getRegion());
            preparedStatement.setString(3,this.circonscription.getDepartement());
            preparedStatement.setString(4,this.circonscription.getArrondissement());
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();
            int a=0;

            while (rs.next()) {

                a=rs.getInt("nbrebureau");


            }
            System.out.println(a);
            this.circonscription.setNbrebureau(a);
            System.out.println("le nbre de bureau :"+this.circonscription.getNbrebureau());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.out.println("le nbre de bureau :"+this.circonscription.getNbrebureau());
        //this.numbureavote =  1 + (int)(Math.random() * ((this.circonscription.getNbrebureau() - 1) + 1));
        System.out.println("le numero :"+this.numbureavote);
    }

    public Electeur(){}

    public String getNIN() {
        return NIN;
    }

    public void setNIN(String NIN) {
        this.NIN = NIN;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDatenaissance() {
        return datenaissance;
    }

    public void setDatenaissance(String datenaissance) {
        this.datenaissance = datenaissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getNumbureavote() {
        return numbureavote;
    }

    public void setNumbureavote(int numbureavote) {
        this.numbureavote = numbureavote;
    }

    public Circonscription getCirconscription() {
        return circonscription;
    }

    public void setCirconscription(Circonscription circonscription) {
        this.circonscription = circonscription;
    }
}
