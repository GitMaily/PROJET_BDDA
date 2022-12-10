import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class TestRecord {

	public static void main(String[] args) throws IOException {
		//création de la relation
		//ColInfo colonne1 = new ColInfo("Age", "INTEGER");
		//ColInfo colonne3 = new ColInfo("Moyenne", "REAL");
		ColInfo colonne2 = new ColInfo("Prenom", "VARCHAR(10)");
		ColInfo colonne4 = new ColInfo("Nom", "VARCHAR(10)");
		
		PageId id = new PageId(0,0);
		
		ArrayList<ColInfo> ci = new ArrayList<ColInfo>();
		//ci.add(colonne1);
		ci.add(colonne2);
		//ci.add(colonne3);
		ci.add(colonne4);
		RelationInfo ri = new RelationInfo("Etudiant", ci, id);
		
		//création du record
		//Record r = new Record(ri);
		ArrayList<String> values = new ArrayList<String>();
		values.add("Samuel");
		values.add("Lavallee");
		Record r = new Record(ri, values);
		System.out.println(r.toString());
		//r.getValues().add("12.5");
		//r.getValues().add("Samuel");
		
		System.out.println("taille record : " + r.getWrittenSize()); //afficher la taille du record
		
		ByteBuffer buff = ByteBuffer.allocate(50);
		
		r.writeToBuffer(buff, 0); 
		
		StringBuilder sb = new StringBuilder();
		sb.append(buff.getInt(0));
		sb.append(buff.getInt(4));
		sb.append(buff.getInt(8));
        for(int i =0;i<buff.capacity();i+=Character.BYTES) {
        	
            sb.append(buff.getChar(i));
        }

        System.out.println(sb.toString());
		System.out.println("\nvaleur avant read : " + r.afficherValues());
        r.readFromBuffer(buff, 0);
        System.out.println("\nvaleur après read : " +r.afficherValues());
		
	}
	
}