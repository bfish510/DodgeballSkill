import IntentHandler.StandingsIntent;
import Util.FriendlyStrings;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.OutputSpeech;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SsmlOutputSpeech;
import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DodgeballSpeechlet implements Speechlet {

    private final LogHelper logger;

    public DodgeballSpeechlet() {
        this.logger = new LogHelper(LoggerFactory.getLogger(DodgeballSpeechlet.class));
    }

    @VisibleForTesting
    DodgeballSpeechlet(Logger logger) {
        this.logger = new LogHelper(logger);
    }

    @Override
    public void onSessionStarted(SessionStartedRequest request, Session session) throws SpeechletException {
        logger.info("Received onSessionStarted request", session, request);
    }

    @Override
    public SpeechletResponse onLaunch(LaunchRequest request, Session session) throws SpeechletException {
        logger.info("Received onLaunch request", session, request);

        String speechOutput = "Welcome to the Dodgeball Skill. You can ask me about upcoming events and past winners";
        String repromptOutput = "You can ask me about upcoming events or past winners of tournaments.";

        return newAskResponse(speechOutput, false, repromptOutput, false);
    }

    @Override
    public SpeechletResponse onIntent(IntentRequest request, Session session) throws SpeechletException {
        logger.info("Received onIntent request", session, request);
        logger.info("Received Intent with name {} and slots {}",
                session,
                request,
                request.getIntent().getName(),
                FriendlyStrings.fromSlotMap(request.getIntent().getSlots()));

        String speechOutput = processRequest(request, session);

        return newTellResponse(String.format(speechOutput, request.getIntent().getName()), false);
    }

    private String processRequest(IntentRequest request, Session session) {
        String intentName = request.getIntent().getName();
        if (intentName.equals(DodgeballIntent.STANDINGS.getIntentName())) {
            return new StandingsIntent().process(request, session);
        } else if (intentName.equals(DodgeballIntent.WHEN.getIntentName())) {
            return new StandingsIntent().process(request, session);
        }
        throw new IllegalArgumentException(String.format("The intent %s is not supported at this time", request.getIntent().getName()));
    }

    @Override
    public void onSessionEnded(SessionEndedRequest request, Session session) throws SpeechletException {
        logger.info("Received onSessionEnded request", session, request);
    }

    private class LogHelper {
        private Logger logger;

        public LogHelper(Logger logger) {
            this.logger = logger;
        }

        public void info(String toLog, Session session, SpeechletRequest request, Object... objects) {
            logger.info("{} sessionId: {} requestId: {} " + toLog,
                    request.getTimestamp().toString(),
                    session.getSessionId(),
                    request.getRequestId(),
                    objects);
        }
    }

    /**
     * From example speechlet for savvyconsumer
     */
    private SpeechletResponse newAskResponse(String stringOutput, boolean isOutputSsml,
                                             String repromptText, boolean isRepromptSsml) {
        OutputSpeech outputSpeech, repromptOutputSpeech;
        if (isOutputSsml) {
            outputSpeech = new SsmlOutputSpeech();
            ((SsmlOutputSpeech) outputSpeech).setSsml(stringOutput);
        } else {
            outputSpeech = new PlainTextOutputSpeech();
            ((PlainTextOutputSpeech) outputSpeech).setText(stringOutput);
        }

        if (isRepromptSsml) {
            repromptOutputSpeech = new SsmlOutputSpeech();
            ((SsmlOutputSpeech) repromptOutputSpeech).setSsml(repromptText);
        } else {
            repromptOutputSpeech = new PlainTextOutputSpeech();
            ((PlainTextOutputSpeech) repromptOutputSpeech).setText(repromptText);
        }
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptOutputSpeech);
        return SpeechletResponse.newAskResponse(outputSpeech, reprompt);
    }

    /**
     * From example speechlet for savvyconsumer
     */
    private SpeechletResponse newTellResponse(String stringOutput, boolean isOutputSsml) {
        OutputSpeech outputSpeech;
        if (isOutputSsml) {
            outputSpeech = new SsmlOutputSpeech();
            ((SsmlOutputSpeech) outputSpeech).setSsml(stringOutput);
        } else {
            outputSpeech = new PlainTextOutputSpeech();
            ((PlainTextOutputSpeech) outputSpeech).setText(stringOutput);
        }
        return SpeechletResponse.newTellResponse(outputSpeech);
    }

    /**
     * From example speechlet for savvyconsumer
     */
    private SpeechletResponse getHelp() {
        String speechOutput =
                "You can ask when the next league, tournament, or pickup game takes place." +
                        "<break time=\"0.2s\" />" +
                        "You can also ask who won a past league or tournament.";
        String repromptText =
                "I'm sorry I didn't understand that. You can say things like," +
                        "books <break time=\"0.2s\" /> " +
                        "movies <break time=\"0.2s\" /> " +
                        "music. Or you can say exit. Now, what can I help you with?";
        return newAskResponse(speechOutput, false, "<speak>" + repromptText + "</speak>", true);
    }
}
