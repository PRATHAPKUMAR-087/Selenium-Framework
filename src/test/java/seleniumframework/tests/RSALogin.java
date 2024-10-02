package seleniumframework.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Class to perform login actions on the Rahul Shetty Academy website.
 */
public class RSALogin {

    public static void main(String[] args) throws InterruptedException {
        // Initialize the ChromeDriver
        WebDriver driver = new ChromeDriver();
        
        // Open the application and maximize the window
        driver.get("https://rahulshettyacademy.com/");
        driver.manage().window().maximize();
        
        // Wait for the page to load completely
        Thread.sleep(10000);
        
        // Click on the Login button
        clickLoginButton(driver);
    }

    /**
     * Clicks the login button on the webpage.
     *
     * @param driver the WebDriver instance used to interact with the webpage
     */
    private static void clickLoginButton(WebDriver driver) {
        driver.findElement(By.xpath("//a[@class='theme-btn register-btn']")).click();
    }
}
