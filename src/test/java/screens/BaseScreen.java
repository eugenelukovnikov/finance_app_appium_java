package screens;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


public class BaseScreen {

    protected final AndroidDriver driver;
    protected final WebDriverWait wait;

    public BaseScreen(AndroidDriver driver){

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void click(By locator) {

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected String getText(By locator){

        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    protected void sendKeys(By locator, String text) {

        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
        element.sendKeys(text);
    }

    protected boolean isDisplayed(By locator){
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
