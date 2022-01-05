package WS;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mysql.cj.log.NullLogger;
import metier.Citoyen;
import metier.Electeur;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
@WebService(name = "InscriptionService")
public class Inscription {
    public int verification=1;
    @WebMethod(operationName = "Inscription")
    public int inscrireElecteur(Electeur electeur){
        if(electeur.getNIN().isEmpty()||electeur.getPrenom().isEmpty()||electeur.getNom().isEmpty()||electeur.getDatenaissance().isEmpty()||electeur.getAdresse().isEmpty()||electeur.getNumbureavote()==-1||electeur.getCirconscription()==null){
            if (electeur.getNIN().isEmpty())
                verification++;
            if (electeur.getPrenom().isEmpty())
                verification++;
            if (electeur.getNom().isEmpty())
                verification++;
            if (electeur.getDatenaissance().isEmpty())
                verification++;
            if (electeur.getAdresse().isEmpty())
                verification++;
            if (electeur.getNumbureavote()==-1)
                verification++;
            if (electeur.getCirconscription()==null || electeur.getCirconscription().getRegion().isEmpty()||electeur.getCirconscription().getDepartement().isEmpty()||electeur.getCirconscription().getRegion().isEmpty()||electeur.getCirconscription().getNom().isEmpty())
                verification++;


        }
        //pour l'instant j'ai pas geré le nbre de bureau sur le test
        if (!Pattern.matches("[0-9]+", electeur.getNIN())||!Pattern.matches("[a-zA-Z- ]*", electeur.getPrenom())||!Pattern.matches("[a-zA-Z- ]*", electeur.getNom())){
            verification++;
        }
        String date = electeur.getDatenaissance().substring(6, 10);
        System.out.println("recupe de date: "+date);
        if (2022 - Integer.parseInt(date) <= 17){
            verification++;
            System.out.println("niania niania");
        }
        int reponse=0;
       if(verification==1){
           try {

               Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetSW?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");

               new Electeur(electeur.getNIN(),electeur.getNom(),electeur.getPrenom(),electeur.getDatenaissance(),electeur.getAdresse(),electeur.getCirconscription());
               electeur.getCirconscription().setNbrebureau(electeur.getCirconscription().getNbrebureau());
               Gson gson = new Gson();
               String circons = gson.toJson(electeur.getCirconscription());
               electeur.setNumbureavote( 1 + (int)(Math.random() * ((electeur.getCirconscription().getNbrebureau() - 1) + 1)));
               if (electeur.getCirconscription().getNbrebureau()==0){
                   System.out.println("la circonscription n'existe pas");
                   return -1;

               }
               System.out.println("numero bureau bii: "+electeur.getNumbureavote());
               String query = "INSERT INTO electeurs values('" + electeur.getNIN() + "','" + electeur.getNom() + "','" + electeur.getPrenom() + "','" +
                       electeur.getDatenaissance() + "','" + electeur.getAdresse() +"','"+ circons +"','"  + electeur.getNumbureavote()+"','"  + "0" +"','"  + "0" +"')";

               Statement sta = connection.createStatement();
               System.out.println("avant sta");
                reponse = sta.executeUpdate(query);
               System.out.println("valeur de stat: "+reponse);
               if (reponse == 0) {
                   System.out.println("dougou nafi");
                   return 0;

               } else {
                   System.out.println("sa passe alors");
                   return 1;
               }
               // connection.close();
           } catch (Exception exception) {
               System.out.println("probleme de doublons la valeur de reponse est de"+reponse);
               exception.printStackTrace();
                     return reponse;
           }
       }
       if (verification==1){
           Inscription a= new Inscription();
           a.verifier(electeur);
       }

     return verification;
    }

