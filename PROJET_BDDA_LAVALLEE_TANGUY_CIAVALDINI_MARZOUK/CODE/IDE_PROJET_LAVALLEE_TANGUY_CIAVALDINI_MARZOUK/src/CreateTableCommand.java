import java.util.ArrayList;

public class CreateTableCommand {
	
	String relation;
	int nb_col;
	ArrayList<String> nom_col;
	ArrayList<String> type_col;
	/**
	 * Constructeur effectuant le parsing de la commande 
	 * @param chaine - String, commande donnée par utilisateur
	 */
 	public CreateTableCommand(String commande) {
		nom_col = new ArrayList<String>();
		type_col =new ArrayList<String>();
		
		String[] liste_commande= commande.split(" ");
		String[] col= liste_commande[3].split(",");
		
		this.relation=liste_commande[2];
		this.nb_col=col.length;
		
		for(String colonne: col) {
			String[] splitted = colonne.split(":");
			if(splitted[0].startsWith("(")) {
				splitted[0] = splitted[0].substring(1);
        	}
        	
			nom_col.add(splitted[0]);
			
			if(splitted[1].contains("INTEGER") && splitted[1].endsWith(")")) {
				splitted[1] = splitted[1].substring(0, splitted[1].length()-1);
			}
			else if(splitted[1].contains("VARCHAR") && splitted[1].endsWith("))")) {
				splitted[1] = splitted[1].substring(0, splitted[1].length()-1);
			}
			
		
			type_col.add(splitted[1]);
		}
	}
	
	public void execute() {
		PageId  HeaderPage = FileManager.getInstance().createNewHeaderPage();
		ArrayList<ColInfo> listeColInfo = new ArrayList<ColInfo>(nb_col);
		ColInfo col;
		
		for (int i =0; i<nom_col.size();i++) {
			col = new ColInfo(nom_col.get(i),type_col.get(i) );
			listeColInfo.add(col);
		}
		
		RelationInfo relinfo = new RelationInfo(this.relation, listeColInfo, HeaderPage);
		
		Catalog.getInstance().AddRelationInfo(relinfo);
		
		//System.out.println(Catalog.getInstance().GetRelationInfo(relation).toString());
	}
	
	/*public String toString() {
		return Catalog.getInstance().GetRelationInfo(relation).toString();
	}*/ 

	public String getRelation(){
		return relation;
	}

	public int getNb_Col(){
		return nb_col;
	}

	public void setNb_Col(int nb_col){
		this.nb_col=nb_col;
	}

	public ArrayList<String> getNom_col(){
		return nom_col;
	}

	public void setNom_col(ArrayList<String> nom_col){
		this.nom_col = nom_col;
	}

	public ArrayList<String> getType_col(){
		return type_col;
	}

	public void setType_col(ArrayList<String> type_col){
		this.type_col=type_col;
	}

	public String toString(){
		return "CREATE TABLE"+relation+", nom colonne:" + nom_col+ ", type colonne=" + type_col + ", nombre de Colonnes=" + nb_col;
	}


}
