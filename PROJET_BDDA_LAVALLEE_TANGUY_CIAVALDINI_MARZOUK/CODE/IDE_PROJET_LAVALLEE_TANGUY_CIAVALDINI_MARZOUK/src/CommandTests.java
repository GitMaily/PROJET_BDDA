
public class CommandTests {
	public static void main(String []args) {
		//insertCommandTest();
		
		selectCommandTest();
		//createTableTest();
		
		//dropDBTest();
	}
	
	private static void dropDBTest() {
		DropDBCommand.supprimerFichiers();
		
	}

	private static void createTableTest() {
		String commande = "CREATE TABLE Profs (X:INTEGER,C2:VARCHAR(5),BLA:VARCHAR(10))";
		String commande2 = "CREATE TABLE Profs (NOM:VARCHAR(10),UE:VARCHAR(5))";
		//CreateTableCommand tc = new CreateTableCommand("CREATE TABLE Profs (X:INTEGER,C2:REAL,BLA:VARCHAR(10))");
		CreateTableCommand tc1 = new CreateTableCommand(commande2);

		
		//tc.execute();
		tc1.execute();
		
		//System.out.println(tc.toString());

		//System.out.println(tc1.toString());

	}

	private static void insertCommandTest() {
		createTableTest();
		
		InsertCommande ic1 = new InsertCommande("INSERT INTO Profs VALUES (Ileana,BDDA)");
		InsertCommande ic2 = new InsertCommande("INSERT INTO Profs VALUES (Jean-Guy-Mailly,PROG)");
		InsertCommande ic3 = new InsertCommande("INSERT INTO Profs VALUES (Crapez,GFE)");

		System.out.println(ic1.toString());
		
		ic1.execute();
		

		
		/*System.out.println(ic2.toString());

		ic2.execute();
		System.out.println(ic3.toString());

		ic3.execute();*/

	}
	
	private static void selectCommandTest() {
		createTableTest();
		SelectCommand sc = new SelectCommand("SELECT * FROM ToutesLesNotes WHERE Cours=IF3BDDA\n"
				+ "AND Note>=8\n"
				+ "AND Note<10");
		
		sc.execute();
	}

}
