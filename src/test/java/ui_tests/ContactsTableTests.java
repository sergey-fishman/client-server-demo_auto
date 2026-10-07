package ui_tests;

import data_providers.ContactDataProvider;
import dto.Contact;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ContactsPage;
import pages.MainPage;
import utils.TestNGListener;

@Listeners(TestNGListener.class)
public class ContactsTableTests extends AppManager {
    ContactsPage contactsPage;
    SoftAssert softAssert = new SoftAssert();

    @BeforeMethod(alwaysRun = true)
    public void goToContactsPage(){
        MainPage mainPage = new MainPage(getDriver());
        mainPage.clickContactsLink();
        contactsPage = new ContactsPage(getDriver());
    }

    // TC-03 Contact update with valid data
    @Test(groups = {"smoke"})
    public void updateContactPositiveTest() {
        Contact contact = Contact.builder()
                .fullName("Joe")
                .phoneNumber("+391514791514760")
                .build();
        contactsPage.clickBtnEdit();
        contactsPage.typeInputFields(contact);
        contactsPage.clickBtnSave();
        Assert.assertTrue(contactsPage.isTextInContactsMessagePresent("Changes saved"),
                "'Changes saved' is present");
    }

    // TC-03 Contact update with valid data w/ parameters
    @Test(dataProvider = "dataProviderPositive", dataProviderClass = ContactDataProvider.class)
    public void updateContactPositiveTestParameters(Contact contact) {
        contactsPage.clickBtnEdit();
        contactsPage.typeInputFields(contact);
        contactsPage.clickBtnSave();
        Assert.assertTrue(contactsPage.isTextInContactsMessagePresent("Changes saved"),
                "'Changes saved' is present");
    }

    // TC-04 Contact delete
    @Test(groups = {"smoke"})
    public void deleteContactPositiveTest() {
        contactsPage.clickBtnDelete();
        contactsPage.getAlert().accept();
        Assert.assertTrue(contactsPage.isTextInContactsMessagePresent("Contact deleted"),
                "'Contact deleted' is present");
    }

    // TC-10 Contact update with empty name
    @Test
    public void updateContactEmptyNameNegativeTest() {
        Contact contact = Contact.builder()
                .fullName("")
                .phoneNumber("+391514091514766")
                .build();
        contactsPage.clickBtnEdit();
        contactsPage.typeInputFields(contact);
        contactsPage.clickBtnSave();
        Assert.assertTrue(contactsPage.isTextInContactsMessagePresent("Full name: Field cannot be empty"),
                "'Full name: Field cannot be empty' is present");
    }

    // TC-11 Contact update with empty phone
    @Test
    public void updateContactEmptyPhoneNegativeTest() {
        Contact contact = Contact.builder()
                .fullName("Joe")
                .phoneNumber("")
                .build();
        contactsPage.clickBtnEdit();
        contactsPage.typeInputFields(contact);
        contactsPage.clickBtnSave();
        Assert.assertTrue(contactsPage.isTextInContactsMessagePresent("Phone number: Field cannot be empty"),
                "'Phone number: Field cannot be empty' is present");
    }

    // TC-12 Contact update with all empty fields
    @Test
    public void updateContactEmptyFieldsNegativeTest() {
        Contact contact = Contact.builder()
                .fullName("")
                .phoneNumber("")
                .build();
        contactsPage.clickBtnEdit();
        contactsPage.typeInputFields(contact);
        contactsPage.clickBtnSave();
        Assert.assertTrue(contactsPage.isTextInContactsMessagePresent("Full name: Field cannot be empty"),
                "'Full name: Field cannot be empty' is present");
    }

    // TC-13 Contact update too short name(<1)
    @Test
    public void updateContactShortNameNegativeTest() {
        Contact contact = Contact.builder()
                .fullName("J")
                .phoneNumber("+391514091514766")
                .build();
        contactsPage.clickBtnEdit();
        contactsPage.typeInputFields(contact);
        contactsPage.clickBtnSave();
        Assert.assertTrue(contactsPage.isTextInContactsMessagePresent("Full name: Minimum 2 chars"),
                "'Full name: Minimum 2 chars' is present");
    }

    // TC-17 Contact update too long name(>60)
    @Test
    public void updateContactLongNameNegativeTest() {
        Contact contact = Contact.builder()
                .fullName("AlexanderArnoldJeffersonOConnorMcGregorTheodorLincolnRoosevet")
                .phoneNumber("+391514091514766")
                .build();
        contactsPage.clickBtnEdit();
        contactsPage.typeInputFields(contact);
        contactsPage.clickBtnSave();
        Assert.assertTrue(contactsPage.isTextInContactsMessagePresent("Full name: Maximum 60 chars"),
                "'Full name: Maximum 60 chars' is present");
    }

    // TC-14 Contact update invalid format all fields w/ parameters
    @Test(dataProvider = "dataProviderNegative", dataProviderClass = ContactDataProvider.class)
    public void updateContactInvalidFormatInputFieldsNegativeTest(Contact contact) {
        contactsPage.clickBtnEdit();
        contactsPage.typeInputFields(contact);
        contactsPage.clickBtnSave();
        Assert.assertTrue(contactsPage.isTextInContactsMessagePresent("Full name: Name can start and end with a letter"),
                "'Full name: Name can start and end with a letter' is present");
    }

    // TC-15 Contact update invalid phone number format w/ parameters
    @Test(dataProvider = "dataProviderNegativePhone", dataProviderClass = ContactDataProvider.class)
    public void updateContactInvalidPhonesNegativeTest(Contact contact) {
        contactsPage.clickBtnEdit();
        contactsPage.typeInputFields(contact);
        contactsPage.clickBtnSave();
        Assert.assertTrue(contactsPage.isTextInContactsMessagePresent("Phone number: Enter a valid international number"),
                "'Phone number: Enter a valid international number' is present");
    }
}
