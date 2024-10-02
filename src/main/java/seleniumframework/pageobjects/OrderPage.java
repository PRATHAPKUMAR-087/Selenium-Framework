package seleniumframework.pageobjects;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import seleniumframework.AbstractComponents.AbstractComponent;

public class OrderPage extends AbstractComponent {

  private final WebDriver driver;

  @FindBy(css = ".totalRow button")
  private WebElement checkoutButton;

  @FindBy(css = "tr td:nth-child(3)")
  private List<WebElement> productNames;

  // Constructor to initialize the WebDriver and PageFactory elements
  public OrderPage(WebDriver driver) {
    super(driver);
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  // Method to verify if a specific product name is displayed in the order
  public boolean verifyOrderDisplay(String productName) {
    return productNames.stream()
        .anyMatch(product -> product.getText().equalsIgnoreCase(productName));
  }
}
