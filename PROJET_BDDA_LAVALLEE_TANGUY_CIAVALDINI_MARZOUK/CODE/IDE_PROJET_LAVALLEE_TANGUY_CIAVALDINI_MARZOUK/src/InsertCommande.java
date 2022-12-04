import java.util.ArrayList;

public class InsertCommande {
     String nomRelation;
     ArrayList<String> valeurCol;

    public InsertCommande(String commande){
        this.valeurCol = new ArrayList<>();

        String [] liste_commande = commande.split(" ");
        this.nomRelation=liste_commande[2];

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

    public void execute(){
        Record r = new Record(Catalog.getInstance().GetRelationInfo(nomRelation), valeurCol);
        FileManager.getInstance().InsertRecordInRel(r); // r.ri = 
        
        System.out.println(r.toString());
        
        //DeleteValCol();
        System.out.println();
    }
    
    private void DeleteValCol() {
    	valeurCol.removeAll(valeurCol);
    	
    	/*for(int i=0; i<valeurCol.size();i++) {
    		valeurCol.remove(i);
    	}*/
    }	
    
    public String toString() {
		return Catalog.getInstance().GetRelationInfo(nomRelation).toString();
	}
}

