package Events;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.joda.time.DateTime;

import java.util.List;

@Builder
@Value
public class Event {
    private String city;
    private String organization;
    private String ballType;
    private String format;
    @NonNull private List<String> modifiers;

    private String winner;
    private DateTime date;
    private String readableDate;
    private String recurrence;
    private String where;
    private String timeFrameHumanReadable;
}
