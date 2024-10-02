package seleniumframework.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import seleniumframework.pageobjects.LandingPage;

public class BaseTest {

    protected WebDriver driver;
    protected LandingPage landingPage;

    // Method to initialize the WebDriver based on the specified browser
    public WebDriver initializeDriver() throws IOException {
        Properties prop = new Properties();

        try (FileInputStream fis = new FileInputStream(
                Paths.get(System.getProperty("user.dir"), 
                          "src", 
                          "main", 
                          "java", 
                          "seleniumframework", 
                          "resources", 
                          "GlobalData.properties").toString())) {
            prop.load(fis);
        }

        String browserName = System.getProperty("browser") != null 
                ? System.getProperty("browser") 
                : prop.getProperty("browser");

        if (browserName.contains("chrome")) {
            ChromeOptions options = new ChromeOptions();
            WebDriverManager.chromedriver().setup();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            options.addArguments("--remote-allow-origins=*"); // Disable notifications
            
            if (browserName.contains("headless")) {
                options.addArguments("--headless");
            }
            driver = new ChromeDriver(options);

        } else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();

        } else if (browserName.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--start-maximized");
            driver = new EdgeDriver(options);
        } else {
            throw new IllegalArgumentException("Browser not supported: " + browserName);
        }

        return driver;
    }

    // Method to read JSON data from a file and convert it to a list of HashMaps
    public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
    }

    // Method to take a screenshot and save it to a specified path
    public String getScreenshot(String testCaseName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String reportPath = Paths.get(System.getProperty("user.dir"), "reports", testCaseName + ".png").toString();
        File file = new File(reportPath);
        FileUtils.copyFile(source, file);
        return file.getAbsolutePath();
    }

    @BeforeMethod(alwaysRun = true)
    public LandingPage launchApplication() throws IOException {
        driver = initializeDriver();
        if (driver != null) {
            landingPage = new LandingPage(driver);
            landingPage.goTo();
            return landingPage;
        }
        throw new RuntimeException("Driver initialization failed"); // Improved error handling
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) { // Ensure driver is not null before quitting
            driver.quit();
        }
    }
}
