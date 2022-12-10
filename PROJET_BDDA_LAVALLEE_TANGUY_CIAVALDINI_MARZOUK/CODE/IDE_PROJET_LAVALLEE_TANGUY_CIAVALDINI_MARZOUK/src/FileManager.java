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
		
		/** 
		 * Création du DataPage 
		 */
		
		ByteBuffer bb = BufferManager.getInstance().GetPage(pi);
		
		// Ecrire 2 entiers égal à 0 à la fin de la page
		bb.putInt(DBParams.pageSize -Integer.BYTES*2, 0);
		bb.putInt(DBParams.pageSize-Integer.BYTES, 0); 

		int m = bb.getInt(DBParams.pageSize - Integer.BYTES*2); // M = nb d'entrées slot dir
		int posDispo = bb.getInt(DBParams.pageSize-Integer.BYTES); // = 0 ici

		BufferManager.getInstance().FreePage(pi, true);

	    /**
	     * Actualisation du HeaderPage 
	     */
		
		
		ByteBuffer bbHeader = BufferManager.getInstance().GetPage(relInfo.getHeaderPageId());
		
		// On incrémente l'entier N correspondant au nombre de DataPage
		int n = bbHeader.getInt(0);
		bbHeader.putInt(0,++n);
		System.out.println("n = "+n);
		// On place l'Id du DataPage ajouté (donc 2*4 octets + 4 octets)
		int posIdDataPageIdxFile = (3*Integer.BYTES*(n-1))+Integer.BYTES; //int posIdDataPageIdxFile = Integer.BYTES*n;
		int posIdDataPageIdxPage = posIdDataPageIdxFile + Integer.BYTES; //int posIdDataPageIdxPage = Integer.BYTES*n + Integer.BYTES;
		bbHeader.putInt(posIdDataPageIdxFile, pi.getFileIdx());
		bbHeader.putInt(posIdDataPageIdxPage, pi.getPageIdx());

		
		
		// On place le nombre d'octets libres pour la dataPage ajoutée
		int posNbOctetsLibres = posIdDataPageIdxPage + Integer.BYTES*1; //int posNbOctetsLibres = Integer.BYTES*n + Integer.BYTES*2;
		int tailleSlotDir = Integer.BYTES*2 + (Integer.BYTES*2)*m;
		
		//System.out.println("posNbOctetsLibres = "+posNbOctetsLibres);
		bbHeader.putInt(posNbOctetsLibres, DBParams.pageSize- posDispo - tailleSlotDir);
		//System.out.println("Nb octets libre DP = "+ bb.getInt(posNbOctetsLibres));
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
		//ByteBuffer bbHeader = BufferManager.getInstance().GetPage(freePage);
		ByteBuffer bbHeader = BufferManager.getInstance().GetPage(relInfo.getHeaderPageId());

		//bbHeader.putInt(0,2);
		//bbHeader.putInt(12,20);
		
		 /*System.out.println(bbHeader.getInt(0));
		 System.out.println(bbHeader.getInt(4));
		 System.out.println(bbHeader.getInt(8));
		 System.out.println(bbHeader.getInt(12));
		 */
		
		// Test cas plus de place
		//bbHeader.putInt(12,sizeRecord-1);
		
		// On cherche pour chaque info des DataPage dans le HeaderPage, laquelle des DataPage a de l'espace disponible pour insérer le Record de taille sizeRecord
		for(int i = 0;i<=bbHeader.getInt(0);i++) {
			// modulo 12e byte
			//System.out.println("Itération = "+i*Integer.BYTES*3);
			//System.out.println("getInt : "+bbHeader.getInt(i*Integer.BYTES*3));
			if(bbHeader.getInt(i*Integer.BYTES*3) >= sizeRecord) { // On a trouvé de l'espace disponible dans la page de RelationInfo correspondante
				
				//System.out.println("Espace libre");
				
				freePage.setFileIdx(bbHeader.getInt((i*Integer.BYTES*3) - Integer.BYTES*2 ));
				freePage.setPageIdx(bbHeader.getInt((i*Integer.BYTES*3) - Integer.BYTES*1 ));
				BufferManager.getInstance().FreePage(relInfo.getHeaderPageId(), false);

				return freePage;
			}
		}
		BufferManager.getInstance().FreePage(relInfo.getHeaderPageId(), false);

		//System.out.println("Tout est max");
		// Toutes les DataPages sont max, on crée un nouveau DataPage
		return addDataPage(relInfo);
		
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
		
		
		/**
		 * Actualisation de la DataPage 
		 */
		
		int m = bb.getInt(DBParams.pageSize - Integer.BYTES*2); // M
		System.out.println("m = "+m);
		System.out.println(DBParams.pageSize - Integer.BYTES*2);
	    //int positionSlot = (DBParams.pageSize-Integer.BYTES*2) - (m+1)*Integer.BYTES*1; // position du début du Record
	    int positionSlot = (DBParams.pageSize - Integer.BYTES*2)- (m+1)*Integer.BYTES*2;
	    // On actualise la position du début du Record
	    bb.putInt(positionSlot,posDispo);
	    // On actualise la taille du record, située juste un Integer après
	    bb.putInt(positionSlot+Integer.BYTES, record.getWrittenSize());
	    
	    // Incrémente d'un slot dans M = nb d'entrées slot dir
	    bb.putInt(DBParams.pageSize-Integer.BYTES*2,++m);
		System.out.println("m = "+m);

		
		System.out.println(bb.getInt(DBParams.pageSize-Integer.BYTES*2));
		// On actualise la nouvelle position d'espace disponible du DataPage
	    int newPosDispo = posDispo+record.getWrittenSize();
	    bb.putInt(DBParams.pageSize-Integer.BYTES,newPosDispo);
	    
	    
		BufferManager.getInstance().FreePage(pageId, true);
		
	    /**
	     *  Actualisation du HeaderPage 
	     */
		
		ByteBuffer bbHeader = BufferManager.getInstance().GetPage(record.getRelInfo().getHeaderPageId());

		System.out.println("record headerPageId ========== "+record.getRelInfo().getHeaderPageId().toString());
		// Recherche du Id DataPage, mise à jour du nombre d'octets libres
		int libres = 0;
		//System.out.println(bbHeader.getInt(0));
		for(int i = 1;i<=bbHeader.getInt(0);i++) {
			/*System.out.println("pos courant fileIdx : "+((i*Integer.BYTES*3) - Integer.BYTES*2));
			System.out.println("pos courant pageIdx : "+((i*Integer.BYTES*3) - Integer.BYTES));
			*/
			if(bbHeader.getInt((i*Integer.BYTES*3) - Integer.BYTES*2) == pageId.getFileIdx() && bbHeader.getInt((i*Integer.BYTES*3) - Integer.BYTES) == pageId.getPageIdx()) {
				//libres = bbHeader.getInt(i*Integer.BYTES*3);
				//bbHeader.position();
				libres = i*Integer.BYTES*3;
				i = bbHeader.getInt(0)+1;
			}
		}
		
		 
		
		int tailleSlotDir = Integer.BYTES*2 + (Integer.BYTES*2)*m;
		
		bbHeader.putInt(libres, DBParams.pageSize- newPosDispo - tailleSlotDir);
		
		System.out.println("Calcul new pos libre: "+(DBParams.pageSize- newPosDispo - tailleSlotDir));
		 
		BufferManager.getInstance().FreePage(record.getRelInfo().getHeaderPageId(), true);
		RecordId rid = new RecordId(pageId, positionSlot- Integer.BYTES);
		
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
		int positionSlot = (DBParams.pageSize-Integer.BYTES*2) - m*Integer.BYTES*2; // pos Record
		//System.out.println("positionSlot = "+positionSlot);

		
		Record rec;
		// 1 pour le premier record
		for(int i = 1;i <= m; i++) {
			rec = new Record(relInfo);
			//System.out.println("pos début rec1 = "+bbDataPage.getInt(positionSlot));
			//System.out.println("pos début rec2 = "+bbDataPage.getInt(positionSlot-8));

			rec.readFromBuffer(bbDataPage, bbDataPage.getInt(positionSlot));
			positionSlot = (DBParams.pageSize-Integer.BYTES*2) - i*Integer.BYTES*2;
			listeDeRecords.add(rec);
			//System.out.println(rec.toString());
			//System.out.println("positionSlot = "+positionSlot);


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
		
		PageId pi;
		for(int i = 1, j = Integer.BYTES;i<=bbHeaderPage.getInt(0);i++, j+=Integer.BYTES*3) {
			pi = new PageId(bbHeaderPage.getInt(j),bbHeaderPage.getInt(j+Integer.BYTES));
			listeDePageIds.add(pi);
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
		ArrayList<Record> listeAllRecords = new ArrayList<Record>();
		
		
		for(PageId pid : listeDePageIds) {
			listeDeRecords = getRecordsInDataPage(relInfo,pid);

			for(Record rec : listeDeRecords) {
				listeAllRecords.add(rec);
			}
		}		
		
		return listeAllRecords;
	}
}
