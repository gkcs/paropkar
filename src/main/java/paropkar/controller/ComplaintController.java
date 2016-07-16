package paropkar.controller;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import paropkar.dao.ComplaintDAO;
import paropkar.dao.DataAccessor;
import paropkar.model.Complaint;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@RestController
public class ComplaintController {

    private final ComplaintDAO complaintDAO;
    private final Random random;

    public ComplaintController() {
        this.complaintDAO = new ComplaintDAO(DataAccessor.getDataAccessor());
        random = new Random();
    }

    @RequestMapping("/fileComplaint")
    public CompletableFuture<ResponseEntity<String>> fileComplaint(@RequestBody final Complaint complaint) {
        return complaintDAO.insert(getParams(complaint))
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

    @RequestMapping("/getComplaint")
    public CompletableFuture<ResponseEntity<Complaint>> getComplaint(@RequestBody final String id) {
        return complaintDAO.getObject(id)
                .thenApply(complaint -> ResponseEntity.ok().body(complaint))
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    @RequestMapping("/getAllComplaints")
    public CompletableFuture<ResponseEntity<List<Complaint>>> getAllComplaints() {
        return complaintDAO.getAll()
                .thenApply(complaints -> ResponseEntity.ok().body(complaints))
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    private Object[] getParams(final Complaint complaint) {
        return new Object[]{
                random.nextInt(),
                complaint.getTitle(),
                complaint.getContent(),
                complaint.getCity(),
                complaint.getDepartment(),
                complaint.getType(),
                new Timestamp(new java.util.Date().getTime()),
                complaint.getUser_id(),
                complaint.getStatus()
        };
    }

    public static void main(String[] args) {
        System.out.println(new Gson().toJson(new Complaint("Land fill sanitation",
                "There is too much candy at my place",
                "Mumbai",
                "Land",
                "valid",
                "212", "processing")));
    }
}