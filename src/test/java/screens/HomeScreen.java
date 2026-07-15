package screens;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Pause;
import java.time.Duration;
import java.util.Arrays;


public class HomeScreen extends BaseScreen {

    final By addButton = By.xpath("//android.widget.ImageButton[@resource-id=" +
            "\"org.secuso.privacyfriendlyfinancemanager:id/fab_add\"]");

    final By constraintLayout = By.xpath("//android.view" +
            ".ViewGroup[@resource-id=\"org.secuso.privacyfriendlyfinancemanager:id/constraintLayout\"]");

    final By transactionTitle = By.xpath("//android.widget." +
            "TextView[@resource-id=\"org.secuso.privacyfriendlyfinancemanager:id/textview_transaction_name\"]");

    final By deleteConfirm = By.xpath("//android.widget.Button[@resource-id='android:id/button1']");

    private static final int DELETE_BUTTON_X = 1009;

    private static final int DELETE_BUTTON_Y = 490;

    public HomeScreen(AndroidDriver driver) {

        super(driver);
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
        // Ждем появления элемента и получаем его

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(constraintLayout));

        // Получаем координаты и размеры элемента
        int elementX = element.getLocation().getX();
        int elementY = element.getLocation().getY();
        int elementWidth = element.getSize().getWidth();
        int elementHeight = element.getSize().getHeight();

        // Вычисляем центр элемента (начало свайпа)
        int startX = elementX + (elementWidth / 2);
        int startY = elementY + (elementHeight / 2);

        // Конечная точка: на 300 пикселей левее
        int endX = startX - 300;

        // Создаем "палец" как устройство ввода
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        // Строим последовательность действий
        Sequence swipeSequence = new Sequence(finger, 0);
        swipeSequence.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), startX, startY));
        swipeSequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipeSequence.addAction(finger.createPointerMove(Duration.ofMillis(500),
                PointerInput.Origin.viewport(), endX, startY));
        swipeSequence.addAction(new Pause(finger, Duration.ofMillis(200)));
        swipeSequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // Выполняем жест
        driver.perform(Arrays.asList(swipeSequence));
    }

    @Step("Клик на кнопку 'Удалить'")
    public void clickDeleteButton() {

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(constraintLayout));

        int cardY = element.getLocation().getY();
        int cardHeight = element.getSize().getHeight();
        int centerY = cardY + (cardHeight / 2);

        //Размер экрана
        int screenWidth = driver.manage().window().getSize().getWidth();

        //Кликаем с отступом от ширины экрана
        int clickX = screenWidth - 50;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), clickX, centerY));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(new Pause(finger, Duration.ofMillis(300)));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(tap));

    }
    @Step("Клик на кнопку 'Подтвердить удаление'")
    public void confirmDelete() {

        click(deleteConfirm);

        System.out.println("Элемент удален!");
    }
    @Step("Открываем созданную запись")
    public TransactionFormScreen openCreatedTransaction() {

        click(constraintLayout);
        return new TransactionFormScreen(driver);
    }




}
