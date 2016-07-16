package paropkar.dao;

import org.springframework.jdbc.core.RowMapper;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public abstract class DAO<T> {
    private final String TABLE_NAME;
    protected final DataAccessor dataAccessor;
    private final RowMapper<T> rowMapper;

    public DAO(final String table_name, final DataAccessor dataAccessor, RowMapper<T> rowMapper) {
        this.TABLE_NAME = table_name;
        this.dataAccessor = dataAccessor;
        this.rowMapper = rowMapper;
    }

    public CompletableFuture<T> getObject(String id) {
        return dataAccessor.queryForObject("select * from " + TABLE_NAME + " where id=?", rowMapper, id);
    }

    public CompletableFuture<List<T>> getAll() {
        return dataAccessor.queryAll("select * from " + TABLE_NAME, rowMapper);
    }

    public CompletableFuture<Integer> insert(final Object... params) {
        return dataAccessor.insert("insert into " + TABLE_NAME + " values (" + placeHolders(params) + ")", params);
    }

    private String placeHolders(final Object... params) {
        return Arrays.stream(params).map(__ -> "?").collect(Collectors.joining(","));
    }
}
