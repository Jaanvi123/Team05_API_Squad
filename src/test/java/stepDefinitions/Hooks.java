package stepDefinitions;

import io.cucumber.java.Before; 
import io.cucumber.java.After; 

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import io.cucumber.java.AfterAll;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
//
public class Hooks {
	
	private static PrintStream printStream;

	static {
		try {
			printStream = new PrintStream(new FileOutputStream("Team5APISquadLog.txt", false));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to initialize logging file.");
		}
	}

	// Apply filters before each scenario
	@Before
	public void beforeScenario() {
		RestAssured.filters(new RequestLoggingFilter(printStream), new ResponseLoggingFilter(printStream));
	}

	@After
	public void afterScenario() {
		printStream.println("\n--- End of Scenario ---\n");
		// Clear filters to avoid duplication in subsequent scenarios
		RestAssured.replaceFiltersWith(new RequestLoggingFilter(printStream), new ResponseLoggingFilter(printStream));
	}

	// Close the stream after all scenarios
	@AfterAll
	public static void closeStream() {
		if (printStream != null) {
			printStream.close();
		}
	}

	@Before public void setup() 
	{ 		
	System.out.println("----- Test Execution Started -----");
		}
	
	
	@After public void tearDown() 
	{ 
		System.out.println("----- Test Execution Completed -----"); 
	} 
	
}