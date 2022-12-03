
public class InsertCommandTest {
	public static void main(String []args) {
		InsertCommande ic = new InsertCommande("INSERT INTO Profs VALUES (Ileana,BDDA)");
		
		InsertCommande.execute();
	}

}
