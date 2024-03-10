package Day6;

public class Student {
    
    String name;
    String location;
    String phone;
    String courses[];
    

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCourses(String[] courses) {
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }
   
    public String getPhone() {
        return phone;
    }
  
    public String[] getCourses() {
        return courses;
    }
}
