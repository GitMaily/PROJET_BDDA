import java.nio.ByteBuffer;
import java.util.ArrayList;

public class FileManagerTests {
	
	public static RelationInfo creationRelationInfoContexte(PageId headerPage) {
		
		ArrayList<ColInfo> al = new ArrayList<ColInfo>();
		ColInfo col1 = new ColInfo("NOM","VARCHAR(10)");
		ColInfo col2 = new ColInfo("UE","VARCHAR(5)");
		al.add(col1);
		al.add(col2);
		
		System.out.println("***** Création d'une relationInfo \"Profs\", al, headerPage *****");
		RelationInfo rel = new RelationInfo("Profs",al, headerPage);
		System.out.println(rel.toString());
		
		/*
		 * ArrayList<ColInfo> a2 = new ArrayList<ColInfo>();
		ColInfo col3 = new ColInfo("X","INTEGER");
		ColInfo col4 = new ColInfo("C2","REAL");
		ColInfo col5 = new ColInfo("BLA","VARCHAR(10)");

		a2.add(col3);
		a2.add(col4);
		a2.add(col5);
		
		RelationInfo rel2 = new RelationInfo("R", 3, a2, headerPage);
		System.out.println(pi2.toString());

		 */
		System.out.println();
		return rel;
		
	}
	
	public static PageId createNewHeaderPageTest() {
		System.out.println("***** Création d'un nouveau HeaderPage *****");
		PageId headerPage = FileManager.getInstance().createNewHeaderPage();
		System.out.println("PageId allouée pour le HeaderPage : "+headerPage.toString());
		System.out.println();

		return headerPage;
	}
	
	public static PageId addDataPageTest(RelationInfo rel) {
		System.out.println("***** Ajout d'une DataPage *****");
		
		PageId pi = FileManager.getInstance().addDataPage(rel);


		System.out.println("PageId du dataPage créée : "+pi.toString());
		System.out.println();

		return pi;
		
	}
	
	public static PageId getFreeDataPageIdTest(RelationInfo rel) {
		System.out.println("***** Recherche d'une dataPage avec de l'espace disponible *****");
		PageId freePage = FileManager.getInstance().getfreeDataPageId(rel, 12);
		System.out.println("Le PageId avec de l'espace disponible est : "+freePage.toString());
		
		/*PageId freePage1 = FileManager.getInstance().getfreeDataPageId(rel, 60);
		System.out.println(freePage1.toString());
		*/
		
		System.out.println();
		return freePage;
	}
	
	/*public static PageId getFreeDataPageIdTest2() {
		RelationInfo rel = creationRelationInfoContexte();

		PageId freePage2 = FileManager.getInstance().getfreeDataPageId(rel, 60);
		System.out.println(freePage2.toString());
		
		return freePage2;
	}*/
	
	public static Record creationRecordContexte(RelationInfo rel) {
		ArrayList<String> values = new ArrayList<String>();
		values.add("Ileana");
		values.add("BDDA");
		Record record = new Record(rel,values);
		
		return record;
	}
	
	
	public static Record creationRecordContexte2(RelationInfo rel) {
		ArrayList<String> values = new ArrayList<String>();
		values.add("Crapez");
		values.add("GFE");
		Record record = new Record(rel,values);
		
		return record;
	}
	
	public static Record creationRecordContexte3(RelationInfo rel) {
		ArrayList<String> values = new ArrayList<String>();
		values.add("Jean-Guy Mailly");
		values.add("POOA");
		Record record = new Record(rel,values);
		
		return record;
	}
	
	public static Record creationRecordContexte4(RelationInfo rel) {
		ArrayList<String> values = new ArrayList<String>();
		values.add("Moisan");
		values.add("MATHS");
		Record record = new Record(rel,values);
		
		return record;
	}
	
