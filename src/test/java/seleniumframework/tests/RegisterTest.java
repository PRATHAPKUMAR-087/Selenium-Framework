package seleniumframework.tests;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Class to perform user registration on the e-commerce website.
 */
public class RegisterTest {

    static WebDriver driver;

    public static void main(String[] args) {
        // Set up and initialize the ChromeDriver
        driver = new ChromeDriver();
        
        // Open the application
        driver.get("https://rahulshettyacademy.com/client");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        // Click on the Register link
        driver.findElement(By.linkText("Register here")).click();
        
        // Fill in registration details
        registerUser("ramesh", "korla", "ramesh.korla@aol.com", "9392976665", "Test#123");

        // Scroll to the login button and click
        scrollToElementAndClick(By.id("login"));
    }

    /**
     * Method to register a user with the provided details.
     */
    private static void registerUser(String firstName, String lastName, String email, String mobile, String password) {
        driver.findElement(By.id("firstName")).sendKeys(firstName);
        driver.findElement(By.id("lastName")).sendKeys(lastName);
        driver.findElement(By.id("userEmail")).sendKeys(email);
        driver.findElement(By.id("userMobile")).sendKeys(mobile);
        driver.findElement(By.id("userPassword")).sendKeys(password);
        driver.findElement(By.id("confirmPassword")).sendKeys(password);
    }

    /**
     * Scrolls to the specified element and clicks it.
     */
    private static void scrollToElementAndClick(By locator) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(locator));
        driver.findElement(locator).click();
    }
}
