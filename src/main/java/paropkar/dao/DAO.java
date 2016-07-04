package paropkar.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

@Singleton
public abstract class DAO<T> {
    protected final DataAccessor dataAccessor;

    @Inject
    public DAO(final DataAccessor dataAccessor) {
        this.dataAccessor = dataAccessor;
    }

    public abstract T getObject(String... args);

    public abstract List<T> getAll();
}
