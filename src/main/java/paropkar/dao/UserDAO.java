package paropkar.dao;

import org.springframework.jdbc.core.RowMapper;
import paropkar.model.User;

public class UserDAO {
    private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
            rs.getString("username"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("aadhaar_number"),
            rs.getString("city"),
            rs.getString("address"),
            rs.getString("phone_number"),
            rs.getString("twitter_handle"));
}
