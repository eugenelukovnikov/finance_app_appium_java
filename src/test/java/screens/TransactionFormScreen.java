package screens;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class TransactionFormScreen extends BaseScreen {

    final By saveButton = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");

    final By titleField = By.xpath("//android.widget.AutoCompleteTextView[@resource-id=" +
            "\"org.secuso.privacyfriendlyfinancemanager:id/editText_transaction_name\"]");

    final By amountField = By.xpath("//android.widget.EditText" +
            "[@resource-id=\"org.secuso.privacyfriendlyfinancemanager:id/dialog_transaction_amount\"]");

    final By editTransactionFrame = By.xpath("//android.widget" +
            ".FrameLayout[@resource-id=\"org.secuso.privacyfriendlyfinancemanager:id/action_bar_root\"]");

    final By expenseRadio = By.xpath("//android.widget.RadioButton[@resource-id=\"org.secuso" +
            ".privacyfriendlyfinancemanager:id/radioButton_transaction_expense\"]");

    final By incomeRadio = By.xpath("//android.widget.RadioButton[@resource-id=\"org.secuso" +
            ".privacyfriendlyfinancemanager:id/radioButton_transaction_income\"]");


    public TransactionFormScreen(AndroidDriver driver) {

        super(driver);

        if (!isEditTransactionFrameDisplayed()) {
            System.out.println("Форма не загрузилась!");
            throw new RuntimeException("Форма создания транзакции не открылась!");
        }
        System.out.println("Форма создания транзакции загружена!");
    }

    @Step("Проверка, что отображается форма редактирования записи")
    public boolean isEditTransactionFrameDisplayed() {

        return isDisplayed(editTransactionFrame);
    }

    @Step("Ввод названия записи в поле")
    public TransactionFormScreen enterTitle(String title) {
        sendKeys(titleField, title);
        return this;
    }

    @Step("Ввод суммы для записи")
    public TransactionFormScreen enterAmount(String amount) {
        sendKeys(amountField, amount);
        return this;
    }

    @Step("Выбор радиобаттона Доход")
    public TransactionFormScreen selectIncome() {
        click(incomeRadio);
        return this;
    }

    @Step("Выбор радиобаттона Расход")
    public TransactionFormScreen selectExpense() {
        click(expenseRadio);
        return this;
    }

    @Step("Клик на кнопку 'Сохранить'")
    public HomeScreen clickSaveButton() {

        click(saveButton);
        return new HomeScreen(driver);
    }


}
