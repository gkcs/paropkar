package paropkar.model;

import java.sql.Timestamp;

public class Notification {
    private String id;
    private String userId;
    private String content;
    private Timestamp created_at;

    public Notification() {
    }

    public Notification(String id, String userId, String content, Timestamp created_at) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
