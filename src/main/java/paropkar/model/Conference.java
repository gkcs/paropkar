package paropkar.model;

import com.sun.istack.internal.Nullable;

public class Conference {
    private String id;
    private String participants;
    private long startTime;
    private long endTime;
    private String booker;
    private String roomId;

    public Conference() {
    }

    public Conference(final String id,
                      final String participants,
                      final long startTime,
                      final long endTime,
                      final String booker,
                      final String roomId) {
        this.id = id;
        this.participants = participants;
        this.startTime = startTime;
        this.endTime = endTime;
        this.booker = booker;
        this.roomId = roomId;
    }

    @Nullable
    public String getId() {
        return id;
    }

    @Nullable
    public String getParticipants() {
        return participants;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    @Nullable
    public String getBooker() {
        return booker;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setBooker(String booker) {
        this.booker = booker;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
