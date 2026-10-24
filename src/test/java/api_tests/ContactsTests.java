package api_tests;

import controllers.ContactsController;
import data_providers.UpdatedDataProvider;
import dto.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static utils.ContactFactory.*;

import utils.TestNGListener;

import java.util.Arrays;

@Listeners(TestNGListener.class)

public class ContactsTests extends ContactsController {
    SoftAssert softAssert = new SoftAssert();

    @Test
    public void getContactsPositiveTest() {
        Response response = getContacts();
        softAssert.assertEquals(response.getStatusCode(), 200,
                "Status code is 200");
        ContactsMessageDto message = response.getBody().as(ContactsMessageDto.class);
        softAssert.assertNotNull(message.getContacts(), "Contacts list is not null");
        softAssert.assertAll();
    }

    @Test
    public void getContactsQueryParamIgnoredPositiveTest() {
        Response response = getContacts("152");
        softAssert.assertEquals(response.getStatusCode(), 200,
                "Status code is 200");
        ContactsMessageDto message = response.getBody().as(ContactsMessageDto.class);
        softAssert.assertNotNull(message.getContacts(), "Contacts list is not null");
        softAssert.assertAll();
    }

    @Test
    public void getContactsWrongPathNegativeTest() {
        Response response = getContactsByPath(BASE_URL + "/api/contact.php");
        Assert.assertEquals(response.getStatusCode(), 404,
                "Status code is 404");
    }

    @Test
    public void createNewContactPositiveTest() {
        Contact contact = positiveContact();
        Response response = createNewContact(contact);
        SuccessMessageDto message = response.getBody().as(SuccessMessageDto.class);
        softAssert.assertEquals(message.getStatus(), "success");
        softAssert.assertEquals(response.getStatusCode(), 201);

        ContactsMessageDto contacts = getContacts().getBody().as(ContactsMessageDto.class);
        String createdId = String.valueOf(message.getId());

        Contact created = Arrays.stream(contacts.getContacts())
                .filter(c -> createdId.equals(c.getId()))
                .findFirst()
                .orElse(null);

        softAssert.assertNotNull(created, "Created contact is present in the list by id " + createdId);
        if (created != null) {
            softAssert.assertEquals(created.getFull_name(), contact.getFull_name(), "full_name matches");
            softAssert.assertEquals(created.getPhone_number(), contact.getPhone_number(), "phone_number matches");
        }
        softAssert.assertAll();
    }

    @Test
    public void updateContactPositiveTest() {
        Contact contact = positiveContact();
        Response createResponse = createNewContact(contact);
        softAssert.assertEquals(createResponse.getStatusCode(), 201,
                "Contact created with status code 201");
        Integer id = createResponse.getBody().as(SuccessMessageDto.class).getId();

        Contact newContact = positiveContact();
        Response response = updateContactById(newContact, id);
        UpdateMessageDto message = response.getBody().as(UpdateMessageDto.class);

        softAssert.assertEquals(response.getStatusCode(), 200, "Status code 200");
        softAssert.assertEquals(message.getFull_name(), newContact.getFull_name(),
                "Request full_name equals response full_name");
        softAssert.assertEquals(message.getPhone_number(), newContact.getPhone_number(),
                "Request phone_number equals response phone_number");
        softAssert.assertAll();
    }

    @Test
    public void deleteContactPositiveTest() {
        Contact contact = positiveContact();
        Response createResponse = createNewContact(contact);
        softAssert.assertEquals(createResponse.getStatusCode(), 201,
                "Contact created with status code 201");

        Integer id = createResponse.getBody().as(SuccessMessageDto.class).getId();
        Response response = deleteContactById(id);
        softAssert.assertEquals(response.getStatusCode(), 200,
                "Delete status code 200");
    }

    @Test(dataProvider = "dataProviderPositive", dataProviderClass = UpdatedDataProvider.class)
    public void createNewContactWithDataProviderPositiveTest(Contact contact) {
        softAssert = new SoftAssert();
        Response response = createNewContact(contact);
        SuccessMessageDto message = response.getBody().as(SuccessMessageDto.class);
        softAssert.assertEquals(message.getStatus(), "success");
        softAssert.assertEquals(response.getStatusCode(), 201);

        ContactsMessageDto contacts = getContacts().getBody().as(ContactsMessageDto.class);
        String createdId = String.valueOf(message.getId());

        Contact created = Arrays.stream(contacts.getContacts())
                .filter(c -> createdId.equals(c.getId()))
                .findFirst()
                .orElse(null);

        softAssert.assertNotNull(created, "Created contact is present in the list by id " + createdId);
        if (created != null) {
            softAssert.assertEquals(created.getFull_name(), contact.getFull_name().trim(), "full_name matches");
            softAssert.assertEquals(created.getPhone_number(), contact.getPhone_number().trim(), "phone_number matches");
        }
        softAssert.assertAll();
    }

