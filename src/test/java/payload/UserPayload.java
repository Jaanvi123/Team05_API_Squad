package payload;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import pojo.UserPojo;  // Import the User POJO with inner classes
import utilities.CommonUtils;

public class UserPayload extends CommonUtils {

    public List<Map<String, String>> excelData;
    private Map<String, String> currentRow;
    private final Logger LOGGER = LogManager.getLogger(UserPayload.class);
    String sheetName = "User";  // Changed to User sheet

    public Map<String, Object> getDataFromExcel(String scenario) 
            throws IOException, ParseException, InvalidFormatException {
        
        currentRow = CommonUtils.getCurrentRow(scenario, sheetName);
        Map<String, Object> userDetails = new HashMap<String, Object>();
        UserPojo userPojo = null;

        if (!scenario.contains("Get")) {
            // Create User POJO with Excel data
            userPojo = new UserPojo();
            
            // Populate main User fields from Excel
            userPojo.setUserComments(currentRow.get("userComments"));
            userPojo.setUserEduPg(currentRow.get("userEduPg"));
            userPojo.setUserEduUg(currentRow.get("userEduUg"));
            userPojo.setUserFirstName(currentRow.get("userFirstName"));
            userPojo.setUserLastName(currentRow.get("userLastName"));
            userPojo.setUserLinkedinUrl(currentRow.get("userLinkedinUrl"));
            userPojo.setUserLocation(currentRow.get("userLocation"));
            userPojo.setUserMiddleName(currentRow.get("userMiddleName"));
            userPojo.setUserPhoneNumber(currentRow.get("userPhoneNumber"));
            userPojo.setUserTimeZone(currentRow.get("userTimeZone"));
            userPojo.setUserVisaStatus(currentRow.get("userVisaStatus"));

            // Handle nested UserRoleMap (if Excel has role data)
            if (currentRow.containsKey("roleId") && currentRow.containsKey("userRoleStatus")) {
            	UserPojo.UserRoleMap roleMap = new UserPojo.UserRoleMap();
                roleMap.setRoleId(currentRow.get("roleId"));
                roleMap.setUserRoleStatus(currentRow.get("userRoleStatus"));
                userPojo.setUserRoleMaps(java.util.Arrays.asList(roleMap));
            }

            // Handle nested UserLogin (if ExceUserPojol has login data)
            if (currentRow.containsKey("userLoginEmail") && currentRow.containsKey("loginStatus")) {
            	UserPojo.UserLogin userLogin = new UserPojo.UserLogin();
                userLogin.setUserLoginEmail(currentRow.get("userLoginEmail"));
                userLogin.setLoginStatus(currentRow.get("loginStatus"));
                userLogin.setStatus(currentRow.get("status"));
                userPojo.setUserLogin(currentRow.get("userLogin"));
            }
        }

        LOGGER.info("Read User details from Excel file: " + userPojo);
        userDetails.put("userPojo", userPojo);
        userDetails.put("currentRow", currentRow);
        return userDetails;
    }
}
