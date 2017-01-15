package paropkar.controller;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import paropkar.dao.NotificationDao;
import paropkar.model.Notification;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
public class NotificationController {

    private final NotificationDao notificationDao;
    private final Random random;

    public NotificationController() {
        this.notificationDao = new NotificationDao();
        random = new Random();
    }

    @RequestMapping("/addNotification")
    public CompletableFuture<ResponseEntity<String>> addNotification(@RequestBody final Notification notification) {
        return notificationDao.insert(getParams(notification))
                .thenApply(count -> {
                    if (count > 0) {
                        return ResponseEntity.ok().body("{}");
                    } else {
                        return new ResponseEntity<>("{\"Error\": \"Failed to insert into database\"}",
                                HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }).exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    @RequestMapping("/getNotification")
    public CompletableFuture<ResponseEntity<Notification>> getNotification(@RequestBody final String id) {
        return notificationDao.getObject(id)
                .thenApply(notification -> ResponseEntity.ok().body(notification))
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    @RequestMapping("/getAllNotifications")
    public CompletableFuture<ResponseEntity<List<Notification>>> getAllNotifications() {
        return notificationDao.getAll()
                .thenApply(notifications -> ResponseEntity.ok().body(notifications))
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    @RequestMapping("/getNotificationsForUser")
    public CompletableFuture<ResponseEntity<List<Notification>>> getNotificationsForUser(@RequestBody String userId) {
        return notificationDao.getUserNotifications(userId)
                .thenApply(notifications -> ResponseEntity.ok().body(notifications))
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    private Object[] getParams(final Notification notification) {
        return new Object[]{
                random.nextInt(),
                notification.getUserId(),
                notification.getContent()
        };
    }
}