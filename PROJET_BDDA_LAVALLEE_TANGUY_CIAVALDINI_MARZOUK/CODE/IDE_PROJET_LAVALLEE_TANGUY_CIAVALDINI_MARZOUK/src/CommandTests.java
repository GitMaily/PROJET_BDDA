
public class CommandTests {
	
	public static void csvTest() {
		CreateTableCommand ct = new CreateTableCommand("CREATE TABLE S (C1:INTEGER,C2:REAL,C3:INTEGER,C4:INTEGER,C5:INTEGER)");
		ct.execute();
		
		InsertCommande ic = new InsertCommande("INSERT INTO S FILECONTENTS(S.csv)");
		ic.execute();
		
		System.out.println(ic.toString());
		
		System.out.println("SELECT * FROM S WHERE C3=12");
		SelectCommand sc = new SelectCommand("SELECT * FROM S WHERE C3=12");
		sc.execute();
		System.out.println();
		
		
		System.out.println("SELECT * FROM S WHERE C3=12 AND C1=167");
		SelectCommand sc2 = new SelectCommand("SELECT * FROM S WHERE C3=12 AND C1=167");
		sc2.execute();
		System.out.println();
		
		
		
		
	}
	
	public static void scenarioTP6() {
		CreateTableCommand ct = new CreateTableCommand("CREATE TABLE R (C1:REAL,C2:VARHCAR(3),C3:INTEGER)");
		ct.execute();
		
		InsertCommande ic = new InsertCommande("INSERT INTO R VALUES (1,aab,2)");
		ic.execute();
		InsertCommande ic1 = new InsertCommande("INSERT INTO R VALUES (2,ab,2)");
		ic1.execute();
		InsertCommande ic2 = new InsertCommande("INSERT INTO R VALUES (1,agh,1)");
		ic2.execute();
		
		System.out.println("SELECT * FROM R");
		SelectCommand sc = new SelectCommand("SELECT * FROM R");
		sc.execute();
		System.out.println();

		System.out.println("SELECT * FROM R WHERE C1=1");
		SelectCommand sc1 = new SelectCommand("SELECT * FROM R WHERE C1=1");
		sc1.execute();
		System.out.println();

		System.out.println("SELECT * FROM R WHERE C3=1");
		SelectCommand sc2 = new SelectCommand("SELECT * FROM R WHERE C3=1");
		sc2.execute();
		System.out.println();

		System.out.println("SELECT * FROM R WHERE C1=1 AND C3=2");
		SelectCommand sc3 = new SelectCommand("SELECT * FROM R WHERE C1=1 AND C3=2");
		sc3.execute();
		System.out.println();

		System.out.println("SELECT * FROM R WHERE C1<2");
		SelectCommand sc4 = new SelectCommand("SELECT * FROM R WHERE C1<2");
		sc4.execute();
		
	}
	
	public static void main(String []args) {
		DBParams.DBPath = args[0];
		DBParams.frameCount = 2;
		DBParams.maxPagesPerFile = 4;
		DBParams.pageSize = 4096;
		
		dropDBTest();
		
		csvTest();
		
		//createTableTest();
		//insertCommandTest();
		//selectCommandTest();
		
		//scenarioTP6();
		//profsTest();
		
		//deleteCommandTest();
		
		
		
	}
	
	private static void deleteCommandTest() {
		csvTest();

		DeleteCommand dc = new DeleteCommand("DELETE * FROM S WHERE C3=12 AND C1=167");
		dc.execute();
		
		SelectCommand sc = new SelectCommand("SELECT * FROM S WHERE C3=12 AND C1=167");

		sc.execute();
	}
	
	
	private static void dropDBTest() {
		DropDBCommand.supprimerFichiers();
		
	}

	private static void createTableTest() {
		//String commande = "CREATE TABLE Profs (X:INTEGER,C2:VARCHAR(5),BLA:VARCHAR(10))";
		String commande1 = "CREATE TABLE Profs (NOM:VARCHAR(10),UE:VARCHAR(5))";
		String commande3 = "CREATE TABLE ToutesLesNotes (Cours:VARCHAR(10),Note:INTEGER";
		
		
		System.out.println("***** Création d'une table avec la commande :"+commande1+" *****");
		CreateTableCommand tc1 = new CreateTableCommand(commande1);
		System.out.println();

		System.out.println("***** Création d'une table avec la commande :"+commande3+" *****");
		CreateTableCommand tc3 = new CreateTableCommand(commande3);

		//CreateTableCommand tc = new CreateTableCommand("CREATE TABLE Profs (X:INTEGER,C2:REAL,BLA:VARCHAR(10))");

		
		//tc.execute();
		tc1.execute();
		tc3.execute();
		
		System.out.println(tc1.toString());
		System.out.println(tc3.toString());

		//System.out.println(tc.toString());

		System.out.println();
	}

