import java.io.IOException;

public class TestCatalog {

		
		public static void addRelationTest() throws ClassNotFoundException, IOException{
			Catalog.getInstance().Init();
			RelationInfo RI = new RelationInfo("Etudiant",null);
			Catalog.getInstance().AddRelationInfo(RI);
			Catalog.getInstance().Finish();
		}
		
		public static void main(String[] args) throws ClassNotFoundException, IOException{
			// TODO Auto-generated method stub
			DBParams.DBPath="../../DB";
			DBParams.maxPagesPerFile = 4;
			DBParams.frameCount = 2;
			DBParams.pageSize = 4096;
			
			addRelationTest();
		
		}
		
}
