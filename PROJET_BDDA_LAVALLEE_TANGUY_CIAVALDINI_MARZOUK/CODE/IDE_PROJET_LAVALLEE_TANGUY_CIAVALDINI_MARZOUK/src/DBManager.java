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
	
	/**
	 * une méthode Init qui contient un appel à la méthode Init du Catalog et un appel à la
	 * méthode Init du BufferManager, ainsi que (si une telle méthode existe) un appel à la
	 * méthode Init du DiskManager
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void Init() throws ClassNotFoundException, IOException {
		Catalog.getInstance().Init();
	}
	
	/**
	 * une méthode Finish qui contient un appel à la méthode Finish du Catalog et un appel à la
	 * méthode FlushAll (ou Finish, si Finish appelle FlushAll) du BufferManager, ainsi que si
	 * besoin un appel à la méthode Finish du DiskManager
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void Finish() throws FileNotFoundException, IOException {
		Catalog.getInstance().Finish();
		BufferManager.getInstance().FlushAll();
	}
	
	
	/**
	 * une méthode ProcessCommand, qui prend en argument une chaîne de caractères qui
	 * correspond à une commande. Pour l’instant cette méthode sera vide
	 * @param commande
	 * @throws IOException
	 */
	public void ProcessCommand(String commande) throws IOException {
		String [] commandeNom = commande.split(" ");
		switch(commandeNom[0]){
			case "CREATE":
				CreateTableCommand c = new CreateTableCommand(commande);
				c.execute();
			break;
			case "DROPDB":
				DropDBCommand dropDB = new DropDBCommand();
				dropDB.execute();
			break;
			case "INSERT":
				if(commande.contains("FILECONTENTS")){
					InsertCommande ic = new InsertCommande(commande);

					//InsertFichier infic = new InsertFichier(commande);
					//infic.insererFichier();
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
			
			case "DELETE":
				DeleteCommand dc = new DeleteCommand(commande);
				dc.execute();
		}
	}
}
