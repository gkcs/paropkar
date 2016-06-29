package paropkar.dao;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class DataAccessor {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static Logger log = LoggerFactory.getLogger(DataAccessor.class.getCanonicalName());

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
}
