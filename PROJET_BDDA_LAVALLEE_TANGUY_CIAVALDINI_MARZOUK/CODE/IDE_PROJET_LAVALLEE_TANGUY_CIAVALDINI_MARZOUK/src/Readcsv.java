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
	
	public Readcsv(String nomfichier) {
		f = new File(nomfichier);
		
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
	
	}
	

}
