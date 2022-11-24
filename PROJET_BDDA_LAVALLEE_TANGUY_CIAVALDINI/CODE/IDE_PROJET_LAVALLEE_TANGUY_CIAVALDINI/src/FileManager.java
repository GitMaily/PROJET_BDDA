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
		
		/* Création du DataPage */
		
		ByteBuffer bb = BufferManager.getInstance().GetPage(pi);
		
		// Ecrire 2 entiers égal à 0 à la fin de la page
		bb.putInt(DBParams.pageSize -Integer.BYTES*2, 0);
		bb.putInt(DBParams.pageSize-Integer.BYTES, 0); 

		int m = bb.getInt(DBParams.pageSize - Integer.BYTES*2); // M = nb d'entrées slot dir
		int posDispo = bb.getInt(DBParams.pageSize-Integer.BYTES); // = 0 ici

		BufferManager.getInstance().FreePage(pi, true);

	    /* Actualisation du HeaderPage */
		
		//PageId headerPage = new PageId(relInfo.getHeaderPageId().getFileIdx(),relInfo.getHeaderPageId().getPageIdx());
		
		ByteBuffer bbHeader = BufferManager.getInstance().GetPage(relInfo.getHeaderPageId());
		
		// On incrémente l'entier N correspondant au nombre de DataPage
		bbHeader.putInt(0,bb.getInt(0)+1);
		
		// On place l'Id du DataPage ajouté (donc 2*4 octets + 4 octets)
		int posIdDataPageIdxFile = Integer.BYTES*bbHeader.get(0);
		int posIdDataPageIdxPage = Integer.BYTES*bbHeader.get(0) + Integer.BYTES;
		bbHeader.putInt(posIdDataPageIdxFile, pi.getFileIdx());
		bbHeader.putInt(posIdDataPageIdxPage, pi.getPageIdx());

		// On place le nombre d'octets libres pour la dataPage ajoutée
		int posNbOctetsLibres = Integer.BYTES*bbHeader.get(0) + Integer.BYTES*2;
		int tailleSlotDir = Integer.BYTES*2 + (Integer.BYTES*2)*m;
		
		
		bb.putInt(posNbOctetsLibres, DBParams.pageSize- posDispo - tailleSlotDir);
		
		BufferManager.getInstance().FreePage(relInfo.getHeaderPageId(), true);

		
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
		for(int i = 1;i<bbHeader.get(0);i++) {
			// modulo 12e byte
			if(bbHeader.getInt(i*Integer.BYTES*3) >= sizeRecord) { // On a trouvé de l'espace disponible dans la page de RelationInfo correspondante
				freePage.setFileIdx(bbHeader.getInt((i*Integer.BYTES*3) - Integer.BYTES*2 ));
				freePage.setPageIdx(bbHeader.getInt((i*Integer.BYTES*3) - Integer.BYTES*1 ));
				return freePage;
			}
		}
		
		// Toutes les DataPages sont max
		return null;
		
	}
	
	/**
	 * Ecrit un Record dans une DataPage. Pas de vérification d'espace disponible.
	 * @param record Le record à écrire
	 * @param pageId La pageId où l'on veut écrire le Record
	 * @return Le RecordId de l'enregistrement dans la DataPage identifiée par pageId
	 */
	public RecordId writeRecordToDataPage(Record record, PageId pageId) {
		
		ByteBuffer bb = BufferManager.getInstance().GetPage(pageId);
		
		// On recherche la position libre du DataPage pour l'écriture du record : le dernier entier stocké dans le slot directory 
		int posDispo = bb.getInt(DBParams.pageSize-Integer.BYTES);
		// Ecriture du record à partir de la postion d'espace disponible
		record.writeToBuffer(bb, posDispo);
		
		
		/* Actualisation de la DataPage */
		
		int m = bb.getInt(DBParams.pageSize - Integer.BYTES*2); // M
		
	    int positionSlot = DBParams.pageSize-Integer.BYTES*2 - m*Integer.BYTES*2; // position du début du Record
	    
	    // On actualise la position du début du Record
	    bb.putInt(positionSlot,posDispo);
	    // On actualise la taille du record, située juste un Integer après
	    bb.putInt(positionSlot+Integer.BYTES, record.getWrittenSize());
	    
	    // Incrémente d'un slot dans M = nb d'entrées slot dir
	    bb.putInt(DBParams.pageSize-Integer.BYTES*2,++m);
	   
		// On actualise la nouvelle position d'espace disponible du DataPage
	    int newPosDispo = posDispo+record.getWrittenSize();
	    bb.putInt(DBParams.pageSize-Integer.BYTES,newPosDispo);
	    
	    
		BufferManager.getInstance().FreePage(pageId, true);
		
	    /* Actualisation du HeaderPage */
		
		ByteBuffer bbHeader = BufferManager.getInstance().GetPage(pageId);
		// Recherche du Id DataPage, mise à jour du nombre d'octets libres
		int libres = 0;
		for(int i = 1;i<bbHeader.getInt(0);i++) {
			if(bbHeader.getInt((i*Integer.BYTES*3) - Integer.BYTES*2) == pageId.getFileIdx() && bbHeader.get((i*Integer.BYTES*3) - Integer.BYTES) == pageId.getFileIdx()) {
				//libres = bbHeader.getInt(i*Integer.BYTES*3);
				//bbHeader.position();
				libres = i*Integer.BYTES*3;
				i = bbHeader.getInt(0);
			}
		}
		int tailleSlotDir = Integer.BYTES*2 + (Integer.BYTES*2)*m;
		
		bbHeader.putInt(libres, DBParams.pageSize- newPosDispo - tailleSlotDir);
		
		BufferManager.getInstance().FreePage(pageId, true);
		RecordId rid = new RecordId(pageId, positionSlot);
		
		return rid;
		
	}
	
	/**
	 * Renvoie la liste des records stockés dans la page identifiée par pageId
	 * @param relInfo Une relationInfo
	 * @param pageId Un pageId
	 * @return Un ArrayList contenant les records de la page
	 */
	public ArrayList<Record> getRecordsInDataPage(RelationInfo relInfo, PageId pageId) {
		ArrayList<Record> listeDeRecords = new ArrayList<Record>();
		
		ByteBuffer bbDataPage = BufferManager.getInstance().GetPage(pageId);
		
		int m = bbDataPage.getInt(DBParams.pageSize - Integer.BYTES * 2);
		for(int i = 0;i<m;i++) {
			Record rec = new Record(relInfo);
			rec.readFromBuffer(bbDataPage, i);
			listeDeRecords.add(rec);
			

		}
		
		BufferManager.getInstance().FreePage(pageId, true);
		
		return listeDeRecords;
	}
	
	
	/**
	 * Renvoie la liste des PageIds des dataPages, tels qu'ils figurent dans la HeaderPage.
	 * @param relInfo Une relationInfo
	 * @return un ArrayList contenant les PageId des DataPage
	 */
	public ArrayList<PageId> getAllDataPages(RelationInfo relInfo){
		ArrayList<PageId> listeDePageIds = new ArrayList<PageId>();
		
		ByteBuffer bbHeaderPage = BufferManager.getInstance().GetPage(relInfo.getHeaderPageId());
		int posIdDataPageIdxFile = Integer.BYTES;
		int posIdDataPageIdxPage = Integer.BYTES *2 ;
		
		for(int i = 1;i<bbHeaderPage.getInt(0);i++) {
			
			PageId pi = new PageId(bbHeaderPage.getInt(posIdDataPageIdxFile),bbHeaderPage.getInt(posIdDataPageIdxPage));
			listeDePageIds.add(pi);
			posIdDataPageIdxFile += Integer.BYTES*3;
			posIdDataPageIdxPage += Integer.BYTES*4;
		}
		
		BufferManager.getInstance().FreePage(relInfo.getHeaderPageId(), true);
		
		return listeDePageIds;

	}
	
	/**
	 * Insertion d'un record dans une relation.
	 * @param record un Record
	 * @return un RecordId
	 */
	public RecordId InsertRecordInRel (Record record) {
		return writeRecordToDataPage(record, getfreeDataPageId(record.getRelInfo(), record.getWrittenSize()));
	}
	
	/**
	 * Liste tous les records dans une relation.
	 * @param relInfo
	 * @return La liste des records de la relation
	 */
	public ArrayList<Record> getAllRecords(RelationInfo relInfo){
		ArrayList<PageId> listeDePageIds = new ArrayList<PageId>();
		listeDePageIds = getAllDataPages(relInfo);
		
		ArrayList<Record> listeDeRecords = new ArrayList<Record>();
		
		for(PageId pid : listeDePageIds) {
			listeDeRecords = getRecordsInDataPage(relInfo,pid);

			for(Record rec : listeDeRecords) {
				listeDeRecords.add(rec);
			}
		}		
		
		return listeDeRecords;
	}
}
