import java.util.ArrayList;

public class InsertCommande {
    String nomRelation;
    ArrayList<String> valeurCol;

    public InsertCommande(String commande){
        this.valeurCol = new ArrayList<>();

        String [] liste_commande = commande.split(" ");
        this.nomRelation=liste_commande[2];

        String [] valeurs = liste_commande[3].split(",");
        for(String val : valeurs){
            valeurCol.add(val);
        }
    }

    public static void execute(){
        //Record r = new Record(nomRelation,valeurCol);
        //à continuer



    }
}

