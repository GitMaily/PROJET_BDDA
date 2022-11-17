import java.util.ArrayList;

public class CreateTableCommand {
	
	String relation;
	int nb_col;
	ArrayList<String> nom_col;
	ArrayList<String> type_col;
/*
	 * constructeur
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
			nom_col.add(splitted[0]);
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
		Catalog.getInstance().GetRelationInfo(relation);
	}
}
