package IntentHandler;

import Events.Event;
import Events.EventDatabase;
import Events.EventQuery;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;

import java.util.Optional;

public class StandingsIntent implements IntentHandlerInterface {

    public String process(IntentRequest request, Session session) {
        Optional<Event> event = lookup(new SessionSlots(request));
        if (!event.isPresent()) {
            return "Sorry, I couldn't find anything.";
        }
        
        return formEventResponse(event.get());
    }

    private String formEventResponse(Event event) {
        String message = "The winner of the %s %s was %s";
        return String.format(message,
                event.getCity() == null ? "" : event.getCity(),
                event.getOrganization(), //An implicit validation.
                event.getWinner());
    }


    public Optional<Event> lookup(SessionSlots slots) {
        return new EventDatabase().getExact(EventQuery.builder()
                .inPast(Optional.of(true))
                .organization(slots.getOrganization())
                .city(slots.getCity())
                .build()
        );
    }
}

class SessionSlots {
    private static final String ORGANIZATION = "Organization";
    private static final String CITY = "City";

    private final Optional<String> organization;
    private final Optional<String> city;

    public SessionSlots(IntentRequest request) {
        Slot organizationSlot = request.getIntent().getSlot(ORGANIZATION);
        this.organization = organizationSlot == null ? Optional.empty() : Optional.of(organizationSlot.getValue());
        Slot citySlot = request.getIntent().getSlot(CITY);
        this.city = organizationSlot == null ? Optional.empty() : Optional.of(citySlot.getValue());
    }

    public Optional<String> getOrganization() {
        return organization;
    }

    public Optional<String> getCity() {
        return city;
    }
}
