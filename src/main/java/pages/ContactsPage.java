package pages;

import dto.Contact;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class ContactsPage extends BasePage {
    public ContactsPage(WebDriver driver) {
        setDriver(driver);
        driver.get("http://localhost/client-server-demo/contacts.html");
        PageFactory.initElements(new AjaxElementLocatorFactory
                (driver, 10), this);
    }

    @FindBy(css = "button[data-action='edit']")
    WebElement btnEdit;
    @FindBy(css = "button[data-action='cancel']")
    WebElement btnCancel;
    @FindBy(css = "button[data-action='save']")
    WebElement btnSave;
    @FindBy(css = "button[data-action='delete']")
    WebElement btnDelete;
    @FindBy(css = "input[type='text']")
    WebElement inputName;
    @FindBy(css = "input[type='tel']")
    WebElement inputPhone;
    @FindBy(id = "contactsMessage")
    WebElement contactsMessage;

    public void clickBtnEdit() {
        btnEdit.click();
    }

    public void clickBtnDelete() {
        btnDelete.click();
    }

    public void typeInputFields(Contact contact) {
        inputName.clear();
        inputName.sendKeys(contact.getFullName());
        inputPhone.clear();
        inputPhone.sendKeys(contact.getPhoneNumber());
    }

    public void clickBtnSave() {
        btnSave.click();
    }

    public boolean isTextInContactsMessagePresent(String text) {
        return isTextInElementPresent(contactsMessage, text);
    }
}
