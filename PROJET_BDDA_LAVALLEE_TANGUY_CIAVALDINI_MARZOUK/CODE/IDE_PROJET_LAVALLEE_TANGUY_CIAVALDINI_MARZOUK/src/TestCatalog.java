import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class TestCatalog {

		public static void ecritureFinish() throws ClassNotFoundException, IOException {
			PageId pi = new PageId(0,0);
			RelationInfo rel = creationRelationInfoContexte(pi);
			RelationInfo rel2 = creationRelationInfoContexte2(pi);

			addRelationTest(rel);
			addRelationTest(rel2);

			System.out.println("***** Sauvegarde le catalog dans le fichier *****");
			Catalog.getInstance().Finish();
			System.out.println(Catalog.getInstance().toString());
			
		}
		
		public static boolean lectureInit() throws FileNotFoundException, ClassNotFoundException, IOException {
			System.out.println("***** Vérification s'il existe un fichier catalog *****");
			
			File f = new File(DBParams.DBPath+File.separator+"Catalog.sv");
			if(f.exists()) {
				
				System.out.println("Trouvé");
				System.out.println("Catalog avant : ");
				System.out.println(Catalog.getInstance().toString());

				Catalog.getInstance().Init();
				System.out.println("Catalog après : ");
				System.out.println(Catalog.getInstance().toString());
				return true;

			}
			else {
				System.out.println("Aucun fichier catalog");
				return false;
			}
		}
		
		public static void addRelationTest(RelationInfo rel) throws ClassNotFoundException, IOException{
			
			
			//Catalog.getInstance().Init();
			//RelationInfo RI = new RelationInfo("Etudiant",null);
			Catalog.getInstance().AddRelationInfo(rel);
			//Catalog.getInstance().AddRelationInfo(rel);

		}
		
		public static void main(String[] args) throws ClassNotFoundException, IOException{
			DBParams.DBPath=args[0];
			DBParams.maxPagesPerFile = 4;
			DBParams.frameCount = 2;
			DBParams.pageSize = 4096;
			
			if(!lectureInit()) {
				System.out.println("Création d'un fichier Catalog.sv");
				ecritureFinish();
			}
			else {
				ecritureFinish();
			}
		
		}
		
		public static RelationInfo creationRelationInfoContexte(PageId headerPage) {
			
			ArrayList<ColInfo> al = new ArrayList<ColInfo>();
			ColInfo col1 = new ColInfo("NOM","VARCHAR(10)");
			ColInfo col2 = new ColInfo("UE","VARCHAR(10)");
			al.add(col1);
			al.add(col2);
			
			System.out.println("***** Création d'une relationInfo \"Profs\", al, headerPage *****");
			RelationInfo rel = new RelationInfo("Profs",al, headerPage);
			System.out.println(rel.toString());
			
			
			System.out.println();
			return rel;
			
		}
		
		public static RelationInfo creationRelationInfoContexte2(PageId headerPage) {
			
			ArrayList<ColInfo> al = new ArrayList<ColInfo>();
			ColInfo col1 = new ColInfo("NOM","VARCHAR(10)");
			ColInfo col2 = new ColInfo("PRENOM","VARCHAR(10)");
			al.add(col1);
			al.add(col2);
			
			System.out.println("***** Création d'une relationInfo \"Etudiants\", al, headerPage *****");
			RelationInfo rel = new RelationInfo("Etudiants",al, headerPage);
			System.out.println(rel.toString());
			
			
			System.out.println();
			return rel;
			
		}
		
}
