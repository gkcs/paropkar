package paropkar.model;

public class Complaint {
    private final String title;
    private final String content;
    private final String city;
    private final String department;
    private final String type;
    private final String user_id;
    private final String status;

    public Complaint(final String title,
                     final String content,
                     final String city,
                     final String department,
                     final String type,
                     final String user_id,
                     final String status) {
        this.title = title;
        this.content = content;
        this.city = city;
        this.department = department;
        this.type = type;
        this.user_id = user_id;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCity() {
        return city;
    }

    public String getDepartment() {
        return department;
    }

    public String getType() {
        return type;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getStatus() {
        return status;
    }
}
