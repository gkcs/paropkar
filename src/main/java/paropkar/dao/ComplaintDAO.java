package paropkar.dao;

import paropkar.model.Complaint;

public class ComplaintDAO extends DAO<Complaint> {

    public ComplaintDAO() {
        super("COMPLAINT", (rs, rowNum) -> new Complaint(
                rs.getString("title"),
                rs.getString("content"),
                rs.getString("city"),
                rs.getString("department"),
                rs.getString("type"),
                rs.getString("user_id"),
                rs.getString("status")));
    }
}
