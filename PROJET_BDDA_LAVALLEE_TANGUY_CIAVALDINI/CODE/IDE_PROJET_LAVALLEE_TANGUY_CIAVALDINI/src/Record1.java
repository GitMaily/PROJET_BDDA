import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Record1 {
		
	private RelationInfo relInfo; // Relation à laquelle appartient le record
	private ArrayList<String> values;
	
	public Record1(RelationInfo relInfo) {
		this.relInfo=relInfo;
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
}

