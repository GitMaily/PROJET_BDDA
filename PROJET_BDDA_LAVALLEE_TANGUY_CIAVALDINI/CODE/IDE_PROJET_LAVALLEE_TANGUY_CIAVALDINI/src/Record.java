
public class Record {

	private RelationInfo relInfo; // Relation à laquelle appartient le record
	private String[] values;
	
	public Record(RelationInfo relInfo) {
		this.relInfo=relInfo;
		
	}
	
	
	public RelationInfo getRelInfo() {
		return relInfo;
	}
	public void setRelInfo(RelationInfo relInfo) {
		this.relInfo = relInfo;
	}
	public String[] getValues() {
		return values;
	}
	public void setValues(String[] values) {
		this.values = values;
	}
	
	
	
	
}



