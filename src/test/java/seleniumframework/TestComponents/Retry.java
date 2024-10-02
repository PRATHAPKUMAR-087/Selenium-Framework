package seleniumframework.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Retry class that implements IRetryAnalyzer to allow re-execution
 * of failed tests.
 */
public class Retry implements IRetryAnalyzer {

    private int count = 0;
    private final int maxTry = 1;

    @Override
    public boolean retry(ITestResult result) {
        // Check if the current attempt count is less than the maximum allowed tries
        if (count < maxTry) {
            count++;
            return true; // Indicate to retry the test
        }
        return false; // No more retries allowed
    }
}
