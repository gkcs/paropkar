package paropkar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import paropkar.dao.EntityDao;
import paropkar.model.Entity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class EntityController {
    private final EntityDao entityDao;

    public EntityController() {
        this.entityDao = new EntityDao();
    }

    @RequestMapping("/getEntity")
    public CompletableFuture<ResponseEntity<Entity>> getEntity(@RequestBody final String id) {
        return entityDao.getObject(id)
                .thenApply(entity -> ResponseEntity.ok().body(entity))
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    @RequestMapping("/addEntity")
    public CompletableFuture<ResponseEntity<String>> addEntity(@RequestBody final Entity entity) {
        return entityDao.insert(getParams(entity))
                .thenApply(count -> {
                    if (count > 0) {
                        return ResponseEntity.ok().body("{}");
                    } else {
                        throw new RuntimeException("Failed to insert into database");
                    }
                }).exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return new ResponseEntity<>(throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    @RequestMapping("/getAllEntities")
    public CompletableFuture<ResponseEntity<List<Entity>>> getAllEntities() {
        return entityDao.getAll()
                .thenApply(entitys -> ResponseEntity.ok().body(entitys))
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    private Object[] getParams(final Entity entity) {
        return new Object[]{
                entity.getGuid(),
                entity.getStartTime(),
                entity.getEndTime()
        };
    }
}
