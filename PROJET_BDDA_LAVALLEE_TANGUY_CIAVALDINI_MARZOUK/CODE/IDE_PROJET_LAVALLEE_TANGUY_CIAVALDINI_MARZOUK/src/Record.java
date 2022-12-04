import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Record {

	public RecordId ri;

	private RelationInfo relInfo; // Relation à laquelle appartient le record
	private ArrayList<String> values;
	
	
	public Record(RelationInfo relInfo) {
		this.relInfo=relInfo;
		this.values= new ArrayList<String>();
		
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
			sb.append("\t\t\t\t");
		}
		
		return sb.toString();
	}
	
	public String toString() {
		return afficherValues();
	}

	
	public void writeToBuffer(ByteBuffer buff, int pos) {
		
		String type;
		
		buff.position(pos);
		for(int i =0; i< values.size();i++) {
			type = relInfo.getNom_col().get(i).getType_col();
			
			switch(type) {
			case "INTEGER" : int vInt = Integer.valueOf(values.get(i));
			buff.putInt(vInt);
			break;
			
			case "REAL" : float vFloat = Float.valueOf(values.get(i));
			buff.putFloat(vFloat);
			break;
			
			default : /*int vString = Integer.valueOf(values.get(i));
			buff.putInt(vString);*/
				
				String vString = String.valueOf(values.get(i));
				for(int j = 0;j<vString.length();j++) {
					buff.putChar(vString.charAt(j));
					
				}
				//buff.putChar(vString.charAt(i));
			}
		}
	}
	

	void readFromBuffer(ByteBuffer buff, int pos) {
		buff.position(pos);
		String rel;
		
		for(int i=0; i<relInfo.getNb_col();i++) {
			rel= relInfo.getNom_col().get(i).getType_col();
			switch (rel) {
			case "INTEGER" : values.add(i,Integer.toString(buff.position()));
						buff.position(buff.position()+Integer.BYTES);		
			break;
			
			case "REAL" :  values.add(i,Float.toString(buff.getFloat(buff.position())));
						buff.position(buff.position()+Float.BYTES);
			break;
			 
			//marche pas
			default : Character tmp = Character.valueOf(buff.getChar(buff.position()));
				StringBuffer sb = new StringBuffer();
				sb.append(tmp);
				for(int j = 0; j<buff.position(); j+=Character.BYTES) {
					sb.append(buff.getChar(j));
				}
				values.add(sb.toString());
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


