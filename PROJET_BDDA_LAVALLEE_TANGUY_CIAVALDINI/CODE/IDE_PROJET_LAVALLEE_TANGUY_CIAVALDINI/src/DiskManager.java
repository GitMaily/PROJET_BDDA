import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class DiskManager {

	// Attributs
	
	/* On ne peut utiliser qu'une seule instance
	 * il faut choisir entre ByteBuffer et byte[]
	 */
	 
	private List<ByteBuffer>page;
	//private byte [] page2;
	//private ByteBuffer page3;
	
	public DiskManager() {
	}
	// Constructeurs
	public DiskManager(ByteBuffer bb) {
		page = new ArrayList<ByteBuffer>();
		//page2 = new byte[4096];
		//this.page3 = bb;
	}
	
	@SuppressWarnings("null")
	public PageId allocPage() {

		PageId pi = new PageId();
		//on cherche un fichier qui n'a pas encore atteint sa taille maximale
		// en l'occurrence, la taille d'un fichier est de 4*4096
		
		
		try {
			RandomAccessFile fichiers = new RandomAccessFile("/users/licence/il01193/Bureau/PROJET_BDDA/PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI/DB/test.txt","rw");

			if(fichiers.length() < (DBParams.maxPagesPerFile*DBParams.pageSize)) {
				//créer une page
				ByteBuffer e = null;
				e.allocate(4096);
				//ByteBuffer.wrap(page2);
				//System.out.println(e.capacity());

				//page3.allocate(4096);
				//System.out.println(page3.capacity());

				page.add(e);
				
			}
			RandomAccessFile pages = new RandomAccessFile("/users/licence/il01193/Bureau/PROJET_BDDA/PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI/DB/test.txt","rw");
			pages.setLength(4096);
	        pages.writeUTF("Hello World");
			System.out.println(pages.length());

			// Ajouter un nouveau fichier si maxi
			if(DBParams.maxPagesPerFile == pi.PageIdx) {
				// créer fichier, peut être faire une boucle while?
				RandomAccessFile rf = new RandomAccessFile("/users/licence/il01193/Bureau/PROJET_BDDA/PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI/DB/test2.txt","rw");
				
				System.out.println(pages.length());
			
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pi;
		
	}
}
