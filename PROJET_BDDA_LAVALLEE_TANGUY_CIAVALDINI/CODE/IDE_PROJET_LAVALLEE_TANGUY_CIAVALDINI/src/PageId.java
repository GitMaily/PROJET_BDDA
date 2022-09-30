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
	
	
	
	public int calcPageIDlibre(int fileID) throws IOException {
		RandomAccessFile fichiers;
		int paid = 0;
		try {
			fichiers = new RandomAccessFile("F"+fileID+".bdda","rw");
			int id = (int) ((16384-fichiers.length()) / 4096 );
			
			switch(id) {
				case 1:  paid= 3;
				break;
				case 2 : paid=2;
				break;
				case 3: paid =1;
				break;
				case 4: paid =0;
			}
		}
			
		 catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return paid;
	}
	
	@SuppressWarnings("resource")
	public boolean estalloc(int pageID, int fileID,@SuppressWarnings("rawtypes") HashMap dico)
	{
	
		
		if(dico.containsKey(fileID)== true) {
			System.out.println("les pages du fichier "+fileID+" sont déja allouer, passage au prochain fichier");
			
			estalloc( pageID, fileID,dico);
			
			
			
		}
		
		


		}
	}

}
