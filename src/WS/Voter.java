package WS;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import metier.Electeur;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.sql.*;
import java.util.Base64;
import java.util.HashMap;

@WebService(name = "Voter")
public class Voter {
    @WebMethod(operationName = "voterEnligne")
    public int voterEnligne(String electeur, String votebi){
        Electeur el = new Electeur();
        int vote=-1;
        String circons="";
        String repart="";
        Authentification auth = new Authentification();

        try{

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetSW?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");
            String QUERY= "select *from electeurs where nin = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setString(1,electeur);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                //tmp=rs.getString("nin");
                System.out.println("recuperer na circons bii");
                vote=rs.getInt("flagvoter");
                circons=rs.getString("circonscription");
                System.out.println(vote +" et "+ circons);
            }
            //pour verifier si la personne na pas encore voter
            if (vote==0){
                System.out.println("magui ci birr avant de transformer circos en hashmap");
                HashMap<String, String> retMap = new Gson().fromJson(circons, new TypeToken<HashMap<String, String>>() {}.getType()
                );
                System.out.println("magui ci birr if voter bii");
                 QUERY= "select  *from circonscription where nom =? and region =? and departement =? and arrondissement =?";
                 preparedStatement = connection.prepareStatement(QUERY);
                preparedStatement.setString(1,retMap.get("nom"));
                preparedStatement.setString(2,retMap.get("region"));
                preparedStatement.setString(3,retMap.get("departement"));
                preparedStatement.setString(4,retMap.get("arrondissement"));

                System.out.println(preparedStatement);

                 rs = preparedStatement.executeQuery();
                int id_cir=-1;
                while (rs.next()) {
                    id_cir=rs.getInt("id");
                }
                System.out.println("voici id circons concerner: "+id_cir);
                if (id_cir!=-1){
                    Gson gson = new Gson();

                    //String repartition = gson.toJson(detail.getRepartition());

                     QUERY= "select *from detailcirconscription where id_circonscription = ?";
                     preparedStatement = connection.prepareStatement(QUERY);
                    preparedStatement.setInt(1,id_cir);
                    System.out.println(preparedStatement);
                     rs = preparedStatement.executeQuery();

                    while (rs.next()) {

                        repart=rs.getString("repartition");
                    }
                    HashMap<String, Integer> repartitio = new Gson().fromJson(repart, new TypeToken<HashMap<String, Integer>>() {}.getType()
                    );
                    System.out.println(repartitio);
                    //pour recuperer la vote correspondante et comptabliser sa
                    for (String key :repartitio.keySet()){
                        if (votebi.toLowerCase().equals(key)){
                            repartitio.put(key,repartitio.get(key)+1);
                            System.out.println(gson.toJson(repartitio));
                            String rep=gson.toJson(repartitio);
                            System.out.println(rep);
                            preparedStatement = connection.prepareStatement("update detailcirconscription set repartition = ? where id_circonscription = ?");
                            preparedStatement.setString(1, rep);
                            preparedStatement.setInt(2, id_cir);
                            preparedStatement.executeUpdate();
                            System.out.println("vote bi diale na");
                            System.out.println(preparedStatement);
                            preparedStatement = connection.prepareStatement("update electeurs set flagvoter = ? where nin = ?");
                            preparedStatement.setInt(1, 1);
                            preparedStatement.setString(2, electeur);
                            preparedStatement.executeUpdate();
                            System.out.println("flag voter bii changer na nekk 1");
                            return 1;
                        }
                    }
                }
                else {
                   // dans le cas ou on recupere pas id de la circonscription
                    System.out.println();
                    return 0;
                }


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
