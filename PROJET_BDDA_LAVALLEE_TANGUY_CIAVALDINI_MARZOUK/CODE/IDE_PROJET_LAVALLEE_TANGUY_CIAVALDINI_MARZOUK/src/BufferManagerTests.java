import java.nio.ByteBuffer;

public class BufferManagerTests {
	
	
	
	static PageId pid = new PageId();
	static PageId p1 = new PageId(0,0);
	static PageId p2 = new PageId(1,0);
	static PageId p3 = new PageId(0,1);
	
	public static void main(String[] args) {
		
		DBParams.DBPath = args[0];
		DBParams.frameCount = 2;
		DBParams.maxPagesPerFile = 4;
		DBParams.pageSize = 4096;
		
		TestEcriture();
		//TestGetPage();
		
		//TestSimple();
		
		//TestAvance();
		TestAvance2();
		
		//AllouerLesPages();

		

	}
	
	
	public static void AllouerLesPages() {
		p1 = DiskManager.getInstance().allocPage();
		p2 = DiskManager.getInstance().allocPage();
		p3 = DiskManager.getInstance().allocPage();
	}
	
	

	/**
	 * Test d'écriture des pages.
	 * @implNote bufferTest1 contiendra que des 5 jusqu'à son byte 2048.
	 * @implNote bufferTest2 contiendra que des 10 à partir de son byte 2048 jusqu'à la fin.
	 * @implNote bufferTest3 contiendra que des 6 à partir de son byte 2048 jusqu'à la fin.
	 */
	public static void TestEcriture() {
		ByteBuffer bufferTest1 = ByteBuffer.allocate(DBParams.pageSize);
		for (int i = 0; i < DBParams.pageSize/2; i++) {
			bufferTest1.put(i, (byte) 5);
		}

		ByteBuffer bufferTest2 = ByteBuffer.allocate(DBParams.pageSize);
		for (int i = DBParams.pageSize/2; i < DBParams.pageSize; i++) {
			bufferTest2.put(i, (byte) 10);
		}
		
		ByteBuffer bufferTest3 = ByteBuffer.allocate(DBParams.pageSize);
		for (int i = 0; i < DBParams.pageSize/2; i++) {
			bufferTest3.put(i, (byte) 6);
		}
		
		
		
		
		DiskManager.getInstance().writePage(p1, bufferTest1);
		DiskManager.getInstance().writePage(p2, bufferTest2);
		DiskManager.getInstance().writePage(p3, bufferTest3);


	}
	
	/**
	 * On va tester le cas où il n'y a pas de remplacement. Flush à la fin.
	 */
	public static void TestSimple() {
		TestSimpleGetPages();
		TestSimpleFreePages();
		BufferManager.getInstance().FlushAll();
		
		// On vérifie que ça a bien flushé
		if(BufferManager.getInstance().getListFrames().get(0).getPageId() == null) {
			System.out.println("Le flush a bien marché !");
		}
		
	}
	
	/**
	 * Fait appel à 2 GetPage(), devrait afficher le contenu des buffersTest
	 */
	public static void TestSimpleGetPages() {
		ByteBuffer bb1 = ByteBuffer.allocate(DBParams.pageSize);
		ByteBuffer bb2 = ByteBuffer.allocate(DBParams.pageSize);

		
		// Afficher le contenu de bb1
		bb1 = BufferManager.getInstance().GetPage(p1);
		for (int i = 0; i < bb1.capacity(); i++) {
			System.out.print(bb1.array()[i]);
		}
		
		// Afficher le contenu de bb2
		bb2 = BufferManager.getInstance().GetPage(p2);
		for (int i = 0; i < bb2.capacity(); i++) {
			System.out.print(bb2.array()[i]);
		}
		
		System.out.println();
		System.out.println("PageId[FileIdx,PageIdx] de ListFrames[0]: ["+BufferManager.getInstance().getListFrames().get(0).getPageId().getFileIdx()+"]["+BufferManager.getInstance().getListFrames().get(0).getPageId().getPageIdx()+"]");
		System.out.println("PageId[FileIdx,PageIdx] de ListFrames[1]: ["+BufferManager.getInstance().getListFrames().get(1).getPageId().getFileIdx()+"]["+BufferManager.getInstance().getListFrames().get(1).getPageId().getPageIdx()+"]");
		System.out.println();
	
	}
	
	public static void TestSimpleFreePages() {
		
		// Affiche les pin count qui devraient être à 1
		System.out.println("Pin_count de ListFrames[0]: "+BufferManager.getInstance().getListFrames().get(0).getPin_count());
		System.out.println("Pin_count de ListFrames[1]: "+BufferManager.getInstance().getListFrames().get(1).getPin_count());

		BufferManager.getInstance().FreePage(p1,false);
		BufferManager.getInstance().FreePage(p2,false);
		
		// Affiche les pin count après un FreePage
		System.out.println();
		System.out.println("Pin_count de ListFrames[0]: "+BufferManager.getInstance().getListFrames().get(0).getPin_count());
		System.out.println("Pin_count de ListFrames[1]: "+BufferManager.getInstance().getListFrames().get(1).getPin_count());

	}
	
	
	
