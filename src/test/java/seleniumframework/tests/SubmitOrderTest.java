package seleniumframework.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;

import seleniumframework.TestComponents.BaseTest;
import seleniumframework.pageobjects.CartPage;
import seleniumframework.pageobjects.CheckoutPage;
import seleniumframework.pageobjects.ConfirmationPage;
import seleniumframework.pageobjects.ProductCatalogue;

/**
 * A test class for submitting orders in the e-commerce application.
 */
public class SubmitOrderTest extends BaseTest {
    private static final String CONFIRMATION_MESSAGE = "THANKYOU FOR THE ORDER.";
    
    @Test(dataProvider = "getData", groups = {"Purchase"})
    public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException {
        ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
        productCatalogue.addProductToCart(input.get("product"));
        
        CartPage cartPage = productCatalogue.goToCartPage();
        Thread.sleep(3000); // Consider using explicit waits instead of Thread.sleep()
        
        Assert.assertTrue(cartPage.verifyProductDisplay(input.get("product")), "Product not displayed in cart.");
        
        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.selectCountry("india");
        
        ConfirmationPage confirmationPage = checkoutPage.submitOrder();
        String confirmMessage = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase(CONFIRMATION_MESSAGE), "Confirmation message does not match.");
    }

    /**
     * Data provider for test cases providing email, password, and product details.
     * 
     * @return an array of objects containing test data
     * @throws IOException if an error occurs while reading data
     */
    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//seleniumframework//data//PurchaseOrder.json");
        return new Object[][] {{data.get(0)}, {data.get(1)}};
    }
    
    // Uncomment if Order History Test is needed
    /*
    @Test(dependsOnMethods = {"submitOrder"})
    public void OrderHistoryTest() {
        ProductCatalogue productCatalogue = landingPage.loginApplication("anshika@gmail.com", "Iamking@000");
        OrderPage ordersPage = productCatalogue.goToOrdersPage();
        Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName), "Order not found in history.");
    }
    */
}
