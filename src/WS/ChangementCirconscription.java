package WS;

import metier.Circonscription;
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
            CirconsriptionService modif = new CirconsriptionService();
            return 1;
        }

        else {
            System.out.println("probleme inscription non valide  donnees err");
            return 0;
        }



    }
}
