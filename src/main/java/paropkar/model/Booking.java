package paropkar.model;

public class Booking {
    @Override
    public String toString() {
        return "Booking{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", roomId='" + roomId + '\'' +
                '}';
    }

    private String start;
    private String end;
    private String roomId;

    public Booking(final String start, final String end, final String roomId) {
        this.start = start;
        this.end = end;
        this.roomId = roomId;
    }

    public Booking() {
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getStart() {
        return start;
    }
}
