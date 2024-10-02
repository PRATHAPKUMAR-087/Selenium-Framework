package seleniumframework.tests;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * A test class for deleting multiple products from the cart in the e-commerce application.
 */
public class TestDeleteMultiple {

    private WebDriver driver;
    private WebDriverWait wait;

    @Test
    public void deleteProductFromCart() throws InterruptedException {
        setupDriver();

        login("anshika@gmail.com", "Iamking@000");

        // Wait until products are visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

        // Select products to add to cart
        String[] productList = {"ZARA COAT 3", "IPHONE 13 PRO", "ADIDAS ORIGINAL"};
        addProductsToCart(productList);

        // Go to cart
        driver.findElement(By.cssSelector("button[routerLink*='/cart']")).click();
        Thread.sleep(4000); // Consider using explicit waits here

        // Delete products from the cart
        deleteProductsFromCart();

        // Verify if the cart is empty
        verifyCartIsEmpty();
    }

    private void setupDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://rahulshettyacademy.com/client");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private void login(String email, String password) {
        driver.findElement(By.id("userEmail")).sendKeys(email);
        driver.findElement(By.id("userPassword")).sendKeys(password);
        driver.findElement(By.id("login")).click();
    }

    private void addProductsToCart(String[] productList) {
        List<String> givenItems = Arrays.asList(productList);
        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

        products.stream()
                .filter(s -> givenItems.contains(s.findElement(By.cssSelector("b")).getText()))
                .limit(givenItems.size())
                .forEach(s -> {
                    s.findElement(By.cssSelector(".card-body button:last-of-type")).click();
                    waitUntilToastVisible();
                });
    }

    private void waitUntilToastVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
    }

    private void deleteProductsFromCart() {
        List<WebElement> deleteButtons;

        do {
            deleteButtons = driver.findElements(By.cssSelector("button[class='btn btn-danger']"));
            for (WebElement deleteButton : deleteButtons) {
                try {
                    deleteButton.click();
                    wait.until(ExpectedConditions.stalenessOf(deleteButton)); // Wait until the button is no longer present
                } catch (StaleElementReferenceException e) {
                    System.out.println("Stale element reference: " + e.getMessage());
                }
            }
        } while (!deleteButtons.isEmpty());
    }

    private void verifyCartIsEmpty() {
        WebElement emptyCartMessage = driver.findElement(By.cssSelector(".ng-star-inserted > h1"));
        System.out.println("Cart: " + emptyCartMessage.getText());
        Assert.assertEquals(emptyCartMessage.getText(), "No Products in Your Cart !");
    }

    @AfterMethod
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}
