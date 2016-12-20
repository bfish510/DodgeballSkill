package IntentHandler;

import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;

public class StopIntent implements IntentHandlerInterface {

    @Override
    public String process(IntentRequest request, Session session) {
        return "Goodbye";
    }
}
