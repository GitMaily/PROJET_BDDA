public class DBParams {
	
	public static String DBPath = "C:\\Users\\milly\\Desktop\\PROJET_BDDA\\PROJET_BDDA_LAVALLEE_TANGUY_CIAVALDINI_MARZOUK\\DB";
	public static int pageSize = 4096;
	public static int maxPagesPerFile = 4;
	
	public static int frameCount = 2;
	
	public DBParams(String DBPath, int pageSize, int maxPagesPerFile) {
		DBParams.DBPath= DBPath;
		DBParams.pageSize = pageSize;
		DBParams.maxPagesPerFile= maxPagesPerFile;
		// TODO Auto-generated constructor stub
		
		
	}
	

	public int getPageSize() {
		return pageSize;
	}

	public  void setPageSize(int pageSize) {
		DBParams.pageSize = pageSize;
	}

	public  int getMaxPagesPerFile() {
		return maxPagesPerFile;
	}

	public  void setMaxPagesPerFile(int maxPagesPerFile) {
		DBParams.maxPagesPerFile = maxPagesPerFile;
	}

	public  void setDBPath(String dBPath) {
		DBPath = dBPath;
	}

	
	
	
	

	
	
	
}
