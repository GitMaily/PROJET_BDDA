
public class CommandTests {
	public static void main(String []args) {
		dropDBTest();
		
		//createTableTest();
		//insertCommandTest();
		selectCommandTest();

	}
	
	private static void dropDBTest() {
		DropDBCommand.supprimerFichiers();
		
	}

	private static void createTableTest() {
		String commande = "CREATE TABLE Profs (X:INTEGER,C2:VARCHAR(5),BLA:VARCHAR(10))";
		String commande2 = "CREATE TABLE Profs (NOM:VARCHAR(10),UE:VARCHAR(5))";
		//String commande3 = "CREATE TABLE ToutesLesNotes (Cours:VARCHAR(10),Note:INTEGER)";
		String commande3 = "CREATE TABLE ToutesLesNotes (Cours:VARCHAR(10),Note:INTEGER";

		//CreateTableCommand tc = new CreateTableCommand("CREATE TABLE Profs (X:INTEGER,C2:REAL,BLA:VARCHAR(10))");
		//CreateTableCommand tc1 = new CreateTableCommand(commande2);
		CreateTableCommand tc3 = new CreateTableCommand(commande3);

		
		//tc.execute();
		//tc1.execute();
		tc3.execute();
		System.out.println(tc3.toString());

		//System.out.println(tc.toString());

		//System.out.println(tc1.toString());

	}

	private static void insertCommandTest() {
		createTableTest();
		
		/*InsertCommande ic1 = new InsertCommande("INSERT INTO Profs VALUES (Ileana,BDDA)");
		InsertCommande ic2 = new InsertCommande("INSERT INTO Profs VALUES (Jean-Guy-Mailly,PROG)");
		InsertCommande ic3 = new InsertCommande("INSERT INTO Profs VALUES (Crapez,GFE)");*/
		
		System.out.println("Insertion de la commande");
		InsertCommande ic4 = new InsertCommande("INSERT INTO ToutesLesNotes VALUES (IF3BDDA,16)" );
		InsertCommande ic5 = new InsertCommande("INSERT INTO ToutesLesNotes VALUES (IF3BDDA,20)" );

		System.out.println(ic4.toString());
		System.out.println(ic5.toString());

		ic4.execute();
		ic5.execute();


		
		/*System.out.println(ic2.toString());

		ic2.execute();
		System.out.println(ic3.toString());

		ic3.execute();*/

	}
	
	private static void selectCommandTest() {
		createTableTest();
		insertCommandTest();
		SelectCommand sc = new SelectCommand("SELECT * FROM ToutesLesNotes WHERE Cours=IF3BDDA AND Note>=10 AND Note=20");
		//SelectCommand sc = new SelectCommand("SELECT * FROM ToutesLesNotes WHERE Note=16");
		//SelectCommand sc = new SelectCommand("SELECT * FROM ToutesLesNotes WHERE Cours=IF3BDDA");


		sc.execute();
	}

}