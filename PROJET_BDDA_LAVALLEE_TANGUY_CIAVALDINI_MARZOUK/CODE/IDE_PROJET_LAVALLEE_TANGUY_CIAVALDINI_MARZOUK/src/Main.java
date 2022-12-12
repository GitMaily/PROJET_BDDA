import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	
	
	/*public static void lireDepuisFichier (ArrayList<String> liste) {
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
	}*/
	

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		
		
		//DropDBCommand.execute();
		
		//ne pas toucher fait partie du projet et pas d un test ET mettre avant les parametres framecount pagesize ect
		DBParams.DBPath = args[0];
		DBParams.frameCount = 2;
		DBParams.maxPagesPerFile = 4;
		DBParams.pageSize = 4096;
		
		
		
		DBManager.getInstance().Init();
		
		
		Scanner scanner = new Scanner (System.in);
		String commande = "";
		
		while(!commande.equals("EXIT")) { //!scanner.equals("EXIT"
			 commande = scanner.nextLine();
			
			if(commande.equals("EXIT")) {
				DBManager.getInstance().Finish();
				break;
			}
			else{ 
				DBManager.getInstance().ProcessCommand(commande);
			}
		}

		DBManager.getInstance().Finish();

		scanner.close();
		
	}

}
