package paropkar.model;

import java.util.List;

public class Conference {
    private final String id;
    private final List<String> participants;
    private final long startTime;
    private final long endTime;
    private final String booker;
    private final String roomId;

    public Conference(final String id,
                      final List<String> participants,
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

    public String getId() {
        return id;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public String getBooker() {
        return booker;
    }

    public String getRoomId() {
        return roomId;
    }
}
