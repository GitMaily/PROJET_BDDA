import java.util.ArrayList;

public class SelectCommand {
	private String saisie;
	private String nomRel;
	private ArrayList<Record> recordR;
	private String[] cmd;
	//private static int indice;
	
	public SelectCommand (String saisie) {
		this.saisie = saisie;
		recordR=new ArrayList<>();
	    spliter(saisie);
	
	}
	/**
	 * split la commande select dans un tableau 
	 * et reccupère le nom de la relation
	 * @param command
	 */
	private void spliter(String command) {
		this.cmd = command.split("WHERE");
        String[] tamp = cmd[0].split(" ");
        this.nomRel=tamp[3];
		
	}
	
	public ArrayList<Record> getRecordR() {
		return recordR;
	}
	public void setRecordR(ArrayList<Record> recordR) {
		this.recordR = recordR;
	}
	/**
	 * Permet de vérifier si le record répond à toutes les conditions de la selection, en comparant les valeurs qui ont été split au préalable.
	 * @param split Le tuple à comparer
	 * @param iterateur Le numéro du record dans la liste des records présents dans la relation
	 * @param allRecords 
	 * @return true si le record répond à toutes les conditions de la sélection, false sinon
	 */
	private boolean comparaison(String split, int iterateur, ArrayList<Record> allRecords) {

		String [] comparateurs = SelectCondition.getComparateur();
		String [] newSplit = null;

		
		String comparateur = null;
		for(int i =0;i<comparateurs.length;i++ ) { // i = 2
			if(split.contains(comparateurs[i])) {
				
				comparateur = comparateurs[i];
				newSplit = split.split(comparateurs[i]); // Cours=IF3BDDA // Note<10 // Note<18
				for(String splits : newSplit) {
					splits = splits.trim();
				}
			}
			
			if(comparateur != null) {
				i = comparateurs.length;

			}
		}
				
		boolean resultat = false;
		//for(int i = 0; i < sizeRecords ;i++) { // comparer nom type colonne
			
			int nbCol = allRecords.get(iterateur).getRelInfo().getNb_col();
			for(int j = 0; j < nbCol ; j ++) {
				String nomCol = allRecords.get(iterateur).getRelInfo().getNom_col().get(j).getNom_col();
				
				
				if(newSplit[0].trim().equals(nomCol)) {
    				String typeCol = allRecords.get(iterateur).getRelInfo().getNom_col().get(j).getType_col();
    				
    				switch(typeCol) {
    				
    				case "INTEGER" : 
    					switch(comparateur) { // Note = 16
            				case "=": resultat =  (Integer.parseInt(allRecords.get(iterateur).getValues().get(j))) == (Integer.parseInt(newSplit[1].trim())); 
            				/*System.out.println("On compare :"+Integer.valueOf(allRecords.get(iterateur).getValues().get(j))+" et:"+Integer.valueOf(newSplit[1].trim()));
            				System.out.println((Integer.parseInt(allRecords.get(iterateur).getValues().get(j))) == (Integer.parseInt(newSplit[1].trim())));
            				*/
            				break;
            				case "<": resultat =  Integer.parseInt(allRecords.get(iterateur).getValues().get(j)) < (Integer.parseInt(newSplit[1].trim())); break;
            				case ">": resultat =  (Integer.parseInt(allRecords.get(iterateur).getValues().get(j))) > (Integer.parseInt(newSplit[1].trim())); break;
            				case "<=": resultat =  (Integer.parseInt(allRecords.get(iterateur).getValues().get(j))) <= (Integer.parseInt(newSplit[1].trim())); break;
            				case ">=": resultat = (Integer.parseInt(allRecords.get(iterateur).getValues().get(j))) >= (Integer.parseInt(newSplit[1].trim())); break;
            				default: System.out.println("comparateur incorrect");
            						resultat = false;
    					}
    					break;

    				case "REAL" : 
    					switch(comparateur) { // Note = 16
            				case "=": resultat =  (Float.parseFloat(allRecords.get(iterateur).getValues().get(j))) == (Float.parseFloat(newSplit[1].trim())); 
            				break;
            				case "<": resultat =  (Float.parseFloat(allRecords.get(iterateur).getValues().get(j))) < (Float.parseFloat(newSplit[1].trim())); break;
            				case ">": resultat =  (Float.parseFloat(allRecords.get(iterateur).getValues().get(j))) > (Float.parseFloat(newSplit[1].trim())); break;
            				case "<=": resultat =  (Float.parseFloat(allRecords.get(iterateur).getValues().get(j))) <= (Float.parseFloat(newSplit[1].trim())); break;
            				case ">=": resultat = (Float.parseFloat(allRecords.get(iterateur).getValues().get(j))) >= (Float.parseFloat(newSplit[1].trim())); break;
            				default: System.out.println("comparateur incorrect");
            						resultat = false;
            						
            						
    					}
    					break;
    					
    				default:
    					if (comparateur.equals("=")) {
    						
    						resultat = allRecords.get(iterateur).getValues().get(j).trim().equals(newSplit[1].trim());
    					}
    					else {
    						System.out.println("Comparateur incorrect");
    						resultat = false;
    					}
    					
    				}
				
				}
				
			}

		//}
	
		return resultat;
	}
	
