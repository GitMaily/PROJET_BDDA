import java.util.ArrayList;

public class InsertFichier {

	private String NomFichier;
	private ArrayList<String> ValRecord;
	private String nomRel;
	
	public InsertFichier(String cmd){
		catchinfos(cmd);
		this.ValRecord= new ArrayList<>();
	}
	
	
	
	private void remplirValRecord() {
		Readcsv l_c;
		try{
			l_c = new lireCsv(NomFichier);
			ValRecord = new ArrayList<>();
			try{
				ValRecord= l_c.lireCsv();
			}catch(IOException e) {
                e.printStackTrace();
            }
		}catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		
	}
	
	private void remplirCmd(){
		for(int i =0; i>ValRecord.size();i++){
			String entree = "INSERT INTO" + this.nomRel +"VALUES (";
			String sasir = chaine + ValRecord.get(i);
			try{
				InsertCommande ic = new InsertCommande(sasir);
				ic.execute();
			}catch (IOException e) {
  	          e.printStackTrace(); 
			}
		}
	}
		


	public void insererFichier() {

		remplirValRecord();
		remplirCmd();

		
	}


	public void catchinfos(String cmd) {
		String[] cmdSepare = cmd.split(" ");
		this.nomRel = cmdSepare[2];
		String[] fichierSepare = cmdSepare[3].split( "\\" );
		String nomFichier = fichierSepare[1].substring(0,fichierSepare[1].length()-1);
		this.NomFichier= nomFichier;
	
	}

	public void affiche(){
		for(int i=0; i<ValRecord.size(); i++){
			System.out.println(ValRecord.get(i));
		}
	}

	public String toString(){
		return this.nomRel+" "+ this.nomFichier;
	}
}
