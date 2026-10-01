package ui_tests;

import data_providers.ContactDataProvider;
import dto.Contact;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.MainPage;
import utils.TestNGListener;

@Listeners(TestNGListener.class)
public class ContactsFormTests extends AppManager {
    MainPage mainPage;
    SoftAssert softAssert = new SoftAssert();

    @BeforeMethod(alwaysRun = true)
    public void setMainPage() {
        mainPage = new MainPage(getDriver());
    }

    // TC ID 1
    @Test(dataProvider = "dataProviderPositive", dataProviderClass = ContactDataProvider.class)
    public void createContactPositiveTest(Contact contact) {
        mainPage.typeContactForm(contact);
        mainPage.clickBtnSubmit();
        Assert.assertTrue(mainPage.validateTextInResultMessagePresent
                ("success"), "Expected Result message to have text 'success'");
    }
    // TC ID 2
    @Test
    public void createContactEmptyNameNegativeTest() {
        Contact contact = Contact.builder()
                .fullName("").
                phoneNumber("+2015147666").
                build();
        mainPage.typeContactForm(contact);
        mainPage.clickBtnSubmit();
        softAssert.assertTrue(mainPage.validateNamePopupIsDisplayed(),
                "'namePopup' is displayed");
        softAssert.assertTrue(mainPage.validateTextInNamePopupIsPresent
                ("Field cannot be empty"), "'Field cannot be empty' is present in NamePopup");
        softAssert.assertAll();
    }
    // TC ID 3
    @Test
    public void createContactEmptyPhoneNegativeTest() {
        Contact contact = Contact.builder()
                .fullName("Alexander-Arnold").phoneNumber("").build();
        mainPage.typeContactForm(contact);
        mainPage.clickBtnSubmit();
        softAssert.assertTrue(mainPage.validatePhoneNumberPopupIsDisplayed(),
                "'PhoneNumberPopup' is displayed");
        softAssert.assertTrue(mainPage.validateTextInPhoneNumberPopupIsPresent
                ("Field cannot be empty"),"'Field cannot be empty' is present in PhonePopup");
        softAssert.assertAll();
    }
    //TC ID 4
    @Test
    public void createContactEmptyFieldsNegativeTest() {
        mainPage.clickBtnSubmit();
        softAssert.assertTrue(mainPage.validateTextInNamePopupIsPresent
                ("Field cannot be empty"), "'Field cannot be empty' is present in NamePopup");
        softAssert.assertTrue(mainPage.validateTextInPhoneNumberPopupIsPresent
                ("Field cannot be empty"),"'Field cannot be empty' is present in PhonePopup");
        softAssert.assertAll();
    }
    // TC ID 5
    @Test
    public void createContactTooShortNameNegativeTest() {
        Contact contact = Contact.builder()
                .fullName(" J ").phoneNumber("+2015147666").build();
        mainPage.typeContactForm(contact);
        mainPage.clickBtnSubmit();
        softAssert.assertTrue(mainPage.validateTextInNamePopupIsPresent
                ("Minimum 2 chars"), "'Minimum 2 chars' is present in NamePopup");
        softAssert.assertAll();
    }
    // TC ID 6
    @Test
    public void createContactTooLongNameNegativeTest() {
        Contact contact = Contact.builder()
                .fullName("AlexanderArnoldJeffersonOConnorMcGregorTheodorLincolnRoosevet")
                .phoneNumber("+2015147666").build();
        mainPage.typeContactForm(contact);
        mainPage.clickBtnSubmit();
        softAssert.assertTrue(mainPage.validateTextInNamePopupIsPresent
                ("Maximum 60 chars"), "'Maximum 60 chars' is present in NamePopup");
        softAssert.assertAll();
    }
    // TC ID 7
    @Test(dataProvider = "dataProviderNegative", dataProviderClass = ContactDataProvider.class)
    public void createContactInvalidFieldsNegativeTest(Contact contact) {
        mainPage.typeContactForm(contact);
        mainPage.clickBtnSubmit();
        softAssert.assertTrue(mainPage.validateTextInNamePopupIsPresent
                ("Name can start and end with a letter"),
                "'Name can start and end with a letter' is present in NamePopup");
        softAssert.assertTrue(mainPage.validateTextInPhoneNumberPopupIsPresent
                ("Enter a valid international number"),
                "'Enter a valid international number' is present in PhonePopup");
        softAssert.assertAll();
    }
}
