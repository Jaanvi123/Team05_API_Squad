package commons;

public class Commons {
	
	private static int programId;
	private static String programName;
	private static float batchId = 42;
	private static String batchName;
		
	public static int getProgramId() {
		return programId;
	}
	public static void setProgramId(int programId) {
		Commons.programId = programId;
	}
	public static String getProgramName() {
		return programName;
	}
	public static void setProgramName(String programName) {
		Commons.programName = programName;
	}
	
	public static float getbatchId() {
		return batchId;
	}
	public static void setbatchId(float batchId) {
		Commons.batchId = batchId;
	}
	
	public static String getbatchName() {
		return batchName;
	}
	public static void setbatchName(String batchName) {
		Commons.batchName = batchName;
	}

}
