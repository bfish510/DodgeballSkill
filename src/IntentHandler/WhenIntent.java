package IntentHandler;

import Events.Event;
import Events.EventDatabase;
import Events.EventQuery;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static Util.SlotUtil.getSlotContents;

public class WhenIntent implements IntentHandlerInterface {

    public String process(IntentRequest request, Session session) {
        WhenSlots whenSlots = new WhenSlots(request);
        Optional<Event> event = lookup(whenSlots);
        if (!event.isPresent()) {
            return "Sorry, I couldn't find anything.";
        }

        return formEventResponse(event.get(), whenSlots);
    }

    private String formEventResponse(Event event, WhenSlots whenSlots) {
        String message = "The next %s is %s";
        return String.format(message,
                whenSlots.formatString(),
                eventString(event));
    }


    public Optional<Event> lookup(IntentHandler.WhenSlots slots) {
        return new EventDatabase().getExact(EventQuery.builder()
                .inFuture(Optional.of(true))
                .organization(slots.getOrganization())
                .city(slots.getCity())
                .ballType(slots.getBallType())
                .modifiers(slots.getModifier())
                .format(slots.getFormat())
                .build()
        );
    }

    private String eventString(Event event) {
        String eventIdentifier;
        if (event.getReadableEventTitle() != null) {
            eventIdentifier = event.getReadableEventTitle();
        } else {
            List<String> fields = Lists.newArrayList(
                    event.getOrganization(),
                    event.getCity(),
                    event.getBallType(),
                    event.getModifiers().stream().collect(Collectors.joining(" ")),
                    event.getFormat());
            eventIdentifier = fields.stream().filter(Objects::nonNull).collect(Collectors.joining(" "));
        }

        if (event.getReadableDate() == null) {
            return eventIdentifier;
        }
        String whenEventString = String.format("%s on %s", eventIdentifier, event.getReadableDate());

        if(event.getRecurrence() != null) {
            whenEventString += event.getRecurrence();
        }

        if(event.getTimeFrameHumanReadable() != null) {
            whenEventString += " from " + event.getTimeFrameHumanReadable();
        }
        return whenEventString;

    }
}

@Getter
class WhenSlots {
    private static final String ORGANIZATION = "Organization";
    private static final String CITY = "City";
    private static final String BALL_TYPE = "BallType";
    private static final String FORMAT = "Format";
    private static final String MODIFIER = "Modifier";

    private final Optional<String> organization;
    private final Optional<String> city;
    private final Optional<String> ballType;
    private final Optional<String> modifier;
    private final Optional<String> format;

    public WhenSlots(IntentRequest request) {
        this.city = getSlotContents(request, CITY);
        this.ballType = getSlotContents(request, BALL_TYPE);
        this.organization = getSlotContents(request, ORGANIZATION);
        this.modifier = getSlotContents(request, MODIFIER);
        this.format = getSlotContents(request, FORMAT);
    }

    public String formatString() {
        List<Optional<String>> fields  = Lists.newArrayList(organization, city, ballType, modifier, format);
        return fields.stream().filter(Optional::isPresent).map(Optional::get).collect(Collectors.joining(" "));
    }
}
