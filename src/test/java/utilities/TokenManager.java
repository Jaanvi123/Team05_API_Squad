package utilities;

public class TokenManager {
	
	private static String token;
	private static String userId;


	// Method to set the token
	public static void setToken(String generatedToken) {
		token = generatedToken;
	}
	   public static String getToken() {
	        if (token == null || token.isEmpty()) {
	            // Optional: default token from team5 for testing
	            token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZWFtNUBnbWFpbC5jb20iLCJpYXQiOjE3NjgzOTkyODYsImV4cCI6MTc2ODQyODA4Nn0.uNz099lY0MWRkorogrtwWwucp9WzC_D84Vgnjh4a-SLxiDzgE425KohuPVWo5cwxYC7AH224CNQ2UCDNDtC6Kw";
	        }
	        return token;
	    }
	// Method to get the token

	public static void setUserId(String newUserId) {
        userId = newUserId;
    }
    public static String getUserId() {
        return userId;
    }

}
