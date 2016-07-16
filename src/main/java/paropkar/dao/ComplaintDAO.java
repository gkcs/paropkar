package paropkar.dao;

import org.springframework.jdbc.core.RowMapper;
import paropkar.model.Complaint;

public class ComplaintDAO extends DAO<Complaint> {

    private static final RowMapper<Complaint> complaintRowMapper = (rs, rowNum) -> new Complaint(
            rs.getString("title"),
            rs.getString("content"),
            rs.getString("city"),
            rs.getString("department"),
            rs.getString("type"),
            rs.getString("user_id"),
            rs.getString("status"));

    public ComplaintDAO(DataAccessor dataAccessor) {
        super("COMPLAINT", dataAccessor, complaintRowMapper);
    }
}
