package paropkar.dao;

import paropkar.model.Conference;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ConferenceDao extends DAO<Conference> {
    public ConferenceDao() {
        super("CONFERENCE", (rs, rowNum) -> new Conference(
                rs.getString("id"),
                Arrays.stream(rs.getString("participants").split(",")).collect(Collectors.toList()),
                rs.getLong("start_time"),
                rs.getLong("end_time"),
                rs.getString("booker"),
                rs.getString("room_id")));
    }
}
