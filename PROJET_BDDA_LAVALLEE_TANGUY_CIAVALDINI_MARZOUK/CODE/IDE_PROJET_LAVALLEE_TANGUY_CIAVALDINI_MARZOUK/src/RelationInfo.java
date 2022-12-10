import java.io.Serializable;
import java.util.ArrayList;

public class RelationInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -47332796009811867L;
	private String nom; //nom de la relation
	private int nb_col; // le nombre de colonnes
	private ArrayList<ColInfo> nom_col; // le nom des colonnes
	
	private PageId header; // le types des colonnes
	
	private PageId headerPageId; // l'identifiant de la HeaderPage de la relation
	
	// le constructeur de la relationInfo

	public RelationInfo(String nom, ArrayList<ColInfo> nom_col) {
		this.nom=nom;
		this.nb_col=nom_col.size();
		this.nom_col=nom_col;
		
	}

	public RelationInfo(String nom, ArrayList<ColInfo> nom_col, PageId headerPageId) {
		this.nom=nom;
		this.nb_col=nom_col.size();
		this.nom_col=nom_col;
		this.headerPageId = headerPageId;

	}
	
	public String afficherNomCol() {
		StringBuilder sb = new StringBuilder();
		for(ColInfo colInfo : nom_col) {
			sb.append("["+colInfo.getNom_col()+"]"+" de type: "+colInfo.getType_col());
			sb.append("\t");
		}
		
		return sb.toString();
	}
	
	public String toStringTest() {
		return nom+" (nom)\n"+nb_col+"(nb_col)\n"+afficherNomCol();
	}

	public String toString() {
		return nom+" (nom relation)\n"+afficherNomCol();
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
