package WS;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import jdk.nashorn.internal.objects.Global;
import metier.Circonscription;
import metier.DetailCirconscription;
import metier.Resultat;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
@WebService(name = "CirconscriptionService")
public class CirconsriptionService {
    int veri=1,tmp=1;
    @WebMethod(operationName = "ajouterCirconscription")
    public int ajouterCirconscription(Circonscription circonscription){
        if (circonscription.getNom().isEmpty()||circonscription.getRegion().isEmpty()||circonscription.getDepartement().isEmpty()||circonscription.getArrondissement().isEmpty()||circonscription.getNbrebureau()==0){
            veri++;
        }
        if (!Pattern.matches("[a-zA-Z- ]*", circonscription.getRegion())||!Pattern.matches("[a-zA-Z- ]*", circonscription.getDepartement())||!Pattern.matches("[a-zA-Z- ]*", circonscription.getArrondissement())){
            veri++;
        }
        if (veri==1){
           // circonscription.getDepartement().charAt(0);
            String firstLtr = circonscription.getRegion().substring(0, 1);
            String restLtrs = circonscription.getRegion().substring(1, circonscription.getRegion().length());
            firstLtr = firstLtr.toUpperCase();
            circonscription.setRegion(firstLtr + restLtrs);
            System.out.println(circonscription.getRegion());
            //pour verifier si la region donner par l'admin est sur la base de donnees;
            try {

                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetSW?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");
                String QUERY= "select  *from region where nom_reg =? ";
                PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
                preparedStatement.setString(1,circonscription.getRegion());
                System.out.println(preparedStatement);
                ResultSet rs = preparedStatement.executeQuery();
                String a="";
                int idregion=1;

                while (rs.next()) {

                    a=rs.getString("nom_reg");
                    idregion=rs.getInt("id_reg");
                }
                System.out.println("region "+a+" id region: "+idregion);

                // pour gerer le cas du departement
                firstLtr = circonscription.getDepartement().substring(0, 1);
                restLtrs = circonscription.getDepartement().substring(1, circonscription.getDepartement().length());
                firstLtr = firstLtr.toUpperCase();
                circonscription.setDepartement(firstLtr + restLtrs);
                System.out.println(circonscription.getDepartement());
                QUERY= "select  *from departement where nom_dept =? ";
                preparedStatement = connection.prepareStatement(QUERY);
                preparedStatement.setString(1,circonscription.getDepartement());
                System.out.println(preparedStatement);
                 rs = preparedStatement.executeQuery();
                String b="";
                int id_dep_region=2;
                int id_dep=3;

                while (rs.next()) {

                    b=rs.getString("nom_dept");
                    id_dep_region=rs.getInt("id_reg");
                    id_dep=rs.getInt("id_dept");
                }
                System.out.println("departement "+b +"id departement: "+id_dep);
                // pour gerer le cas arrondissement
                circonscription.setArrondissement( circonscription.getArrondissement().toUpperCase());
                System.out.println(circonscription.getArrondissement());
                QUERY= "select  *from arrondissement where nom_arrondissement =? ";
                preparedStatement = connection.prepareStatement(QUERY);
                preparedStatement.setString(1,circonscription.getArrondissement());
                System.out.println(preparedStatement);
                rs = preparedStatement.executeQuery();
                String c="";
                int id_arron=4;
                while (rs.next()) {

                    c=rs.getString("nom_arrondissement");
                    id_arron=rs.getInt("id_dept");
                }


                System.out.println("arrondissement "+c);
                //System.out.println("le nbre de bureau :"+this.circonscription.getNbrebureau());
                if (a.isEmpty()||b.isEmpty()||c.isEmpty()){
                    veri++;
                    return veri;
                }
                else if(idregion==id_dep_region&&id_dep==id_arron) {
                    String query = "INSERT INTO circonscription values('"+ 0 +"','" + circonscription.getNom().toLowerCase() + "','" + circonscription.getRegion().toLowerCase() + "','" + circonscription.getDepartement().toLowerCase()  + "','" + circonscription.getArrondissement().toLowerCase() +"','"+ circonscription.getNbrebureau() +"')";

                    Statement sta = connection.createStatement();
                    System.out.println("avant sta");
                   int reponse = sta.executeUpdate(query);
                    System.out.println("valeur de stat: "+reponse);
                    if (reponse == 0){
                        System.out.println("dougou nafi");
                        return veri;

                    } else {
                        System.out.println("sa passe alors");
                        return veri;
                    }

                }
                else {
                    System.out.println("dafa melanger region arrondissement et departement you bokoul");
                    veri++;
                    return veri;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }
        return veri;
    }
    @WebMethod(operationName = "ajouterDetailCirconscription")
    public int ajouterDetailCirconscription(DetailCirconscription detail){
        if (detail.getNom().isEmpty()||detail.getRegion().isEmpty()||detail.getDepartement().isEmpty()||detail.getArrondissement().isEmpty()||detail.getNbrebureau()==0){
            tmp++;
        }
        if (tmp==1){
            try {

                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetSW?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");
                String QUERY= "select  *from circonscription where nom =? and region =? and departement =? and arrondissement =? and nbrebureau =?";
                PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
                preparedStatement.setString(1,detail.getNom());
                preparedStatement.setString(2,detail.getRegion());
                preparedStatement.setString(3,detail.getDepartement());
                preparedStatement.setString(4,detail.getArrondissement());
                preparedStatement.setInt(5,detail.getNbrebureau());
                System.out.println(preparedStatement);

                ResultSet rs = preparedStatement.executeQuery();
                int id_cir=-1;
                while (rs.next()) {
                    id_cir=rs.getInt("id");
                }
                System.out.println("voici id circons concerner: "+id_cir);
                if (id_cir!=-1){
                    Gson gson = new Gson();
                    String repartition = gson.toJson(detail.getRepartition());
                    QUERY = "INSERT INTO detailcirconscription values('"+ 0 +"','" + detail.getNbreinscrits() + "','" + detail.getNbrsuffrageexprime() + "','" + detail.getSuffragevalable()  + "','" + detail.getSuffrageinvalide() +"','"+ repartition+"','"+ id_cir  +"')";
                    Statement sta = connection.createStatement();
                    System.out.println("avant sta");
                    int reponse = sta.executeUpdate(QUERY);
                    int teup=reponse;
                    System.out.println("valeur de stat: "+reponse);
                    if (teup==0){
                        System.out.println("dara dougoul");
                        return tmp;
                    } else {
                        System.out.println("dougal nako ci birr ");
                        return tmp;
                    }

                }
                else {
                    tmp++;
                    System.out.println("du coup circonscription bimou diokhe exister woul ci base de donner bii ");
                    return tmp;
                }


                //System.out.println("le nbre de bureau :"+this.circonscription.getNbrebureau());
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }
        return tmp;
    }
    @WebMethod(operationName = "RecupererRepartionCirconscription")
        public String recupererRepartionCirconscription(String nom, String region, String departement, String arrondissement){
        String json="";
        Gson gson = new Gson();
            //String req="SELECT *FROM demo WHERE json_extract(niania, '$.key1')='value1'";
        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetSW?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");
            String QUERY= "select  *from circonscription where nom =? and region =? and departement =? and arrondissement =?";
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setString(1,nom);
            preparedStatement.setString(2,region);
            preparedStatement.setString(3,departement);
            preparedStatement.setString(4,arrondissement);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            int id=-1;

            while (rs.next()) {

                id=rs.getInt("id");

            }
            if (id!=-1){

                QUERY ="select repartition from detailcirconscription where id_circonscription =?";
                preparedStatement = connection.prepareStatement(QUERY);
                preparedStatement.setInt(1,id);
                System.out.println(preparedStatement);
                rs = preparedStatement.executeQuery();
                //int recup=-1;

                while (rs.next()) {

                    json=rs.getString("repartition");

                }

            }




        } catch (Exception exception) {
            exception.printStackTrace();
        }
            System.out.println(gson.toJson(json));
            HashMap<String, Integer> retMap = new Gson().fromJson(json, new TypeToken<HashMap<String, Integer>>() {}.getType()
            );
            System.out.println(retMap);
            //JE RETOURNE UNE STRING QUI EST AU FORMAT JSON MAINTENANT POUR LE CLIENT POUR AVOIR SA SOUS FORMAT JSON IL FAIT LOPERATION CI-DESSUS
        return gson.toJson(json);
    }

    @WebMethod(operationName = "InfosdetailsCirconscription")
    public DetailCirconscription InfosdetailsCirconscription(String nom, String region, String departement, String arrondissement){
        DetailCirconscription niania;
        Gson gson = new Gson();
        int nbreinscrit=0;
        int sufrageexp=0;
        int sufrageva=0;
        int sufragein=0;
        int nbrebureau=0;
        String repartition="";
        //String req="SELECT *FROM demo WHERE json_extract(niania, '$.key1')='value1'";
        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetSW?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");
            String query= "select  *from circonscription where nom =? and region =? and departement =? and arrondissement =?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,nom);
            preparedStatement.setString(2,region);
            preparedStatement.setString(3,departement);
            preparedStatement.setString(4,arrondissement);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            int id=-2;

            while (rs.next()) {

                id=rs.getInt("id");

            }
            if (id!=-2){

                query ="select *from detailcirconscription where id_circonscription =?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,id);
                System.out.println(preparedStatement);
                rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    nbreinscrit=rs.getInt("nbreinscrit");
                    sufrageexp=rs.getInt("nbrsuffrageexprime");
                    sufrageva=rs.getInt("suffragevalable");
                    sufragein=rs.getInt("suffrageinvalide");
                   repartition =rs.getString("repartition");

                }

            }
            else {

                return new DetailCirconscription("","","","",0,0,0,0,0,new HashMap<String, Integer>());
            }

        } catch (Exception exception) {
            exception.printStackTrace();

            //
        }

        Map<String, Integer> retMap = new Gson().fromJson(repartition, new TypeToken<HashMap<String, Integer>>() {}.getType());
        return new DetailCirconscription(nom,region,departement,arrondissement,nbrebureau,nbreinscrit,sufrageexp,sufrageva,sufragein,retMap);
    }

