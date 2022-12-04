import java.nio.ByteBuffer;
import java.util.ArrayList;

public class FileManagerTests {

	public static void main(String[]args) {
		
		//createNewHeaderPage
		PageId headerPage = FileManager.getInstance().createNewHeaderPage();
		System.out.println(headerPage.toString());

		//addDataPage
		ArrayList<ColInfo> al = new ArrayList<ColInfo>();
		ColInfo col1 = new ColInfo("NOM","VARCHAR(10)");
		ColInfo col2 = new ColInfo("UE","VARCHAR(5)");
		al.add(col1);
		al.add(col2);

		ArrayList<ColInfo> a2 = new ArrayList<ColInfo>();
		ColInfo col3 = new ColInfo("X","INTEGER");
		ColInfo col4 = new ColInfo("C2","REAL");
		ColInfo col5 = new ColInfo("BLA","VARCHAR(10)");

		a2.add(col3);
		a2.add(col4);
		a2.add(col5);

		RelationInfo rel = new RelationInfo("Profs", 2, al, headerPage);
		RelationInfo rel2 = new RelationInfo("R", 3, a2, headerPage);

		PageId pi = FileManager.getInstance().addDataPage(rel);
		PageId pi2 = FileManager.getInstance().addDataPage(rel2);


		System.out.println(pi.toString());
		System.out.println(pi2.toString());

		
		//getFreeDataPageId
		PageId freePage = FileManager.getInstance().getfreeDataPageId(rel, 12);
		System.out.println(freePage.toString());
		
		PageId freePage1 = FileManager.getInstance().getfreeDataPageId(rel, 60);
		System.out.println(freePage1.toString());
		
		
		//writeRecordToDataPage
		//1
		ArrayList<String> values = new ArrayList<String>();
		values.add("Ileana");
		values.add("BDDA");
		Record record = new Record(rel,values);
		RecordId rid = FileManager.getInstance().writeRecordToDataPage(record, freePage);
		
		System.out.println(rid.toString());
		
		//2
		ArrayList<String> values2 = new ArrayList<String>();
		values2.add("Crapez");
		values2.add("GFE");
		Record record2 = new Record(rel,values2);
		RecordId rid2 = FileManager.getInstance().writeRecordToDataPage(record2, freePage1);
		
		System.out.println(rid2.toString());
		
		
		//Affichage du contenu du DataPage
		//1
		ByteBuffer bb = BufferManager.getInstance().GetPage(freePage);
		
		StringBuffer sbA = new StringBuffer();
		StringBuffer sbB = new StringBuffer();
		for(int i = 0;i<bb.limit();i+=Character.BYTES) {
			sbA.append(bb.getChar(i));
		}
		System.out.println(sbA.toString());
		
		System.out.println(bb.getInt(4092));
		
		for(int i = bb.capacity()-Integer.BYTES;i>=DBParams.pageSize-Integer.BYTES*2-(2*Integer.BYTES*bb.get(4088));i-=Character.BYTES) {
			sbB.append(bb.getInt(i)+",");
		}
		System.out.println(sbB.toString());
		
		
		BufferManager.getInstance().FreePage(freePage, true);
		
		//2
		ByteBuffer bb1 = BufferManager.getInstance().GetPage(freePage1);
		
		StringBuffer sb1A = new StringBuffer();
		StringBuffer sb1B = new StringBuffer();

		for(int i = 0;i<bb1.limit();i+=Character.BYTES) {
			sb1A.append(bb1.getChar(i));
		}
		System.out.println(sb1A.toString());
		
		System.out.println(bb1.getInt(4092));

		for(int i = bb1.capacity()-Integer.BYTES;i>=DBParams.pageSize-Integer.BYTES*2-(2*Integer.BYTES*bb1.get(4088));i-=Character.BYTES) {
			sb1B.append(bb1.getInt(i)+",");
		}
		System.out.println(sb1B.toString());
		
		BufferManager.getInstance().FreePage(freePage1, true);

		
		System.out.println(bb.toString());
		System.out.println(bb1.toString());
		
		
		//getRecordsInDataPage
		ArrayList<Record> records = new ArrayList<Record>();
		records = FileManager.getInstance().getRecordsInDataPage(rel, freePage1);
		for(Record rec : records) {
			System.out.println(rec.toString());
		}
		
		
		//getAllDataPages
		ArrayList<PageId> pids = new ArrayList<PageId>();
		pids = FileManager.getInstance().getAllDataPages(rel);
		
		for(PageId pid : pids) {
			System.out.println(pid.toString());
		}

		//InsertRecordInRel
		RecordId recId = FileManager.getInstance().InsertRecordInRel(record);
		System.out.println(recId.toString());
		
		//getAllRecords
		ArrayList<Record> recs = new ArrayList<Record>();
		records = FileManager.getInstance().getAllRecords(rel);
		for(Record rec : recs) {
			System.out.println(rec.toString());
		}

	}
}
