import java.util.ArrayList;

public class DeleteCommand {
	private String saisie;

	private String nomRel;
	private SelectCommand sc;
	private String[] cmd;
	private ArrayList<Record> recordR;


	public DeleteCommand (String saisie) {
		this.saisie = saisie;
		recordR=new ArrayList<>();
		
		this.cmd = saisie.split("WHERE");
        String[] tamp = cmd[0].split(" ");
        this.nomRel=tamp[3];	    
        
        String commande = "SELECT * FROM "+nomRel+" WHERE"+cmd[1];
	    this.sc = new SelectCommand(commande);
	    sc.execute();
	    
	    this.recordR = sc.getRecordR();
	    
	
	}
	
	public void execute() {
		int count = 0;
		for(int i=0; i<recordR.size(); i++) {
        	if( i == recordR.size()-1) {
        		
            	System.out.println(recordR.get(i).toString()+ ".");
            	if(deleteRecord(recordR.get(i))) {
        			count++;
        		}

        	}
        	else {
            	System.out.println(recordR.get(i).toString()+ " ; ");
            	
            	
        		if(deleteRecord(recordR.get(i))) {
        			count++;
        		}


        	}
        	
        }
        System.out.println("Total deleted records="+ count);
        
        
	}
	
	private boolean deleteRecord(Record record) {
		ArrayList<Record> records = new ArrayList<Record>();
		
		records = FileManager.getInstance().getAllRecords(record.getRelInfo());
		
		/*for(Record rec : records) {
			if(rec.getRi() != null && rec.getRi().getSlotIdx() == record.getRi().getSlotIdx()) {
				
				System.out.println("Le slotIdx"+rec.getRi().getSlotIdx());
		        FileManager.getInstance().DeleteRecordInRel(rec); // r.ri = 
		        
		        return true;

			}
		}*/
		//System.out.println("to string"+record.toString());
		for(Record rec : records) {
			for(int i = 0;i<rec.getValues().size();i++) {
				if(rec.getValues().get(i).equals(record.getValues().get(i))) {
					
					
					
					
			        //FileManager.getInstance().DeleteRecordInRel(rec); // non fonctionnel

				}
				else {
					i = rec.getValues().size();
				}
			
			}
		}
		
		return false;
		

	}

	
	

}
