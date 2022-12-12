import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DiskManager {

	private ByteBuffer page;
	
	private static int fileId;
	static int countpage ;
	static ArrayList<PageId> listeblanche = new ArrayList<PageId>();
	static ArrayList<PageId> listenoire = new ArrayList<PageId>();

	
	static DiskManager INSTANCE = new DiskManager();
	
	public static DiskManager getInstance() {
		if (INSTANCE == null) {
            INSTANCE = new DiskManager();
        }
		return INSTANCE;	
	}
	
	/**
	 * Crée un fichier vide, alloue un ByteBuffer de 4096 octets
	 * @param pi Le PageId du fichier à créer
	 */
	public void creerFichier(PageId pi) {
		String path = DBParams.DBPath;
		//String path = "C:\\Users\\milly\\Desktop\\PROJET_BDDA\\PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI_MARZOUK\\DB";

		try {
			RandomAccessFile fichier = new RandomAccessFile(path + File.separator+"F"+pi.getFileIdx()+".bdda","rw"); //DBParams.DBpath
            //fichier.setLength(DBParams.maxPagesPerFile*DBParams.pageSize);


			ByteBuffer bb = ByteBuffer.allocate(DBParams.pageSize); // alloue 4096 octets
			
			bb.put(4095-2, (byte) 32);
		
            fichier.seek(fichier.length());
            //fichier.writeByte(bb.get(4095));
            fichier.write(bb.array());
            
            //Tests fichier complet
            //fichier.seek(4*4096-4);
            //fichier.writeByte(5);
            
            fichier.close();
            
		} catch (IOException e) {
			e.printStackTrace();
		}
		fileId++;

	}
	
	
	public PageId allocPage() {
		
		String path = DBParams.DBPath;  //"C:\\Users\\milly\\Desktop\\PROJET_BDDA\\PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI_MARZOUK\\DB"

		//String path = "C:\\Users\\milly\\Desktop\\PROJET_BDDA\\PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI_MARZOUK\\DB";
		File file = new File(path); // DBParams.DBPath 
		
		File [] f = file.listFiles();
		PageId pi = new PageId();
		
		
		int count = 0;

		boolean complets = false;
		for(int i = 0;i<f.length;i++) {
			if(f[i].getName().endsWith(".bdda")) {
				int fileIdx = Integer.valueOf(f[i].getName().substring(1,f[i].getName().length()-5));

				if(f[i].length() <= DBParams.pageSize ) {
					pi = new PageId(fileIdx,1);
					//System.out.println("Page = 1");
					//System.out.println(pi.toString());
					countpage++;

					i = f.length;
					
				}
				else if(f[i].length() <= DBParams.pageSize*2 ) {
					pi = new PageId(fileIdx,2);
					//System.out.println("Page = 2");
					//System.out.println(pi.toString());
					countpage++;

					i = f.length;

					
				}
				else if(f[i].length() <= DBParams.pageSize*3 ) {
					pi = new PageId(fileIdx,3);
					//System.out.println("Page = 3");
					//System.out.println(pi.toString());
					countpage++;

					i = f.length;

				}
				else {
					complets = true;
					count++;
				}
				
				
			}
		}
		// Aucun fichier
		if(complets != true) {
			try {
				
				
				RandomAccessFile fichier = new RandomAccessFile(path +File.separator+ "F"+pi.getFileIdx()+".bdda","rw"); //DBParams.DBpath
				ByteBuffer bb = ByteBuffer.allocate(DBParams.pageSize); // alloue 4096 octets
				bb.put(4096-4, (byte) 32);
	            fichier.seek(fichier.length());
	            fichier.write(bb.array());
				
				fichier.close();
				countpage++;
				return pi;
			
			
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		else {
			// Le fichier est plein (4*4096)
			// créer nouveau fichier
			pi.setFileIdx(count);
			
			creerFichier(pi);
			countpage++;

			return pi;
			
		}
		
		
		

		/*StringBuffer sb = new StringBuffer();
		for(File fs : f) {
			if(fs.getName().endsWith(".bdda")) {
				sb.append(fs.getName()+"\t");
				System.out.println("Liste des fichiers .bdda : "+sb.toString());

				System.out.println("fs length"+fs.length());
				
				if(fs.length() >= DBParams.maxPagesPerFile*DBParams.pageSize ) {  //DBParams.maxPagesPerFile*DBParams.pageSize
					// Le fichier est plein (4*4096)
					// créer nouveau fichier
					
					
					/////////////////////////////
					
					
					pi.setFileIdx(Integer.valueOf(fs.getName().substring(1,fs.getName().length()-5))+1);
					
					creerFichier(pi);
					
					return pi;
					
				}
				else { // il reste de la place
					try {
						pi.setFileIdx(Integer.valueOf(fs.getName().substring(1,fs.getName().length()-5)));
						
						// Chercher cb de Pages allouées... Pas sûre qu'il faut faire comme ça mais ça marche :D
						if(fs.length() <= DBParams.pageSize) {
							pi.setPageIdx(1);
							System.out.println("Page 1");
						}
						else if(fs.length() <= DBParams.pageSize*2) {
							pi.setPageIdx(2);
							System.out.println("Page 2");

						}
						else if(fs.length() <= DBParams.pageSize*3) {
							pi.setPageIdx(3);
							System.out.println("Page 3");

						}
						
						RandomAccessFile fichier = new RandomAccessFile(path + "\\F"+pi.getFileIdx()+".bdda","rw"); //DBParams.DBpath
						ByteBuffer bb = ByteBuffer.allocate(DBParams.pageSize); // alloue 4096 octets
						bb.put(4096-4, (byte) 32);
			            fichier.seek(fichier.length());
			            fichier.write(bb.array());
						
						fichier.close();
						
						return pi;
					
					
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} 


				}
				

			}
			
			
		}*/
		
		// Aucun fichier .bdda, création d'un nouveau fichier
		pi.setFileIdx(0);
		pi.setPageIdx(0);
		creerFichier(pi);
		
		return pi;
	}
	
	static Map<Integer, ArrayList<Integer> > dico = new HashMap<Integer, ArrayList<Integer>>(); 
	public PageId allocPage2() {
		 int c =0;
		 
		  PageId pi = new PageId();

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

				creerFichier(pi);
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
						creerFichier(pi);
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
	
	public void readPage(PageId pageId, ByteBuffer buff) {
	
		
		try (RandomAccessFile fichier = new RandomAccessFile(DBParams.DBPath+File.separator+"F"+pageId.getFileIdx()+".bdda","rw")) { // Ouvre le fichier d'id fileId
			fichier.seek(pageId.PageIdx*4096);
			fichier.read(buff.array());
            fichier.close();

		} catch (IOException e) {
			e.printStackTrace();
		} //"/users/licence/il01193/Bureau/PROJET_BDDA/PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI/DB/test.txt","rw"
	}
	
	
	/**
	 *  Ecrit le contenu de l’argument buff dans le fichier et à la position indiqués par l’argument pageId. 
	 *  @param pageId position du fichier
	 *  @param buff le contenu du ByteBuffer à écrire dans un fichier
	 */
	public void writePage(PageId pageId, ByteBuffer buff) {

		
		try (RandomAccessFile fichier = new RandomAccessFile(DBParams.DBPath+File.separator+"F"+pageId.getFileIdx()+".bdda","rw")) { // Ouvre le fichier d'id fileId
			
			
			fichier.seek(pageId.PageIdx*4096); // On se place à la position de la page, donc page*byte-ième position
			fichier.write(buff.array()); // puis on écrit le contenu du buff dans le fichier

            fichier.close();
		} catch (IOException e) {
			e.printStackTrace();
		} //"/users/licence/il01193/Bureau/PROJET_BDDA/PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI/DB/test.txt","rw"
	}
	
	
	/**
	 * Désalloue une page. Méthode de listes.
	 * @param pi - La page à désallouer
	 */
	public void Deadalloc2 (PageId pi) {

		dico.get(pi.FileIdx).remove(pi.PageIdx);
		
		System.out.println("apres dealloc");
		System.out.println(dico.toString());
		countpage--;
	}
		
	
	/**
	 * Désalloue une page. Ignore la page (ne jamais la « réutiliser » par la suite)
	 * @param pageId - La page à désallouer
	 */
	public void deallocPage(PageId pageId) {
		countpage--;
	}

	public String toString() {
		String path = DBParams.DBPath;
		File file = new File(path); // DBParams.DBPath 
		File [] f = file.listFiles();
		
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i<f.length;i++) {
			if(f[i].getName().endsWith(".bdda")) {
				sb.append(f[i].getName()+" : "+f[i].length() + " Ko" + "\n");
				//System.out.println(f[i].getName()+" : "+f[i].length() + " Ko");
			}
		}
		
		return sb.toString();
	}
	


	public int GetCurrentCountAllocPages() {
		return countpage;
		
	}


	/*
	 * Reinitialise le DiskManager lors de la saisie de la commande DROPDB
	 */
	public void reinitialiser(){
		countpage = 0;
	}



	public ByteBuffer getPage() {
		return page;
	}



	public void setPage(ByteBuffer page) {
		this.page = page;
	}


	

}
