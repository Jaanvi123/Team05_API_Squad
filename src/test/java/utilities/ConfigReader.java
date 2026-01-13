package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	
	private Properties properties; 

	public ConfigReader() {
        try {
            FileInputStream configFileReader = new FileInputStream("src/test/resources/config.properties");
            properties = new Properties();
            try {
                properties.load(configFileReader); // Loads properties from the stream
               // configFileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("config.properties not found at " + "src/test/resources/Config/Config.properties");
        }
    }

    // Method to return the loaded properties object, as seen in the original image
    public Properties loadProperties() {
        return properties;
    }
    
 // Example method to get a specific property with error handling
    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value != null) {
            return value;
        } else {
            throw new RuntimeException(key + " not specified in the configuration.properties file.");
        }
    }
}
