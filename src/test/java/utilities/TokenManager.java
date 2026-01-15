package utilities;

public class TokenManager {
	
	private static String token;

	// Method to set the token
	public static void setToken(String generatedToken) {
		token = generatedToken;
	}

	// Method to get the token
	public static String getToken() {
		token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyb2JpbnJhbmphbkBnbWFpbC5jb20iLCJpYXQiOjE3Njg0ODc1NTIsImV4cCI6MTc2ODUxNjM1Mn0.51vjqzhbFdbsjIDJ1PZBegx8gojOpmUh0BmWT8aBubFd1bQUL-c7NW2ixhl60m0clGubXkbvQm3DHeh4O-zp7g";
		return token;
	}

}
