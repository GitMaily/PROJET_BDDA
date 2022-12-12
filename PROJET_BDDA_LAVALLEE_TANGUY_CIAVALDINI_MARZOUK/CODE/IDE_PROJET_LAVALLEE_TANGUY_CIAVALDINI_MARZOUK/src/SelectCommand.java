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
	
	/**
	 * Permet de vérifier si le record répond à toutes les conditions de la selection, en comparant les valeurs qui ont été split au préalable.
	 * @param split Le tuple à comparer
	 * @param iterateur Le numéro du record dans la liste des records présents dans la relation
	 * @return true si le record répond à toutes les conditions de la sélection, false sinon
	 */
	private boolean comparaison(String split, int iterateur) {
		ArrayList<Record> allRecords = null;
		allRecords= FileManager.getInstance().getAllRecords(Catalog.getInstance().GetRelationInfo(nomRel));

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
            				case "=": resultat =  (Integer.valueOf(allRecords.get(iterateur).getValues().get(j))) == (Integer.valueOf(newSplit[1].trim())); break;
            				case "<": resultat =  Integer.valueOf(allRecords.get(iterateur).getValues().get(j)) < (Integer.valueOf(newSplit[1].trim())); break;
            				case ">": resultat =  (Integer.valueOf(allRecords.get(iterateur).getValues().get(j))) > (Integer.valueOf(newSplit[1].trim())); break;
            				case "<=": resultat =  (Integer.valueOf(allRecords.get(iterateur).getValues().get(j))) <= (Integer.valueOf(newSplit[1].trim())); break;
            				case ">=": resultat = (Integer.valueOf(allRecords.get(iterateur).getValues().get(j))) >= (Integer.valueOf(newSplit[1].trim())); break;
            				default: System.out.println("comparateur incorrect");
            						resultat = false;
    					}
    					break;

    				case "REAL" : 
    					switch(comparateur) { // Note = 16
            				case "=": resultat =  (Float.valueOf(allRecords.get(iterateur).getValues().get(j))) == (Float.valueOf(newSplit[1].trim())); break;
            				case "<": resultat =  Float.valueOf(allRecords.get(iterateur).getValues().get(j)) < (Float.valueOf(newSplit[1].trim())); break;
            				case ">": resultat =  (Float.valueOf(allRecords.get(iterateur).getValues().get(j))) > (Float.valueOf(newSplit[1].trim())); break;
            				case "<=": resultat =  (Float.valueOf(allRecords.get(iterateur).getValues().get(j))) <= (Float.valueOf(newSplit[1].trim())); break;
            				case ">=": resultat = (Float.valueOf(allRecords.get(iterateur).getValues().get(j))) >= (Float.valueOf(newSplit[1].trim())); break;
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
	    				
	    				resultatAND = comparaison(splitAND[y],j);
	    				
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
	    				
	    				resultat = comparaison(cmd[1],j);
	    				
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
	}
	
	/**
	 * 
	 */
    public void execute2(){
    	ArrayList<Record> allRecords = null;
		ArrayList<SelectCondition> condition = new ArrayList<SelectCondition>();//fonction a faire 

    	if(cmd.length==2) {
    		 condition = liste_condi();//fonction a faire 
    		 allRecords= FileManager.getInstance().getAllRecords(Catalog.getInstance().GetRelationInfo(nomRel));
    		 String [] split = cmd[1].split("=");
    	
    	
    		 for(int i =0; i< allRecords.size() ;i++) {
    			 boolean resultat = true;
				 for(int j = 0;j<allRecords.get(i).getValues().size();j++) {

    			 //while(i<condition.size() && resultat && j < allRecords.size()) {
    					 
	    				 int indiceCol = liste_condi().get(i).getIndice();
	    				 String type = allRecords.get(i).getRelInfo().getNom_col().get(i).getType_col(); // methode mis en static et la variable qui va avec pour acces , pas reussi autrement
	    				 
	    				 if(type.equals( split[0])) {
		    				 switch(type) {
		    				 case "INTEGER":resultat= liste_condi().get(i).VerifCondition(Integer.valueOf(allRecords.get(indiceCol).getValues().get(indiceCol)));
		    				 break;
		    				 case "REAL": resultat= liste_condi().get(i).VerifCondition(Float.valueOf(allRecords.get(indiceCol).getValues().get(indiceCol)));
		    				 break;
		    				 default:resultat= condition.get(i).VerifCondition(String.valueOf(allRecords.get(indiceCol).getValues().get(indiceCol)));
		    				 break;
		    				 }
    				 
	    				 //j++;
	    				 //i++;

    				 //}
	    				 }
    			 }
    			 if(resultat == true) {
    				 recordR.add(allRecords.get(i));
    			 }
    		
    		 }
    	}else {
    		recordR=FileManager.getInstance().getAllRecords(Catalog.getInstance().GetRelationInfo(nomRel));
    	}
    	//affiche , NAN SANS DEC, ducoup pas toucher svp 
        for(int i=0; i<recordR.size(); i++) {
        	if( i == recordR.size()-1) {
            	System.out.println(recordR.get(i).toString()+ ".");

        	}
        	else {
            	System.out.println(recordR.get(i).toString()+ " ; ");

        	}
        	
        }
        System.out.println("Total records= "+ recordR.size());
    }
    
    
    
   
    
    private   int donneIndiceCol (String NomCol) {
        RelationInfo r = Catalog.getInstance().GetRelationInfo(nomRel);

        for (int i = 0; i< r.getNb_col(); i++) { // a revoir
        	if(r.getNom_col().get(i).getNom_col().equals(NomCol)) {
        		return i;
        	}
        }
        return -1;
    }
    
    
    private  SelectCondition SeparateurDeCommande (String commande) {
    	int indice = 0;
    	String operateur = null;
    	String value = null;
    	
    	
    	for(int i=0; i<SelectCondition.getComparateur().length;i++) {
    		if(commande.contains(SelectCondition.getComparateur()[i])) {
    			String condi[] = commande.split(SelectCondition.getComparateur()[i]);
    			indice = donneIndiceCol(condi[0].substring(1));//a revoir si cela compile (sinon ajouyer getindicecolonne)
    			operateur = SelectCondition.getComparateur()[i];
    			if(condi[1].contains(" ")) {
    				//value = condi[1].substring(0, condi[i].length()-1);
    				value = condi[1].trim();
    			
    			}
    			else {
    				value = condi[1]; 
    			}
    		}
    		
    		
    	}
    	System.out.println("***** Séparateur *****");
    	System.out.println("Indice = "+indice);
    	System.out.println("Operateur = "+operateur);
    	System.out.println("Value ="+value);
    	return new SelectCondition(indice, operateur, value);
    }
    
    
    
    public  ArrayList<SelectCondition> liste_condi(){
    	String[] condi = cmd[1].split("AND");
    	
    	ArrayList<SelectCondition> l_c = new ArrayList<>();
    	if(condi.length<=20) {
    		for(int i=0; i< condi.length;i++) {
    			l_c.add(SeparateurDeCommande(condi[i]));
    		}
    	}
    	else {
    		System.out.println("plus de 20 conditions");
    	}
    	
    	return l_c;
    	
    }
    
    public String toString() {
    	return saisie;
    }
    
    
}
