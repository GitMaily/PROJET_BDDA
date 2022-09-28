import java.io.File;
import java.io.IOException;

public class GestionED {

	public static int FileIdx = 0;

	void crea (int num) {
		File file = new File("Bureau/PROJET_BDDA/PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI/DB/F"+FileIdx+".bdda");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
		}

		FileIdx++;



	}


	//sert noramalement a ecrire dans un fichier bianire ici

	    /*DataOutputStream out;
        int nombre;

        try {
            out = new DataOutputStream(
                    new BufferedOutputStream(
                        new FileOutputStream("nombres.dat")));

            for (int i = 0; i < 1000; i++) {
                nombre = (int)(Math.random() * 10 + 1);
                out.writeInt( nombre );
            }

            out.close();
        } catch (IOException ioe) {
            System.err.println(ioe);
            System.exit(1);
        }*/

}
