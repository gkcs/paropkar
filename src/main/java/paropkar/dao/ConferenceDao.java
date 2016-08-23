package paropkar.dao;

import paropkar.model.Booking;
import paropkar.model.Conference;

public class ConferenceDao extends DAO<Conference> {
    public ConferenceDao() {
        super("CONFERENCE", (rs, rowNum) -> new Conference(
                rs.getString("id"),
                rs.getString("participants"),
                rs.getString("booker"),
                new Booking(rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getString("room_id"))));
    }
}
