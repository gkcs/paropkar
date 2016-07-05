package paropkar.dao;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataAccessor {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static Logger log = LoggerFactory.getLogger(DataAccessor.class.getCanonicalName());
    private static final Map<String, ?> BLANK_PARAMS = new HashMap<>();

    public DataAccessor() {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                createDataSource("jdbc:mysql://localhost:3306/connect4", "com.mysql.jdbc.Driver", 10, 40));
    }

    protected BasicDataSource createDataSource(String url, String driver, int timeout, int poolSize) {
        checkNotNull(driver);
        checkNotNull(url);
        log.info("PooledDataSource : Configuring data source [url=" +
                url +
                ",driver=" +
                driver +
                ",pool-size=" +
                poolSize +
                "]");
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setMaxActive(poolSize);
        dataSource.setMaxWait(timeout);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setValidationQuery("select 1");

        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        return dataSource;

    }

    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    public <T> T queryForObject(final String sql,
                                final Map<String, ?> params,
                                final RowMapper<T> rowMapper) {
        return namedParameterJdbcTemplate.queryForObject(sql, params, rowMapper);
    }

    public <T> List<T> queryForList(final String sql,
                                    final Map<String, ?> params,
                                    final RowMapper<T> rowMapper) {
        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }

    public <T> List<T> queryAll(String sql, RowMapper<T> rowMapper) {
        return queryForList(sql, BLANK_PARAMS, rowMapper);
    }
}
