package paropkar.dao;

import org.springframework.jdbc.core.RowMapper;
import paropkar.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

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

    public UserDAO(final DataAccessor dataAccessor) {
        super(dataAccessor);
    }

    @Override
    public CompletableFuture<User> getObject(String id) {
        final Map<String, String> params = new HashMap<>();
        params.put("aadhaar_number", id);
        return dataAccessor.queryForObject("select * from user", params, userRowMapper);
    }

    @Override
    public CompletableFuture<List<User>> getAll() {
        return dataAccessor.queryAll("select * from user", userRowMapper);
    }
}
