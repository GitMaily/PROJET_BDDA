
public class FileManagerTests {

	public static void main(String[]args) {
		
		
		PageId id = new PageId();
		RelationInfo rel = new RelationInfo("Test", 0, args, args);
		id = FileManager.getInstance().addDataPage(rel);
		
	}
}
