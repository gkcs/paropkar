package paropkar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import paropkar.dao.ConferenceDao;
import paropkar.model.Conference;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
public class ConferenceController {
    private final ConferenceDao conferenceDao;

    public ConferenceController() {
        this.conferenceDao = new ConferenceDao();
    }

    @RequestMapping("/getConference")
    public CompletableFuture<ResponseEntity<Conference>> getConference(@RequestBody final String id) {
        return conferenceDao.getObject(id)
                .thenApply(conference -> ResponseEntity.ok().body(conference))
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    @RequestMapping("/bookConference")
    public CompletableFuture<ResponseEntity<String>> bookConference(@RequestBody final Conference conference) {
        final String id = UUID.randomUUID().toString();
        return conferenceDao.getAll().thenApply(conferences -> conferences.stream()
                .filter(booking -> booking.getRoomId().equals(conference.getRoomId()))
                .filter(booking -> doesNotClash(booking, conference))
                .collect(Collectors.toList()))
                .thenAccept(conferences -> {
                    if (!conferences.isEmpty()) {
                        throw new RuntimeException("Conference clashes with another booking");
                    }
                }).thenCompose(__ -> conferenceDao.insert(getParams(conference, id))
                        .thenApply(count -> {
                            if (count > 0) {
                                return ResponseEntity.ok().body("{\"id\":\"" + id + "\"}");
                            } else {
                                throw new RuntimeException("Failed to insert into database");
                            }
                        }).exceptionally(throwable -> {
                            throwable.printStackTrace();
                            return new ResponseEntity<>(throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                        }));
    }

    private boolean doesNotClash(final Conference booking, final Conference conference) {
        final long start1 = booking.getStartTime(), start2 = conference.getStartTime(),
                end1 = booking.getEndTime(), end2 = conference.getEndTime();
        return !((start1 >= start2 && start1 <= end2) || (end1 >= start2 && end1 <= end2));
    }

    @RequestMapping("/getAllConferences")
    public CompletableFuture<ResponseEntity<List<Conference>>> getAllConferences() {
        return conferenceDao.getAll()
                .thenApply(conferences -> ResponseEntity.ok().body(conferences))
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    private Object[] getParams(final Conference conference, final String id) {
        return new Object[]{
                id,
                conference.getParticipants(),
                conference.getStartTime(),
                conference.getEndTime(),
                conference.getBooker(),
                conference.getRoomId()
        };
    }
}
