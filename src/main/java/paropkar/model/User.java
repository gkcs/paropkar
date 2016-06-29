package paropkar.model;

public class User {
    private final String username;
    private final String email;
    private final String password;
    private final String aadhaarNumber;
    private final String city;
    private final String address;
    private final String phoneNumber;
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

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }
}
