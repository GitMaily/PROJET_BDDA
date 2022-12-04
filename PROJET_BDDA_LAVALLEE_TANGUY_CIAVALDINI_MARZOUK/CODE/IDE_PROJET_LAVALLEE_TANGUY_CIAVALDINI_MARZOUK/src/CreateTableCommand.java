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
			
			if(splitted[1].endsWith("))")) {
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
		
		RelationInfo relinfo = new RelationInfo(this.relation,this.nb_col, listeColInfo, HeaderPage);
		
		Catalog.getInstance().AddRelationInfo(relinfo);
		
		//System.out.println(Catalog.getInstance().GetRelationInfo(relation).toString());
	}
	
	public String toString() {
		return Catalog.getInstance().GetRelationInfo(relation).toString();
	}
}
