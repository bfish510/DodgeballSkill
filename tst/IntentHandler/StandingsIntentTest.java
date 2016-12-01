package IntentHandler;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StandingsIntentTest {

    @Test
    public void emptySlots() {
        StandingsIntent intent = new StandingsIntent();
        IntentRequest request = IntentRequest.builder()
                .withIntent(Intent.builder()
                        .withName("StandingsIntent")
                        .withSlots(Maps.newHashMap())
                        .build())
                .withRequestId("testRequestId")
                .build();
        String process = intent.process(request, null);
        assertEquals("Sorry, I couldn't find anything.", process);
    }

    @Test
    public void nonExistantEntry() {
        StandingsIntent intent = new StandingsIntent();
        IntentRequest request = IntentRequest.builder()
                .withIntent(Intent.builder()
                        .withName("StandingsIntent")
                        .withSlots(ImmutableMap.of(
                                "City", getSlot("City", "new york city"),
                                "Organization", getSlot("Organization", "standard")))
                        .build())
                .withRequestId("testRequestId")
                .build();
        String process = intent.process(request, null);
        assertEquals("Sorry, I couldn't find anything for new york city standard", process);
    }

    @Test
    public void whoWonCityOrg() {
        StandingsIntent intent = new StandingsIntent();
        IntentRequest request = IntentRequest.builder()
                .withIntent(Intent.builder()
                        .withName("StandingsIntent")
                        .withSlots(ImmutableMap.of(
                                "City", getSlot("City", "seattle"),
                                "Organization", getSlot("Organization", "classic")))
                        .build())
                .withRequestId("testRequestId")
                .build();
        String process = intent.process(request, null);
        assertEquals("The winner of the Seattle classic was Not Safe For Work",
                process);
    }

    private Slot getSlot(String name, String value) {
        return Slot.builder()
                .withName(name)
                .withValue(value)
                .build();
    }
}
