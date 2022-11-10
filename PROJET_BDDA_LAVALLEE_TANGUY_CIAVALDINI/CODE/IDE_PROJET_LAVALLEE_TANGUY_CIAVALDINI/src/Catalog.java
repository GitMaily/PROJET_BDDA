import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Catalog {
		
	private ArrayList<RelationInfo> listRI; // liste de relation
	private int compteurRelation; // un compteur de relation
	private String path = DBParams.DBPath + "/catalog.sv";
	
	private static Catalog INSTANCE = new Catalog();
	
	public static Catalog getInstance() {
		if (INSTANCE == null) {
            INSTANCE = new Catalog();
        }
		return INSTANCE;	
	}
	

    /**
     * sauvegarde les informations du Catalog dans un fichier nommé Catalog.sv.
     * 
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    public void Finish() throws FileNotFoundException, IOException {
    	
 
    		File f  = new File(path); // DBParams.DBPath + "/catalog.sv" (le chemin vers le fichier catalog.sv)
    		try{
    			
    			FileOutputStream fileOut = new FileOutputStream(f);
    			ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
    			objOut.writeObject(this);
    			objOut.flush();
    			objOut.close();
    			fileOut.close();
    		}catch(IOException e) {
    			
    			e.printStackTrace();
    		}
    }
    
    /**
     * code qui lit les informations du fichier Catalog.sv et remplit le Catalog avec ces informations.
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
	public void Init() throws FileNotFoundException, IOException, ClassNotFoundException {
		
		File f = new File(path); // path = DBParams.DBPath + "/catalog.sv" (le chemin vers le fichier catalog.sv)
		if(f.exists()) {
			
			FileInputStream fileInput = new FileInputStream(f);
			ObjectInputStream objIn = new ObjectInputStream(fileInput);
			objIn.readObject();
			fileInput.close();
			objIn.close();
		}
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
