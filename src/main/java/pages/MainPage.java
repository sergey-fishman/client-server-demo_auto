package pages;

import dto.Contact;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class MainPage extends BasePage {
    public MainPage(WebDriver driver) {
        setDriver(driver);
        driver.get("http://localhost/client-server-demo/");
        PageFactory.initElements(new AjaxElementLocatorFactory
                (driver, 10), this);
    }

    @FindBy(id = "fullName")
    WebElement inputFullName;
    @FindBy(css = "input[type='tel']")
    WebElement inputPhoneNumber;
    @FindBy(xpath = "//button[@type='submit']")
    WebElement btnSubmit;
    @FindBy(id = "result")
    WebElement messageResult;
    @FindBy(id ="fullNamePopup")
    WebElement fullNamePopup;
    @FindBy(id = "phoneNumberPopup")
    WebElement phoneNumberPopup;
    @FindBy(css = "a[href='contacts.html']")
    WebElement contactsLink;

    public void clickContactsLink() {
        contactsLink.click();
    }

    public void typeContactForm(Contact contact) {
        inputFullName.sendKeys(contact.getFullName());
        inputPhoneNumber.sendKeys(contact.getPhoneNumber());
    }

    public void clickBtnSubmit() {
        btnSubmit.click();
    }

    public boolean validateTextInResultMessagePresent(String text) {
        return isTextInElementPresent(messageResult, text);
    }

    public boolean validateNamePopupIsDisplayed(){
        return isDisplayed(fullNamePopup);
    }

    public boolean validateTextInNamePopupIsPresent(String text) {
        return isTextInElementPresent(fullNamePopup, text);
    }

    public boolean validatePhoneNumberPopupIsDisplayed() {
        return isDisplayed(phoneNumberPopup);
    }

    public boolean validateTextInPhoneNumberPopupIsPresent(String text) {
        return isTextInElementPresent(phoneNumberPopup, text);
    }
}
