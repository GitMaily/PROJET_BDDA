import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class DiskManagerTests {
	static DiskManager dm = DiskManager.getInstance();

	public static void main(String[] args) {

		//TestEcriturePage(0);
		//TestLecturePage(0);
		
		TestCreerFichier();

		TestAllocPage();
		TestCountAllocPage();
	}
	
	
	public static void TestAllocPage() {
		dm.allocPage();
		dm.allocPage();
		dm.allocPage();
		dm.allocPage();

	}
	
	public static void TestCreerFichier() {
		dm.creerFichier();
	
		dm.creerFichier();
		dm.creerFichier();

		
		
	}
	
	public static void TestCountAllocPage() {
		
	

		System.out.println("Current allocated pages: "+dm.GetCurrentCountAllocPages());
	}
	
	
	public static void TestEcriturePage(int fileIdx) {
	
		PageId id = new PageId();
		id.setFileIdx(fileIdx);
		ByteBuffer buff = ByteBuffer.allocate(4096);
		
		buff.put(5,(byte) 55);
		
		System.out.println("Test:"+buff.get(5));
		
		DiskManager dm = DiskManager.getInstance();
		dm.writePage(id, buff);

		
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
			
			
			
			
			fichier.seek(10);
			fichier.write(78);
			
			fichier.seek(10);

			System.out.println("seek 10 dans le main: "+fichier.read());
			disk.readPage(id,buff);
			
			
			fichier.seek(5);

			
			System.out.println("seek 5 dans le main: "+fichier.read());

			
			fichier.close();

		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
		
	
	

}
