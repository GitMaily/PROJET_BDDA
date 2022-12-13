import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Readcsv {
	
	private File f;
	private ArrayList<String> ligne;
	private String path = "/users/licence/il01193/Bureau/PROJET_BDDA/PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI_MARZOUK";
	public Readcsv(String nomFichier) {
		//f = new File(nomfichier);
		f = new File(path+File.separator+nomFichier);

		ligne = new ArrayList<>();
		
	}
	
	public ArrayList<String> lireCsv() throws IOException{
		FileReader fr = new FileReader(f);
		BufferedReader bfrrd = new BufferedReader(fr);
		String line="";
	
		while((line = bfrrd.readLine()) != null ) {
			ligne.add(line);
		}
		
		bfrrd.close();
		return ligne;
	}

	public void affiche(){
		for(int i=0; i<ligne.size();i++){
			System.out.println("ligne"+ i+1 + " : "+ligne.get(i));
		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	

}
