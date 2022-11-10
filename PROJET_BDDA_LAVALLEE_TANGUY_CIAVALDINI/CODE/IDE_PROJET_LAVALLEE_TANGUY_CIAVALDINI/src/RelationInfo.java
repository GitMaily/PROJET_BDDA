import java.util.ArrayList;

public class RelationInfo {
	
	private String nom; //nom de la relation
	private int nb_col; // le nombre de colonnes
	private ArrayList<ColInfo> nom_col; // le nom des colonnes
	
	private PageId header; // le types des colonnes
	
	private PageId headerPageId; // l'identifiant de la HeaderPage de la relation
	
	// le constructeur de la relationInfo

	public RelationInfo(String nom, int nb_col, ArrayList<ColInfo> nom_col) {
		this.nom=nom;
		this.nb_col=nb_col;
		this.nom_col=nom_col;
		
	}

	public RelationInfo(String nom, int nb_col, ArrayList<ColInfo> nom_col, PageId headerPageId) {
		this.nom=nom;
		this.nb_col=nb_col;
		this.nom_col=nom_col;
		this.headerPageId = headerPageId;

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


	public ArrayList<ColInfo> getNom_col() {
		return nom_col;
	}


	public void setNom_col(ArrayList<ColInfo> nom_col) {
		this.nom_col = nom_col;
	}


	public PageId getHeader() {
		return header;
	}


	public void setHeader(PageId header) {
		this.header = header;
	}



	public PageId getHeaderPageId() {
		return headerPageId;
	}


	public void setHeaderPageId(PageId headerPageId) {
		this.headerPageId = headerPageId;
	}

}
