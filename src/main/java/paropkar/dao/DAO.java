package paropkar.dao;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class DAO<T> {
    protected final DataAccessor dataAccessor;

    public DAO(final DataAccessor dataAccessor) {
        this.dataAccessor = dataAccessor;
    }

    public abstract CompletableFuture<T> getObject(String id);

    public abstract CompletableFuture<List<T>> getAll();
}
