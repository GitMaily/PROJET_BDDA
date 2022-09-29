import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class DiskManagerTests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		TestEcriturePage(0);
		//TestLecturePage(1);
	}
	
	public static void TestEcriturePage(int fileIdx) {
	
		PageId id = new PageId();
		id.setFileIdx(fileIdx);
		ByteBuffer buff = ByteBuffer.allocate(4096);
		
		buff.put(5,(byte) 55);
		
		System.out.println("Test:"+buff.get(4));
		
		DiskManager disk = new DiskManager();
		disk.writePage(id, buff);

		
	}
	
	public static void TestLecturePage(int fileIdx) {
		try {
			PageId id = new PageId();

			RandomAccessFile fichier = new RandomAccessFile("F"+id.getFileIdx()+".bdda","rw");

			//System.out.println(fichier.length());
			
			id.setFileIdx(fileIdx);
			ByteBuffer buff = ByteBuffer.allocate(4096);
			//buff.put(4,(byte) 32);
			DiskManager disk = new DiskManager();
			
			
			
			
			fichier.seek(5);
			fichier.write(78);
			
			fichier.seek(5);

			System.out.println("seek 5 dans le main: "+fichier.read());
			disk.readPage(id,buff);
			
			
			fichier.close();

		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
			System.out.println("Position 5:"+buff.get(5));
		} catch (IOException e) {
			e.printStackTrace();
		} //"/users/licence/il01193/Bureau/PROJET_BDDA/PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI/DB/test.txt","rw"
	}
	
		
	public void writePage(PageId pageId, ByteBuffer buff) {

		try (RandomAccessFile fichier = new RandomAccessFile("F"+pageId.getFileIdx()+".bdda","rw")) { // Ouvre le fichier d'id fileId
			/*
			// On se place à la position de la page, donc page*byte-ième position, puis on écrit le contenu du buff dans le fichier
			for(int i = pageId.PageIdx;i<buff.limit();i++) {
				fichier.seek(pageId.getPageIdx()*4096);
				fichier.write(buff.get(i));
			}*/
			fichier.write(buff.array());
			
			// Test sur le Main
			fichier.seek(5);
			System.out.println(fichier.read());
            
		} catch (IOException e) {
			e.printStackTrace();
		} //"/users/licence/il01193/Bureau/PROJET_BDDA/PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI/DB/test.txt","rw"
	}
	

}
