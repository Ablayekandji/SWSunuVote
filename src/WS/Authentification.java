package WS;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.management.Query;
import java.sql.*;
import java.util.Base64;

@WebService(name = "Authentification")
public class Authentification {

    //String password="passer123";

   // String strasBase64 = Base64.getEncoder().encodeToString(password.getBytes());
    @WebMethod(operationName = "CreerCompte")
    public int creerCompte(String nin,String pass1,String pass2){
        String tmp="";
        int flag=0;
          try{

              Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetSW?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");
            String QUERY= "select *from electeurs where nin = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setString(1,nin);
            //preparedStatement.setString(2,region);
            //preparedStatement.setString(3,depart);
            //preparedStatement.setString(4,aron);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                tmp=rs.getString("nin");
                flag=rs.getInt("flagvalide");
            }
            if (tmp.equals(nin) && pass1.equals(pass2) && flag==1 && pass1.length()>=6){
                System.out.println("interieur de if ");
                String motpcrypt = Base64.getEncoder().encodeToString(pass1.getBytes());
                String QUER ="INSERT INTO authentification (nin,motdepass) values('"+nin+"','"+motpcrypt+"')";
                Statement sta = connection.createStatement();
                System.out.println(sta);
                System.out.println("avant sta");
                int retour = sta.executeUpdate(QUER);
                System.out.println("valeur de stat: "+retour);
                if (retour == 0) {
                    System.out.println("mogui ci birr et sa passe pas");;
                    return 0;

                } else {
                    System.out.println("mogui ci birr et sa passe alors");
                    return 1;
                }
              }

                System.out.println(tmp);
            }catch (Exception exception){
              System.out.println(exception);
            }

        return  0;
    }

    @WebMethod(operationName = "connexion")
    public int connexion(String nin,String mdp){

            int c=0;
            int ad=-2;
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetSW?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");
            String motpcrypt = Base64.getEncoder().encodeToString(mdp.getBytes());
            System.out.println(motpcrypt);
            String QUERY= "select *from authentification where nin=? and  motdepass= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            //preparedStatement.setInt(1,a);
            System.out.println("avant sa");
            preparedStatement.setString(1,nin);
            preparedStatement.setString(2,motpcrypt);
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println(rs);
            System.out.println(preparedStatement);
            while (rs.next()) {
               // c=rs.getInt("id");
                ad=rs.getInt("admin");
            }
            if (ad==0){
                return 1; //electeur simple
            }
            if (ad==1){
                return 2; //admin
            }
            else {
                return 0;
            }
        }catch (Exception exception){
            System.out.println(exception);
        }


        return 0;
    }
}
