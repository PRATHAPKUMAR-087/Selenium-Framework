package seleniumframework.AbstractComponents;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import seleniumframework.pageobjects.CartPage;
import seleniumframework.pageobjects.OrderPage;

public class AbstractComponent {

  // WebDriver instance to interact with the browser
  private final WebDriver driver;

  // Constructor to initialize WebDriver and PageFactory elements
  public AbstractComponent(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  // Locators for cart and order page headers
  @FindBy(css = "[routerlink*='cart']")
  private WebElement cartHeader;

  @FindBy(css = "[routerlink*='myorders']")
  private WebElement orderHeader;

  // Wait until the specified element is visible on the page
  public void waitForElementToAppear(By findBy) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
  }

  // Wait until the specified web element is visible on the page
  public void waitForWebElementToAppear(WebElement findBy) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    wait.until(ExpectedConditions.visibilityOf(findBy));
  }

  // Wait for the presence of a web element using a locator
  public void presenceOfWebElementToAppear(By locator) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    wait.until(ExpectedConditions.presenceOfElementLocated(locator));
  }

  // Navigate to the cart page
  public CartPage goToCartPage() {
    cartHeader.click();
    return new CartPage(driver);
  }

  // Navigate to the orders page
  public OrderPage goToOrdersPage() {
    orderHeader.click();
    return new OrderPage(driver);
  }

  // Wait for the element to disappear (using a simple sleep for now)
  public void waitForElementToDisappear(WebElement ele) throws InterruptedException {
    Thread.sleep(2000);  // This can be replaced with a proper wait if needed in the future
  }

  // Wait for the element to become stale (no longer attached to the DOM)
  public void waitForStaleness(WebElement element) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    wait.until(ExpectedConditions.stalenessOf(element));
  }
}
