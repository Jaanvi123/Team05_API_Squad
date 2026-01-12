package utilities;

public class TokenManager {
	
	private static String token;

	// Method to set the token
	public static void setToken(String generatedToken) {
		token = generatedToken;
	}

	// Method to get the token
	public static String getToken() {
		return token;
	}

}
