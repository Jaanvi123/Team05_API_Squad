package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProgramPojo {


		@JsonProperty("programDescription")
		private String programDescription;
		@JsonProperty("programName")
		private String programName;
		@JsonProperty("programStatus")
		private String programStatus;

		public ProgramPojo(String programName, String programDescription, String programStatus) {
			this.programName = programName;
			if(programDescription!=null) {
				this.programDescription = programDescription;
			}
			this.programStatus = programStatus;
		}

		// getter Methods 
		public String getProgramDescription() {
			return programDescription;
		}

		public String getProgramName() {
			return programName;
		}

		public String getProgramStatus() {
			return programStatus;
		}

		// Setter Methods 
		public void setProgramDescription( String programDescription ) {
			this.programDescription = programDescription;
		}

		public void setProgramName( String programName ) {
			this.programName = programName;
		}

		public void setProgramStatus( String programStatus ) {
			this.programStatus = programStatus;
		}

}
