package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    static WebDriver driver;

    public void setDriver(WebDriver wd) {
        driver = wd;
    }

    public boolean isTextInElementPresent(WebElement element, String text) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.textToBePresentInElement
                            (element, text));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean isDisplayed(WebElement element) {
        return element.isDisplayed();
    }

    public Alert getAlert() {
        return new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions
                        .alertIsPresent());
    }
}
