package paropkar.controller;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paropkar.dao.ConferenceDao;
import paropkar.model.Booking;
import paropkar.model.Conference;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
public class ConferenceController {
    public static final Gson GSON = new Gson();
    private final ConferenceDao conferenceDao;
    //private final SimpleDateFormat simpleDateFormat;

    public ConferenceController() {
        //this.simpleDateFormat=new SimpleDateFormat("yyyy-MM-ddThh:mm:ss");
        //2016-06-09T16:00:00-05:00
        this.conferenceDao = new ConferenceDao();
    }

    @CrossOrigin("*")
    @RequestMapping("/getConference")
    public CompletableFuture<ResponseEntity<Conference>> getConference(@RequestBody final String id) {
        return conferenceDao.getObject(id)
                .thenApply(conference -> ResponseEntity.ok().body(conference))
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    @CrossOrigin("*")
    @RequestMapping("/getBookings")
    public CompletableFuture<ResponseEntity<String>> getBookings(@RequestParam final String roomId,
                                                                 @RequestParam final String start,
                                                                 @RequestParam final String end) {
        return conferenceDao.getAll()
                .thenApply(conferences -> conferences
                        .stream()
                        .map(Conference::getBooking)
                        .filter(booking -> booking.getRoomId().equals(roomId))
                        .map(Booking::convertToWrapper)
                        .collect(Collectors.toList()))
                .thenApply(GSON::toJson)
                .thenApply(bookings -> ResponseEntity.ok().body(bookings));
    }

    @CrossOrigin("*")
    @RequestMapping("/bookConference")
    public CompletableFuture<ResponseEntity<String>> bookConference(@RequestParam final String jid,
                                                                    @RequestParam final String title,
                                                                    @RequestParam final String start,
                                                                    @RequestParam final String end,
                                                                    @RequestParam final String roomId) {
        final String id = UUID.randomUUID().toString();
        final Booking book = new Booking(title, start, end, roomId);
        return conferenceDao.insert(getParams(new Conference("", "", jid, book), id))
                .thenApply(count -> {
                    if (count > 0) {
                        return ResponseEntity.ok().body("{\"id\":\"" + id + "\"}");
                    } else {
                        throw new RuntimeException("Failed to insert into database");
                    }
                }).exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return new ResponseEntity<>(throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    private boolean clashes(final Booking booking, final Booking other) {
        return !doesNotClash(booking.getStart(), other.getStart(), booking.getEnd(), other.getEnd());
    }

    private boolean doesNotClash(final String start1, final String start2, final String end1, final String end2) {
        return !((start1.compareTo(end2) <= 0 && start1.compareTo(start2) >= 0)
                || (end1.compareTo(start2) >= 0 && end1.compareTo(end2) <= 0));
    }

    @CrossOrigin("*")
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
                conference.getBooking().getRoomId(),
                conference.getBooking().getTitle()
        };
    }
}
