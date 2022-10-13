import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class BufferManager {
	
	// Ne comporte qu'une seule et unique instance
	
	public PageId pageId;
	
	
	private static int numero_tache;
	
	
	private List<Frame> ListFrames; // buffer pool
	private DiskManager dm;
	private static BufferManager INSTANCE = new BufferManager();
	
	public static BufferManager getInstance() {
		if (INSTANCE == null) {
            INSTANCE = new BufferManager();
        }
		return INSTANCE;	
	}
	
	BufferManager()
	{
		
		this.dm = DiskManager.getInstance();
		init();
		
		
		
		
	}
	
	private void init() {
		ListFrames = new ArrayList<Frame>(DBParams.frameCount);
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
		numero_tache++;

		
		System.out.println("Size de ListFrames: "+ListFrames.size());
		
		// Vérifie si on veut accéder à une page déjà existante!
		int i = 0;
		for(Frame frame : ListFrames) {
			i++;
			if(frame.getPageId() == pageId) {
				frame.setPin_count(frame.getPin_count()+1);
				//ListFrames.get(i-1).setPin_count(frame.getPin_count()+1);
				return frame.getBuffer();
			}
		}

		

		// Vérifie si une case est libre ! Place la page dans cette case si c'est le cas
		int j = 0;
		for(Frame frame : ListFrames) {
			j++;

			if(frame.getPageId( )== null) {
				

				frame.setPageId(pageId);
				frame.setPin_count(1);
				frame.setDirty(false);
				DiskManager.readPage(pageId, frame.getBuffer());
				//DiskManager.readPage(pageId, frame.getBuffer());
				/*frame.setPin_count(1);
				frame.setPageId(pageId);
				ListFrames.set(j-1,new Frame(pageId)); // Remplace la case vide

				//DiskManager.readPage(pageId, frame.getBuffer());
				//frame.setBuffer(frame.getBuffer());
				//ListFrames.set(j-1,new Frame(pageId,frame.getBuffer())); // Remplace la case vide

				//Frame test = new Frame(pageId,frame.getBuffer());
				return frame.getBuffer();
				//return frame.getBuffer();
				
				//ListFrames.set(j-1,frame); // Remplace la case vide
				//ListFrames.get(j-1).setPin_count(1); // Incrémente le pin count
				
				//return frame.getBuffer();	
				//return ListFrames.get(j-1).getBuffer();
 
				
				
				fremplacer.setPin_count(1);
				//fremplacer.setDirty(false);
				DiskManager.readPage(pageId, fremplacer.getFrame());
				
				ListFrames.set(j-1, fremplacer);*/
				
				//ListFrames.set(j-1,new Frame(pageId)); // Remplace la case vide
				//ListFrames.get(j-1).setPin_count(frame.getPin_count()+1); // Incrémente le pin count
				
				return ListFrames.get(j-1).getBuffer();
				//return fremplacer.getFrame();
			}
				
		}

		
		System.out.println("Application de LRU!!");
		
		// Dans ce cas, la page demandée n'est pas déjà existante, et ne trouve pas de place dans le buffer pool
		// Il faut remplacer parmis les frames existantes.
		// On doit utiliser la politique de remplacement LRU
		
		ArrayList<Frame> ListCandidats = new ArrayList<>();
		System.out.println("Création de la liste des frames candidates ");
		System.out.println("Size de candidat: "+ListCandidats.size());
		
		// On ajoute les candidats dans la liste
		// Un candidat = une case avec le pin_count à 0.
		for(Frame frame : ListFrames) {
			
			if(frame.getPin_count() == 0 && frame.getTimestamp() != 0) { //  
				ListCandidats.add(frame);
				System.out.println("Ajout d'un candicat dans la liste des candidats ! Taille : "+ListCandidats.size());
			}
			
			
		}
		
		// On élit le candidat ayant le timestamp le plus petit
		if(ListCandidats.size() != 0) {
			int candidat_elu = 0;
			
			System.out.println("candidat elu = "+candidat_elu);
			
			for(int candidat = 0;candidat < ListCandidats.size()-1;candidat++) {
				if(ListCandidats.size() == 1) {
					candidat_elu = candidat;
				}
				
				else{
					if(ListCandidats.get(candidat).getTimestamp() <= ListCandidats.get(candidat+1).getTimestamp()){
						candidat_elu = candidat;
						System.out.println("candidat = "+candidat);
					
					}
					else {
						candidat_elu = candidat+1;
					}
				}
				
			}
			
			Frame fremplacer = ListFrames.get(candidat_elu);
			
			if (fremplacer.isDirty())
				DiskManager.writePage(fremplacer.getPageId(), fremplacer.getBuffer());
			
			fremplacer.setPin_count(1);
			fremplacer.setDirty(false);
			DiskManager.readPage(pageId, fremplacer.getBuffer());
			
			//ListFrames.set(candidat_elu,new Frame(pageId)); // Remplace la page dans la case élue
			//ListFrames.get(candidat_elu).setPin_count(ListFrames.get(candidat_elu).getPin_count()+1);
			
			return ListFrames.get(candidat_elu).getBuffer();
			
			/*for(int frame_elu = 0;frame_elu<ListFrames.size();frame_elu++) {
				
				System.out.println("print frame elu : "+ListFrames.get(frame_elu).getTimestamp());
				System.out.println("print candidat elu : "+ListCandidats.get(candidat_elu).getTimestamp());
				
				// On  vérifie que c'est bien celui qui a le timestamp concerné
				if(ListFrames.get(frame_elu).getTimestamp() == ListCandidats.get(candidat_elu).getTimestamp()) {
					
					ListFrames.set(frame_elu,new Frame(pageId)); // Remplace la page dans la case élue
					ListFrames.get(frame_elu).setPin_count(ListFrames.get(frame_elu).getPin_count()+1);
					return ListFrames.get(frame_elu).getFrame();
				}
			}*/
		
		}
		else {
			System.out.println("La liste de candidat est vide, toutes les frames sont en cours d'utilisation ! Remplacement impossible...");

		}
		return null;
		
		
		
		
		
	
		
	}
	
	
	/* Décrémenter le pin_count
	 * Actualiser le flag dirty de la page
	 * (et aussi potentiellement actualiser des informations concernant la politique de remplacement).
	 */
	public void FreePage(PageId pageId, boolean valdirty) {
		numero_tache++;
		for(int i=0; i<ListFrames.size();i++) {
			if(ListFrames.get(i).getPageId()!=null) { // On vérifie que la case n'est pas vide
				if(ListFrames.get(i).getPageId() == pageId) { // On vérifie que la page demandée à libérer correspond bien à l'itérateur
					if(ListFrames.get(i).getPin_count()==0) { // On vérifie si le pin_count est à 0
						System.out.println("pin count à 0");
						System.out.println("Erreur de page, on ne peut pas libérer la case car elle n'est pas utilisée !");
					return;
					}
					else {
						Frame temps = ListFrames.get(i);
						temps.setPin_count(temps.getPin_count()-1);
						temps.setDirty(valdirty);
						if(temps.getPin_count()==0) {
							//System.out.println("Après avoir décrémenté, le pin_count est à 0");
							//System.out.println("On ajoute un timestamp");
							temps.setTimestamp(numero_tache);
						//ListFrames.remove(temps);
						//ListFrames.add(temps);
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
	
	/* écriture de toutes les pages dont le flag dirty = 1 sur disque
	 * remise à 0 de tous les flags/informations et contenus des buffers (buffer pool « vide »)
	 */
	public void FlushAll() {
		for (int i=0; i<ListFrames.size();i++) {
			if(ListFrames.get(i).isDirty()==true) {
				DiskManager.writePage(ListFrames.get(i).getPageId(), ListFrames.get(i).getBuffer());
			}
		}
		ListFrames.clear();
		for(int i=0; i<DBParams.frameCount;i++) {
			ListFrames.add(new Frame());
		}
		
	}
	
	

}
