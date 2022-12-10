import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class BufferManager {
	
	
	public PageId pageId;
	private static int numero_tache;
	private List<Frame> ListFrames; // buffer pool
	
	/**
	 *  L'instance unique du BufferManager
	 */
	public static BufferManager INSTANCE = new BufferManager();
	
	/**
	 * Ne comporte qu'une seule et unique instance
	 * @return L'instance unique du BufferManager
	 */
	public static BufferManager getInstance() {
		if (INSTANCE == null) {
            INSTANCE = new BufferManager();
        }
		return INSTANCE;	
	}
	
	public BufferManager()
	{
		
		init();
		
	}
	
	/**
	 * Getter de la liste des frames
	 * @return La liste des frames
	 */
	public List<Frame> getListFrames() {
		return ListFrames;
	}

	/**
	 * Initialise le buffer pool.
	 * Alloue dans chaque frame un buffer de taille DBParam.pageSize.
	 */
	public void init() {
		ListFrames = new ArrayList<Frame>(DBParams.frameCount);
		for (int i=0; i<DBParams.frameCount;i++) {
			ListFrames.add(new Frame());
		}
	}
	
	
	
	
	
	/**
	 * Répond à une demande de page venant des couches plus hautes.
	 * S’occupe du remplacement du contenu d’une frame si besoin (application du LRU).

	 * @param pageId La page du ByteBuffer demandé
	 * @return Le ByteBuffer associé à la page entrée en argument.

	 */
	public ByteBuffer GetPage(PageId pageId) {
		numero_tache++;

		
		
		/**
		 *  Vérifie si on veut accéder à une page déjà existante!
		 */
		for(Frame frame : ListFrames) {
			if(frame.getPageId() == pageId) {
				frame.setPin_count(frame.getPin_count()+1);
				return frame.getBuffer();
			}
		}

		

		/**
		 * Vérifie si une case est libre ! Place la page dans cette case si c'est le cas
		 */
		for(Frame frame : ListFrames) {

			if(frame.getPageId( )== null) {
				
				frame.setPageId(pageId);
				frame.setPin_count(1);
				frame.setDirty(false);
				DiskManager.getInstance().readPage(pageId, frame.getBuffer());
				
				return frame.getBuffer();
			}
				
		}

		//System.out.println("Application de LRU");
		
		/**
		 *  Dans ce cas, la page demandée n'est pas déjà existante, et ne trouve pas de place dans le buffer pool
		 *  Il faut remplacer parmis les frames existantes.
		 *	On doit utiliser la politique de remplacement LRU
		 */
		
		
		int tps = 0;
		Frame fremplacer = ListFrames.get(tps);

		int surcharge = 0;
		int candidat_elu = 0;
		for(int i = 0;i < ListFrames.size();i++) {
			if(ListFrames.get(i).getPin_count() == 0 && ListFrames.get(i).getTimestamp() != 0) {
				if(fremplacer.getTimestamp() == 0 || ListFrames.get(i).getTimestamp() <= fremplacer.getTimestamp()) {

					fremplacer = ListFrames.get(i);
					candidat_elu = i;
				}
			}
			else {
				surcharge++;
			}
		}
				
		if(surcharge != ListFrames.size()) {

			
			if (fremplacer.isDirty())
				DiskManager.getInstance().writePage(fremplacer.getPageId(), fremplacer.getBuffer());
			
			fremplacer.setPin_count(1);
			fremplacer.setDirty(false);
			fremplacer.setPageId(pageId);
			fremplacer.setTimestamp(0);
			DiskManager.getInstance().readPage(pageId, fremplacer.getBuffer());
			ListFrames.set(candidat_elu, fremplacer);
		
			return ListFrames.get(candidat_elu).getBuffer();
			
		
		}
		else {
			System.out.println("Aucun candidat, toutes les frames sont en cours d'utilisation ! Remplacement impossible...");

		}
		return null;
		
	}
	
	
	/**
	 * Libère la page entrée en argument. Utilise la politique de remplacement LRU.
	 * @param pageId La page à libérer
	 * @param valdirty Le dirty voulu à actualiser
	 */
	public void FreePage(PageId pageId, boolean valdirty) {
		numero_tache++;
		for(int i=0; i<ListFrames.size();i++) {
			// On vérifie que la case n'est pas vide
			if(ListFrames.get(i).getPageId()!=null) { 
				// On vérifie que la page demandée à libérer correspond bien à l'itérateur
				if(ListFrames.get(i).getPageId() == pageId) { 
					// Cas page non utilisée
					// On vérifie si la page est contenue dans la frame : si le pin_count est à 0 alors la frame n'est pas utilisée
					if(ListFrames.get(i).getPin_count()==0) { 
						System.out.println("pin count à 0");
						System.out.println("Erreur de page, on ne peut pas libérer la case car elle n'est pas utilisée !");
					return;
					}
					
					// Cas page utilisée
					else {
						Frame temps = ListFrames.get(i);
						temps.setPin_count(temps.getPin_count()-1);
						temps.setDirty(valdirty);
						// Si le pin_count est à 0 après l'avoir décrémenté, on ajoute un timestamp.
						if(temps.getPin_count()==0) {
							
							temps.setTimestamp(numero_tache);
					
						//return;
						}
						ListFrames.set(i, temps);
						return;
					}
				}
				
			}
		}
		System.out.println("page non trouvé dans buffer manager");
		return;
	}
	
	/** 
	 * Ecrit toutes les pages dont le flag dirty est true sur le disque. Remet à 0 toutes les informations des frames et contenus du bufferpool.
	 */
	public void FlushAll() {
		for (int i=0; i<ListFrames.size();i++) {
			if(ListFrames.get(i).isDirty()==true) {
				DiskManager.getInstance().writePage(ListFrames.get(i).getPageId(), ListFrames.get(i).getBuffer());
			}
		}
		ListFrames.clear();
		for(int i=0; i<DBParams.frameCount;i++) {
			ListFrames.add(new Frame());
		}
		
	}
	
	/**
	 * Reinitialise le BufferManager lors de la saisie de la commande DROPDB
	 */
	public void reinitialiser(){
		for(Frame frame : ListFrames) {
			frame.setPin_count(0);
			frame.setDirty(false);
			frame.setPageId(null);
			frame.setBuffer(ByteBuffer.allocate(DBParams.pageSize));
			frame.setTimestamp(0);
		}
	}

}
