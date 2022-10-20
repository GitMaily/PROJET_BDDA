
public class RecordId {
	
	private PageId pageId; // la page à laquelle appartient le record
	private int slotIdx; // l'indice de la case du slot directory qui pointe vers le record
	public PageId getPageId() {
		return pageId;
	}
	public void setPageId(PageId pageId) {
		this.pageId = pageId;
	}
	public int getSlotIdx() {
		return slotIdx;
	}
	public void setSlotIdx(int slotIdx) {
		this.slotIdx = slotIdx;
	}

}
