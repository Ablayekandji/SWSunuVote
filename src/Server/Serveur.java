package Server;

import WS.*;

import javax.xml.ws.Endpoint;

public class Serveur {
    public static void main(String[] args){
        String url = "http://localhost:8686/";
        String url2 = "http://localhost:8787/";
        String url3 = "http://localhost:8585/";
        String url4 = "http://localhost:8484/";
        String url5 = "http://localhost:8383/";
        Endpoint.publish(url, new Inscription());
        Endpoint.publish(url2, new CirconsriptionService());
        Endpoint.publish(url3,new ChangementCirconscription());
        Endpoint.publish(url4,new Authentification());
        Endpoint.publish(url5,new Voter());
        System.out.println("Web service Inscription demarre sur "+url);
        System.out.println("Web service CirconscriptionService demarre sur "+url2);
        System.out.println("Web service changement Circonscription demarre sur "+ url3);
        System.out.println("Web service Authentification demmarre sur "+ url4);
        System.out.println("Web service Voter demmarre sur "+ url5);



    }




}
