import java.lang.reflect.Array;
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
		this.nomRel=tamp[3];
	}
	
	
	
	
    public static void execute(){
    	ArrayList<Record> allRecords = null;
		ArrayList<SelectCondition> condition = new ArrayList<SelectCondition>();//fonction a faire 

    	if(cmd.length==2) {
    		 condition = liste_condi();//fonction a faire 
    		 allRecords= FileManager.getInstance().getAllRecords(Catalog.getInstance().GetRelationInfo(nomRel));
    	}
    	
    	
		for(int i =0; i< allRecords.size() ;i++) {
    		boolean resultat = true;
    		while(i<condition.size() && resultat) {
    			int indiceCol = liste_condi().get(i).getIndice();
    			resultat= liste_condi().get(i).VerifCondition();
    		}
    		
    	}
    	
    	
    	
    	
        for(int i=0; i<recordR.size(); i++) {
        	System.out.println(recordR.get(i).toString()+ " ; ");
        	
        }
        System.out.println("Total records= "+ recordR.size());
    }
    
    public static  ArrayList<SelectCondition> liste_condi(){
    	String[] condi = cmd[1].split("AND");
    	
    	ArrayList<SelectCondition> l_c = new ArrayList<>();
    	if(condi.length<=20) {
    		for(int i=0; i< condi.length;i++) {
    			l_c.add(null)
    		}
    	}
    	else {
    		System.out.println("plus de 20 conditions");
    	}
    	
    }
}
