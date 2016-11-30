package Events;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EventDatabaseTest {

    @Test
    public void getExactNoFields() {
        Optional<Event> event = new EventDatabase().getExact(EventQuery.builder().build());
        assertFalse(event.isPresent());
    }

    @Test
    public void getNextSeattlePickup() {
        Optional<Event> event = new EventDatabase().getExact(EventQuery.builder()
                .city(Optional.of("Seattle"))
                .format(Optional.of("pick up"))
                .inFuture(Optional.of(true))
                .build());
        assertTrue("Event is not present", event.isPresent());

        Event presentEvent = event.get();
        assertEquals("2016-11-30T00:00:00.000-08:00", presentEvent.getDate().toString());
        assertEquals("foam", presentEvent.getBallType().toString());
    }

    @Test
    public void getPreviousSeattlePickup() {
        Optional<Event> event = new EventDatabase().getExact(EventQuery.builder()
                .city(Optional.of("Seattle"))
                .format(Optional.of("pick up"))
                .inPast(Optional.of(true))
                .build());
        assertTrue("Event is not present", event.isPresent());

        Event presentEvent = event.get();
        assertEquals("2016-11-14T00:00:00.000-08:00", presentEvent.getDate().toString());
        assertEquals("foam", presentEvent.getBallType().toString());
    }

    @Test
    public void getClassicWinner() {
        Optional<Event> event = new EventDatabase().getExact(EventQuery.builder()
                .organization(Optional.of("Classic"))
                .inPast(Optional.of(true))
                .build());
        assertTrue("Event is not present", event.isPresent());

        Event presentEvent = event.get();
        assertEquals("2016-03-26T00:00:00.000-07:00", presentEvent.getDate().toString());
        assertEquals("Seattle", presentEvent.getCity().toString());
        assertEquals("tournament", presentEvent.getFormat().toString());
    }
}
