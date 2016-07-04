package paropkar.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.springframework.jdbc.core.RowMapper;
import paropkar.model.User;

import java.util.List;

@Singleton
public class UserDAO extends DAO<User> {

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
            rs.getString("username"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("aadhaar_number"),
            rs.getString("city"),
            rs.getString("address"),
            rs.getString("phone_number"),
            rs.getString("twitter_handle"));

    @Inject
    public UserDAO(DataAccessor dataAccessor) {
        super(dataAccessor);
    }

    @Override
    public User getObject(String... args) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
