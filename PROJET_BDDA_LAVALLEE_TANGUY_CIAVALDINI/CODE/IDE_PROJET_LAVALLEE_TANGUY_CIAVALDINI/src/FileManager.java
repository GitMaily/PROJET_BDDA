
public class FileManager {

private static FileManager INSTANCE = new FileManager();
	
	public static FileManager getInstance() {
		if (INSTANCE == null) {
            INSTANCE = new FileManager();
        }
		return INSTANCE;	
	}
	
	/**
	 * Allocation d'une nouvelle page (AllocPage)
	 * Ecriture dans la page allouée de 4 octets (initialement 0)
	 * 
	 * @return La PageId de la page allouée (par AllocPage)
	 */
	public PageId createNewHeaderPage() {
		PageId pi = new PageId();
		pi = DiskManager.getInstance().allocPage();
		BufferManager.getInstance().GetPage(pi);
		byte [] bytes;
		
		
		BufferManager.getInstance().FreePage(pi, true);
		return pi;
	}
}
