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

    public WrapperBooking convertToWrapper() {
        return new WrapperBooking(roomId, start, end);
    }

    public static class WrapperBooking {
        private final String title;
        private final String start;
        private final String end;

        public WrapperBooking(String id, String start, String end) {
            this.title = id;
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "WrapperBooking{" +
                    "title='" + title + '\'' +
                    ", start='" + start + '\'' +
                    ", end='" + end + '\'' +
                    '}';
        }
    }
}
