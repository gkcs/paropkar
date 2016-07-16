package paropkar.dao;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataAccessor {
    private final JdbcTemplate jdbcTemplate;
    private static Logger log = LoggerFactory.getLogger(DataAccessor.class.getCanonicalName());
    private final ExecutorService executorService;

    public DataAccessor() {
        this.jdbcTemplate = new JdbcTemplate(createDataSource("jdbc:mysql://localhost:3306/paropkar",
                "com.mysql.jdbc.Driver", 10, 40));
        this.executorService = Executors.newFixedThreadPool(10);
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
        dataSource.setUsername("root");
        dataSource.setPassword("");
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

    public <T> CompletableFuture<T> queryForObject(final String sql,
                                                   final RowMapper<T> rowMapper,
                                                   final Object... params) {
        return CompletableFuture.supplyAsync(() -> jdbcTemplate.queryForObject(sql, rowMapper, params),
                executorService);
    }

    public <T> CompletableFuture<List<T>> queryForList(final String sql,
                                                       final RowMapper<T> rowMapper,
                                                       final Object... params) {
        return CompletableFuture.supplyAsync(() -> jdbcTemplate.query(sql, params, rowMapper),
                executorService);
    }

    public <T> CompletableFuture<List<T>> queryAll(String sql, RowMapper<T> rowMapper) {
        return queryForList(sql, rowMapper);
    }

    public CompletableFuture<Integer> insert(String sql, Object... params) {
        return CompletableFuture.supplyAsync(() -> jdbcTemplate.update(sql, params), executorService);
    }
}