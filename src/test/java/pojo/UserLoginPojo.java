package pojo;

public class UserLoginPojo {
	private String userLoginEmailId;
    private String password;
    // Default Constructor
    public UserLoginPojo() {
    }
    // Parametrized Constructor used by UserLoginPayload
    public UserLoginPojo(String userLoginEmailId, String password) {
        this.userLoginEmailId = userLoginEmailId;
        this.password = password;
    }
    public void UserResetPasswordPojo() {}
    // Getters and Setters
    public String getUserLoginEmailId() {
        return userLoginEmailId;
    }
    public void setUserLoginEmailId(String userLoginEmailId) {
        this.userLoginEmailId = userLoginEmailId;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}


