package paropkar.model;

import com.sun.istack.internal.Nullable;

public class Conference {
    private String id;
    private String participants;
    private String booker;

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    private Booking booking;

    public Conference() {
    }

    public Conference(final String id,
                      final String participants,
                      final String booker,
                      final Booking booking) {
        this.id = id;
        this.participants = participants;
        this.booker = booker;
        this.booking = booking;
    }

    @Nullable
    public String getId() {
        return id;
    }

    @Nullable
    public String getParticipants() {
        return participants;
    }

    @Nullable
    public String getBooker() {
        return booker;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public void setBooker(String booker) {
        this.booker = booker;
    }

    @Override
    public String toString() {
        return "Conference{" +
                "id='" + id + '\'' +
                ", participants='" + participants + '\'' +
                ", booker='" + booker + '\'' +
                ", booking=" + booking +
                '}';
    }
}
