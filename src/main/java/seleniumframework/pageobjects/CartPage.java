package seleniumframework.pageobjects;

import java.util.List;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumframework.AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent {

  // WebDriver instance for interacting with the browser
  private final WebDriver driver;

  // Locators for checkout button, cart products, delete buttons, and empty cart message
  @FindBy(css = ".totalRow button")
  private WebElement checkoutEle;

  @FindBy(css = ".cartSection h3")
  private List<WebElement> cartProducts;

  @FindBy(css = "button[class='btn btn-danger']")
  private List<WebElement> deleteButtons;

  @FindBy(css = ".ng-star-inserted >h1")
  private WebElement emptyCartMessage;

  // Constructor to initialize the WebDriver and page elements
  public CartPage(WebDriver driver) {
    super(driver);
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  // Verify if the product is displayed in the cart
  public Boolean verifyProductDisplay(String productName) {
    return cartProducts.stream()
        .anyMatch(product -> product.getText().equalsIgnoreCase(productName));
  }

  // Navigate to the checkout page
  public CheckoutPage goToCheckout() {
    checkoutEle.click();
    return new CheckoutPage(driver);
  }

  // Method to delete all items from the cart
  public void deleteAllItems() throws InterruptedException {
    List<WebElement> buttons = deleteButtons;
    while (!buttons.isEmpty()) {
      for (WebElement deleteButton : buttons) {
        try {
          deleteButton.click();
          waitForStaleness(deleteButton);  // Wait until the element becomes stale before proceeding
        } catch (StaleElementReferenceException e) {
          // Handle the stale element exception by printing a message
          System.out.println("Stale element reference: " + e.getMessage());
        }
      }
      buttons = deleteButtons;  // Re-fetch the delete buttons after clicking
    }
  }

  // Check if the cart is empty by looking for the empty cart message
  public boolean isCartEmpty() {
    try {
      System.out.println("Cart Message is: " + emptyCartMessage.getText());
      return emptyCartMessage.getText().equals("No Products in Your Cart !");
    } catch (org.openqa.selenium.NoSuchElementException e) {
      // If the message element is not found, assume the cart is empty
      return true;
    }
  }
}
