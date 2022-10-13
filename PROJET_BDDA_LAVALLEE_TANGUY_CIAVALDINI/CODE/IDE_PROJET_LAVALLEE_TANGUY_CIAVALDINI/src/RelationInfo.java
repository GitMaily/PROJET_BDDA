
public class RelationInfo {
	
	private String nom; //nom de la relation
	private int nb_col; // le nombre de colonnes
	private String[] nom_col; // le nom des colonnes
	private String[] type_col; // le types des colonnes
	
	// le constructeur de la relationInfo
	public RelationInfo(String nom, int nb_col, String[] nom_col, String[] type_col) {
		this.nom=nom;
		this.nb_col=nb_col;
		this.nom_col=nom_col;
		this.type_col=type_col;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public int getNb_col() {
		return nb_col;
	}


	public void setNb_col(int nb_col) {
		this.nb_col = nb_col;
	}


	public String[] getNom_col() {
		return nom_col;
	}


	public void setNom_col(String[] nom_col) {
		this.nom_col = nom_col;
	}


	public String getType_col(int indice) {
		return type_col[indice];
	}


	public void setType_col(String[] type_col) {
		this.type_col = type_col;
	}
}
