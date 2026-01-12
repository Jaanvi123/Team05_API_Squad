package hooks;

import io.cucumber.java.Before; 
import io.cucumber.java.After; 



public class Hooks {

	@Before public void setup() 
	{ 		
	System.out.println("----- Test Execution Started -----");
		}
	
	
	@After public void tearDown() 
	{ 
		System.out.println("----- Test Execution Completed -----"); 
	} 
	
}