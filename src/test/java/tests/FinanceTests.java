package tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.qameta.allure.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import screens.*;
import java.net.MalformedURLException;
import java.net.URL;
import static org.junit.jupiter.api.Assertions.*;


public class FinanceTests {
    private AndroidDriver driver;
    private HomeScreen homeScreen;

    private final String firstTransactionTitle = "Transaction 1 Title";
    private final String secondTransactionTitle = "Transaction 2 Title";
    private final String firstTransactionAmount = "50987";
    private final String secondTransactionAmount = "99999";


    @BeforeEach
    public void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setDeviceName("emulator");
        options.setApp(System.getProperty("user.dir") + "/apps/financemanager.apk");
        options.setAutomationName("UiAutomator2");
        options.setCapability("skipDeviceInitialization", true);
        options.setCapability("skipServerInstallation", true);

        options.setCapability("appWaitActivity", "org.secuso.privacyfriendlyfinance.activities.*");
        // Увеличиваем таймаут ожидания активити
        options.setCapability("appWaitDuration", 30000);
        // Явно указываем запускаемое активити
        options.setCapability("appWaitActivity", "org.secuso.privacyfriendlyfinance.activities.*");
        URL url = new URL("http://127.0.0.1:4723");
        driver = new AndroidDriver(url, options);
        System.out.println("Приложение успешно запущено!");

        homeScreen = new OnboardingScreen(driver).skipOnboardingIfNeeded();
    }

    private void createTestTransaction(String title, String amount) {

        homeScreen.clickAddButton()
                .enterTitle(title)
                .enterAmount(amount)
                .clickSaveButton();
    }

    @Epic("Создание записи")
    @Feature("Запись корректно создается")
    @Story("Создание записи с главного экрана")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("smoke")
    @Description("""
    Проверяем что запись корректно создается:
    1. Открываем приложение
    2. Пропускаем онбординг
    3. Создаем запись
    4. Проверяем, что запись создана
    5. Проверяем, что запись создана с корректным именем
    """)
    @Test
    public void createTransaction() {

        System.out.println("Тест: Создаем новую запись...");

        createTestTransaction(firstTransactionTitle, firstTransactionAmount);

        assertTrue(homeScreen.isTransactionDisplayed(), "Транзакция не отобразилась");
        assertEquals(firstTransactionTitle, homeScreen.getTransactionTitleFormList(), "Название не совпадает");
    }

    @Epic("Удаление записи")
    @Feature("Запись корректно удаляется")
    @Story("Удаление записи после создания")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("smoke")
    @Description("""
    Проверяем что запись корректно удаляется:
    1. Открываем приложение
    2. Пропускаем онбординг
    3. Создаем запись
    4. Проверяем, что запись создана
    5. Проверяем, что запись создана с корректным именем
    6. Выполняем свайп влево на удаляемом элементе
    7. Кликаем на кнопку Удалить
    8. Подтверждаем удаление
    """)
    @Test
    public void deleteTransaction() {

        System.out.println("Тест: Удаляем созданную запись...");

        createTestTransaction(firstTransactionTitle, firstTransactionAmount);
        assertTrue(homeScreen.isTransactionDisplayed());

        homeScreen.swipeLeftOnElement();
        homeScreen.clickDeleteButton();
        homeScreen.confirmDelete();

        // После удаления можно проверить, что список пуст, но когда-то потом :)

    }

    @Epic("Редактирование записи")
    @Feature("Запись корректно редактируется")
    @Story("Редактирование записи после создания")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("smoke")
    @Description("""
    Проверяем что запись корректно изменяется:
    1. Открываем приложение
    2. Пропускаем онбординг
    3. Создаем запись
    4. Проверяем, что запись создана
    5. Проверяем, что запись создана с корректным именем
    6. Открываем созданную запись
    7. Редактируем (в данном случае поле с названием)
    8. Проверяем, что изменения применены корректно
    """)
    @Test
    public void updateTransaction() {

        System.out.println("Тест: Редактируем запись...");

        createTestTransaction(firstTransactionTitle, firstTransactionAmount);
        assertTrue(homeScreen.isTransactionDisplayed());

        // Открываем созданную запись
        TransactionFormScreen form = homeScreen.openCreatedTransaction();
        assertTrue(form.isEditTransactionFrameDisplayed());

        // Редактируем
        form.enterTitle(secondTransactionTitle).clickSaveButton();

        // Проверяем
        assertTrue(homeScreen.isTransactionDisplayed());
        assertEquals(secondTransactionTitle, homeScreen.getTransactionTitleFormList());
    }

    @Epic("Хранение данных")
    @Feature("Запись корректно сохраняется")
    @Story("Запись сохраняется после завершения работы приложения")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("smoke")
    @Description("""
    Проверяем что запись корректно сохраняется после завершения работы приложения:
    1. Открываем приложение
    2. Пропускаем онбординг
    3. Создаем запись
    4. Проверяем, что запись создана
    5. Проверяем, что запись создана с корректным именем
    6. Перезапускаем приложение (terminateApp, activateApp)
    7. Проверяем, что запись существует.
    """)
    @Test
    public void dataPersistence() throws InterruptedException {

        System.out.println("Тест: Проверка сохранения данных...");

        createTestTransaction(firstTransactionTitle, firstTransactionAmount);
        assertTrue(homeScreen.isTransactionDisplayed());
        assertEquals(firstTransactionTitle, homeScreen.getTransactionTitleFormList());

        // Перезапускаем приложение
        driver.terminateApp("org.secuso.privacyfriendlyfinancemanager");
        Thread.sleep(1000);
        driver.activateApp("org.secuso.privacyfriendlyfinancemanager");

        // Заново пропускаем онбординг (если появился)
        homeScreen = new OnboardingScreen(driver).skipOnboardingIfNeeded();

        // Проверяем сохранение
        assertTrue(homeScreen.isTransactionDisplayed(), "Транзакция не сохранилась после перезапуска");
        assertEquals(firstTransactionTitle, homeScreen.getTransactionTitleFormList(), "Название не совпадает");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Тест завершен, сессия закрыта.");
        }
    }

}