    @Test(dataProvider = "dataProviderPositive", dataProviderClass = UpdatedDataProvider.class)
    public void updateContactWithDataProviderPositiveTest(Contact contact) {
        softAssert = new SoftAssert();
        Response createResponse = createNewContact(positiveContact());
        softAssert.assertEquals(createResponse.getStatusCode(), 201,
                "Contact created with status code 201");

        Integer id = createResponse.getBody().as(SuccessMessageDto.class).getId();
        Response updateResponse = updateContactById(contact, id);
        UpdateMessageDto message = updateResponse.getBody().as(UpdateMessageDto.class);
        softAssert.assertEquals(updateResponse.getStatusCode(), 200, "Status code 200");

        String updatedId = String.valueOf(message.getId());
        ContactsMessageDto contacts = getContacts().getBody().as(ContactsMessageDto.class);

        Contact updated = Arrays.stream(contacts.getContacts())
                .filter(c -> updatedId.equals(c.getId()))
                .findFirst()
                .orElse(null);

        softAssert.assertNotNull(updated, "Updated contact is present in the list by id " + updatedId);
        if (updatedId != null) {
            softAssert.assertEquals(updated.getFull_name(), contact.getFull_name().trim(), "full_name matches");
            softAssert.assertEquals(updated.getPhone_number(), contact.getPhone_number().trim(), "phone_number matches");
        }
        softAssert.assertAll();
    }

    @Test
    public void createNewContactEmptyNameNegativeTest() {
        Contact contact = Contact.builder()
                .full_name("")
                .phone_number("+7999888555444")
                .build();
        Response response = createNewContact(contact);
        ErrorDto errorDto = response.getBody().as(ErrorDto.class);
        softAssert.assertEquals(response.getStatusCode(), 422, "Status code 422");
        softAssert.assertEquals(errorDto.getError(), "Input fields cannot be empty");
        softAssert.assertAll();
    }

    @Test
    public void createNewContactNullValuesNegativeTest() {
        Contact contact = Contact.builder().build();
        Response response = createNewContact(contact);
        softAssert.assertEquals(response.getStatusCode(), 422, "Status code 422");
        ErrorMissingFieldsDto error = response.getBody().as(ErrorMissingFieldsDto.class);
        softAssert.assertEquals(error.getMissing_fields(), new String[] {"full_name", "phone_number"});
        softAssert.assertAll();
    }

    @Test
    public void createNewContactEmptyPhoneNegativeTest() {
        Contact contact = Contact.builder()
                .full_name("Alex")
                .phone_number("")
                .build();
        Response response = createNewContact(contact);
        ErrorDto errorDto = response.getBody().as(ErrorDto.class);
        softAssert.assertEquals(response.getStatusCode(), 422, "Status code 422");
        softAssert.assertEquals(errorDto.getError(), "Input fields cannot be empty");
        softAssert.assertAll();
    }

    @Test(dataProvider = "dataProviderWrongName", dataProviderClass = UpdatedDataProvider.class)
    public void createNewContactWrongNameNegativeTest(Contact contact) {
        Response response = createNewContact(contact);
        Assert.assertEquals(response.getStatusCode(), 422, "Status code 422");
    }

    @Test(dataProvider = "dataProviderWrongPhone", dataProviderClass = UpdatedDataProvider.class)
    public void createNewContactWrongPhoneNegativeTest(Contact contact) {
        Response response = createNewContact(contact);
        Assert.assertEquals(response.getStatusCode(), 422, "Status code 422");
    }

    @Test
    public void createContactWrongValuesTypeNegativeTest(){
        String body = """
        {
            "full_name": {"AlexanderArnold": "value"},
            "phone_number": 915147222
        }
        """;
        Response response = createNewContactWithRawJson(body);
        Assert.assertEquals(response.getStatusCode(), 422);
    }
}
