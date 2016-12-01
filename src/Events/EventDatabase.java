package Events;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventDatabase {
    private static List<Event> events = Lists.newArrayList();

    public Optional<Event> getExact(EventQuery event) {
        if (allSlotsEmpty(event)) {
            return Optional.empty();
        }

        List<Event> events = EventDatabase.events.stream()
                .filter(e -> !event.getBallType().isPresent()
                        || e.getBallType() != null
                        && event.getBallType().get().toLowerCase().equals(e.getBallType().toLowerCase()))
                .filter(e -> !event.getCity().isPresent()
                        || e.getCity() != null
                        && event.getCity().get().toLowerCase().equals(e.getCity().toLowerCase()))
                .filter(e -> !event.getFormat().isPresent()
                        || e.getFormat() != null
                        && event.getFormat().get().toLowerCase().equals(e.getFormat().toLowerCase()))
                .filter(e -> !event.getModifiers().isPresent()
                        || e.getModifiers().contains(event.getModifiers().get().toLowerCase().toLowerCase()))
                .filter(e -> !event.getOrganization().isPresent()
                        || e.getOrganization() != null
                        && event.getOrganization().get().toLowerCase().equals(e.getOrganization().toLowerCase()))
                .filter(e -> !event.getInFuture().isPresent()
                        || e.getDate() != null
                        && e.getDate().isAfter(DateTime.now().minusDays(1)))
                .filter(e -> !event.getInPast().isPresent()
                        || e.getDate() != null
                        && e.getDate().isBefore(DateTime.now().minusDays(1)))
                .collect(Collectors.toList());

        if (event.getInFuture().orElse(false)) {
            events.sort((e1, e2) -> e1.getDate().compareTo(e2.getDate().toInstant()));
        } else if (event.getInPast().orElse(false)){
            events.sort((e1, e2) -> e2.getDate().compareTo(e1.getDate().toInstant()));
        }

        return events.isEmpty() ? Optional.empty() : Optional.of(events.get(0));
    }

    private boolean allSlotsEmpty(EventQuery event) {
        return !event.getOrganization().isPresent()
                && !event.getFormat().isPresent()
                && !event.getCity().isPresent()
                && !event.getModifiers().isPresent()
                && !event.getBallType().isPresent();
    }

    //temp database until moved into dynamo
    static {
        events.add(Event.builder()
                .ballType("rubber")
                .city("Seattle")
                .date(DateTime.parse("2016-03-26"))
                .readableDate("March 26 2016")
                .organization("classic")
                .winner("Not Safe For Work")
                .format("tournament")
                .modifiers(Lists.newArrayList())
                .build());
        events.add(Event.builder()
                .ballType("rubber")
                .city("Seattle")
                .date(DateTime.parse("2017-03-26"))
                .readableDate("March 26 2017")
                .organization("classic")
                .format("tournament")
                .modifiers(Lists.newArrayList())
                .build());
        events.add(Event.builder()
                .ballType("rubber")
                .city("Portland")
                .date(DateTime.parse("2016-06-18"))
                .readableDate("June 18 2016")
                .organization("Dodge North West")
                .winner("Not Safe For Work")
                .format("tournament")
                .modifiers(Lists.newArrayList())
                .build());
        events.add(Event.builder()
                .ballType("rubber")
                .city("Corvallis")
                .date(DateTime.parse("2016-04-16"))
                .readableDate("April 4 2016")
                .organization("Dodge North West")
                .winner("Pizza")
                .format("tournament")
                .modifiers(Lists.newArrayList())
                .build());
        events.add(Event.builder()
                .ballType("rubber")
                .city("Seattle")
                .date(DateTime.parse("2016-07-23"))
                .readableDate("July 23 2016")
                .organization("Block Party Showdown")
                .winner("Here For Beer")
                .format("tournament")
                .modifiers(Lists.newArrayList())
                .build());
        events.add(Event.builder()
                .ballType("rubber")
                .city("Seattle")
                .date(DateTime.parse("2016-12-05"))
                .readableDate("December 5 2016")
                .recurrence("every monday")
                .where("Loyal Heights Community Center in Ballard")
                .timeFrameHumanReadable("seven to nine p.m.")
                .format("pick up")
                .modifiers(Lists.newArrayList())
                .build());
        events.add(Event.builder()
                .ballType("rubber")
                .city("Seattle")
                .date(DateTime.parse("2016-12-12"))
                .readableDate("December 12 2016")
                .recurrence("every monday")
                .where("Loyal Heights Community Center in Ballard")
                .timeFrameHumanReadable("seven to nine p.m.")
                .format("pick up")
                .modifiers(Lists.newArrayList())
                .build());
        events.add(Event.builder()
                .ballType("rubber")
                .city("Seattle")
                .date(DateTime.parse("2016-12-19"))
                .readableDate("December 19 2016")
                .recurrence("every monday")
                .where("Loyal Heights Community Center in Ballard")
                .timeFrameHumanReadable("seven to nine p.m.")
                .format("pick up")
                .modifiers(Lists.newArrayList())
                .build());
        events.add(Event.builder()
                .ballType("foam")
                .city("Seattle")
                .date(DateTime.parse("2016-11-30"))
                .readableDate("November 30 2016")
                .recurrence("every wednesday")
                .where("Green Lake Community Center")
                .timeFrameHumanReadable("seven to nine p.m.")
                .format("pick up")
                .modifiers(Lists.newArrayList())
                .build());
        events.add(Event.builder()
                .ballType("foam")
                .city("Seattle")
                .date(DateTime.parse("2016-12-07"))
                .readableDate("December 7 2016")
                .recurrence("every wednesday")
                .where("Green Lake Community Center")
                .timeFrameHumanReadable("seven to nine p.m.")
                .format("pick up")
                .modifiers(Lists.newArrayList())
                .build());
        events.add(Event.builder()
                .ballType("foam")
                .city("Seattle")
                .date(DateTime.parse("2016-12-14"))
                .readableDate("December 14 2016")
                .recurrence("every wednesday")
                .where("Green Lake Community Center")
                .timeFrameHumanReadable("seven to nine p.m.")
                .format("pick up")
                .modifiers(Lists.newArrayList())
                .build());
        events.add(Event.builder()
                .ballType("foam")
                .city("Seattle")
                .date(DateTime.parse("2016-11-07"))
                .readableDate("November 7 2016")
                .recurrence("every wednesday")
                .where("Green Lake Community Center")
                .timeFrameHumanReadable("seven to nine p.m.")
                .format("pick up")
                .modifiers(Lists.newArrayList())
                .build());
        events.add(Event.builder()
                .ballType("foam")
                .city("Seattle")
                .date(DateTime.parse("2016-11-14"))
                .readableDate("November 11 2016")
                .recurrence("every wednesday")
                .where("Green Lake Community Center")
                .timeFrameHumanReadable("seven to nine p.m.")
                .format("pick up")
                .modifiers(Lists.newArrayList())
                .build());
    }
}
