package data_providers;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import dto.Contact;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class UpdatedDataProvider {
    private static final String DATA_DIR = "src/test/resources/data_parameters/";

    @DataProvider
    public Iterator<Contact> dataProviderPositive() {
        return readContacts("positive_contact.csv").iterator();
    }

    @DataProvider
    public Iterator<Contact> dataProviderNegative() {
        return readContacts("negative_contact.csv").iterator();
    }

    @DataProvider
    public Iterator<Contact> dataProviderWrongName() {
        return readContacts("negative_contact.csv").stream()
                .map(c -> Contact.builder()
                        .full_name(c.getFull_name())
                        .phone_number("+7999666333")
                        .build())
                .iterator();
    }
    @DataProvider
    public Iterator<Contact> dataProviderWrongPhone() {
        return readContacts("negative_contact.csv").stream()
                .map(c -> Contact.builder()
                        .full_name("Joe")
                        .phone_number(c.getPhone_number())
                        .build())
                .iterator();
    }

    private List<Contact> readContacts(String fileName) {
        Path path = Path.of(DATA_DIR, fileName);
        try (CSVReader reader = new CSVReader(Files.newBufferedReader(path))) { // UTF-8
            List<Contact> contacts = new ArrayList<>();
            String[] row;
            while ((row = reader.readNext()) != null) {
                if (row.length == 1 && row[0].isBlank()) continue; // пустая строка
                if (row.length != 2) {
                    throw new IllegalStateException("Expected 2 columns in " + path
                            + " but got " + row.length + ": " + Arrays.toString(row));
                }
                contacts.add(Contact.builder()
                        .full_name(row[0])
                        .phone_number(row[1])
                        .build());
            }
            return contacts;
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Cannot read test data file: " + path.toAbsolutePath(), e);
        }
    }
}
