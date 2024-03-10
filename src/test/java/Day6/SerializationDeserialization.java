package Day6;

import org.testng.annotations.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

// 1st we need to create POJO Class 
// then
// POJO -> JSON Object = Serialization
// JSON Object -> POJO = Deserialization

public class SerializationDeserialization {

    @Test
    void convertPojo2Json() throws Exception {

        // Created java object using POJO Class
        Student stupojo = new Student();

        stupojo.setName("Rohan");
        stupojo.setLocation("Canada");
        stupojo.setPhone("123456");
        String courseArr[] = {"C", "C++"};
        stupojo.setCourses(courseArr);

        // Converting java object -> JSON Object(Serialization)
        ObjectMapper objMapper = new ObjectMapper();

        try {
            String jsonData = objMapper.writeValueAsString(stupojo);
            System.out.println(jsonData);
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Handle the exception according to your application's needs
        }
    }

    //@Test
    void convertJson2POJO() throws Exception {

        String jsonData = "{\"name\":\"Rohan\",\"location\":\"Canada\",\"phone\":\"123456\",\"courses\":[\"C\",\"C++\"]}";

        // Converting json data -> POJO Object(DeSerialization)
        ObjectMapper objMapper = new ObjectMapper();

        try {
            Student stupojo = objMapper.readValue(jsonData, Student.class); // Convert json to pojo class 

            System.out.println("Name: " + stupojo.getName());
            System.out.println("Location: " + stupojo.getLocation());
            System.out.println("Phone: " + stupojo.getPhone());
            System.out.println("Course 1: " + stupojo.getCourses()[0]);
            System.out.println("Course 2: " + stupojo.getCourses()[1]);
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Handle the exception according to your application's needs
        }
    }
}
