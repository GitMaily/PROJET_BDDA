public class DBParams {
	
	public static String DBPath;
	public static int pageSize = 4096;
	public static int maxPagesPerFile = 4;
	
	public DBParams() {
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
