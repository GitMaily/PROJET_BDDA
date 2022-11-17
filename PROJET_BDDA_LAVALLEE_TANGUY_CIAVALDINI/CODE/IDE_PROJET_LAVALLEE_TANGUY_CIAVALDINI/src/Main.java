import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
	

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		/*DBParams param = new DBParams("/users/licence/ik05057/Bureau/PROJET_BDDA/PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI/DB/db.txt",4096,4);
		
		System.out.println("le chemin marche ");
		System.out.println(param.getPageSize());
	
	*/

		
		
		DiskManager dm = new DiskManager();
	
		//dm.creerFichierTexte();
		//dm.lireFichier();
		//dm.creerFichierTest();
		//dm.creerFichierBinaire();
		dm.creerFichier();
		//dm.allocPage();
		dm.readPage(null, null);
		
		
		
		//ne pas toucher fait partie du projet et pas d un test ET mettre avant les parametres framecount pagesize ect
		
		
		DBManager.getInstance().Init();
		
		boolean fin = false;
		Scanner scanner = new Scanner (System.in);
		while(fin!=true) {
			String commande = scanner.nextLine();
			
			switch(commande) {
			case "EXIT": DBManager.getInstance().Finish();
						fin = true;
						break;
			default: DBManager.getInstance().ProcessCommand(commande);
			}
		}
		scanner.close();
		
	}

}
