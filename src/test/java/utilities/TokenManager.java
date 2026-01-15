package utilities;

public class TokenManager {
	
	private static String token;

	// Method to set the token
	public static void setToken(String generatedToken) {
		token = generatedToken;
	}

	// Method to get the token
	public static String getToken() {
		token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyb2JpbnJhbmphbkBnbWFpbC5jb20iLCJpYXQiOjE3Njg1MTY5ODMsImV4cCI6MTc2ODU0NTc4M30.7VHGOXnx82czXE0npPuKxl9bDEe3b41Cipuj4O2WCo_uyzZZnaYwyZjUKUyLNYu82DQAaHKyAEuDL43djygXow";
		return token;
	}

}
