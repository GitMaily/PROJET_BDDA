import java.util.ArrayList;

public class SelectCommand {
	
	private static String nomRel;
	private static ArrayList<Record> recordR;
	private static String[] cmd;
	
	public SelectCommand (String saisie) {
		recordR=new ArrayList<>();
	    spliter(saisie);
	
	}
	/**
	 * split la commande select dans un tableau 
	 * et reccupère le nom de la relation
	 * @param command
	 */
	private void spliter(String command) {
		cmd = command.split("WHERE");
		String [] tamp = cmd[0].split(" ");
		SelectCommand.nomRel=tamp[3];
	}
	
	
	
	
    public static void execute(){
    	ArrayList<Record> allRecords = null;
		ArrayList<SelectCondition> condition = new ArrayList<SelectCondition>();//fonction a faire 

    	if(cmd.length==2) {
    		 condition = liste_condi();//fonction a faire 
    		 allRecords= FileManager.getInstance().getAllRecords(Catalog.getInstance().GetRelationInfo(nomRel));
    	
    	
    	
    		 for(int i =0; i< allRecords.size() ;i++) {
    			 boolean resultat = true;
    			 while(i<condition.size() && resultat) {
    				 int indiceCol = liste_condi().get(i).getIndice();
    				 resultat= liste_condi().get(i).VerifCondition(indiceCol);// a un doute sur la forme Integer.valueOf(Record.values.get(indiceCol)) pas toucher execute pas finis
    			 }
    			 if(resultat) {
    				 recordR.add(allRecords.get(i));
    			 }
    		
    		 }
    	}else {
    		recordR=FileManager.getInstance().getAllRecords(Catalog.getInstance().GetRelationInfo(nomRel));
    	}
    	//affiche , NAN SANS DEC, ducoup pas toucher svp 
        for(int i=0; i<recordR.size(); i++) {
        	System.out.println(recordR.get(i).toString()+ " ; ");
        	
        }
        System.out.println("Total records= "+ recordR.size());
    }
    
    
    
   
    
    private static int donneIndiceCol (String NomCol) {
        RelationInfo r = Catalog.getInstance().GetRelationInfo(nomRel);

        for (int i = 0; i< r.getNb_col(); i++) {
        	if(r.getNom_col().get(i).getNom_col().equals(nomRel)) {
        		return i;
        	}
        }
        return -1;
    }
    
    
    private static SelectCondition SeparateurDeCommande (String commande) {
    	int indice = 0;
    	String operateur = null;
    	String value = null;
    	
    	
    	for(int i=0; i<SelectCondition.getComparateur().length;i++) {
    		if(commande.contains(SelectCondition.getComparateur()[i])) {
    			String condi[] = commande.split(SelectCondition.getComparateur()[i]);
    			indice = donneIndiceCol(condi[1].substring(0,condi[1].length()-1));
    			operateur = SelectCondition.getComparateur()[i];
    			if(condi[1].contains(" ")) {
    				value = condi[1].substring(0, condi[i].length()-1);
    			}
    			else {
    				value = condi[1]; 
    			}
    		}
    		
    		
    	}
    	return new SelectCondition(indice, operateur, value);
    }
    
    
    
    public static  ArrayList<SelectCondition> liste_condi(){
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
    
    
}
