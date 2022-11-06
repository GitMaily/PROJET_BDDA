import java.nio.ByteBuffer;
import java.util.ArrayList;

public class FileManager {

private static FileManager INSTANCE = new FileManager();
	
	public static FileManager getInstance() {
		if (INSTANCE == null) {
            INSTANCE = new FileManager();
        }
		return INSTANCE;	
	}
	
	/**
	 * Allocation d'une nouvelle page (AllocPage du DiskManager)
	 * Ecriture dans la page allouée de 4 octets 0 au début
	 * 
	 * @return La PageId de la page allouée (par AllocPage)
	 */
	public PageId createNewHeaderPage() {
		/*PageId pi =  DiskManager.getInstance().allocPage();
		
		byte[] buffer = BufferManager.getInstance().GetPage(pi).array();

		// Ajout du headerPage
		ArrayList<int[]> headerPage = new ArrayList<int[]>();
		
		int [] nbDataPages = {DiskManager.getInstance().GetCurrentCountAllocPages()}; // entier N correspondant au nombre de dataPage
		int [] dataPages = new int[3]; // PageIdx + FileIdx + nbre d'octets dispo sur la dataPage
		dataPages[0] = pi.PageIdx;
		dataPages[1] = pi.FileIdx;
		dataPages[2] = buffer.length;
		
		headerPage.add(nbDataPages);
		headerPage.add(dataPages);
		
		
		/*byte [] bytes = new byte[DiskManager.getInstance().GetCurrentCountAllocPages()]; //DBParams.maxPagesPerFile ?
		for(int i =0;i<4;i++) {
			bytes[i] = 0;
		}///
		
		
		BufferManager.getInstance().FreePage(pi, true);
		return pi;*/
		
		
		PageId headerPage =  DiskManager.getInstance().allocPage();
		ByteBuffer bbHeader = BufferManager.getInstance().GetPage(headerPage);
		
		// Ecriture dans la page allouée de 4 octets 0 au tout début
		bbHeader.clear();
		bbHeader.putInt(0);
		
		BufferManager.getInstance().FreePage(headerPage, true);
		
		return headerPage;
	}
	
	
	
	
	/**
	 * Rajoute une DataPage vide au HeapFile correspondant à la relation identifiée par relInfo.
	 * @param relInfo La relation de la page à ajouter
	 * @return La pageId de cette page
	 */
	public PageId addDataPage(RelationInfo relInfo) {
		PageId pi = DiskManager.getInstance().allocPage();
		
		// Méthode avec un tableau de byte
		
		/*byte[] buffer = BufferManager.getInstance().GetPage(pi).array();
		// On écrit un entier 0 au 4088e et 4092e (donc 4*2 octets utilisés)
		System.out.println(buffer.length);
		for(int i =buffer.length-8;i<buffer.length;i+=4) {
			buffer[i] = (int)0;
		}*/
		
		// Méthode avec ByteByffer (meilleure?)
		
		ByteBuffer bb = BufferManager.getInstance().GetPage(pi);
		bb.putInt(bb.capacity()-Integer.BYTES*2, 0);
		bb.putInt(bb.capacity()-Integer.BYTES, 0);

		
		// Actualisation des informations de la HeaderPage
		
		PageId headerPage = new PageId(relInfo.getHeaderPageId().getFileIdx(),relInfo.getHeaderPageId().getPageIdx());
		
		ByteBuffer bbHeader = BufferManager.getInstance().GetPage(headerPage);
		// On incrémente l'entier N correspondant au nombre de DataPage
		bbHeader.putInt(0,bbHeader.getInt(0)+1);
		// On met à jour 
		bbHeader.putInt(bbHeader.getInt(0)* Integer.BYTES,DiskManager.getInstance().GetCurrentCountAllocPages());
		
		BufferManager.getInstance().FreePage(pi, true);
		BufferManager.getInstance().FreePage(headerPage, true);

		
		return pi;
	}
	
	/**
	 * Trouve une DataPageId avec de l'espace disponible pour une insertion d'un Record
	 * 
	 * @param relInfo La relationInfo
	 * @param sizeRecord Entier correspondant à la taille du record à insérer
	 * @return Le pageId d'une DataPage sur laquelle il reste assez de place pour insérer le record.
	 * Retourne null si une telle page n'existe pas
	 */
	public PageId getfreeDataPageId(RelationInfo relInfo, int sizeRecord) {

		// On récupère la PageId correspondate à la RelationInfo
		PageId freePage = new PageId(relInfo.getHeaderPageId().getFileIdx(),relInfo.getHeaderPageId().getPageIdx());
		// On récupère le contenu du buffer de la page
		ByteBuffer bbHeader = BufferManager.getInstance().GetPage(freePage);

		// On cherche pour chaque info des DataPage dans le HeaderPage, laquelle des DataPage a de l'espace disponible pour insérer le Record de taille sizeRecord
		for(int i = 0;i<bbHeader.get(0);i++) {
			if(bbHeader.getInt(i*Integer.BYTES) >= sizeRecord) { // On a trouvé de l'espace disponible dans la page de RelationInfo correspondante
				return freePage;
			}
		}
		
		// Toutes les DataPages sont max
		return null;
		
	}
	
	/**
	 * Ecrit un Record dans une DataPage
	 * @param record Le record à écrire
	 * @param pageId La pageId où l'on veut écrire le Record
	 * @return Le RecordId de l'enregistrement dans la DataPage identifiée par pageId
	 */
	public RecordId writeRecordToDataPage(Record record, PageId pageId) {
		
		// On suppose que la page dispose d'assez d'espace disponible pour l'insertion (on ne vérifie pas)
		
		ByteBuffer bb = BufferManager.getInstance().GetPage(pageId);
		
		bb.get(0);
		record.writeToBuffer(bb, 0);
		
		return null;
		
	}
}
