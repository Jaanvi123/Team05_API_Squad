package utilities;

public class TokenManager {
	
	private static String token;

	// Method to set the token
	public static void setToken(String generatedToken) {
		token = generatedToken;
	}

	// Method to get the token
	public static String getToken() {
		token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZWFtNUBnbWFpbC5jb20iLCJpYXQiOjE3NjgzOTkyODYsImV4cCI6MTc2ODQyODA4Nn0.uNz099lY0MWRkorogrtwWwucp9WzC_D84Vgnjh4a-SLxiDzgE425KohuPVWo5cwxYC7AH224CNQ2UCDNDtC6Kw";
		return token;
	}

}
