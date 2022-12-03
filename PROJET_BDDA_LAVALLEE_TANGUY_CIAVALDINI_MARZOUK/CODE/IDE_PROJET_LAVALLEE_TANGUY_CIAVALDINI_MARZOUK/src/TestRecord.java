import java.nio.ByteBuffer;
import java.util.ArrayList;

public class TestRecord {

	public static void main(String[] args) {
		//création de la relation
		ColInfo colonne1 = new ColInfo("Age", "INTEGER");
		ColInfo colonne3 = new ColInfo("Moyenne", "REAL");
		ColInfo colonne2 = new ColInfo("Prenom", "VARCHAR(10)");
		
		PageId id = new PageId(0,1);
		
		ArrayList<ColInfo> ci = new ArrayList<ColInfo>();
		ci.add(colonne1);
		ci.add(colonne2);
		ci.add(colonne3);
		RelationInfo ri = new RelationInfo("Etudiant", 3, ci, id);
		
		//création du record
		Record r = new Record(ri);
		
		r.getValues().add("Samuel");
		r.getValues().add("22");
		r.getValues().add("12.5");
		System.out.println(r.getWrittenSize());
		
		ByteBuffer buff = ByteBuffer.allocate(ri.getNb_col()*4 + r.getWrittenSize());
		
		r.writeToBuffer(buff, ri.getNb_col()*4);
		r.readFromBuffer(buff, ri.getNb_col()*4);
		
		
	}
	
}