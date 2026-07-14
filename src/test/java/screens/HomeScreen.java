package screens;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;


public class HomeScreen extends BaseScreen {

    final By addButton = By.xpath("//android.widget.ImageButton[@resource-id=" +
            "\"org.secuso.privacyfriendlyfinancemanager:id/fab_add\"]");

    final By constraintLayout = By.xpath("//android.view" +
            ".ViewGroup[@resource-id=\"org.secuso.privacyfriendlyfinancemanager:id/constraintLayout\"]");

    final By transactionTitle = By.xpath("//android.widget." +
            "TextView[@resource-id=\"org.secuso.privacyfriendlyfinancemanager:id/textview_transaction_name\"]");

    private static final int DELETE_BUTTON_X = 1009;

    private static final int DELETE_BUTTON_Y = 490;

    public HomeScreen(AndroidDriver driver) {

        super(driver);

        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        longWait.until(ExpectedConditions.visibilityOfElementLocated(addButton));
        System.out.println("Главный экран загружен!");
    }

    @Step("Клик на кнопку 'Добавить'")
    public TransactionFormScreen clickAddButton() {

        click(addButton);
        return new TransactionFormScreen(driver);
    }

    @Step("Проверка, что запись отображается")
    public boolean isTransactionDisplayed() {

        return isDisplayed(constraintLayout);
    }

    @Step("Получаем имя записи")
    public String getTransactionTitleFormList() {

        return getText(transactionTitle);
    }
    @Step("Свайп влево на элементе")
    public void swipeLeftOnElement() {
        // 1. Ждем появления элемента и получаем его

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(constraintLayout));

        // 2. Получаем координаты и размеры элемента
        int elementX = element.getLocation().getX();
        int elementY = element.getLocation().getY();
        int elementWidth = element.getSize().getWidth();
        int elementHeight = element.getSize().getHeight();

        // 3. Вычисляем центр элемента (начало свайпа)
        int startX = elementX + (elementWidth / 2);
        int startY = elementY + (elementHeight / 2);

        // 4. Конечная точка: на 300 пикселей левее
        int endX = startX - 300;

        // 5. Создаем "палец" как устройство ввода
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        // 6. Строим последовательность действий
        Sequence swipeSequence = new Sequence(finger, 0);
        swipeSequence.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), startX, startY));
        swipeSequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipeSequence.addAction(finger.createPointerMove(Duration.ofMillis(500),
                PointerInput.Origin.viewport(), endX, startY));
        swipeSequence.addAction(new Pause(finger, Duration.ofMillis(200)));
        swipeSequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // 7. Выполняем жест
        driver.perform(Arrays.asList(swipeSequence));
    }

    @Step("Клик на кнопку 'Удалить'")
    public void clickDeleteButton() {

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), DELETE_BUTTON_X, DELETE_BUTTON_Y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(new Pause(finger, Duration.ofMillis(100)));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(tap));

    }
    @Step("Клик на кнопку 'Подтвердить удаление'")
    public void confirmDelete() {

        By deleteConfirm = By.xpath("//android.widget.Button[@resource-id='android:id/button1']");
        click(deleteConfirm);

        System.out.println("Элемент удален!");
    }
    @Step("Открываем созданную запись")
    public TransactionFormScreen openCreatedTransaction() {

        click(constraintLayout);
        return new TransactionFormScreen(driver);
    }




}
