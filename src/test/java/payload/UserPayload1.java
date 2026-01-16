package payload;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import pojo.UserPojo1;
import utilities.CommonUtils;

public class UserPayload1 extends CommonUtils {
	  private static final Logger LOGGER = LogManager.getLogger(UserPayload.class);
	    private static final String sheetName = "User";
	    public Map<String, Object> getDataFromExcel(String scenario)
	            throws IOException, ParseException, InvalidFormatException {
	        Map<String, Object> userDetails = new HashMap<>();
	        currentRow = CommonUtils.getCurrentRow(scenario, sheetName);
	        if (currentRow == null || currentRow.isEmpty()) {
	            LOGGER.warn("No data found in Excel for scenario: " + scenario);
	            return userDetails;
	        }
	        UserPojo1 userPojo = new UserPojo1();
	        userPojo.setUserComments(getStringValue(currentRow.get("userComments")));
	        userPojo.setUserEduPg(getStringValue(currentRow.get("userEduPg")));
	        userPojo.setUserEduUg(getStringValue(currentRow.get("userEduUg")));
	        userPojo.setUserFirstName(getStringValue(currentRow.get("userFirstName")));
	        userPojo.setUserLastName(getStringValue(currentRow.get("userLastName")));
	        userPojo.setUserLinkedinUrl(getStringValue(currentRow.get("userLinkedinUrl")));
	        userPojo.setUserLocation(getStringValue(currentRow.get("userLocation")));
	        userPojo.setUserMiddleName(getStringValue(currentRow.get("userMiddleName")));
	        userPojo.setUserPhoneNumber(getStringValue(currentRow.get("userPhoneNumber")));
	        userPojo.setUserTimeZone(getStringValue(currentRow.get("userTimeZone")));
	        userPojo.setUserVisaStatus(getStringValue(currentRow.get("userVisaStatus")));
	        parseUserRoleMaps(userPojo, scenario);
	        parseUserLogin(userPojo, scenario);
	        LOGGER.info("User details from Excel for scenario", scenario);
	        userDetails.put("userPojo", userPojo);
	        userDetails.put("currentRow", currentRow);
	        return userDetails;
	    }
	    private void parseUserRoleMaps(UserPojo1 userPojo, String scenario) {
	        String roleJson = currentRow.get("userRoleMaps");
	        if (roleJson == null || roleJson.trim().isEmpty()) {
	            userPojo.setUserRoleMaps(Collections.emptyList());
	            return;
	        }
	        try {
	            ObjectMapper mapper = new ObjectMapper();
	            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	            List<UserPojo1.UserRoleMap> roles = mapper.readValue(roleJson,
	                new TypeReference<List<UserPojo1.UserRoleMap>>() {});
	            userPojo.setUserRoleMaps(roles);
	        } catch (Exception e) {
	            LOGGER.error("Failed to parse userRoleMaps for scenario: " + scenario + ". Using empty list.", e);
	            userPojo.setUserRoleMaps(Collections.emptyList());
	        }
	    }
	    private void parseUserLogin(UserPojo1 userPojo, String scenario) {
	        String loginJson = currentRow.get("userLogin");
	        if (loginJson == null || loginJson.trim().isEmpty()) {
	            LOGGER.warn("userLogin empty for scenario: " + scenario);
	            return;
	        }
	        try {
	            ObjectMapper mapper = new ObjectMapper();
	            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	            UserPojo1.UserLogin userLogin = mapper.readValue(loginJson, UserPojo1.UserLogin.class);
	            userLogin.setUserId(null);
	            userLogin.setProgramName(null);
	            userLogin.setBatchName(null);
	            userPojo.setUserLogin(userLogin);
	        } catch (Exception e) {
	        }
	    }
	    private String getStringValue(Object value) {
	        return value != null ? String.valueOf(value).trim() : null;
	    }
	    public Map<String, Object> getUsersByProgramData(String scenario, UserPojo1 userPojo) throws Exception {
	        currentRow = CommonUtils.getCurrentRow(scenario, sheetName);
	        Map<String, Object> data = new HashMap<>();
	        data.put("programId", getSafeValue(currentRow.get("programId"),
	            userPojo != null && userPojo.getUserRoleMaps() != null && !userPojo.getUserRoleMaps().isEmpty()
	                ? userPojo.getUserRoleMaps().get(0).getRoleId() : null));
	        data.put("expectedStatus", getStringValue(currentRow.get("StatusCode")));
	        return data;
	    }
	    public Map<String, Object> getUsersByRoleData(String scenario, UserPojo1 userPojo) throws Exception {
	        currentRow = CommonUtils.getCurrentRow(scenario, sheetName);
	        Map<String, Object> data = new HashMap<>();
	        data.put("roleId", getSafeValue(currentRow.get("roleId"),
	            userPojo != null && userPojo.getUserRoleMaps() != null && !userPojo.getUserRoleMaps().isEmpty()
	                ? userPojo.getUserRoleMaps().get(0).getRoleId() : null));
	        data.put("expectedStatus", getStringValue(currentRow.get("StatusCode")));
	        return data;
	    }
	    public Map<String, Object> getUserDetails(String scenario, UserPojo1 userPojo) throws Exception {
	        currentRow = CommonUtils.getCurrentRow(scenario, sheetName);
	        Map<String, Object> data = new HashMap<>();
	        data.put("userId", getSafeValue(currentRow.get("userId"),
	            userPojo != null && userPojo.getUserLogin() != null ? userPojo.getUserLogin().getUserId() : null));
	        data.put("expectedStatus", getStringValue(currentRow.get("StatusCode")));
	        return data;
	    }
	    private String getSafeValue(Object excelValue, String pojoValue) {
	        if (pojoValue != null && !pojoValue.trim().isEmpty()) return pojoValue;
	        return getStringValue(excelValue);
	    }
	}

}
