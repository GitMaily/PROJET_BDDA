import java.util.ArrayList;

public class Catalog {
		
	private ArrayList<RelationInfo> listRI; // liste de relation
	private int compteurRelation; // un compteur de relation
	
	
	public void Finish() {
		
	}
	public void Init() {
		
	}
	
	//pour ajouter une relation a la liste des relations
	public void AddRelationInfo (RelationInfo RI) {
		listRI.add(RI);
	}
	
	/*On rentre un nom et ça nous renvoie la relation 
	assiocié dans la liste des relations sinon renvoie null*/
	public RelationInfo GetRelationInfo (String nomRI) {
		int indice = -1;
		for (int i =0; i<listRI.size(); i++) {
			if(nomRI == listRI.get(i).getNom()) {
				indice = i;
				break;
			}
		}
		if(indice != -1) {
			return listRI.get(indice);
		}
		else {
			return null;
		}
	}
	
	public ArrayList<RelationInfo> getListRI() {
		return listRI;
	}

	public void setListRI(ArrayList<RelationInfo> listRI) {
		this.listRI = listRI;
	}

	public int getCompteurRelation() {
		return compteurRelation;
	}

	public void setCompteurRelation(int compteurRelation) {
		this.compteurRelation = compteurRelation;
	}
}