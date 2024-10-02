package seleniumframework.tests;

import java.util.Arrays;
import java.util.List;
import org.testng.annotations.Test;
import seleniumframework.TestComponents.BaseTest;

/**
 * Test class to search for products on the landing page.
 */
public class SearchProductTest extends BaseTest {

    /**
     * Tests the product search functionality.
     *
     * @throws InterruptedException if the thread is interrupted while sleeping
     */
    @Test
    public void searchProduct() throws InterruptedException {
        // Wait for the landing page to load completely
        Thread.sleep(5000);
        
        List<String> productList = Arrays.asList("ZARA COAT 3", "ADIDAS ORIGINAL", "IPHONE 13 PRO");
        for (String product : productList) {
            System.out.println("Searching for product: " + product);
            landingPage.searchProduct(product);
            landingPage.clickHomeLink();
        }
    }
}
