package paropkar.dao;

import org.springframework.jdbc.core.RowMapper;
import paropkar.model.Complaint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ComplaintDAO extends DAO<Complaint> {

    private final RowMapper<Complaint> complaintRowMapper = (rs, rowNum) -> new Complaint(
            rs.getString("title"),
            rs.getString("content"),
            rs.getString("city"),
            rs.getString("department"),
            rs.getString("type"),
            rs.getString("user_id"),
            rs.getString("status"));

    public ComplaintDAO(DataAccessor dataAccessor) {
        super(dataAccessor);
    }

    @Override
    public CompletableFuture<Complaint> getObject(String id) {
        final Map<String, String> params = new HashMap<>();
        params.put("id", id);
        return dataAccessor.queryForObject("select * from complaint", params, complaintRowMapper);
    }

    @Override
    public CompletableFuture<List<Complaint>> getAll() {
        return dataAccessor.queryAll("select * from complaint", complaintRowMapper);
    }
}
