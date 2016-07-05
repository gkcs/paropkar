package paropkar.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.springframework.jdbc.core.RowMapper;
import paropkar.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public UserDAO(final DataAccessor dataAccessor) {
        super(dataAccessor);
    }

    @Override
    public User getObject(String id) {
        final Map<String, String> params = new HashMap<>();
        params.put("aadhaar_number", id);
        return dataAccessor.queryForObject("select * from user", params, userRowMapper);
    }

    @Override
    public List<User> getAll() {
        return dataAccessor.queryAll("select * from user", userRowMapper);
    }
}
