
public class DropDBCommand {

    public static void execute(){
        supprimerFichiers();
        Catalog.INSTANCE.reinitialiser();
        BufferManager.INSTANCE.reinitialiser();
        DiskManager.INSTANCE.reinitialiser();
    }

    public static void supprimerFichiers(){
        
    }


}
