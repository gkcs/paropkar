package paropkar.model;

public class Entity {
    private String guid;
    private long startTime;

    public Entity() {
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    private long endTime;

    public Entity(final String guid, final long startTime, final long endTime) {
        this.guid = guid;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
