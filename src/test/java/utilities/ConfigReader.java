package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConfigReader {
    
	private static final Logger logger =
	        LoggerFactory.getLogger(ConfigReader.class);

    private static Properties properties;
    private static final String CONFIG_FILE = "src/test/resources/config/config.properties";

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE);
            properties = new Properties();
            properties.load(fileInputStream);
            fileInputStream.close();
            logger.info("Configuration file loaded successfully from: " + CONFIG_FILE);
        } catch (IOException e) {
            logger.error("Failed to load configuration file: " + CONFIG_FILE, e);
            throw new RuntimeException("Configuration file not found: " + CONFIG_FILE);
        }
    }

    /**
     * Get property value by key
     * @param key - Property key
     * @return Property value
     */
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property key not found: " + key);
            return "";
        }
        logger.debug("Retrieved property - Key: " + key + ", Value: " + value);
        return value;
    }

    /**
     * Get property value with default value
     * @param key - Property key
     * @param defaultValue - Default value if key not found
     * @return Property value or default value
     */
    public static String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key, defaultValue);
        logger.debug("Retrieved property - Key: " + key + ", Value: " + value);
        return value;
    }

    /**
     * Get base URL
     */
    public static String getBaseUrl() {
        String baseUrl = getProperty("base_url");
        logger.info("Base URL: " + baseUrl);
        return baseUrl;
    }

    /**
     * Get implicit wait timeout
     */
    public static int getImplicitWait() {
        String implicitWait = getProperty("implicit_wait", "10");
        return Integer.parseInt(implicitWait);
    }

    /**
     * Get explicit wait timeout
     */
    public static int getExplicitWait() {
        String explicitWait = getProperty("explicit_wait", "15");
        return Integer.parseInt(explicitWait);
    }

    /**
     * Get API timeout
     */
    public static int getApiTimeout() {
        String apiTimeout = getProperty("api.timeout", "30");
        return Integer.parseInt(apiTimeout);
    }

    /**
     * Get bearer token
     */
    public static String getBearerToken() {
        return getProperty("auth.bearer.token");
    }

    /**
     * Get environment
     */
    public static String getEnvironment() {
        return getProperty("environment", "QA");
    }

    /**
     * Get log level
     */
    public static String getLogLevel() {
        return getProperty("log.level", "INFO");
    }

    /**
     * Get report path
     */
    public static String getReportPath() {
        return getProperty("report.path", "target/cucumber-reports/");
    }

    /**
     * Get API content type
     */
    public static String getContentType() {
        return getProperty("api.content_type", "application/json");
    }

    /**
     * Get API accept header
     */
    public static String getAccept() {
        return getProperty("api.accept", "application/json");
    }
}
