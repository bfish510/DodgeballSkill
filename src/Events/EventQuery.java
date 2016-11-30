package Events;

import lombok.Builder;
import lombok.Value;

import java.util.Optional;

@Value
@Builder
public class EventQuery {
    private Optional<String> city;
    private Optional<String> organization;
    private Optional<String> ballType;
    private Optional<String> format;
    private Optional<String> modifiers;
    private Optional<Boolean> inPast;
    private Optional<Boolean> inFuture;

    public static class EventQueryBuilder {
        private Optional<String> city = Optional.empty();
        private Optional<String> organization = Optional.empty();
        private Optional<String> ballType = Optional.empty();
        private Optional<String> format = Optional.empty();
        private Optional<String> modifiers = Optional.empty();
        private Optional<Boolean> inPast = Optional.empty();
        private Optional<Boolean> inFuture = Optional.empty();
    }

}
