import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class DiskManagerTests {
	static DiskManager dm = DiskManager.getInstance();

	public static void main(String[] args) {

		//TestEcriturePage(0);
		//TestLecturePage(0);
		
		//TestCreerFichier();

		TestAllocPage();
		TestCountAllocPage();
		deallocPageTest();
		TestEcriturePage2();
		TestLecturePage2();
	}
	
	
	public static void TestAllocPage2() {
		
		System.out.println("Allocation de 4 pages. Résultat attendu : F0 avec 16 Ko de taille");
		dm.allocPage2();
		dm.allocPage2();
		dm.allocPage2();
		dm.allocPage2();
		
		System.out.println("Allocation de 3 pages supplémentaires. Résultat attendu : F1 avec 12 Ko de taille");
		dm.allocPage2();
		dm.allocPage2();
		dm.allocPage2();

	}
	
	public static void TestAllocPage() {
		System.out.println("Allocation de 4 pages. Résultat attendu : F0 avec 16 Ko de taille");
		dm.allocPage();
		dm.allocPage();
		dm.allocPage();
		dm.allocPage();
		
		System.out.println("Allocation de 3 pages supplémentaires. Résultat attendu : F1 avec 12 Ko de taille");
		dm.allocPage();
		dm.allocPage();
		dm.allocPage();
		
		
	 	System.out.println(dm.toString());
	}
	
	
	
	public static void TestCountAllocPage() {
		
		System.out.println("Current allocated pages: "+dm.GetCurrentCountAllocPages());
	}
	
	public static void deallocPageTest() {
		dm.deallocPage(new PageId(0,2));
		System.out.println(dm.GetCurrentCountAllocPages());
	}
	
	
	public static void TestEcriturePage2() {
		PageId pi = new PageId(0,0);
		
		ByteBuffer buff = ByteBuffer.allocate(4096);
		String testString1 = "Utilisation de writePage() test dans (0,0)";
		for(Character c : testString1.toCharArray()) {
			buff.putChar(c);
		}
		//buff.putChar('a');
		//buff.putChar(4096-Character.BYTES, 'z');
		dm.writePage(pi, buff);
		
		PageId pi2 = new PageId(0,3);
		
		ByteBuffer buff2 = ByteBuffer.allocate(4096);
		
		String testString2 = "Utilisation de writePage() test dans (0,3)";
		for(Character c : testString2.toCharArray()) {
			buff2.putChar(c);
		}
		//buff2.putChar('a');
		//buff2.putChar(4096-Character.BYTES, 'z');
		dm.writePage(pi2, buff2);
		
		
		//(0,2)
		PageId pi3 = new PageId(0,2);

		ByteBuffer buff3 = ByteBuffer.allocate(4096);
		
		String testString3 = "Utilisation de writePage() test dans (0,2)";
		for(Character c : testString3.toCharArray()) {
			buff3.putChar(c);
		}
		
		dm.writePage(pi3, buff3);
		
		
	}
	
	public static void TestLecturePage2() {
		PageId pi = new PageId(0,0);
		
		ByteBuffer buff = ByteBuffer.allocate(4096);
		
		dm.readPage(pi, buff);
		System.out.println("Lecture de (0,0)");
		for(int i = 0;i<buff.capacity();i+=Character.BYTES) {
			System.out.print(buff.getChar());

		}
		
		PageId pi2 = new PageId(0,3);
		
		ByteBuffer buff2 = ByteBuffer.allocate(4096);
		
		dm.readPage(pi2, buff2);
		System.out.println("Lecture de (0,3)");
		for(int i = 0;i<buff2.capacity();i+=Character.BYTES) {
			System.out.print(buff2.getChar());

		}
		
		
		//(0,2)
		PageId pi3 = new PageId(0,2);
		
		ByteBuffer buff3 = ByteBuffer.allocate(4096);
		
		dm.readPage(pi3, buff3);
		System.out.println("Lecture de (0,2)");
		for(int i = 0;i<buff3.capacity();i+=Character.BYTES) {
			System.out.print(buff3.getChar());

		}
		
	}
	
	public static void TestEcriturePage(int fileIdx) {
	
		PageId id = new PageId();
		id.setFileIdx(fileIdx);
		ByteBuffer buff = ByteBuffer.allocate(4096);
		
		buff.put(5,(byte) 55);
		
		System.out.println("Test:"+buff.get(5));
		
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
			
			
			
			
			fichier.seek(10);
			fichier.write(78);
			
			fichier.seek(10);

			System.out.println("seek 10 dans le main: "+fichier.read());
			dm.readPage(id,buff);
			
			
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
