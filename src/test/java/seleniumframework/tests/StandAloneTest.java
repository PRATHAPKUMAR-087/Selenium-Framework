package seleniumframework.tests;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * A standalone test class for adding a product to the cart and verifying the order.
 */
public class StandAloneTest {
    
    private static final String PRODUCT_NAME = "ADIDAS ORIGINAL";
    private static final String URL = "https://rahulshettyacademy.com/client";
    private static final String USER_EMAIL = "anshika@gmail.com";
    private static final String USER_PASSWORD = "Iamking@000";

    public static void main(String[] args) {
        WebDriver driver = initializeWebDriver();
        try {
            loginToApplication(driver);
            addProductToCart(driver);
            verifyProductInCart(driver);
            placeOrder(driver);
        } finally {
            driver.quit();
        }
    }

    /**
     * Initializes the WebDriver with Chrome options.
     * 
     * @return a WebDriver instance
     */
    private static WebDriver initializeWebDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }

    /**
     * Logs into the application.
     * 
     * @param driver the WebDriver instance
     */
    private static void loginToApplication(WebDriver driver) {
        driver.get(URL);
        driver.findElement(By.id("userEmail")).sendKeys(USER_EMAIL);
        driver.findElement(By.id("userPassword")).sendKeys(USER_PASSWORD);
        driver.findElement(By.id("login")).click();
        
        // Wait for the products to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
    }

    /**
     * Adds a specified product to the cart.
     * 
     * @param driver the WebDriver instance
     */
    private static void addProductToCart(WebDriver driver) {
        List<WebElement> products = driver.findElements(By.xpath("//div[contains(@class, 'mb-3')]"));
        
        WebElement product = products.stream()
            .filter(p -> p.findElement(By.xpath(".//div[@class='card-body']//b")).getText().equals(PRODUCT_NAME))
            .findFirst()
            .orElse(null);
        
        if (product != null) {
            product.findElement(By.xpath(".//div[@class='card-body']/button[2]")).click();
        }

        // Wait for the toast notification to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
    }

    /**
     * Verifies that the added product is present in the cart.
     * 
     * @param driver the WebDriver instance
     */
    private static void verifyProductInCart(WebDriver driver) {
        List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
        boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(PRODUCT_NAME));
        Assert.assertTrue(match, "Product not found in cart.");
    }

    /**
     * Places the order for the items in the cart.
     * 
     * @param driver the WebDriver instance
     */
    private static void placeOrder(WebDriver driver) {
        driver.findElement(By.cssSelector(".totalRow button")).click();

        Actions actions = new Actions(driver);
        actions.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));

        driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
        driver.findElement(By.cssSelector(".action__submit")).click();

        String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."), "Order confirmation message does not match.");
    }
}
