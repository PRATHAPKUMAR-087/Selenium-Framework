package seleniumframework.tests;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Class to add multiple products to the cart on the e-commerce website.
 */
public class MultipleProductsToCart {

    public static void main(String[] args) {
        // Set up ChromeDriver with options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(options);
        
        // Configure implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        // Open the application
        driver.get("https://rahulshettyacademy.com/client");

        // Login to the application
        driver.findElement(By.id("userEmail")).sendKeys("vigupta.kws@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("P@ssw0rd@94");
        driver.findElement(By.id("login")).click();

        // Wait until the products are visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

        // List of target products to add to the cart
        List<String> targetProducts = Arrays.asList("ZARA COAT 3", "ADIDAS ORIGINAL", "IPHONE 13 PRO");

        // Find all available products on the page
        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

        // Loop through the target products and add them to the cart
        targetProducts.forEach(targetProduct -> {
            WebElement product = products.stream()
                    .filter(p -> p.findElement(By.cssSelector("b")).getText().equals(targetProduct))
                    .findFirst().orElse(null);

            if (product != null) {
                // Click the "Add to Cart" button
                product.findElement(By.cssSelector(".card-body button:last-of-type")).click();

                // Wait for the animation to finish and the spinner to disappear
                wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("div[class*='ng-animating']"))));
                wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector("div[class*='ngx-spinner-overlay']"))));
            }
        });

        // Navigate to the cart page
        driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();
        
        // Close the browser (optional)
        // driver.quit();
    }
}
