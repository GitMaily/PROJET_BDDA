import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class BufferManager {
	
	// Ne comporte qu'une seule et unique instance
	
	public PageId pageId;
	private int pin_count;
	private boolean isDirty;
	
	private Frame frame;
	
	private List<Frame> ListFrames; // buffer pool
	
	private static final BufferManager INSTANCE = new BufferManager();
	
	public static BufferManager getInstance() {
		return INSTANCE;
	}
	
	private BufferManager()
	{
		ListFrames = new ArrayList<Frame>();
		for (int i=0; i<DBParams.frameCount;i++) {
			ListFrames.add(new Frame());
		}
	}
	
	
	
	
	
	
	/*
	 * Répondre à une demande de page venant des couches plus hautes, et donc
	 * retourner un des buffers associés à une frame. 
	 * 
	 * S’occuper du remplacement du contenu d’une frame si besoin (LRU)
	 * 
	 * 
	 */
	public ByteBuffer GetPage(PageId pageId) {
		

		//frame.getFrame();
		DiskManager dm = new DiskManager();
		//DiskManager.allocPage2();
		
		
		// Choisir quelle est la bonne frame à récupérer
		dm.readPage(pageId,frame.getFrame());

		dm.readPage(pageId, ListFrames.get(pageId.getPageIdx()).getFrame());
		
		return frame.getFrame();
		
	}
	
	
	/* Décrémenter le pin_count
	 * Actualiser le flag dirty de la page
	 * (et aussi potentiellement actualiser des informations concernant la politique de remplacement).
	 */
	public void FreePage(PageId pageId, boolean valdirty) {
		for(int i=0; i<ListFrames.size();i++) {
			if(ListFrames.get(i).getPageId()!=null) {
				if(ListFrames.get(i).equals(pageId)) {
					if(ListFrames.get(i).getPin_count()==0) {
						System.out.println("pin count à 0");
					return;
					}
					else {
						Frame temps = ListFrames.get(i);
						temps.setPin_count(temps.getPin_count()-1);
						temps.setDirty(valdirty);
						ListFrames.remove(temps);
						ListFrames.add(temps);
						return;
					}
				}
				
			}
		}
		System.out.println("page non trouvé dans buffer manager");
		return;
	}
	
	/* écriture de toutes les pages dont le flag dirty = 1 sur disque
	 * remise à 0 de tous les flags/informations et contenus des buffers (buffer pool « vide »)
	 */
	public void FlushAll() {
		for (int i=0; i<ListFrames.size();i++) {
			if(ListFrames.get(i).isDirty()==true) {
				DiskManager.writePage(ListFrames.get(i).getPageId(), ListFrames.get(i).getFrame());
			}
		}
		ListFrames.clear();
		for(int i=0; i<DBParams.frameCount;i++) {
			ListFrames.add(new Frame());
		}
		
	}
	
	

}
