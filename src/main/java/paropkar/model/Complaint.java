package paropkar.model;

public class Complaint {
    private String title;
    private String content;
    private String city;
    private String department;
    private String type;
    private String user_id;
    private String status;

    public Complaint() {
    }

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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
