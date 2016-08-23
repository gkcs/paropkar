package paropkar.dao;

import paropkar.model.Conference;

public class ConferenceDao extends DAO<Conference> {
    public ConferenceDao() {
        super("CONFERENCE", (rs, rowNum) -> new Conference(
                rs.getString("id"),
                rs.getString("participants"),
                rs.getLong("start_time"),
                rs.getLong("end_time"),
                rs.getString("booker"),
                rs.getString("room_id")));
    }
}
