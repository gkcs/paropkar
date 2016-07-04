package paropkar.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.springframework.jdbc.core.RowMapper;
import paropkar.model.Complaint;

import java.util.List;

@Singleton
public class ComplaintDAO extends DAO<Complaint>{

    private final RowMapper<Complaint> complaintRowMapper = (rs, rowNum) -> new Complaint(
            rs.getString("title"),
            rs.getString("content"),
            rs.getString("city"),
            rs.getString("department"),
            rs.getString("type"),
            rs.getString("user_id"),
            rs.getString("status"));

    @Inject
    public ComplaintDAO(DataAccessor dataAccessor) {
        super(dataAccessor);
    }

    @Override
    public Complaint getObject(String... args) {
        return null;
    }

    @Override
    public List<Complaint> getAll() {
        return null;
    }
}
