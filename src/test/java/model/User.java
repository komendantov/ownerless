package model;

public class User {
    private String firsname;
    private String lastname;
    private String address1;
    private String postcode;
    private String city;
    private String phone;
    private String country;
    private String zone;

    private String login;
    private String password;

    public User() {
        firsname = "John";
        lastname = "Doe";
        address1 = "West Side Str. 22/11";
        postcode = "90210";
        city = "San Diego";
        phone = "+1902105545";
        country = "United States";
        zone = "California";
        login = "user" + System.currentTimeMillis() + "@example.com";
        password = "qwe123";
    }

    public User(String login, String password){
        this();
        this.login = login;
        this.password = password;
    }

    public String getFirsname() {
        return firsname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAddress1() {
        return address1;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCity() {
        return city;
    }

    public String getPhone() {
        return phone;
    }

    public String getCountry() {
        return country;
    }

    public String getZone() {
        return zone;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
