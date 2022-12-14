import java.io.IOException;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		
		
		//DropDBCommand.execute();
		
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
