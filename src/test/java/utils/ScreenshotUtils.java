package utils;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;

public class ScreenshotUtils {

    private static AndroidDriver driver;

    public static void setDriver(AndroidDriver driverInstance) {
        driver = driverInstance;
    }

    public static void attachPageScreenshot(String name) {
        if (driver == null) {
            System.out.println("⚠️ Драйвер не инициализирован, скриншот не создан");
            return;
        }

        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), ".png");
            System.out.println("✅ Скриншот '" + name + "' добавлен в Allure");
        } catch (Exception e) {
            System.out.println("⚠️ Скриншот '" + name + "' не создан: " + e.getClass().getSimpleName());
        }
    }
}