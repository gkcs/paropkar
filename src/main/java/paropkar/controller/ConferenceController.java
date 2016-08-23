package paropkar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import paropkar.dao.ConferenceDao;
import paropkar.model.Booking;
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

    @RequestMapping("/getBookings")
    public CompletableFuture<ResponseEntity<String>> getBookings(@RequestBody final Booking booking1) {
        return conferenceDao.getAll()
                .thenApply(conferences -> conferences
                        .stream()
                        .filter(booking -> booking.getBooking().getRoomId().equals(booking1.getRoomId()))
                        .filter(booking -> clashes(booking.getBooking(), booking1))
                        .map(Conference::toString)
                        .collect(Collectors.joining(",")))
                .thenApply(conferences -> ResponseEntity.ok().body("{\"conferences\":" + conferences + "}"));
    }

    private boolean clashes(Booking booking, Booking other) {
        return !doesNotClash(booking, other);
    }

    @RequestMapping("/bookConference")
    public CompletableFuture<ResponseEntity<String>> bookConference(@RequestBody final Conference conference) {
        final String id = UUID.randomUUID().toString();
        return conferenceDao.getAll().thenApply(conferences -> conferences.stream()
                .filter(booking -> booking.getBooking().getRoomId().equals(conference.getBooking().getRoomId()))
                .filter(booking -> doesNotClash(booking.getBooking(), conference.getBooking()))
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

    private boolean doesNotClash(final Booking booking, final Booking conference) {
        final String start1 = booking.getStart(), start2 = conference.getStart(),
                end1 = booking.getEnd(), end2 = conference.getEnd();
        return !((start1.compareTo(end2) <= 0 && start1.compareTo(start2) >= 0)
                || (end1.compareTo(start2) >= 0 && end1.compareTo(end2) <= 0));
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
                conference.getBooking().getStart(),
                conference.getBooking().getEnd(),
                conference.getBooker(),
                conference.getBooking().getRoomId()
        };
    }
}
