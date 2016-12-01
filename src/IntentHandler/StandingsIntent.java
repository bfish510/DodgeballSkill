package IntentHandler;

import Events.Event;
import Events.EventDatabase;
import Events.EventQuery;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

import static Util.SlotUtil.getSlotContents;

public class StandingsIntent implements IntentHandlerInterface {

    public String process(IntentRequest request, Session session) {
        SessionSlots sessionSlots = new SessionSlots(request);
        Optional<Event> event = lookup(sessionSlots);
        if (!event.isPresent()) {
            if (StringUtils.isBlank(sessionSlots.getSpecifiedSlotString())) {
                return "Sorry, I couldn't find anything.";
            }
            return "Sorry, I couldn't find anything for " + sessionSlots.getSpecifiedSlotString();
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
        this.organization = getSlotContents(request, ORGANIZATION);
        this.city = getSlotContents(request, CITY);
    }

    public Optional<String> getOrganization() {
        return organization;
    }

    public Optional<String> getCity() {
        return city;
    }

    public String getSpecifiedSlotString() {
        return city.orElse("") + " " + organization.orElse("");
    }
}
