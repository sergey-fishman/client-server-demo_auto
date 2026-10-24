package utils;

import dto.Contact;
import net.datafaker.Faker;
import org.testng.annotations.Test;

public class ContactFactory {
    static Faker faker = new Faker();

    public static Contact positiveContact() {
        return Contact.builder()
                .full_name(faker.name().firstName()+faker.options().option("-", " ", "'")+faker.name().lastName())
                .phone_number("+7"+faker.number().digits(9))
                .build();
    }

    @Test
    public void printContact() {
        System.out.println(positiveContact());
    }
}
