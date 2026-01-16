package payload;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Commons;
import payload.ProgramBatchPayload;
import pojo.ProgramBatchPojo;
import utilities.CommonUtils;




public class ProgramBatchPayload {
	
	public List<Map<String, String>> excelData;
    private Map<String, String> currentRow;
    private final Logger LOGGER = LogManager.getLogger(ProgramBatchPayload.class);
    String sheetName="Batch";
    private static final String FILE_PATH = "src/test/resources/batchDescriptionStorage.json";
   
    public Map<String, Object> getDataFromExcel(String scenario)
            throws IOException, ParseException, InvalidFormatException {
    	Map<String, String> currentRow = CommonUtils.getCurrentRow(scenario, sheetName);
       if (currentRow == null || currentRow.isEmpty()) {
            throw new IllegalArgumentException("No data found in Excel for scenario: " + scenario);
        }
     //  ProgramBatchPojo batch = new ProgramBatchPojo();
       String batchName;
       if ("ExistingBatchNameBatchPost".equalsIgnoreCase(scenario.trim()) ||
    		    "InvalidBatchNameFormatBatchPost".equalsIgnoreCase(scenario.trim()) ||
    		    "SequenceMoreThan99BatchPost".equalsIgnoreCase(scenario.trim())) {
    		    batchName = currentRow.get("batchName");
    		    if (batchName == null || batchName.isEmpty()) {
    		        throw new IllegalArgumentException("Batch name is missing in Excel for scenario: " + scenario);
    		    }
    		    LOGGER.info("Using batch name from Excel for scenario '{}': {}", scenario, batchName);
    		   
    		} else if ("MissingBatchNameBatchPost".equalsIgnoreCase(scenario.trim())) {
    		    batchName = " ";
    		    LOGGER.info("Setting empty batchName for 'MissingBatchNamePost' test scenario");
    		   
    		} else {
    		     Random random = new Random();
    		    String randomDigits = String.format("%02d", random.nextInt(100000));
    		    batchName = "APITeam5Batch" + randomDigits;
    		    LOGGER.info("Auto-generated batch name for scenario '{}': {}", scenario, batchName);
    		}
       String batchStatus = currentRow.get("batchStatus");
       if ("MissingMandatoryFieldStatusBatchPost".equalsIgnoreCase(scenario.trim())) {
           batchStatus = "";
           LOGGER.info("Setting empty batchStatus for 'MissingMandatoryField' scenario");
       } else if (batchStatus == null || batchStatus.isEmpty()) {
           batchStatus = "Active";
           LOGGER.info("Using default batchStatus: 'Active' for batch: {}", batchName);
       }
      LOGGER.info("Final batchStatus set to: '{}'", batchStatus);
       String batchDescription = currentRow.get("batchDescription");
       int batchNoOfClasses;
       String batchNoOfClassesStr = currentRow.get("batchNoOfClasses");
      if (batchNoOfClassesStr == null || batchNoOfClassesStr.trim().isEmpty()) {
    	  	batchNoOfClasses = 0;
      } else {
           batchNoOfClassesStr = batchNoOfClassesStr.trim();
           if (batchNoOfClassesStr.contains(".")) {
               batchNoOfClassesStr = batchNoOfClassesStr.substring(0, batchNoOfClassesStr.indexOf("."));
           }
              batchNoOfClasses = Integer.parseInt(batchNoOfClassesStr);
        }
       int programId;
       if ("InactiveProgIdBatchPost".equalsIgnoreCase(scenario.trim())) {
                    // Only for "Inactive progId" scenario, use Excel programId
                    String programIdStr = currentRow.get("programId");
                    if (programIdStr == null || programIdStr.isEmpty()) {
                    	programId = 0;
                    //    throw new IllegalArgumentException("programId is missing in Excel for 'Inactive progId' scenario");
                    }
                    programIdStr = programIdStr.trim();
                    if (programIdStr.contains(".")) {
                        programIdStr = programIdStr.substring(0, programIdStr.indexOf("."));
                    }
                    programId = Integer.parseInt(programIdStr);
                    LOGGER.info("Using Excel programId for 'Inactive progId' scenario: {}", programId);
                } else {
                    // For ALL OTHER scenarios (including "Valid details"), use Commons programId
                    programId = Commons.getProgramId();
                    LOGGER.info("Using Commons programId for scenario '{}': {}", scenario.trim(), programId);
           }
         ProgramBatchPojo batch = new ProgramBatchPojo(
            batchDescription,
            batchName,
            batchNoOfClasses,
            batchStatus,
            programId
        );
         
        LOGGER.info("Created batch from Excel: {}", batchName);
        Map<String, Object> batchDetails = new HashMap<>();
        batchDetails.put("batch", batch);
        batchDetails.put("currentRow", currentRow);
        return batchDetails;
       }

}
