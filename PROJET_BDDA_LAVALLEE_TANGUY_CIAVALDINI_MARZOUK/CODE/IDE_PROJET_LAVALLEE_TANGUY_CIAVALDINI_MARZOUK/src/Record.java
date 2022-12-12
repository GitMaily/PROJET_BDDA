import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Record {

	private RecordId ri;

	public RelationInfo relInfo; // Relation à laquelle appartient le record
	private ArrayList<String> values;
	
	
	public Record(RelationInfo relInfo) {
		this.relInfo=relInfo;
		this.values= new ArrayList<String>();
		this.ri = null;
		
	}
	public Record(RelationInfo relInfo, ArrayList<String> values) {
		super();
		this.relInfo = relInfo;
		this.values = values;
	}
	
	
	public RelationInfo getRelInfo() {
		return relInfo;
	}
	public void setRelInfo(RelationInfo relInfo) {
		this.relInfo = relInfo;
	}
	public ArrayList<String> getValues() {
		return values;
	} 
	public void setValues(ArrayList<String> values) {
		this.values = values;
	}
	
	
	
	public String afficherValues() {
		StringBuilder sb = new StringBuilder();
		for(String val : values) {
			sb.append(val);
			sb.append("");
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
		buff.position(pos);
		for(int i =0; i< values.size();i++) {
			buff.position(pos+position); // on set la position dans le buffer au début pour ajouter le pointeur
			buff.putInt((1+relInfo.getNb_col() * 4) + tailleValeurAjouter); // On met dans le buffer l'adresse de la valeur pointé 
			position +=4;
			type = relInfo.getNom_col().get(i).getType_col();
			
			switch(type) {
			case "INTEGER" : int vInt = Integer.valueOf(values.get(i));
			buff.putInt(pos+ totalVal + tailleValeurAjouter,vInt);// a l'adresse de la position de la valeur, on ajoute la valeur
			tailleValeurAjouter+=4;
			break;
			
			case "REAL" : float vFloat = Float.valueOf(values.get(i));
			buff.putFloat(pos+ totalVal + tailleValeurAjouter,vFloat); // a l'adresse de la position de la valeur, on ajoute la valeur
			tailleValeurAjouter+=4;
			break;
			
			default :
			String vString = String.valueOf(values.get(i));
			for(int j = 0, vCharOctet = 0;j<vString.length();j++, vCharOctet+=2) { //
				//System.out.println("adresse :"+(pos+totalVal+tailleValeurAjouter+vCharOctet));
				buff.putChar(pos+totalVal+tailleValeurAjouter+vCharOctet,vString.charAt(j)); // a l'adresse de la position de la valeur, on ajoute chaque char avec une boucle
				
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
		String type;
		int emplacementChaine;
		ArrayList<Character> tableauDeChar;
		char[] tableauDeCharVrai;
		int position = 0;
		int totalVal = (1+relInfo.getNb_col()) * 4; //nombre total de case + 1 * 4 octet
		
		for(int i=0; i<relInfo.getNb_col();i++) {
			type= relInfo.getNom_col().get(i).getType_col();
			position = pos + totalVal; // on démarre a la premiere adresse des valeurs
			switch (type) {
			case "INTEGER" : values.add(String.valueOf(buff.getInt(position)));	
			totalVal += 4;
			break;
			
			case "REAL" :  values.add(String.valueOf(buff.getFloat(position)));
			totalVal +=4;
			break;
			 
			default : 
				
				//System.out.println(buff.getInt(pos + (i*4) + 4));
				//System.out.println(buff.getInt(pos + (i*4)));

				emplacementChaine = buff.getInt(pos + (i*4) + 4) - buff.getInt(pos + (i*4)); //grâce au pointeur, on récupère l'adresse des emplacement de la chaine  
				
				tableauDeChar = new ArrayList<Character>();
				for(int j = pos + totalVal; j < pos + totalVal + emplacementChaine; j+= 2 /*+2 car chaque lettre vaut 2 octets*/) {
					
					tableauDeChar.add(buff.getChar(j)); // chaque char on le met dans la liste
				}
				tableauDeCharVrai = new char[tableauDeChar.size()]; //grace a la liste on a la taille de la chaine
				for(int j = 0, x = 0; j<tableauDeCharVrai.length; j++, x++) {
					tableauDeCharVrai[x] = tableauDeChar.get(j); // on met chaque char dans le tableau de char 
				}
				values.add(new String(tableauDeCharVrai)); // et le tableau de char on le convertie en string qu'on rajoute dans values
				totalVal+=emplacementChaine;
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
				res += 8; // t'aille d'un int + taille adresse
				break;
			case("REAL"):
				res+= 8; // Taille d'un float + taille adresse 
				break;
			default:
				res += (values.get(i).length())*2; // Taille du string
				res *= 2; // + taille adresse
			} 
		}
        return res;
    }
	public RecordId getRi() {
		return ri;
	}
	public void setRi(RecordId ri) {
		this.ri = ri;
	}
}


