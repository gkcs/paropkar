package paropkar.dao;

import org.springframework.jdbc.core.RowMapper;
import paropkar.model.Notification;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class NotificationDao extends DAO<Notification> {
    private static final RowMapper<Notification> rowMapper = (rs, rowNum) -> new Notification(rs.getString("id"),
            rs.getString("user_id"),
            rs.getString("content"),
            rs.getTimestamp("created_at"));

    public NotificationDao() {
        super("NOTIFICATION", rowMapper);
    }

    public CompletableFuture<List<Notification>> getUserNotifications(final String userId) {
        return dataAccessor.queryForList("select * from notification where user_id=?", rowMapper, userId);
    }
}