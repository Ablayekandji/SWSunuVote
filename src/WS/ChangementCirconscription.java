package WS;

import metier.Electeur;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(name = "ChangementCirconscription")
public class ChangementCirconscription {

    @WebMethod(operationName = "demandeChangement")
    public int demandeChangement(Electeur electeur){
        Inscription a= new Inscription();
        if (a.verifier(electeur)==1){
            System.out.println("niania");
            return 1;
        }

        else {
            System.out.println("probleme inscription non valide  donnees err");
            return 0;
        }



    }
}
