import java.io.IOException;
import java.util.ArrayList;

public class InsertCommande {
     String nomRelation;
     ArrayList<String> valeurCol;

    public InsertCommande(String commande){
        this.valeurCol = new ArrayList<>();

        
        String [] liste_commande = commande.split(" ");
        this.nomRelation=liste_commande[2];
        String [] valeurs = null;
        
        if(liste_commande[3].contains("FILECONTENTS")) {
	        valeurs = liste_commande[3].split("FILECONTENTS");
        	ArrayList<String> valeursRecord = new ArrayList<String>();
        	String[] split = null;
        	
        	String nomFichier = valeurs[1].substring(1,valeurs[1].length()-1);
        	Readcsv csv = new Readcsv(nomFichier);
        	try {
        		
        		valeursRecord = csv.lireCsv();
        		String [] newValeurs = valeursRecord.toArray(new String[valeursRecord.size()]);
        		
        		for(int i = 0;i<newValeurs.length;i++) {
        			split = newValeurs[i].split(",");
        			for(String s : split) {
            			valeurCol.add(s);
        			}
        			execute();
        		}
        		//csv.affiche();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        else {
	        valeurs = liste_commande[4].split(",");
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
    }

    public void execute(){
        Record r = new Record(Catalog.getInstance().GetRelationInfo(nomRelation), valeurCol);
        RecordId rid = FileManager.getInstance().InsertRecordInRel(r); // r.ri = 
        r.setRi(rid);
        r.getRi().setPageId(rid.getPageId());
        r.getRi().setSlotIdx(rid.getSlotIdx());
        
        DeleteValCol();
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

