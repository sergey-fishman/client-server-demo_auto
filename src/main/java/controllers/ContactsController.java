package controllers;

import dto.Contact;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.BaseApi;

import static io.restassured.RestAssured.given;

public class ContactsController implements BaseApi {

    public Response getContacts() {
        return given()
                .when()
                .get(BASE_URL + CONTACTS_URL)
                .then()
                .log().status()
                .log().body()
                .extract()
                .response();
    }

    public Response getContactsCustomMethod(String method) {
        return given()
                .when()
                .request(method, BASE_URL + CONTACTS_URL)
                .then()
                .log().status()
                .log().body()
                .extract()
                .response();
    }

    public Response getContacts(String id) {
        return given()
                .log().all()
                .when()
                .queryParam("id", id)
                .get(BASE_URL + CONTACTS_URL)
                .then()
                .log().status()
                .log().body()
                .extract()
                .response();
    }

    public Response getContactsByPath(String path) {
        return given()
                .log().all()
                .when()
                .get(path)
                .then()
                .log().status()
                .log().body()
                .extract()
                .response();
    }

    public Response createNewContact(Contact contact) {
        return given()
                .log().body()
                .body(contact)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + CONTACTS_URL)
                .then()
                .log().status()
                .log().body()
                .extract()
                .response();
    }

    public Response createNewContactCustomContentType(Contact contact, ContentType contentType) {
        return given()
                .log().body()
                .body(contact)
                .contentType(contentType)
                .when()
                .post(BASE_URL + CONTACTS_URL)
                .then()
                .log().status()
                .log().body()
                .extract()
                .response();
    }

    public Response updateContactCustomContentType(Contact contact, ContentType contentType, Integer id) {
        return given()
                .log().body()
                .body(contact)
                .contentType(contentType)
                .when()
                .queryParam("id", id)
                .put(BASE_URL + CONTACTS_URL)
                .then()
                .log().status()
                .log().body()
                .extract()
                .response();
    }

    public Response createNewContactWithRawJson(String rawJson) {
        return given()
                .log().body()
                .body(rawJson)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + CONTACTS_URL)
                .then()
                .log().status()
                .log().body()
                .extract()
                .response();
    }

    public Response updateContactWithRawJson(String rawJson, Integer id) {
        return given()
                .log().body()
                .body(rawJson)
                .contentType(ContentType.JSON)
                .when()
                .queryParam("id", id)
                .put(BASE_URL + CONTACTS_URL)
                .then()
                .log().status()
                .log().body()
                .extract()
                .response();
    }

    public Response updateContactById(Contact contact, Integer id) {
        return given()
                .log().uri()
                .log().body()
                .body(contact)
                .contentType(ContentType.JSON)
                .when()
                .queryParam("id", id)
                .put(BASE_URL + CONTACTS_URL)
                .then()
                .log().status()
                .log().body()
                .extract()
                .response();
    }

    public Response deleteContactById(Integer id) {
        return given()
                .when()
                .queryParam("id", id)
                .delete(BASE_URL+ CONTACTS_URL)
                .then()
                .log().status()
                .log().body()
                .extract()
                .response();
    }
}
