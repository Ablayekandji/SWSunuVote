package Server;

import WS.*;
import metier.Circonscription;
import metier.DetailCirconscription;
import metier.Electeur;
import metier.Region;

import javax.xml.ws.Endpoint;
import java.util.LinkedList;

public class Serveur {
    public static void main(String[] args){
        String url = "http://localhost:8686/";
        String url2 = "http://localhost:8787/";
        String url3 = "http://localhost:8585/";
        String url4 = "http://localhost:8484/";
        Endpoint.publish(url, new Inscription());
        Endpoint.publish(url2, new CirconsriptionService());
        Endpoint.publish(url3,new ChangementCirconscription());
        Endpoint.publish(url4,new Authentification());
        System.out.println("Web service Inscription demarre sur "+url);
        System.out.println("Web service CirconscriptionService demarre sur "+url2);
        System.out.println("Web service changement Circonscription demarre sur "+ url3);
        System.out.println("Web service Authentification demmarre sur "+ url4);
        Region thies = new Region("ninoi145","dakar",1452,456,new LinkedList());
        LinkedList a = new LinkedList();
        a.add("niania");
        a.add("nionio");
        thies.setListedepartement(a);

        System.out.println(thies.getListedepartement());

       // Electeur b = new Electeur("1915199700130","niania","nionio","12/01/1997","parcelle",new Circonscription("parcelle","thies","thies","thiesoues"));
       // System.out.println(b);
       // CirconsriptionService test= new CirconsriptionService();
        //test.ajouterCirconscription(new Circonscription("banlieu","dakar","dakar","pikine dagoudane",25));
    }




}
