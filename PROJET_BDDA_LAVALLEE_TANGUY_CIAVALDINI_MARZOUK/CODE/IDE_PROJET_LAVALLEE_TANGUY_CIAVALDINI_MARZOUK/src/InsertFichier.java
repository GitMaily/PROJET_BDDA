import java.util.ArrayList;

public class InsertFichier {

	private String NomFichier;
	private ArrayList<String> ValRecord;
	private String nomRel;
	
	public InsertFichier(String cmd){
		catchinfos(cmd);
		this.ValRecord= new ArrayList<>();
	}
	
	public void catchinfos(String cmd) {
		String[] cmdSepare = cmd.split(" ");
		this.nomRel = cmdSepare[2];
		String[] fichierSepare = cmdSepare[3].split( "\\" );
		String nomFichier = fichierSepare[1].substring(0,fichierSepare[1].length()-1);
		this.NomFichier= nomFichier;
	}
	
	private void remplirValRecord() {
		Readcsv l_c;
		
	}
	
	public void insererFichier() {
		
	}
	
	
}