	private static void insertCommandTest() {
		createTableTest();
		System.out.println("***** Insertion de commande *****");

		InsertCommande ic1 = new InsertCommande("INSERT INTO Profs VALUES (Ileana,BDDA)");
		InsertCommande ic2 = new InsertCommande("INSERT INTO Profs VALUES (Jean-Guy_Mailly,PROG)");
		InsertCommande ic3 = new InsertCommande("INSERT INTO Profs VALUES (Crapez,GFE)");
		InsertCommande ic7 = new InsertCommande("INSERT INTO Profs VALUES (Delobelle,PROG)");

		
		InsertCommande ic4 = new InsertCommande("INSERT INTO ToutesLesNotes VALUES (IF3BDDA,16)" );
		InsertCommande ic5 = new InsertCommande("INSERT INTO ToutesLesNotes VALUES (IF3BDDA,20)" );
		InsertCommande ic6 = new InsertCommande("INSERT INTO ToutesLesNotes VALUES (IF3BDDA,10)" );

		System.out.println(ic4.toString());
		System.out.println(ic5.toString());
		ic1.execute();
		ic2.execute();
		ic3.execute();
		ic4.execute();
		ic5.execute();
		ic6.execute();
		ic7.execute();


		
		/*System.out.println(ic2.toString());

		ic2.execute();
		System.out.println(ic3.toString());

		ic3.execute();*/
		System.out.println();

	}
	
	private static void selectCommandTest() {
		//createTableTest();
		insertCommandTest();
		//SelectCommand sc = new SelectCommand("SELECT * FROM ToutesLesNotes WHERE Cours=IF3BDDA AND Note>=17 AND Note<=20 AND Note>0");
		//SelectCommand sc = new SelectCommand("SELECT * FROM ToutesLesNotes WHERE Note>=10");
		//SelectCommand sc = new SelectCommand("SELECT * FROM ToutesLesNotes WHERE Cours=IF3BDDA");
		SelectCommand sc = new SelectCommand("SELECT * FROM ToutesLesNotes ");

		System.out.println("Commande de selection : "+sc.toString());
		sc.execute();

		//SelectCommand sc1 = new SelectCommand("SELECT * FROM Profs ");
		//SelectCommand sc1 = new SelectCommand("SELECT * FROM Profs WHERE NOM=Ileana AND UE=BDDA");
		SelectCommand sc1 = new SelectCommand("SELECT * FROM Profs WHERE NOM=Ileana AND UE=PROG");

		System.out.println("Commande de selection : "+sc1.toString());

		sc1.execute();

		System.out.println();

	}
	
	public static void profsTest() {
		CreateTableCommand ct = new CreateTableCommand("CREATE TABLE Profs (NOM:VARCHAR(10),UE:VARCHAR(5))");
		ct.execute();
		System.out.println("INSERT INTO Profs VALUES (Ileana,BDDA)");
		InsertCommande ic1 = new InsertCommande("INSERT INTO Profs VALUES (Ileana,BDDA)");
		System.out.println();

		System.out.println("INSERT INTO Profs VALUES (Jean-Guy_Mailly,PROG)");
		InsertCommande ic2 = new InsertCommande("INSERT INTO Profs VALUES (Jean-Guy_Mailly,PROG)");
		System.out.println();

		System.out.println("INSERT INTO Profs VALUES (Crapez,GFE)");
		InsertCommande ic3 = new InsertCommande("INSERT INTO Profs VALUES (Crapez,GFE)");
		System.out.println();

		System.out.println("INSERT INTO Profs VALUES (Delobelle,PROG)");
		InsertCommande ic4 = new InsertCommande("INSERT INTO Profs VALUES (Delobelle,PROG)");	
		System.out.println();

		ic1.execute();
		ic2.execute();
		ic3.execute();
		ic4.execute();
		System.out.println("SELECT * FROM Profs");
		SelectCommand sc1 = new SelectCommand("SELECT * FROM Profs");
		sc1.execute();
		System.out.println();

		System.out.println("SELECT * FROM Profs WHERE NOM=Ileana AND UE=BDDA");
		SelectCommand sc2 = new SelectCommand("SELECT * FROM Profs WHERE NOM=Ileana AND UE=BDDA");
		sc2.execute();
		System.out.println();

		System.out.println("SELECT * FROM Profs WHERE UE=PROG");
		SelectCommand sc3 = new SelectCommand("SELECT * FROM Profs WHERE UE=PROG");
		sc3.execute();
		System.out.println();

		

	}

}