import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;

public class PageId {
	
	
	public  int FileIdx; // id du fichier
	public int PageIdx; // indice de la page 
	
	public PageId(int fileIdx, int pageIdx) {
		FileIdx = fileIdx;
		PageIdx = pageIdx;
	}
	
	
	
	public PageId() {
		// TODO Auto-generated constructor stub
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
	
	
	
	
}
