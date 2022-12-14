import java.io.IOException;

public class ReadcsvTests {

	public static void main (String[]args) {
		
		DBParams.DBPath = args[0];
		DBParams.frameCount = 2;
		DBParams.maxPagesPerFile = 4;
		DBParams.pageSize = 4096;
		
		Readcsv csv = new Readcsv("S.csv");
		
		try {
			csv.lireCsv();
			
			csv.affiche();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
