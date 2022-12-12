import java.io.IOException;

public class ReadcsvTests {

	public static void main (String[]args) {
		String path = "C:\\Users\\milly\\Desktop\\PROJET_BDDA\\PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI_MARZOUK";
		Readcsv csv = new Readcsv("S.csv");
		
		try {
			csv.lireCsv();
			
			csv.affiche();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
