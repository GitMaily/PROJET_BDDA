import java.nio.ByteBuffer;

public class BufferManagerTests {
	
	static BufferManager buff = new BufferManager();
	
	static PageId pid = new PageId();
	static PageId p1 = new PageId(0,0);
	static PageId p2 = new PageId(1,0);
	static PageId p3 = new PageId(2,0);
	
	public static void main(String[] args) {
		
		TestGetPage();
		
		//TestFreePage();
		
		//TestFlushAll();

	}
	
	public static void TestGetPage() {
		
		//ByteBuffer bb = ByteBuffer.allocate(DBParams.pageSize); // alloue 4096 octets
		
		
		buff.GetPage(p1);
		//buff.FlushAll();
		buff.GetPage(p2);
	
		buff.FreePage(p2, true);
		buff.FreePage(p2, true);

		
		buff.GetPage(p3);
		buff.GetPage(p1);
	
		buff.FlushAll();
		
	}
	
	public static void TestFreePage() {
		
		buff.FreePage(pid, true);
		buff.FreePage(pid, false);
		buff.FreePage(pid, false);
		buff.FreePage(pid, true);
		
	}
	
	public static void TestFlushAll() {
		
		buff.FlushAll();
		
	}
}
