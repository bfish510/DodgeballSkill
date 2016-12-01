import IntentHandler.StandingsIntent;
import IntentHandler.WhenIntent;
import Util.FriendlyStrings;
import Util.LogHelper;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;

import static Util.SpeechUtils.newAskResponse;
import static Util.SpeechUtils.newTellResponse;

public class DodgeballSpeechlet implements Speechlet {

    private static final LogHelper logger = new LogHelper();

    @Override
    public void onSessionStarted(SessionStartedRequest request, Session session) throws SpeechletException {
        logger.info("Received onSessionStarted request", session, request);
    }

    @Override
    public SpeechletResponse onLaunch(LaunchRequest request, Session session) throws SpeechletException {
        logger.info("Received onLaunch request", session, request);

        String speechOutput = "Welcome to the dodge ball Skill. You can ask me about upcoming events and past winners";
        String repromptOutput = "You can ask me about upcoming events or past winners of tournaments.";

        return newAskResponse(speechOutput, false, repromptOutput, false);
    }

    @Override
    public SpeechletResponse onIntent(IntentRequest request, Session session) throws SpeechletException {
        logger.info("Received onIntent request", session, request);
        logger.info("Received Intent with name %s and slots %s",
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
            return new WhenIntent().process(request, session);
        }
        throw new IllegalArgumentException(String.format("The intent %s is not supported at this time", request.getIntent().getName()));
    }

    @Override
    public void onSessionEnded(SessionEndedRequest request, Session session) throws SpeechletException {
        logger.info("Received onSessionEnded request", session, request);
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
                "You can ask when the next league, tournament, or pickup game takes place." +
                        "<break time=\"0.2s\" />" +
                        "You can also ask who won a past league or tournament.";
        return newAskResponse(speechOutput, false, "<speak>" + repromptText + "</speak>", true);
    }
}
