
public class BufferManagerTests {
	
	static BufferManager buff = new BufferManager();
	
	static PageId pid = new PageId();
	
	public static void main(String[] args) {
		
		TestGetPage();
		
		TestFreePage();
		
		TestFlushAll();

	}
	
	public static void TestGetPage() {
		
		buff.GetPage(pid);
		buff.GetPage(pid);
		buff.GetPage(pid);
		buff.GetPage(pid);
		
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
