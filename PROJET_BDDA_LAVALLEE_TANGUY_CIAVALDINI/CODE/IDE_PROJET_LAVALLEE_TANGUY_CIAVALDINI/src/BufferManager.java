import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class BufferManager {
	
	// Ne comporte qu'une seule et unique instance
	
	private PageId pageId;
	private int pin_count;
	private boolean isDirty;
	
	private Frame frame;
	private List<Frame> frames; // buffer pool
	
	public BufferManager() {
		frames = new ArrayList<Frame>();
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

		dm.readPage(pageId, frames.get(pageId.getPageIdx()).getFrame());
		
		return frame.getFrame();
		
	}
	
	
	/* Décrémenter le pin_count
	 * Actualiser le flag dirty de la page
	 * (et aussi potentiellement actualiser des informations concernant la politique de remplacement).
	 */
	public void FreePage(PageId pageId, boolean valdirty) {
		
	}
	
	/* écriture de toutes les pages dont le flag dirty = 1 sur disque
	 * remise à 0 de tous les flags/informations et contenus des buffers (buffer pool « vide »)
	 */
	public void FlushAll() {
		
	}

}
