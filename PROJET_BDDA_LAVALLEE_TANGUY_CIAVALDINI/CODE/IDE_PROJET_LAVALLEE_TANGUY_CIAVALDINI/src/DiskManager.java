import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DiskManager {

	// Attributs
	
	/* On ne peut utiliser qu'une seule instance
	 * il faut choisir entre ByteBuffer et byte[]
	 */
	 
	//private List<ByteBuffer>page;
	//private byte [] page2;
	private ByteBuffer page;
	
	public DiskManager() {
	}
	// Constructeurs
	public DiskManager(ByteBuffer bb) {
		//page2 = new byte[4096];
		this.page = bb;
	}
	
	/*public void creerFichier3() {
		// Create the set of options for appending to the file.
	    Set<OpenOption> options = new HashSet<OpenOption>();
	    options.add(APPEND);
	    options.add(CREATE);

	    // Create the custom permissions attribute.
	    Set<PosixFilePermission> perms =
	      PosixFilePermissions.fromString("rw-r-----");
	    FileAttribute<Set<PosixFilePermission>> attr =
	      PosixFilePermissions.asFileAttribute(perms);

	    // Convert the string to a ByteBuffer.
	    String s = "Hello World! ";
	    byte data[] = s.getBytes();
	    ByteBuffer bb = ByteBuffer.wrap(data);
	    
	    Path file = Paths.get("./permissions.log");

	    try (SeekableByteChannel sbc =
	      Files.newByteChannel(file, options, attr)) {
	      sbc.write(bb);
	    } catch (IOException x) {
	      System.out.println("Exception thrown: " + x);
	    }
	  }
	}*/
	
	
	/*public void creerFichier2() {
		// Convert the string to a
	    // byte array.
	    String s = "Hello World! ";
	    byte data[] = s.getBytes();
	    Path p = Paths.get("./logfile.txt");

	    try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(p, CREATE, APPEND))) {
	      out.write(data, 0, data.length);
	    } catch (IOException x) {
	      System.err.println(x);
	    }
	}*/
	
	
	
	public void creerFichier() {
		
		PageId pi = new PageId();
		pi.setFileIdx(pi.FileIdx);
		try {
			RandomAccessFile fichier = new RandomAccessFile("F"+pi.getFileIdx()+".bdda","rw");
            //fichier.setLength(DBParams.maxPagesPerFile*DBParams.pageSize);

			fichier.setLength(0);
            System.out.println(fichier.length());

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

	}
	
	
	
	
	//// TEST 	//// TEST 	//// TEST 	//// TEST
	public void creerFichierBinaire() {
		PageId pi = new PageId();
		pi.setFileIdx(++pi.FileIdx);



		
		try {
			System.out.println(pi.PageIdx);
			FileOutputStream fout = new FileOutputStream("Ffff"+pi.getFileIdx()+".bdda");
			ObjectOutputStream oout = new ObjectOutputStream(fout);
			
			oout.write(5555);
			ByteBuffer bb = ByteBuffer.allocate(DBParams.pageSize); // alloue 4096 octets

            bb.put((byte) 4095);
            
            //System.out.println(((CharSequence) oout).length());
			//out.write();
		} catch (IOException e) {
			System.out.println(" Erreur E/S ");

			//e.printStackTrace();
		}

	}
	
	public void creerFichierTest() {
		try {
			FileOutputStream f = new FileOutputStream ("testtttt.txt");
			ObjectOutputStream s = new ObjectOutputStream(f);
			s.write(1);
			s.flush();
		}
		catch (IOException e){
			System.out.println(" Erreur E/S ");
			} 
		
	}
	
	public void creerFichierTexte() {
		
		PageId pi = new PageId();
		
		
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("F"+pi.getFileIdx()+".bdda"), "utf-8"))) { //  "test.txt"
			System.out.println("Création");
			writer.write("testt");
			writer.close();
			
		} catch(IOException e) {
			System.out.println("Erreur");
			e.printStackTrace();
		} finally {
			try {
				//writer.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void lireFichier() {
		
	    Path path = FileSystems.getDefault().getPath("C:", "\\test.txt.txt");

		Charset charset = Charset.forName("US-ASCII");

		try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		        System.out.println(line);
		    }
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
		
	}
	//// TEST 	//// TEST 	//// TEST 	//// TEST
	
	
	
	
	@SuppressWarnings("null")
	public PageId allocPage() {

		PageId pi = new PageId();
		//on cherche un fichier qui n'a pas encore atteint sa taille maximale
		// en l'occurrence, la taille d'un fichier est de 4*4096
		
		
		try {
			RandomAccessFile fichiers = new RandomAccessFile("F"+pi.getFileIdx()+".bdda","rw"); //"/users/licence/il01193/Bureau/PROJET_BDDA/PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI/DB/test.txt","rw"

			
			// Si la taille du fichier F est inférieure à sa taille maximale
			if(fichiers.length() < (DBParams.maxPagesPerFile*DBParams.pageSize)) {
				// On créé une page
				ByteBuffer bb = ByteBuffer.allocate(DBParams.pageSize); // alloue 4096 octets
				
				bb.put(4095, (byte) 32);
				
				System.out.println(bb.limit());
	            
	            System.out.println(bb.get(4095));
	            
	            fichiers.seek(fichiers.length());
	            
	            fichiers.write(bb.get(4095));
	            
	       
				//ByteBuffer e = null;
				//e.allocate(4096);
				//ByteBuffer.wrap(page2);
				//System.out.println(e.capacity());

				//page3.allocate(4096);
				//System.out.println(page3.capacity());

				//page.add(e);
	            fichiers.close();
				
			}
			/*RandomAccessFile pages = new RandomAccessFile("/users/licence/il01193/Bureau/PROJET_BDDA/PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI/DB/test.txt","rw");
			pages.setLength(4096);
	        pages.writeUTF("Hello World");
			System.out.println(pages.length());*/

			// Ajouter un nouveau fichier si maxi
			else {
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
		return pi;
		
		
		
	}
	
	public void readPage(PageId pageId, ByteBuffer buff) {
		

		for(int i =pageId.PageIdx;i<buff.limit();i++) {
			buff.putInt(pageId.getPageIdx()); // Place le contenu de PageId dans le buff
			buff.put(page.get(i)); // ? ça a l'air + logique
		}
		
		// Au cas où on a besoin de test?
		for(int i =0;i<buff.limit();i++) {
			buff.get(i);
			//System.out.println(buff.get(i)); // Print le contenu de buff
		}
		
		/*Iterator<Byte> it = new page.Iterator();
		
		
		for(Byte it : pageId) { // Pour chaque contenu Byte dans pageId
			page.put();
		 */
		}
		
	public void writePage(PageId pageId, ByteBuffer buff) {

		try {
			RandomAccessFile fichier = new RandomAccessFile("F"+pageId.getFileIdx()+".bdda","rw");
			
			buff.hasArray();
			/*for(int i = 0;i<buff.limit();i++) {
				fichier.seek(fichier.length());
	            fichier.write(buff.get(pageId.PageIdx));
			}*/
			
			
			for(int i = pageId.PageIdx;i<buff.limit();i++) {
				fichier.seek(fichier.length());
				fichier.write(buff.get(i));
			}
			
            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //"/users/licence/il01193/Bureau/PROJET_BDDA/PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI/DB/test.txt","rw"
	}
	
}
