package Day7;

import org.testng.annotations.Test;

import com.github.javafaker.Faker;

public class FakerDataGenerator {
    
    @Test
    void testGenerateDummyData(){
       
        Faker faker = new Faker();

        String fullname = faker.name().fullName();
        String firstname = faker.name().firstName();
        String lastname = faker.name().lastName();

        String username = faker.name().username();
        String password = faker.internet().password();

        String phoneNo = faker.phoneNumber().cellPhone();

        String email = faker.internet().safeEmailAddress();

        String company = faker.company().name();

        System.out.println("Fullname: " + fullname);
        System.out.println("Firstname: " + firstname);
        System.out.println("Lastname: " + lastname);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("PhoneNo " + phoneNo);
        System.out.println("Email: " + email);
        System.out.println("Company: " + company);
    }
}