    @WebMethod(operationName = "ListeCirconscription")
    public List<Circonscription> ListeCirconscription(){
        String nom="";
        String region="";
        String departement="";
        String arrondissement="";
        int nbrebureau=0;
        List<Circonscription> liste=new LinkedList<>();


        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetSW?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");
            String QUERY= "select  *from circonscription ";
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            int a=0;
            while (rs.next()) {

                nom=rs.getString("nom");
                region=rs.getString("region");
                departement=rs.getString("departement");
                arrondissement=rs.getString("arrondissement");
                nbrebureau=rs.getInt("nbrebureau");
                liste.add(new Circonscription(nom,region,departement,arrondissement,nbrebureau));

            }
            System.out.println(liste);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return liste;
    }

    @WebMethod(operationName = "repartitionparCirconscription")
    public List<Resultat>  repartitionparCirconscription(){
        List<Resultat> liste=new LinkedList<>();
        Gson gson = new Gson();
        String nom="";
        String dept="";
        String arron="";
        String repart="";
        String region="";
        try {
            //niania

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetSW?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");
            String QUERY= "select nom,region,departement,arrondissement,repartition from circonscription,detailcirconscription where circonscription.id=detailcirconscription.id_circonscription";
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            int a=0;
            //System.out.println(rs);
            while (rs.next()) {
                // liste.add(gson.toJson(rs));
                nom=rs.getString("nom");
                region=rs.getString("region");
                dept=rs.getString("departement");
                arron=rs.getString("arrondissement");
                repart=rs.getString("repartition");
                System.out.println(gson.toJson(repart));
                liste.add(new Resultat(nom,region,dept,arron,repart));
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return liste;
    }

}
