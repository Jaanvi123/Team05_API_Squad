package payload;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	    public Map<String, Object> getDataFromExcel(String scenario)
	            throws IOException, ParseException, InvalidFormatException {
	        currentRow = CommonUtils.getCurrentRow(scenario,sheetName);
	        Map<String, Object> batchDetails = new HashMap<String, Object>();
	        ProgramBatchPojo batch = new ProgramBatchPojo(currentRow.get("batchName"),currentRow.get("batchStatus"));
	        BatchDescriptionStoragePojo batchDescriptionStoragePojo = getBatchDetailsFromStorage(currentRow.get("batchName"));
	        batch.setBatchDescription(batchDescriptionStoragePojo.getBatchDescription());
	        batch.setBatchNoOfClasses(batchDescriptionStoragePojo.getBatchNoOfClasses());
	        LOGGER.info("Read batch details from Excel file: " + batch);
	        batchDetails.put("batch", batch);
	        batchDetails.put("currentRow", currentRow);
	        return batchDetails;
	    }

	    public static BatchDescriptionStoragePojo getBatchDetailsFromStorage(String batchName) throws IOException {
	        ObjectMapper mapper = new ObjectMapper();
	        List<BatchDescriptionStoragePojo> data = mapper.readValue(new File(FILE_PATH), new TypeReference <List<BatchDescriptionStoragePojo>>(){});
	        return data.stream()
	                .filter(batch -> batch.getBatchName().equalsIgnoreCase(batchName))
	                .findFirst()
	                
	                
	                .orElse(null);
	    }

}
