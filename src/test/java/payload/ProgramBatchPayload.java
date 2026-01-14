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
import utilities.CommonUtils;
import payload.ProgramBatchPayload;
import pojo.ProgramBatchDescriptionStoragePojo;
import pojo.ProgramBatchPojo;

public class ProgramBatchPayload {
	
	 public List<Map<String, String>> excelData;
	    private Map<String, String> currentRow;
	    private final Logger LOGGER = LogManager.getLogger(ProgramBatchPayload.class);
	    String sheetName="Batch";
	    private static final String FILE_PATH = "src/test/resources/batchDescriptionStorage.json";

//	    public Map<String, Object> getDataFromExcel(String scenario)
//	            throws IOException, ParseException, InvalidFormatException {
//	        currentRow = CommonUtils.getCurrentRow(scenario,sheetName);
//	        Map<String, Object> batchDetails = new HashMap<String, Object>();
//	        ProgramBatchPojo batch = new ProgramBatchPojo(currentRow.get("batchName"),currentRow.get("batchStatus"));
//	        ProgramBatchDescriptionStoragePojo batchDescriptionStoragePojo = getBatchDetailsFromStorage(currentRow.get("batchName"));
//	        batch.setBatchDescription(batchDescriptionStoragePojo.getBatchDescription());
//	        batch.setBatchNoOfClasses(batchDescriptionStoragePojo.getBatchNoOfClasses());
//	        LOGGER.info("Read batch details from Excel file: " + batch);
//	        batchDetails.put("batch", batch);
//	        batchDetails.put("currentRow", currentRow);
//	        return batchDetails;
//	    }
	    
	    
	        public Map<String, Object> getDataFromExcel(String scenario) 
	                throws IOException, ParseException, InvalidFormatException {
	            
	            // Get current row from Excel
	            Map<String, String> currentRow = CommonUtils.getCurrentRow(scenario, sheetName);
	            
	            if (currentRow == null || currentRow.isEmpty()) {
	                throw new IllegalArgumentException("No data found in Excel for scenario: " + scenario);
	            }
	            
	         // === MODIFIED: Auto-generate batch name for ALL scenarios EXCEPT "Existing batch Name" ===
	            // === SIMPLE: Auto-generate batch name for ALL scenarios EXCEPT "Existing batch Name" ===
	            String batchName;
	            
	            // Check if this is the "Existing batch Name" scenario
	          if ("Existing batch Name".equalsIgnoreCase(scenario.trim()) ||"MissingBatchName".equalsIgnoreCase(scenario.trim())||"InvalidBatchNameFormat".equalsIgnoreCase(scenario.trim())||"SequenceMoreThan99".equalsIgnoreCase(scenario.trim())) {
	                // For "Existing batch Name" scenario, use Excel value
	                batchName = currentRow.get("batchName");
	                if (batchName == null || batchName.isEmpty()) {
	                    throw new IllegalArgumentException("Batch name is missing in Excel for 'Existing batch Name' scenario");
	                }
	                LOGGER.info("Using existing batch name from Excel: {}", batchName);
	            } else {
	                // For ALL OTHER scenarios, generate "APITeam5Batch" + 5 random numbers
	                Random random = new Random();
	                // Generate 5 random digits (00000 to 99999)
	                String randomDigits = String.format("%05d", random.nextInt(100000));
	                batchName = "APITeam5Batch" + randomDigits;
	                
	                System.out.println("✅ Batch name generated is: " + batchName);
	                LOGGER.info("Auto-generated batch name for scenario '{}': {}", scenario, batchName);
	            }
	            
	            String batchStatus = currentRow.get("batchStatus");
	            if (batchStatus == null || batchStatus.isEmpty()) {
	                batchStatus = "Active"; // Default value
	                LOGGER.info("Using default batchStatus: 'Active' for batch: {}", batchName);
	            }
	            
	            // Extract batchDescription
	            String batchDescription = currentRow.get("batchDescription");
	            if (batchDescription == null || batchDescription.isEmpty()) {
	                batchDescription = "Batch for " + batchName; // Default description
	                LOGGER.info("Using default description for batch: {}", batchName);
	            }
	            
	            int batchNoOfClasses;
	            String batchNoOfClassesStr = currentRow.get("batchNoOfClasses");
	            if (batchNoOfClassesStr == null || batchNoOfClassesStr.isEmpty()) {
	                batchNoOfClasses = 10; // Default value
	                LOGGER.info("Using default batchNoOfClasses: 10 for batch: {}", batchName);
	            } else {
	                try {
	                    // FIX: Handle decimal values
	                    batchNoOfClassesStr = batchNoOfClassesStr.trim();
	                    if (batchNoOfClassesStr.contains(".")) {
	                        batchNoOfClassesStr = batchNoOfClassesStr.substring(0, batchNoOfClassesStr.indexOf("."));
	                    }
	                    
	                    batchNoOfClasses = Integer.parseInt(batchNoOfClassesStr);
//	                    if (batchNoOfClasses <= 0) {
//	                        LOGGER.warn("batchNoOfClasses should be positive. Using default 10.");
//	                        batchNoOfClasses = 10;
//	                    }
	                } catch (NumberFormatException e) {
	                    LOGGER.warn("Invalid batchNoOfClasses value '{}'. Using default 10.", batchNoOfClassesStr);
	                    batchNoOfClasses = 10;
	                }
	            }
	            
	            
	             // Check batchNoOfClasses
	       //   String batchNoOfClassesStr = currentRow.get("batchNoOfClasses");
	            System.out.println("batchNoOfClasses from currentRow: '" + batchNoOfClassesStr + "'");
	            System.out.println("==================================\n");
	            
	            
	            int programId;
	            
	            // Check if scenario is "Valid details"
	            if ("Valid details".equalsIgnoreCase(scenario.trim())) {
	                // Use Commons.getProgramId() for "Valid details" scenario
	                programId = Commons.getProgramId();
	                LOGGER.info("Using programId from Commons class for 'Valid details' scenario: {}", programId);
	            } else {
	                // For other scenarios, read from Excel
	                String programIdStr = currentRow.get("programId");
	                if (programIdStr == null || programIdStr.isEmpty()) {
	                    programId = 0; // Default value
	                    LOGGER.info("Using default programId: 0 for batch: {}", batchName);
	                } else {
	                    try {
	                        // Handle decimal values
	                        programIdStr = programIdStr.trim();
	                        if (programIdStr.contains(".")) {
	                            programIdStr = programIdStr.substring(0, programIdStr.indexOf("."));
	                        }
	                        programId = Integer.parseInt(programIdStr);
	                    } catch (NumberFormatException e) {
	                        LOGGER.warn("Invalid programId value '{}'. Using default 0.", programIdStr);
	                        programId = 0;
	                    }
	                }
	            }
	            
	            // Create ProgramBatchPojo object
	            ProgramBatchPojo batch = new ProgramBatchPojo(
	                batchDescription, 
	                batchName, 
	                batchNoOfClasses, 
	                batchStatus, 
	                programId
	            );
	            
	            LOGGER.info("Created batch from Excel: {}", batchName);
	            
	            // Prepare return map
	            Map<String, Object> batchDetails = new HashMap<>();
	            batchDetails.put("batch", batch);
	            batchDetails.put("currentRow", currentRow);
	            
	            // For debugging
	            System.out.println("Batch details extracted from Excel:");
	            System.out.println("Scenario name is: "+scenario);
	            System.out.println("  Name: " + batchName);
	            System.out.println("  Status: " + batchStatus);
	            System.out.println("  Description: " + batchDescription);
	            System.out.println("  No of Classes: " + batchNoOfClasses);
	            System.out.println("  Program ID: " + programId);
	            
	            return batchDetails;
	        }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
//	    public Map<String, Object> getDataFromExcel(String scenario)  throws IOException, ParseException, InvalidFormatException {
//	        currentRow = CommonUtils.getCurrentRow(scenario, sheetName);
//	        
//	        String batchName = currentRow.get("batchName");
//	        if (batchName == null || batchName.isEmpty()) {
//	            throw new IllegalArgumentException("Batch name is missing in Excel");
//	        }
//	        
//	        ProgramBatchPojo batch = new ProgramBatchPojo(batchName,currentRow.get("batchStatus"));	        
//	        ProgramBatchDescriptionStoragePojo storageData = getBatchDetailsFromStorage(batchName);
//	        
//	        if (storageData != null) {
//	            batch.setBatchDescription(storageData.getBatchDescription());
//	            batch.setBatchNoOfClasses(storageData.getBatchNoOfClasses());
//	        } else {
//	            LOGGER.warn("No batch description found in JSON for: {}", batchName);
//	            // Use defaults or throw exception based on requirements
//	            batch.setBatchDescription("Default Description");
//	            batch.setBatchNoOfClasses("10");
//	        }
//	        
//	      //  LOGGER.info("Batch details for {}: {}", batchName, batch);
//	        
//	        Map<String, Object> batchDetails = new HashMap<>();
//	        batchDetails.put("batch", batch);
//	        batchDetails.put("currentRow", currentRow);
//	        System.out.println("Batch details for {}: {}"+ batchDetails);
//		      
//	        return batchDetails;
//	    }
//
//	    public static ProgramBatchDescriptionStoragePojo getBatchDetailsFromStorage(String batchName) throws IOException {
//	        ObjectMapper mapper = new ObjectMapper();
//	        List<ProgramBatchDescriptionStoragePojo> data = mapper.readValue(new File(FILE_PATH), new TypeReference <List<ProgramBatchDescriptionStoragePojo>>(){});
//	        return data.stream()
//	                .filter(batch -> batch.getBatchName().equalsIgnoreCase(batchName))
//	                .findFirst()
//	                
//	                
//	                .orElse(null);
//	    }

}