	public static void writeRecordToDataPageTest(Record record, PageId freePage) {
		System.out.println("***** Ecriture du Record dans le DataPage libre *****");
		RecordId rid = FileManager.getInstance().writeRecordToDataPage(record, freePage);
		System.out.println(rid.toString());
		
		System.out.println();
		System.out.println("// Affichage du contenu du DataPage //");
		//Affichage du contenu du DataPage
		ByteBuffer bb = BufferManager.getInstance().GetPage(freePage);
		
		StringBuffer sbA = new StringBuffer();
		for(int i = 0;i<bb.limit();i+=Character.BYTES) {
			sbA.append(bb.getChar(i));
		}
		
		System.out.println(sbA.toString());

		
		
		//Affichage de la position du début de l'espace disponible
		//System.out.println(bb.getInt(4092));
		System.out.println();

		System.out.println("// Affichage du contenu de la fin du DataPage //");
		//Affichage fin du DataPage
		StringBuffer sb2 = new StringBuffer();
		for(int i = bb.capacity()-Integer.BYTES;i>=DBParams.pageSize-Integer.BYTES*2-(2*Integer.BYTES*bb.getInt(4088));i-=Integer.BYTES) {
			sb2.append(bb.getInt(i)+",");
		}
		System.out.println(sb2.toString());

		System.out.println();

		BufferManager.getInstance().FreePage(freePage, true);
	}
	
	
	/*public static void writeRecordToDataPageTest1() {
		RelationInfo rel = creationRelationInfoContexte();
		PageId pi = addDataPageTest(rel);
		System.out.println("addDataPageTest pageId toString = "+pi.toString());
		PageId freePage = getFreeDataPageIdTest();
		System.out.println("getFreeDataPageIdTest freePage toString = "+pi.toString());

		ArrayList<String> values = new ArrayList<String>();
		values.add("Ileana");
		values.add("BDDA");
		Record record = new Record(rel,values);
		RecordId rid = FileManager.getInstance().writeRecordToDataPage(record, freePage);
		
		System.out.println(rid.toString());
		
		//Affichage du contenu du DataPage
		ByteBuffer bb = BufferManager.getInstance().GetPage(freePage);
		
		StringBuffer sbA = new StringBuffer();
		for(int i = 0;i<bb.limit();i+=Character.BYTES) {
			sbA.append(bb.getChar(i));
		}
		
		System.out.println(sbA.toString());
		
		//Affichage de la position du début de l'espace disponible
		//System.out.println(bb.getInt(4092));
		
		//Affichage fin du DataPage
		StringBuffer sb2 = new StringBuffer();
		for(int i = bb.capacity()-Integer.BYTES;i>=DBParams.pageSize-Integer.BYTES*2-(2*Integer.BYTES*bb.getInt(4088));i-=Character.BYTES) {
			sb2.append(bb.getInt(i)+",");
		}
		System.out.println(sb2.toString());

		
		BufferManager.getInstance().FreePage(freePage, true);
	}*/
	
	/*public static void writeRecordToDataPageTest2() {
		RelationInfo rel = creationRelationInfoContexte();
		PageId freePage2 = getFreeDataPageIdTest2();

		ArrayList<String> values2 = new ArrayList<String>();
		values2.add("Crapez");
		values2.add("GFE");
		Record record2 = new Record(rel,values2);
		RecordId rid2 = FileManager.getInstance().writeRecordToDataPage(record2, freePage2);
		
		System.out.println(rid2.toString());
		
		ByteBuffer bb2 = BufferManager.getInstance().GetPage(freePage2);
		
		StringBuffer sb1B = new StringBuffer();
		for(int i = 0;i<bb2.limit();i+=Character.BYTES) {
			sb1B.append(bb2.getChar(i)+",");
		}
		System.out.println(bb2.getInt(4092));

		StringBuffer sb2 = new StringBuffer();

		for(int i = bb2.capacity()-Integer.BYTES;i>=DBParams.pageSize-Integer.BYTES*2-(2*Integer.BYTES*bb2.getInt(4088));i-=Character.BYTES) {
			sb2.append(bb2.getInt(i)+",");
		}
		System.out.println(sb1B.toString());
		System.out.println(sb2.toString());

		BufferManager.getInstance().FreePage(freePage2, true);

		
		System.out.println(bb2.toString());
	}*/
	
