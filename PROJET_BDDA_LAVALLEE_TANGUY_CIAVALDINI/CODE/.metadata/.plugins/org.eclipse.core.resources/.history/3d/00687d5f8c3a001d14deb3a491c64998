import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	
	
	
	public static void lireDepuisFichier (ArrayList<String> liste) {
		 try {
	            BufferedReader reader = new BufferedReader(new FileReader("personnes.txt"));
	 
	            String line;
	 
	            while ((line = reader.readLine()) != null) {
	                liste.add(line);
	            }
	            reader.close();
	 
	        } catch (IOException e) {
	            System.out.println("Pas de fichier! Ce n'est pas grave ... : )");
	        }
	}
	
	public static void ecrireDansFichier (ArrayList<String>liste ) {
		try {
           BufferedWriter writer = new BufferedWriter(new FileWriter("personnes.txt", false));
           
           for (int i = 0; i < liste.size(); ++i) {
           	writer.write(liste.get(i));
           	writer.write("\n");;
           }

           writer.close();
           
       } catch (IOException e) {
       	System.out.println("La sauvegarde s'est mal passée...");
           e.printStackTrace();
       }
	}
	

	public static void main(String[] args) {
		/*DBParams param = new DBParams();
		DBParams.DBPath="/users/licence/ik05057/Bureau/PROJET_BDDA/PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI/DB/db.txt";
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFile = 4;
		
		
		
		// TODO Auto-generated method stub
		System.out.println("le chemin marche ");
		System.out.println(param.getPageSize());
	
	*/
		
		DiskManager dm = new DiskManager();
		dm.allocPage();
	}

}
