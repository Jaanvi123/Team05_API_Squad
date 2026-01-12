package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProgramBatchPojo {
	
	@JsonProperty("batchDescription")
    private String batchDescription;
    @JsonProperty("batchName")
    private String batchName;
    @JsonProperty("batchNoOfClasses")
    private String batchNoOfClasses;
    @JsonProperty("batchStatus")
    private String batchStatus;
    @JsonProperty("programId")
    private String programId;
    @JsonProperty("programName")
    private String programName;

    public ProgramBatchPojo(String batchName, String batchStatus) {
    }

}
