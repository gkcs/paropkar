package paropkar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import paropkar.dao.UserDAO;
import paropkar.model.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class UserController {
    private final UserDAO userDAO;

    @Autowired
    public UserController(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @RequestMapping("/register")
    public CompletableFuture<ResponseEntity<String>> register(final User user) {
        return userDAO.insert(getParams(user))
                .thenApply(count -> {
                    if (count > 0) {
                        return ResponseEntity.ok().body("{}");
                    } else {
                        return new ResponseEntity<>("{\"Error\": \"Failed to insert into database\"}",
                                HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }).exceptionally(throwable -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @RequestMapping("/getUser")
    public CompletableFuture<ResponseEntity<User>> getUser(final String id) {
        return userDAO.getObject(id)
                .thenApply(complaint -> ResponseEntity.ok().body(complaint))
                .exceptionally(throwable -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @RequestMapping("/getAllUsers")
    public CompletableFuture<ResponseEntity<List<User>>> getAllUsers() {
        return userDAO.getAll()
                .thenApply(complaints -> ResponseEntity.ok().body(complaints))
                .exceptionally(throwable -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private Object[] getParams(final User user) {
        return new Object[]{
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getAadhaarNumber(),
                user.getCity(),
                user.getAddress(),
                user.getPhoneNumber(),
                new Timestamp(new java.util.Date().getTime()),
                UUID.randomUUID().toString(),
                user.getTwitterHandle()
        };
    }
}
