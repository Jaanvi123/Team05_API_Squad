package payload;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pojo.UserPojo;  // Import the User POJO with inner classes
import utilities.CommonUtils;


public class UserPayload extends CommonUtils {

	private static final Logger LOGGER = LogManager.getLogger(UserPayload.class);
	public Map<String, Object> getDataFromExcel(String scenario)
	        throws IOException, ParseException, InvalidFormatException {
	    Map<String, Object> userDetails = new HashMap<>();
	    currentRow = CommonUtils.getCurrentRow(scenario, "User");
	    if (currentRow == null || currentRow.isEmpty()) {
	        LOGGER.warn("No data found in Excel for scenario: " + scenario);
	        return userDetails;
	    }
	    UserPojo userPojo = new UserPojo();
	    // Basic fields
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
	    // Parse userRoleMaps JSON
	    String roleJson = currentRow.get("userRoleMaps");
	    if (roleJson != null && !roleJson.trim().isEmpty()) {
	        try {
	            ObjectMapper mapper = new ObjectMapper();
	            List<UserPojo.UserRoleMap> roles = mapper.readValue(
	                roleJson, new com.fasterxml.jackson.core.type.TypeReference<List<UserPojo.UserRoleMap>>() {});
	            userPojo.setUserRoleMaps(roles);
	        } catch (Exception e) {
	            LOGGER.error("Failed to parse userRoleMaps JSON for scenario: " + scenario, e);
	        }
	    }
	    // Parse userLogin JSON
	    String loginJson = currentRow.get("userLogin");
	    if (loginJson != null && !loginJson.trim().isEmpty()) {
	        try {
	            UserPojo.UserLogin userLogin = new UserPojo.UserLogin(loginJson);
	            userPojo.setUserLogin(userLogin);
	        } catch (Exception e) {
	            LOGGER.error("Failed to parse userLogin JSON for scenario: " + scenario, e);
	        }
	    }
	    LOGGER.info("Read User details from Excel file for scenario '{}': {}", scenario, userPojo);
	    userDetails.put("userPojo", userPojo);
	    userDetails.put("currentRow", currentRow);
	    return userDetails;
	}
}