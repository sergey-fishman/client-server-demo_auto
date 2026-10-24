package data_providers;

import dto.Contact;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContactDataProvider {
    @DataProvider
    public Iterator<Contact> dataProviderPositive() {
        List<Contact> contactList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader
                ("src/test/resources/data_parameters/positive_contact.csv"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] parameters = line.split(",");
                contactList.add(Contact.builder()
                        .full_name(parameters[0])
                        .phone_number(parameters[1])
                        .build());
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return contactList.listIterator();
    }

    @DataProvider
    public Iterator<Contact> dataProviderNegative() {
        List<Contact> contactList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader
                ("src/test/resources/data_parameters/negative_contact.csv"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] parameters = line.split(",");
                contactList.add(Contact.builder()
                        .full_name(parameters[0])
                        .phone_number(parameters[1])
                        .build());
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return contactList.listIterator();
    }

    @DataProvider
    public Iterator<Contact> dataProviderNegativePhone() {
        List<Contact> contactList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader
                ("src/test/resources/data_parameters/negative_contact.csv"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] parameters = line.split(",");
                contactList.add(Contact.builder()
                        .full_name("Joe")
                        .phone_number(parameters[1])
                        .build());
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return contactList.listIterator();
    }
}
