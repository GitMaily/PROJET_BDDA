import java.io.Serializable;

public class PageId implements Serializable{
	
	
	public  int FileIdx; // id du fichier
	public int PageIdx; // indice de la page 
	
	public PageId(int fileIdx, int pageIdx) {
		FileIdx = fileIdx;
		PageIdx = pageIdx;
	}
	
	
	
	public PageId() {
	}



	public int getFileIdx() {
		return FileIdx;
	}
	public void setFileIdx(int fileIdx) {
		FileIdx = fileIdx;
	}
	public int getPageIdx() {
		return PageIdx;
	}
	public void setPageIdx(int pageIdx) {
		PageIdx = pageIdx;
	}
	
	
	public String toString() {
		return "("+FileIdx+","+PageIdx+")";
	}
	
	
}
