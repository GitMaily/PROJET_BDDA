import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DiskManager {

	// Attributs
	
	/* On ne peut utiliser qu'une seule instance
	 * il faut choisir entre ByteBuffer et byte[]
	 */

	private ByteBuffer page;
	
	private static int fileId;
	static int countpage ;
	static ArrayList<PageId> listeblanche = new ArrayList<PageId>();
	static ArrayList<PageId> listenoire = new ArrayList<PageId>();

	
	private static DiskManager INSTANCE = new DiskManager();
	
	public static DiskManager getInstance() {
		if (INSTANCE == null) {
            INSTANCE = new DiskManager();
        }
		return INSTANCE;	
	}
	
	
	public DiskManager() {
	}
	// Constructeurs
	public DiskManager(ByteBuffer bb) {
		//page2 = new byte[4096];
		this.page = bb;
	}
	
	
	
	public void creerFichier() {
		
		
		try {
			RandomAccessFile fichier = new RandomAccessFile("F"+fileId+".bdda","rw");
            //fichier.setLength(DBParams.maxPagesPerFile*DBParams.pageSize);


			ByteBuffer bb = ByteBuffer.allocate(DBParams.pageSize); // alloue 4096 octets
			
			bb.put(4095, (byte) 32);
			
			System.out.println(bb.limit());
            
            System.out.println(bb.get(4095));
            
            
            
            fichier.seek(fichier.length());
            fichier.writeByte(bb.get(4095));
            
            System.out.println(fichier.length());
            
            fichier.seek(0);
            System.out.println("Dernier élément: "+fichier.readByte());

            
            fichier.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		fileId++;

	}
	
	
	static Map<Integer, ArrayList<Integer> > dico = new HashMap<Integer, ArrayList<Integer>>(); 
	static PageId allocPage() {
		 int c =0;
		 
		  PageId pi = new PageId();

		// TODO Auto-generated method stub
		 /*etape 1 , verifie que le dictionnaire est vide 
			 * si oui alors creer premeir FICHIER et l initialise (la clé) à zéro +
			 * sinon parcour (for ou while) le dico en cherchant une case de valeur vide
			 * si ne trouve pas de case vide, alors creer une nouveau FICHIER 
			 */
			
			if (dico.isEmpty()==true){
				System.out.println("le dico est vide, un premier fichier sera créer");

				//"/users/licence/il01193/Bureau/PROJET_BDDA/PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI/DB/test.txt","rw"
				
				/*ajoute un élémnt dans le dico, en l occurence 0*/
				dico.computeIfAbsent((dico.size()), k -> new ArrayList<>()).add(0);
				
				pi.FileIdx=0;
				pi.PageIdx=0;

				//creerFichier();
				fileId++;
				countpage++;
				
				return pi;
				
			}
			
		//	System.out.println(dico.toString());
			
			else if (dico.isEmpty()==false) {
				
				for (Integer key : dico.keySet()) {
			        
			        System.out.println(key + " = " + dico.get(key))	;

			    
						if((dico.get(key)).size()<4) {
						//	dico.get(key).containsAll(null);
							
							pi.FileIdx =key;
							
							if(dico.get(key).contains(0)==false) {
								dico.computeIfAbsent(key, k -> new ArrayList<>()).add(0, 0);
								pi.PageIdx=0;
								countpage++;

								return pi;
							}
							else if(dico.get(key).contains(1)==false) {
								dico.computeIfAbsent(key, k -> new ArrayList<>()).add(1, 1);
								pi.PageIdx=1;
								countpage++;

								return pi;
							}
							else if(dico.get(key).contains(2)==false) {
								dico.computeIfAbsent(key, k -> new ArrayList<>()).add(2,2 );
								pi.PageIdx=2;
								countpage++;

								return pi;
							}
							else if(dico.get(key).contains(3)==false) {
								dico.computeIfAbsent(key, k -> new ArrayList<>()).add(3, 3);
								pi.PageIdx=3;
								countpage++;

								return pi;
							}
							
							
							/*dico.computeIfAbsent(key, k -> new ArrayList<>()).add((dico.get(key)).size());*/
							System.out.println("Fin du parcour emplacement libre trouver");

							
							break;
						}
					c++;	
					 	
				}
		        System.out.println(dico.toString())	;

				if(c==dico.size()){
						dico.computeIfAbsent(dico.size(), k -> new ArrayList<>()).add(0);
						System.out.println("tout les fichier sont complet, creation d'un fichier existant");
							//creerFichier();
						fileId++;
						pi.FileIdx=dico.size()-1;
						pi.PageIdx=0;
						countpage++;

				}
				

				
			}
			return pi;
	 }

	
	/**
	 * Remplie l’argument buff avec le contenu disque de la page identifiée par l’argument pageId.
	 * @param pageId la position du fichier
	 * @param buff le ByteBuffer (externe) à remplir
	 */
	
	public static void readPage(PageId pageId, ByteBuffer buff) {
		/*fichier.seek(pageId.PageIdx*4096);
		for(int i = pageId.PageIdx*4096;i<pageId.PageIdx*4096+4096;i++) {
			buff.put(fichier.readByte());
		}*/
		
		try (RandomAccessFile fichier = new RandomAccessFile("F"+pageId.getFileIdx()+".bdda","rw")) { // Ouvre le fichier d'id fileId
			fichier.seek(pageId.PageIdx*4096);
			fichier.read(buff.array());
			
			//Test sur le Main
			//System.out.println(buff.limit());
			//System.out.println("Position 10:"+buff.get(10));
		} catch (IOException e) {
			e.printStackTrace();
		} //"/users/licence/il01193/Bureau/PROJET_BDDA/PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI/DB/test.txt","rw"
	}
	
	
	/**
	 *  Ecrit le contenu de l’argument buff dans le fichier et à la position indiqués par l’argument pageId. 
	 *  @param pageId position du fichier
	 *  @param buff le contenu du ByteBuffer à écrire dans un fichier
	 */
	public static void writePage(PageId pageId, ByteBuffer buff) {

		try (RandomAccessFile fichier = new RandomAccessFile("F"+pageId.getFileIdx()+".bdda","rw")) { // Ouvre le fichier d'id fileId
			/*
			for(int i = pageId.PageIdx;i<buff.limit();i++) {
				fichier.seek(pageId.getPageIdx()*4096);
				fichier.write(buff.get(i));
			}*/
			
			fichier.seek(pageId.PageIdx*4096); // On se place à la position de la page, donc page*byte-ième position
			fichier.write(buff.array()); // puis on écrit le contenu du buff dans le fichier

			
			// Test sur le Main
			fichier.seek(5);
			//System.out.println(fichier.read());
            
		} catch (IOException e) {
			e.printStackTrace();
		} //"/users/licence/il01193/Bureau/PROJET_BDDA/PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI/DB/test.txt","rw"
	}
	
	
	public void Deadalloc (PageId pi) {

		dico.get(pi.FileIdx).remove(pi.PageIdx);
		
		System.out.println("apres dealloc");
		System.out.println(dico.toString());
		countpage--;
	}
		
		
/*
	public class Delete_File(){
	
}
    public static void main(String[] args)
    {
     try{

      File file = new File("c:\\fichier.log");

      if(file.delete()){
       System.out.println(file.getName() + " est supprimé.");
      }else{
       System.out.println("Opération de suppression echouée");
      }
                File dossier = new File("c:\\dossier_log");

      if(dossier.delete()){
       System.out.println(dossier.getName() + " est supprimé.");
      }else{
       System.out.println("Opération de suppression echouée");
      }

     }catch(Exception e){
      e.printStackTrace();
     }
    }
}*/
	


	public int GetCurrentCountAllocPages() {
		return countpage;
		
	}







}