	/**
	 * On test le cas avec un (ou plusieurs) remplacements LRU
	 */
	public static void TestAvance() {
		
		ByteBuffer bb1 = ByteBuffer.allocate(DBParams.pageSize);
		ByteBuffer bb2 = ByteBuffer.allocate(DBParams.pageSize);
		
		BufferManager.getInstance().GetPage(p1);
		BufferManager.getInstance().GetPage(p2);
		
		BufferManager.getInstance().FreePage(p1, true);
		BufferManager.getInstance().FreePage(p2, true);
		
		System.out.println();
		// Afficher le contenu du ByteBuffer de ListFrames[0]
		// Résultat attendu : contenu de p1
		System.out.println("Contenu de p1");
		for(int i = 0;i <BufferManager.getInstance().getListFrames().get(0).getBuffer().capacity();i++) {
			System.out.print(BufferManager.getInstance().getListFrames().get(0).getBuffer().array()[i]);
		}
		
		// Normalement la prochaine page demandée devra remplacer p1
		bb1 = BufferManager.getInstance().GetPage(p3);
		
		System.out.println();
		// Afficher le contenu du ByteBuffer de ListFrames[0]
		// Résultat attendu : contenu de p3
		
		System.out.println("Contenu de p3");
		for(int i = 0;i <BufferManager.getInstance().getListFrames().get(0).getBuffer().capacity();i++) {
			System.out.print(BufferManager.getInstance().getListFrames().get(0).getBuffer().array()[i]);
		}
		
		
		System.out.println();
		// Afficher le contenu de bb1
		System.out.println("Contenu de bb1");
		for (int i = 0; i < bb1.capacity(); i++) {
			System.out.print(bb1.array()[i]);
		}
		
		
		
		bb2 = BufferManager.getInstance().GetPage(p1);
		System.out.println();
		// Afficher le contenu du ByteBuffer de ListFrames[1]
		// Résultat attendu : contenu de p1
		System.out.println("Contenu de p1");
		for(int i = 0;i <BufferManager.getInstance().getListFrames().get(1).getBuffer().capacity();i++) {
			System.out.print(BufferManager.getInstance().getListFrames().get(1).getBuffer().array()[i]);
		}
		
		System.out.println();
		// Afficher le contenu de bb2
		System.out.println("Contenu de bb2");
		for (int i = 0; i < bb2.capacity(); i++) {
			System.out.print(bb2.array()[i]);
		}
		
		BufferManager.getInstance().FlushAll();

	}
	
	/**
	 * Mêmes tests que TestAvance, avec + de LRU
	 */
	public static void TestAvance2() {

		BufferManager.getInstance().GetPage(p1);
		BufferManager.getInstance().GetPage(p2);
		BufferManager.getInstance().GetPage(p1);
		BufferManager.getInstance().GetPage(p1);
		BufferManager.getInstance().GetPage(p1);

		BufferManager.getInstance().FreePage(p1, true);
		BufferManager.getInstance().FreePage(p2, true);
		
		
		BufferManager.getInstance().GetPage(p3);
		
		BufferManager.getInstance().GetPage(p1);
		BufferManager.getInstance().FreePage(p1, true);
		BufferManager.getInstance().FreePage(p1, true);
		BufferManager.getInstance().FreePage(p1, true);
		BufferManager.getInstance().FreePage(p1, true);
		BufferManager.getInstance().FreePage(p1, true);

		BufferManager.getInstance().GetPage(p2);
		BufferManager.getInstance().GetPage(p1);
		BufferManager.getInstance().GetPage(p3);


		BufferManager.getInstance().FlushAll();

	}
	
	public static void TestGetPage() {
		
		//ByteBuffer bb = ByteBuffer.allocate(DBParams.pageSize); // alloue 4096 octets
		
		
		BufferManager.getInstance().GetPage(p1);
		BufferManager.getInstance().GetPage(p2);
		
		//BufferManager.getInstance().FreePage(p1, true);
		//BufferManager.getInstance().FreePage(p2, true);

		
		BufferManager.getInstance().GetPage(p3);
		BufferManager.getInstance().GetPage(p1);
	
		BufferManager.getInstance().FlushAll();
		
	}
	
	public static void TestFreePage() {
		
		BufferManager.getInstance().FreePage(pid, true);
		BufferManager.getInstance().FreePage(pid, false);
		BufferManager.getInstance().FreePage(pid, false);
		BufferManager.getInstance().FreePage(pid, true);
		
	}
	
	public static void TestFlushAll() {
		
		BufferManager.getInstance().FlushAll();
		
	}
}
