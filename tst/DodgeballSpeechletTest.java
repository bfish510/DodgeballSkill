import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DodgeballSpeechletTest {

    private static final String NOW = "2007-12-03T10:15:30.00Z";
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    private DodgeballSpeechlet speechlet = new DodgeballSpeechlet();

    @Before
    public void before() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void sessionStarted() throws SpeechletException {
        speechlet.onSessionStarted(getTestSessionStartedRequest(Instant.parse(NOW)), getSession());
    }

    @Test
    public void intentRequest() throws SpeechletException {
        IntentRequest whenRequest = getIntentRequest(Instant.parse(NOW), "WhenIntent", Maps.newHashMap());
        Session session = getSession();
        SpeechletResponse whenIntent = speechlet.onIntent(whenRequest, session);

        assertEquals("[INFO] Mon Dec 03 05:15:30 EST 2007 sessionId: testSessionId requestId: testRequestId Received onIntent request\n" +
                "[INFO] Mon Dec 03 05:15:30 EST 2007 sessionId: testSessionId requestId: testRequestId Received Intent with name WhenIntent and slots SlotMap: {}\n",
        outContent.toString());
    }

    private Session getSession() {
        return Session.builder()
                .withSessionId("testSessionId")
                .build();
    }

    private SessionStartedRequest getTestSessionStartedRequest(Instant timestamp) {
        return SessionStartedRequest.builder()
                .withRequestId("testRequestId")
                .withLocale(Locale.US)
                .withTimestamp(Date.from(timestamp))
                .build();
    }

    private IntentRequest getIntentRequest(Instant timestamp, String intentName, Map<String, Slot> slots) {
        return IntentRequest.builder()
                .withRequestId("testRequestId")
                .withLocale(Locale.US)
                .withTimestamp(Date.from(timestamp))
                .withIntent(Intent.builder()
                        .withName(intentName)
                        .withSlots(slots)
                        .build())
                .build();
    }
}
