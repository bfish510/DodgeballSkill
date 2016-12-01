import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;
import com.google.common.collect.Sets;

public final class DodgeballSpeechletHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds;

    static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
        supportedApplicationIds = Sets.newHashSet(System.getenv().get("SUPPORTED_SKILL_APP_ID"));
    }

    public DodgeballSpeechletHandler() {
        super(new DodgeballSpeechlet(), supportedApplicationIds);
    }
}
