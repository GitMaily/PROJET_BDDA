import java.util.ArrayList;

public class FileManagerTests {

	public static void main(String[]args) {
		
		
		PageId id = new PageId();
		
		ArrayList<ColInfo> al = new ArrayList<ColInfo>();
		RelationInfo rel = new RelationInfo("Test", 5, al, id);
		id = FileManager.getInstance().addDataPage(rel);
		
	}
}
