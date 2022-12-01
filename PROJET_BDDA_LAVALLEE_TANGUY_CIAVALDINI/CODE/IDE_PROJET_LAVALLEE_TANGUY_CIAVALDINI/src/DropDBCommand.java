import java.io.File;

public class DropDBCommand {

    public static void execute(){
        supprimerFichiers();
        Catalog.INSTANCE.reinitialiser();
        BufferManager.INSTANCE.reinitialiser();
        DiskManager.INSTANCE.reinitialiser();
    }

    public static void supprimerFichiers(){
    	
    	int nb = 0;
		
		File file = new File(DBParams.DBPath+"F"+nb+".bdda");
		System.out.println(file.exists());
		
		
		while(file.exists()) {
			System.out.println(file.exists());

			file.delete();

			file = new File(DBParams.DBPath+"F"+(++nb)+".bdda");
			System.out.println(file.exists());

		}

    }


}
