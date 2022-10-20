import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Record {

	private RelationInfo relInfo; // Relation à laquelle appartient le record
	private ArrayList<String> values;
	
	public Record(RelationInfo relInfo) {
		this.relInfo=relInfo;
		
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
	
	public void writeToBuffer(ByteBuffer buff, int pos) {
		
		String type;
		
		buff.position(pos);
		for(int i =0; i< values.size();i++) {
			type = relInfo.getType_col()[i];
			
			switch(type) {
			case "INTEGER" : int vInt = Integer.valueOf(values.get(i));
			buff.putInt(vInt);
			break;
			
			case "REAL" : float vFloat = Float.valueOf(values.get(i));
			buff.putFloat(vFloat);
			break;
			
			default : int vString = Integer.valueOf(values.get(i).substring(8).replace(")", ""));
			buff.putInt(vString);
			}
		}
	}
	

	void readFromBuffer(ByteBuffer buff, int pos) {
		buff.position(pos);
		String rel;
		
		for(int i=0; i<relInfo.getNb_col();i++) {
			rel= relInfo.getType_col()[i];
			switch (rel) {
			case "INTEGER" : values.add(i,Integer.toString(buff.position()));
						buff.position(buff.position()+Integer.BYTES);		
			break;
			
			case "REAL" :  values.add(i,Float.toString(buff.getFloat(buff.position())));
						buff.position(buff.position()+Float.BYTES);
			break;
			
			default : int tmp = Integer.parseInt(rel.substring(8).replace(")", ""));
				StringBuffer sb = new StringBuffer();
				for(int j = 0; j<tmp; j++) {
					sb.append(buff.getChar(j));
				}
				values.add(sb.toString());
			}
		}
	}

	
	
}



