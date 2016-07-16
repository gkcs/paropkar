package paropkar.model;

public class User {
    private String username;
    private String email;
    private String password;
    private String aadhaarNumber;
    private String city;
    private String address;
    private String phoneNumber;

    public User() {
    }

    private String twitterHandle;

    public User(final String username,
                final String email,
                final String password,
                final String aadhaarNumber,
                final String city,
                final String address,
                final String phoneNumber,
                final String twitterHandle) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.aadhaarNumber = aadhaarNumber;
        this.city = city;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.twitterHandle = twitterHandle;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }
}
