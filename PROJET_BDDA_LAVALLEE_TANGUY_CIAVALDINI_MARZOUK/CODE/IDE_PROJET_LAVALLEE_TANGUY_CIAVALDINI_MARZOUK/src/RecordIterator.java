import java.nio.ByteBuffer;

/**
 * Classe permettant d'utiliser un Iterator pour les jointures
 * Ne sera pas utilisée, je l'ai seulement vaguement implémentée pour voir 
 * @author Maily
 *
 */
public class RecordIterator {
	private RelationInfo relInfo;
	private PageId pageId;
	private static int posCourante = -1;
	private ByteBuffer bbIterator;
	
	public RecordIterator(RelationInfo relInfo, PageId pageId) {
		ByteBuffer bbIterator = BufferManager.getInstance().GetPage(pageId);
		
		bbIterator.rewind();
		
		
	}
	
	public Record getNextRecord() {
		int m = bbIterator.getInt(DBParams.pageSize - Integer.BYTES * 2);
		if(posCourante <= m) {
			Record rec = new Record(relInfo);
			rec.readFromBuffer(bbIterator, posCourante);
			posCourante++;
			return rec;
		}
		else {
			return null;
		}
	}
	
	public void close() {
		BufferManager.getInstance().FreePage(pageId, true);
		//posCourante = -1;
	}
	
	public void reset() {
		posCourante = -1;
	}
	

}
