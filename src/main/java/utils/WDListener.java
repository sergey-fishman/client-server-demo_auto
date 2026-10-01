package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WDListener implements WebDriverListener {
    Logger logger = LoggerFactory.getLogger(WDListener.class);

    @Override
    public void onError(Object target, Method method,
                        Object[] args, InvocationTargetException e) {
        WebDriverListener.super.onError(target, method, args, e);
        logger.warn("Exception: {}", e.toString());
    }

    @Override
    public void afterIsDisplayed(WebElement element, boolean result) {
        WebDriverListener.super.afterIsDisplayed(element, result);
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        WebDriverListener.super.afterFindElement
                (driver, locator, result);
        logger.info("After find: locator {}; driver: {}"
                ,locator.toString(), driver.toString());
    }

    @Override
    public void beforeQuit(WebDriver driver) {
        WebDriverListener.super.beforeQuit(driver);
        logger.info("Before quit driver: {}", driver.getWindowHandle());
    }

    @Override
    public void beforeClick(WebElement element) {
        WebDriverListener.super.beforeClick(element);
        logger.info("Before click element: {}",element.toString());
    }

    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        WebDriverListener.super.afterSendKeys(element, keysToSend);
        logger.info("User sendKeys for element: {}, type: {}",
                element.getTagName(),keysToSend);
    }

    @Override
    public void afterMaximize(WebDriver.Window window) {
        WebDriverListener.super.afterMaximize(window);
        logger.info("Window size: {}", window.getSize());
    }
}
