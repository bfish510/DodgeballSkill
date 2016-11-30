import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.SpeechletException;
import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.time.Instant;
import java.util.Date;
import java.util.Locale;

import static org.mockito.Mockito.*;

public class DodgeballSpeechletTest {

    private static final String NOW = "2007-12-03T10:15:30.00Z";
    private Logger logger = mock(Logger.class);
    private DodgeballSpeechlet speechlet;

    @Before
    public void before() {
        speechlet = new DodgeballSpeechlet(logger);
    }

    @After
    public void after() {
        verifyZeroInteractions(logger);
    }

    @Test
    public void InitialRequest() throws SpeechletException {
        speechlet.onSessionStarted(getTestSessionStartedRequest(Instant.parse(NOW)), getSession());

        verify(logger).info("{} sessionId: {} requestId: {} Received onSessionStarted request",
                "Mon Dec 03 02:15:30 PST 2007",
                "testSessionId",
                "testRequestId",
                Lists.newArrayList().toArray());
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
}
