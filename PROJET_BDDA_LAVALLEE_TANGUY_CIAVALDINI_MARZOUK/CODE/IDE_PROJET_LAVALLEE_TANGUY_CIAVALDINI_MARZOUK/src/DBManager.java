import java.io.FileNotFoundException;
import java.io.IOException;

public class DBManager {
	private static DBManager INSTANCE = new DBManager();

	public static DBManager getInstance() {
		if (INSTANCE == null) {
            INSTANCE = new DBManager();
        }
		return INSTANCE;	
	}
	private DBManager() {
		
	}
	
	public void Init() throws ClassNotFoundException, IOException {
		Catalog.getInstance().Init();
	}
	
	public void Finish() throws FileNotFoundException, IOException {
		Catalog.getInstance().Finish();
		BufferManager.getInstance().FlushAll();
	}
	
	public void ProcessCommand(String commande) throws IOException {
		switch(commande){
			case "CREATE TABLE":
				CreateTableCommand c = new CreateTableCommand(commande);
				c.execute();
			break;
			case "DROPDB":
				DropDBCommand.execute();
			break;
			case "INSERT":
				if(commande.contains("FILECONTENTS")){
					InsertFichier infic = new InsertFichier(commande);
					infic.insererFichier();
				}
				else{
					InsertCommande ic = new InsertCommande(commande);
					ic.execute();
				}
				
			break;
			case "SELECT":
				SelectCommand sc = new SelectCommand(commande);
				sc.execute();
			break;
		}
	}
}
