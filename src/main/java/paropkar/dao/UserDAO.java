package paropkar.dao;

import paropkar.model.User;

public class UserDAO extends DAO<User> {

    public UserDAO() {
        super("USER", (rs, rowNum) -> new User(
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("aadhaar_number"),
                rs.getString("city"),
                rs.getString("address"),
                rs.getString("phone_number"),
                rs.getString("twitter_handle")));
    }
}
