package IntentHandler;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WhenIntentTest {

    @Test
    public void emptySlots() {
        WhenIntent whenIntent = new WhenIntent();
        IntentRequest request = IntentRequest.builder()
                .withIntent(Intent.builder()
                        .withName("WhenIntent")
                        .withSlots(Maps.newHashMap())
                        .build())
                .withRequestId("testRequestId")
                .build();
        String process = whenIntent.process(request, null);
        assertEquals("Sorry, I couldn't find anything.", process);
    }

    @Test
    public void nextCityFormat() {
        WhenIntent whenIntent = new WhenIntent();
        IntentRequest request = IntentRequest.builder()
                .withIntent(Intent.builder()
                        .withName("WhenIntent")
                        .withSlots(ImmutableMap.of(
                                "City", getSlot("City", "seattle"),
                                "Format", getSlot("Format", "tournament")))
                        .build())
                .withRequestId("testRequestId")
                .build();
        String process = whenIntent.process(request, null);
        assertEquals("The next seattle tournament is Super Sunday Morning No-Sting Cartoon Tourney Spectacular" +
                        " on December 11 2016",
                process);
    }

    private Slot getSlot(String name, String value) {
        return Slot.builder()
                .withName(name)
                .withValue(value)
                .build();
    }
}
