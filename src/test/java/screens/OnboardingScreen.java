package screens;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class OnboardingScreen extends BaseScreen {

    final By skipButton = By.xpath("//android.widget.Button[@resource-id=" +
            "\"org.secuso.privacyfriendlyfinancemanager:id/btn_skip\"]");

    public OnboardingScreen(AndroidDriver driver) {
        super(driver);
    }

    public boolean isOnboardingDisplayed() {
        
        return isDisplayed(skipButton);
    }

    @Step("Клик на кнопку 'Пропустить' на онбординге")
    public HomeScreen clickSkipButton() {

        click(skipButton);
        return new HomeScreen(driver);
    }

    @Step("Клик на кнопку 'Пропустить' на онбординге при необходимости")
    public HomeScreen skipOnboardingIfNeeded() {

        if (isOnboardingDisplayed()) {
            System.out.println("Онбординг пропущен");
            return clickSkipButton();
        } else {
            return new HomeScreen(driver);
        }
    }
}
