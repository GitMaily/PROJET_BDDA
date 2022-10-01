import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;

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

	
	
	
	@SuppressWarnings("null")
	public PageId allocPage() {

		
		//on cherche un fichier qui n'a pas encore atteint sa taille maximale
		// en l'occurrence, la taille d'un fichier est de 4*4096
		
		
		  PageId pi = new PageId();
		
		try {
			RandomAccessFile fichiers = new RandomAccessFile("F"+fileId+".bdda","rw"); //"/users/licence/il01193/Bureau/PROJET_BDDA/PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI/DB/test.txt","rw"

			
			// Si la taille du fichier F est inférieure à sa taille maximale
			if(fichiers.length() < (DBParams.maxPagesPerFile*DBParams.pageSize)) {
				// On créé une page
				ByteBuffer bb = ByteBuffer.allocate(DBParams.pageSize); // alloue 4096 octets
				
				bb.put(4095, (byte) 32);
				
				System.out.println(bb.limit());
	            
	            System.out.println(bb.get(4095));
	            
	            fichiers.seek(fichiers.length());
	            
	            fichiers.write(bb.get(4095));
	            

				//page.add(e);
	            int id = (int) ((16384-fichiers.length()) / 4096 );
	            int paid = 0;
	            switch(id) {
	            	case 1:  paid= 3;
	            	break;
	            	case 2 : paid=2;
	            	break;
	            	case 3: paid =1;
	            	break;
	            	case 4: paid =0;
	            }
	            fichiers.close();
	            
	           
	            pi.FileIdx= fileId;
	            pi.PageIdx=paid;
	          
	            
	            listeblanche.add(pi);
	            
				
			}
			/*RandomAccessFile pages = new RandomAccessFile("/users/licence/il01193/Bureau/PROJET_BDDA/PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI/DB/test.txt","rw");
			pages.setLength(4096);
	        pages.writeUTF("Hello World");
			System.out.println(pages.length());*/

			// Ajouter un nouveau fichier si maxi
			else {
				 pi.FileIdx=fileId;
				 pi.PageIdx=0;

				if(DBParams.maxPagesPerFile == pi.getPageIdx()) { // On vérifie si on a dépassé le nombre de pages max dans le fichier
			
					// créer fichier, peut être faire une boucle while?
					pi.setFileIdx(++pi.FileIdx); // On incrémente l'Id du fichier
	
					creerFichier();
					
				
					//RandomAccessFile rf = new RandomAccessFile("/users/licence/il01193/Bureau/PROJET_BDDA/PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI/DB/test2.txt","rw");
					
					//System.out.println(pages.length());
				}
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		countpage++;
		
		return pi;
		
		
		
	}
	
	public void readPage(PageId pageId, ByteBuffer buff) {
		/*fichier.seek(pageId.PageIdx*4096);
		for(int i = pageId.PageIdx*4096;i<pageId.PageIdx*4096+4096;i++) {
			buff.put(fichier.readByte());
		}*/
		
		try (RandomAccessFile fichier = new RandomAccessFile("F"+pageId.getFileIdx()+".bdda","rw")) { // Ouvre le fichier d'id fileId
			fichier.seek(pageId.PageIdx*4096);
			fichier.read(buff.array());
			
			//Test sur le Main
			System.out.println(buff.limit());
			System.out.println("Position 10:"+buff.get(10));
		} catch (IOException e) {
			e.printStackTrace();
		} //"/users/licence/il01193/Bureau/PROJET_BDDA/PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI/DB/test.txt","rw"
	}
	
		
	public void writePage(PageId pageId, ByteBuffer buff) {

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
			System.out.println(fichier.read());
            
		} catch (IOException e) {
			e.printStackTrace();
		} //"/users/licence/il01193/Bureau/PROJET_BDDA/PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI/DB/test.txt","rw"
	}
	
	
	public void Deadalloc (PageId pi) {
		
		
		
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
		countpage-- ;
	}


	public int GetCurrentCountAllocPages() {
		return countpage;
		
	}







}
