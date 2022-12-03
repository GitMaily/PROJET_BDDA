import java.util.ArrayList;

public class InsertCommande {
     static String nomRelation;
     static ArrayList<String> valeurCol;

    public InsertCommande(String commande){
        InsertCommande.valeurCol = new ArrayList<>();

        String [] liste_commande = commande.split(" ");
        InsertCommande.nomRelation=liste_commande[2];

        String [] valeurs = liste_commande[4].split(",");
        for(String val : valeurs){
        	if(val.startsWith("(")) {
        		val = val.substring(1);
        	}
        	if(val.endsWith(")")) {
        		val = val.substring(0, val.length()-1);
        	}
            valeurCol.add(val);
        }
    }

    public static void execute(){
        Record r = new Record(Catalog.getInstance().GetRelationInfo(nomRelation), valeurCol);
        r.ri = FileManager.getInstance().InsertRecordInRel(r);
        DeleteValCol();
    }
    
    private static void DeleteValCol() {
    	for(int i=0; i<valeurCol.size();i++) {
    		valeurCol.remove(i);
    	}
    }		
}