	public void afficherListeRecordId() {
		for(Record rec : recordR) {
			System.out.println("afficher chaque rec null ou pas?"+rec.getRi());
		}
	}
	
	public void execute() { // SELECT * FROM ToutesLesNotes // Cours=IF3BDDA AND Note<10 AND Note<18
		ArrayList<Record> allRecords = null;
		allRecords= FileManager.getInstance().getAllRecords(Catalog.getInstance().GetRelationInfo(nomRel));

		
		
		
		/**
		 * Cas _WHERE_
		 */
    	if(cmd.length==2) {

    		/**
    		 * Cas _AND_ 
    		 */
    		if(cmd[1].contains("AND")) { // Cours=IF3BDDA // Note<10 // Note<18
    			String [] splitAND = cmd[1].split("AND");
    			
    			for(int i = 0;i<splitAND.length;i++) {
    				splitAND[i] = splitAND[i].trim();
    			}
    			
    			boolean resultatAND = false;
    			for(int j = 0;j<allRecords.size();j++) {
	    			for(int y = 0;y<splitAND.length;y++) {
	    				
	    				resultatAND = comparaison(splitAND[y],j,allRecords);
	    				
	    				if(resultatAND == false) {
	    					y = splitAND.length;
	    				}
	    			}
	    			
	    			if(resultatAND == true) {
	    				
	      				 recordR.add(allRecords.get(j));
	
	    			}
    			}
    		}
    		
    		else { // Cas SELECT * FROM X WHERE _X_  // Cours=IF3BDDA 
    			
    			boolean resultat = false;
    			
    			for(int j = 0;j<allRecords.size();j++) {
	    			for(int y = 0;y<cmd.length;y++) {
	    				
	    				resultat = comparaison(cmd[1],j,allRecords);
	    				
	    				if(resultat == false) {
	    					y = cmd.length;
	    				}
	    			}
	    			
	    			if(resultat == true) {
	      				 recordR.add(allRecords.get(j));
	
	    			}
    			}
    		}
    	}
    	/**
    	 * Cas SELECT * FROM _X_
    	 */
    	else {
    		for(int i = 0;i<allRecords.size();i++) {
				if(allRecords.get(i).getRelInfo().getNom().equals(nomRel)) {
					recordR.add(allRecords.get(i));
				}

    		}
    	}
    	
    	for(int i=0; i<recordR.size(); i++) {
        	if( i == recordR.size()-1) {
            	System.out.println(recordR.get(i).toString()+ ".");

        	}
        	else {
            	System.out.println(recordR.get(i).toString()+ " ; ");

        	}
        	
        }
        System.out.println("Total records="+ recordR.size());
        
        //afficherListeRecordId();
	}
	
	
    
    public String toString() {
    	return saisie;
    }
    
    
}