	public static ArrayList<Record> getRecordsInDataPageTest(RelationInfo rel, PageId freePage) {
		System.out.println("***** Affichage de tous les records du DataPage *****");
		
		ArrayList<Record> records = new ArrayList<Record>();
		records = FileManager.getInstance().getRecordsInDataPage(rel, freePage);
		
		int count = 1;
		System.out.println("Size de records = "+records.size());
		for(Record rec : records) {
			System.out.println("Record n°"+count+" : "+rec.toString());
			count++;
		}
		
		System.out.println();
		return records;
	}
	
	public static void getAllDataPagesTest(RelationInfo rel) {

		System.out.println("***** Affichage de tous les DataPages *****");
		ArrayList<PageId> pids = new ArrayList<PageId>();
		pids = FileManager.getInstance().getAllDataPages(rel);
		
		for(PageId pid : pids) {
			System.out.println(pid.toString());
			
		}
		
		System.out.println();

	}
	
	public static void insertRecordInRelTest(Record record, PageId freePage) {
		System.out.println("***** Insertion d'un Record dans la relation *****");
		RecordId recId = FileManager.getInstance().InsertRecordInRel(record);
		System.out.println(recId.toString());
		System.out.println();
		
		/**
		 *  Affichage
		 */
		System.out.println();
		System.out.println("// Affichage du contenu du DataPage //");
		//Affichage du contenu du DataPage
		ByteBuffer bb = BufferManager.getInstance().GetPage(freePage);
		
		StringBuffer sbA = new StringBuffer();
		for(int i = 0;i<bb.limit();i+=Character.BYTES) {
			sbA.append(bb.getChar(i));
		}
		
		System.out.println(sbA.toString());

		
		
		//Affichage de la position du début de l'espace disponible
		//System.out.println(bb.getInt(4092));
		System.out.println();

		System.out.println("// Affichage du contenu de la fin du DataPage //");
		//Affichage fin du DataPage
		StringBuffer sb2 = new StringBuffer();
		for(int i = bb.capacity()-Integer.BYTES;i>=DBParams.pageSize-Integer.BYTES*2-(2*Integer.BYTES*bb.getInt(4088));i-=Integer.BYTES) {
			sb2.append(bb.getInt(i)+",");
		}
		System.out.println(sb2.toString());

		System.out.println();

		BufferManager.getInstance().FreePage(freePage, true);
		

	}
	
	public static void getAllRecordsTest(RelationInfo rel, ArrayList<Record> records) {
		System.out.println("***** Affichage de tous les Records existants dans la db *****");

		//ArrayList<Record> recs = new ArrayList<Record>();
		records = FileManager.getInstance().getAllRecords(rel);
		for(Record rec : records) {
			System.out.println(rec.toString());
		}
	}
	public static void main(String[]args) {
		
		//createNewHeaderPage
		PageId headerPage = createNewHeaderPageTest();
		RelationInfo rel = creationRelationInfoContexte(headerPage);
		
		//addDataPage
		addDataPageTest(rel);
		addDataPageTest(rel);

		
		//getFreeDataPageId
		PageId freePage = getFreeDataPageIdTest(rel);

		//writeRecordToDataPage
		//writeRecordToDataPageTest(creationRecordContexte(rel),freePage);
		
		
		PageId freePage2 = getFreeDataPageIdTest(rel);

		//writeRecordToDataPageTest(creationRecordContexte2(rel),freePage);

		

		insertRecordInRelTest(creationRecordContexte(rel), freePage);
		insertRecordInRelTest(creationRecordContexte2(rel), freePage);
		insertRecordInRelTest(creationRecordContexte3(rel), freePage);
		insertRecordInRelTest(creationRecordContexte4(rel), freePage);

		

		//getRecordsInDataPage
		ArrayList<Record> records = getRecordsInDataPageTest(rel, freePage);
	
		
		
		
		//getAllDataPages
		getAllDataPagesTest(rel);

		//InsertRecordInRel
		
		//getAllRecords
		getAllRecordsTest(rel,records);

	}
}
