package paropkar.dao;

import paropkar.model.Entity;

public class EntityDao extends DAO<Entity> {
    public EntityDao() {
        super("ENTITY", (rs, rowNum) -> new Entity(rs.getString("guid"),
                rs.getLong("start_time"),
                rs.getLong("end_time")));
    }
}