    @WebMethod(operationName = "listeelecteursparregion")
    public List<Citoyen> listeelecteursparregion(String region){
        List<Citoyen> liste =new LinkedList<Citoyen>();

        try {
            System.out.println("niania");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetSW?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");
            String QUERY= "select *from electeurs where json_extract(circonscription, '$.region') =?";
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setString(1,region);
            //preparedStatement.setString(2,region);
            //preparedStatement.setString(3,depart);
            //preparedStatement.setString(4,aron);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            String cni;
            String no;
            String prenom;

            while (rs.next()) {

                cni=rs.getString("nin");
                no=rs.getString("nom");
                prenom=rs.getString("prenom");
                liste.add(new Citoyen(cni,no,prenom));
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

      return liste;
    }

    @WebMethod(operationName = "verifierElecteur")
    public int verifier(Electeur electeur){
        String cir="";
        int a=0;
        int b=0;
        int c=0;
        int tmp=0;
        int tmp2=0;

        //if (electeur.getCirconscription().getRegion()==)
        try {
            System.out.println("niania");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetSW?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");
            String QUERY= "select *from electeurs where nin = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setString(1,electeur.getNIN());
            //preparedStatement.setString(2,region);
            //preparedStatement.setString(3,depart);
            //preparedStatement.setString(4,aron);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                cir=rs.getString("circonscription");
            }
            Map<String, String> retMap = new Gson().fromJson(cir, new TypeToken<HashMap<String, String>>() {}.getType());
            System.out.println(retMap.get("region"));
            System.out.println(retMap.get("departement"));
            System.out.println(retMap.get("arrondissement"));
            //pour cas de region mettre la premiere string en majiscule
            String firstLtr = retMap.get("region").substring(0, 1);
            String restLtrs = retMap.get("region").substring(1, retMap.get("region").length());
            firstLtr = firstLtr.toUpperCase();
            retMap.put("region",firstLtr + restLtrs);
            System.out.println(retMap.get("region"));
            try{
                System.out.println("magui ci birr");
                QUERY= "select *from region where nom_reg =?";
                preparedStatement = connection.prepareStatement(QUERY);
                preparedStatement.setString(1,retMap.get("region"));
                 rs = preparedStatement.executeQuery();
                System.out.println("ci requete bi");
                while (rs.next()) {
                    //a prend id de la region
                    a=rs.getInt("id_reg");
                }

                System.out.println(a);
            }catch (Exception exception){

            }
            //pour verifier le departement
            try{
                 firstLtr = retMap.get("departement").substring(0, 1);
                 restLtrs = retMap.get("departement").substring(1, retMap.get("departement").length());
                firstLtr = firstLtr.toUpperCase();
                retMap.put("departement",firstLtr + restLtrs);
                System.out.println("departement apres modification: "+retMap.get("departement"));
                retMap.put("arrondissement",retMap.get("arrondissement").toUpperCase());
                System.out.println(retMap.get("arrondissement"));
                QUERY= "select *from departement where nom_dept=?";
                preparedStatement = connection.prepareStatement(QUERY);
                preparedStatement.setString(1,retMap.get("departement"));
                System.out.println(preparedStatement);
                System.out.println("a avant requete departement"+a);
                rs = preparedStatement.executeQuery();
                //preparedStatement.setString(1,retMap.get("arrondissement"));
                System.out.println(rs);
                while (rs.next()) {
                    b=rs.getInt("id_reg");
                    tmp=rs.getInt("id_dept");
                }

            }catch (Exception exception){

            }
            System.out.println("valeur de b: "+b);
            //pour verifier arrondissement bi
            try{

                QUERY= "select *from arrondissement where nom_arrondissement= ?";
                preparedStatement = connection.prepareStatement(QUERY);
                //preparedStatement.setInt(1,a);
                preparedStatement.setString(1,retMap.get("arrondissement"));
                rs = preparedStatement.executeQuery();
                System.out.println(rs);
                System.out.println(preparedStatement);
                while (rs.next()) {
                    c=rs.getInt("id_dept");
                }
                System.out.println("valaeur de c: "+c);

            }catch (Exception exception){

            }
           /*try{
                System.out.println("circonscription");
                QUERY= "select *from circonscription where nom ="+retMap.get("nom");
                preparedStatement = connection.prepareStatement(QUERY);
                //preparedStatement.setString(1,retMap.get("region"));
                rs = preparedStatement.executeQuery();
                System.out.println("ci requete bi");
                while (rs.next()) {
                    //a prend id de la region
                    tmp2=rs.getInt("id");
                }

                System.out.println(tmp2);
            }catch (Exception exception){

            }*/

            if (a==b&&tmp==c){
                System.out.println("niania");
                QUERY="UPDATE electeurs SET flagvalide = '1' where nin = "+electeur.getNIN();
                preparedStatement = connection.prepareStatement(QUERY);
                preparedStatement.executeUpdate(QUERY);
                //System.out.println(rs);
                System.out.println(preparedStatement);
                return 1;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    @WebMethod(operationName = "recupererElecteur")
    public String recupererElecteur(String nin){
        String cni="";
        String nom="";
        String prenom="";
        String circons="";
        int valide=0;
        Gson gson = new Gson();

        try {
            System.out.println("niania");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetSW?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");
            String QUERY= "select *from electeurs where nin = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setString(1,nin);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                cni=rs.getString("nin");
                nom=rs.getString("nom");
                prenom=rs.getString("prenom");
                circons=rs.getString("circonscription");
                valide=rs.getInt("flagvalide");

            }
            if (valide==1){
                System.out.println("inscription valide "+gson.toJson(gson.toJson(cni)+gson.toJson(nom)+gson.toJson(prenom)+circons));
                System.out.println(cni+","+nom+","+prenom+","+circons);
                return gson.toJson(gson.toJson(cni)+gson.toJson(nom)+gson.toJson(prenom)+circons);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }


        return "";
    }
}
