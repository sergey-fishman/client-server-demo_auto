package manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.WDListener;

public class AppManager {
    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    static String browser =
            System.getProperty("browser", Browser.CHROME.browserName());

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        driver = switch (browser) {
            case String b when b.equals(Browser.CHROME.browserName()) -> new ChromeDriver();
            case String b when b.equals(Browser.FIREFOX.browserName()) -> new FirefoxDriver();
            case String b when b.equals(Browser.EDGE.browserName()) -> new EdgeDriver();
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };
        driver.manage().window().maximize();
        WebDriverListener listener = new WDListener();
        driver = new EventFiringDecorator<>(listener).decorate(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        if (driver != null)
            driver.quit();
    }
}
