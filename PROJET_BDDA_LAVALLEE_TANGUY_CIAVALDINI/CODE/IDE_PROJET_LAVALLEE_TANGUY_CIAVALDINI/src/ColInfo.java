import java.io.Serializable;

@SuppressWarnings("serial")
public class ColInfo implements Serializable {

	private String nom_col;
	private String type_col;

	public ColInfo(String nom_col, String type_col) {
		this.setNom_col(nom_col);
		this.setType_col(type_col);
	}

	public String getNom_col() {
		return nom_col;
	}

	public void setNom_col(String nom_col) {
		this.nom_col = nom_col;
	}

	public String getType_col() {
		return type_col;
	}

	public void setType_col(String type_col) {
		this.type_col = type_col;
	}
}
