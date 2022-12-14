import java.io.File;

public class DropDBCommand {

    public void execute(){
		supprimerFichiers();
        Catalog.INSTANCE.reinitialiser();
        BufferManager.INSTANCE.reinitialiser();
        DiskManager.INSTANCE.reinitialiser();
		
    }

    public void supprimerFichiers(){
    	
    	int nb = 0;
		
		File file = new File(DBParams.DBPath+File.separator+"F"+nb+".bdda");
		//System.out.println(file.exists());
		
		
		while(file.exists()) {
			//System.out.println(file.exists());

			file.delete();

			file = new File(DBParams.DBPath+File.separator+"F"+(++nb)+".bdda");
			//System.out.println(file.exists());

		}

    }


}
