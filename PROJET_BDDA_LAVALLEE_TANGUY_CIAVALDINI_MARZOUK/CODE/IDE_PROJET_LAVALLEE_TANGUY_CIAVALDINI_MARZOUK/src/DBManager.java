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
	
	public void ProcessCommand(String commande) {
		switch(commande){
			case "CREATE TABLE":
				CreateTableCommand c = new CreateTableCommand(commande);
				c.execute();
			break;
			case "DROPDB":
				DropDBCommand.execute();
			break;
			case "INSERT":
				InsertCommande ic = new InsertCommande(commande);
				ic.execute();
			break;
			case "SELECT":
				SelectCommand sc = new SelectCommand(commande);
				sc.execute();
			break;
		}
	}
}
