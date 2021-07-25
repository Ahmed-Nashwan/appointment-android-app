package ahmed.ucas.edu.finalproject.Classes;

public class User {
    private String uid;
    private String full_name;
    private String email;
    private String gender;
    private String location;


    public String getUid() {
        return uid;
    }
    public String getGender() {
        return gender;
    }
    public String getLocation() {
        return location;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setLocation(String location) {
        this.location = location;
    }


    public String getFull_name() {
        return full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
