import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Record {

	public RecordId ri;

	public static RelationInfo relInfo; // Relation à laquelle appartient le record
	static ArrayList<String> values;
	
	
	public Record(RelationInfo relInfo) {
		Record.relInfo=relInfo;
		Record.values= new ArrayList<String>();
		
	}
	public Record(RelationInfo relInfo, ArrayList<String> values) {
		super();
		Record.relInfo = relInfo;
		Record.values = values;
	}
	
	
	public static RelationInfo getRelInfo() {
		return relInfo;
	}
	public void setRelInfo(RelationInfo relInfo) {
		Record.relInfo = relInfo;
	}
	public ArrayList<String> getValues() {
		return values;
	} 
	public void setValues(ArrayList<String> values) {
		Record.values = values;
	}
	
	
	
	public String afficherValues() {
		StringBuilder sb = new StringBuilder();
		for(String val : values) {
			sb.append(val);
			sb.append("\t\t\t\t");
		}
		
		return sb.toString();
	}
	
	public String toString() {
		return afficherValues();
	}

	
	
	
	public void writeToBuffer(ByteBuffer buff, int pos) {
		
		String type;
		/**
		 * cette variable sera mis à jour avec un +4 octet a chaque fois nouvelle itération de la boucle
		 * elle correspond a la position des pointeur dans le buffer au début
		 */
		int position = 0; // taille en octet des pointeur et donc leur position à chacun
		int totalVal = (1+relInfo.getNb_col()) * 4; //nombre total de case + 1 * 4 octet
		/**
		 * cette variable sera mis a jour avec le nombre d'octet de chaque valeur ajouté au buffer
		 */
		int tailleValeurAjouter = 0; // taille en octet des valeur ajouté au buffer
		int adresseValeurPointer = pos + totalVal + tailleValeurAjouter;
		buff.position(pos);
		for(int i =0; i< values.size();i++) {
			buff.position(pos+position); // on set la position dans le buffer au début pour ajouter le pointeur
			buff.putInt((1+relInfo.getNb_col() * 4) + tailleValeurAjouter); // On met dans le buffer l'adresse de la valeur pointé 
			position +=4;
			type = relInfo.getNom_col().get(i).getType_col();
			
			switch(type) {
			case "INTEGER" : int vInt = Integer.valueOf(values.get(i));
			buff.putInt(pos+adresseValeurPointer,vInt);// a l'adresse de la position de la valeur, on ajoute la valeur
			tailleValeurAjouter+=4;
			break;
			
			case "REAL" : float vFloat = Float.valueOf(values.get(i));
			buff.putFloat(pos+adresseValeurPointer,vFloat); // a l'adresse de la position de la valeur, on ajoute la valeur
			tailleValeurAjouter+=4;
			break;
			
			default :
			String vString = String.valueOf(values.get(i));
			for(int j = 0, vCharOctet = 0;j<vString.length();j++, vCharOctet+=2) { //
				buff.putChar(pos+adresseValeurPointer+vCharOctet,vString.charAt(j)); // a l'adresse de la position de la valeur, on ajoute chaque char avec une boucle
				
			}
			tailleValeurAjouter += vString.length()*2;
			}
			if (i == (values.size()-1)) {
				buff.putInt(pos+position, (1+relInfo.getNb_col()*4) + tailleValeurAjouter);
			}
		}
	}
	
	
	
	void readFromBuffer(ByteBuffer buff, int pos) {
		buff.position(pos);
		String chaine;
		String type;
		int Emplacement;
		int position = 0;
		int totalVal = (1+relInfo.getNb_col()) * 4; //nombre total de case + 1 * 4 octet
		
		for(int i=0; i<relInfo.getNb_col();i++) {
			type= relInfo.getNom_col().get(i).getType_col();
			position = pos + totalVal; // on démarre a la premiere adresse des valeurs
			switch (type) {
			case "INTEGER" : values.add(String.valueOf(buff.getInt(pos+position)));	
			position += 4;
			break;
			
			case "REAL" :  values.add(String.valueOf(buff.getFloat(pos+position)));
			position +=4;
			break;
			 
			default :;
				
				
			}
		}
	}
	
	public int getWrittenSize(){
		int res = 0;
		//ArrayList<ColInfo> CI = relInfo.getNom_col();
		
		ArrayList<ColInfo> CI = new ArrayList<ColInfo>();
		CI = relInfo.getNom_col();
		for(int i = 0; i < CI.size(); i++) { // -1?
			switch(CI.get(i).getType_col()) {
			case("INTEGER"):
				res += 4; // t'aille d'un int
				break;
			case("REAL"):
				res+= 4; // Taille d'un float
				break;
			default:
				res += (values.get(i).length())*2; // Taille du string
			} 
		}
        return res;
    }
}


