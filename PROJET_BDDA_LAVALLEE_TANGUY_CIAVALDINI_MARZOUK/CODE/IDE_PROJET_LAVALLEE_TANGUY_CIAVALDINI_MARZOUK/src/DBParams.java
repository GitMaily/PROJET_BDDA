public class DBParams {
	
	public static String DBPath;
	public static int pageSize;
	public static int maxPagesPerFile;
	
	public static int frameCount;
	
	public DBParams(String DBPath, int pageSize, int maxPagesPerFile, int frameCount) {
		DBParams.DBPath= DBPath;
		DBParams.pageSize = pageSize;
		DBParams.maxPagesPerFile= maxPagesPerFile;
		DBParams.frameCount = frameCount;
		
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
