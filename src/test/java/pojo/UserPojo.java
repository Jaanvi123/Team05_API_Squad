package pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserPojo {
	  @JsonProperty("userComments")
	    private String userComments;
	    
	    @JsonProperty("userEduPg")
	    private String userEduPg;
	    
	    @JsonProperty("userEduUg")
	    private String userEduUg;
	    
	    @JsonProperty("userFirstName")
	    private String userFirstName;
	    
	    @JsonProperty("userLastName")
	    private String userLastName;
	    
	    @JsonProperty("userLinkedinUrl")
	    private String userLinkedinUrl;
	    
	    @JsonProperty("userLocation")
	    private String userLocation;
	    
	    @JsonProperty("userMiddleName")
	    private String userMiddleName;
	    
	    @JsonProperty("userPhoneNumber")
	    private String userPhoneNumber;
	    
	    @JsonProperty("userRoleMaps")
	    private List<UserRoleMap> userRoleMaps;
	    
	    public static class UserRoleMap {
	        @JsonProperty("roleId")
	        private String roleId;
	        
	        @JsonProperty("userRoleStatus")
	        private String userRoleStatus; 
	        
	        public UserRoleMap() {}
	        
	        public String getRoleId() { 
	        	return roleId; 
	        	}
	        public void setRoleId(String roleId) { 
	        	this.roleId = roleId; }
	        
	        public String getUserRoleStatus() { 
	        	return userRoleStatus; 
	        	}
	        
	        public void setUserRoleStatus(String userRoleStatus) { 
	        	this.userRoleStatus = userRoleStatus; 
	        	}
   }
	    
	    @JsonProperty("userTimeZone")
	    private String userTimeZone;
	    
	    @JsonProperty("userVisaStatus")
	    private String userVisaStatus;
	//solution1:    
	 //   @JsonProperty("userLogin")
	//    private String userLogin;
	//solution 2:   // @JsonProperty("userLogin")
	   // private UserLogin userLogin;
	    
	  public static class UserLogin {
	        @JsonProperty("userLoginEmail")
	        private String userLoginEmail;
	        
	        @JsonProperty("loginStatus")
	        private String loginStatus;
	        
	        @JsonProperty("status")
	        private String status;
	        
	        public UserLogin() {}
	       
	        public UserLogin(String jsonString) throws Exception {
	            ObjectMapper mapper = new ObjectMapper();
	            UserLogin temp = mapper.readValue(jsonString, UserLogin.class);
	            this.userLoginEmail = temp.userLoginEmail;
	            this.loginStatus = temp.loginStatus;
	            this.status = temp.status;
	        
	        }
	  


	 
	        public String getUserLoginEmail() { 
	        	return userLoginEmail; 
	        	}
	        
	        public void setUserLoginEmail(String userLoginEmail) { 
	        	this.userLoginEmail = userLoginEmail; 
	        	}
	        
	        public String getLoginStatus() { 
	        	return loginStatus; 
	        	}
	        
	        public void setLoginStatus(String loginStatus) { 
	        	this.loginStatus = loginStatus; 
	        	}
	        
	        public String getStatus() {
	        	return status; 
	        	}
	        
	        public void setStatus(String status) { 
	        	this.status = status; 
	        	}
	    }
	        
	 // Getters Methods
	    public String getUserComments() {
	    	return userComments; 
	    	}
	   
	    public String getUserEduPg() { 
	    	return userEduPg; 
	    	}
	    
	    public String getUserEduUg() { 
	    	return userEduUg; 
	    	}
	    
	    public String getUserFirstName() { 
	    	return userFirstName; 
	    	}
	    
	    public String getUserLastName() { 
	    	return userLastName; 
	    	}    
	    
	    public String getUserLinkedinUrl() { 
	    	return userLinkedinUrl; 
	    	}
	    
	    public String getUserLocation() { 
	    	return userLocation; 
	    	}
	   	    
	    public String getUserMiddleName() { 
	    	return userMiddleName; 
	    	}
	     
	    public String getUserPhoneNumber() { 
	    	return userPhoneNumber; 
	    	}
	        
	    public List<UserRoleMap> getUserRoleMaps() { 
	    	return userRoleMaps; 
	    	}
	        
	    public String getUserTimeZone() { 
	    	return userTimeZone; 
	    	}
	    	    
	    public String getUserVisaStatus() { 
	    	return userVisaStatus; 
	    	}    
	    
	 //   public String getUserLogin() { 
	    //	return userLogin; 
	    	//}
	    public UserLogin getUserLogin() { return userLogin; }
	    
	    //Setter Method
	    
	    public void setUserComments(String userComments) {
	    	this.userComments = userComments; 
	    	}
	    
	    public void setUserEduPg(String userEduPg) { 
	    	this.userEduPg = userEduPg; 
	    	}
	    
	    public void setUserEduUg(String userEduUg) { 
	    	this.userEduUg = userEduUg; 
	    	}
	    
	    public void setUserFirstName(String userFirstName) { 
	    	this.userFirstName = userFirstName; 
	    	}
	    
	    public void setUserLastName(String userLastName) { 
	    	this.userLastName = userLastName; 
	    	}
	    
	    public void setUserLinkedinUrl(String userLinkedinUrl) { 
	    	this.userLinkedinUrl = userLinkedinUrl; 
	    	}
	    
	    public void setUserLocation(String userLocation) { 
	    	this.userLocation = userLocation; 
	    	}
	    
	    public void setUserMiddleName(String userMiddleName) { 
	    	this.userMiddleName = userMiddleName; 
	    	}
	    
	    public void setUserPhoneNumber(String userPhoneNumber) { 
	    	this.userPhoneNumber = userPhoneNumber; 
	    	}
	    
	    public void setUserRoleMaps(List<UserRoleMap> userRoleMaps) { 
	    	this.userRoleMaps = userRoleMaps; 
	    	}
	    
	    public void setUserTimeZone(String userTimeZone) { 
	    	this.userTimeZone = userTimeZone; 
	    	}
	    
	    public void setUserVisaStatus(String userVisaStatus) { 
	    	this.userVisaStatus = userVisaStatus; 
	    	}
	    
	  //  public void setUserLogin(String userLogin) { 
	    //	this.userLogin = userLogin; 
	    	//}
	    public void setUserLogin(UserLogin userLogin) { this.userLogin = userLogin; }
	}


