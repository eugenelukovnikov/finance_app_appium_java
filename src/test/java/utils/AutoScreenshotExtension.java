package utils;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

public class AutoScreenshotExtension implements TestExecutionExceptionHandler {

    // Конструктор по умолчанию (нужен для @ExtendWith)
    public AutoScreenshotExtension() {
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        // Делаем скриншот при падении
        String testName = context.getDisplayName();
        String errorName = throwable.getClass().getSimpleName();
        ScreenshotUtils.attachPageScreenshot("❌ Упал тест: " + testName + " (" + errorName + ")");

        // Пробрасываем исключение дальше
        throw throwable;
    }
}