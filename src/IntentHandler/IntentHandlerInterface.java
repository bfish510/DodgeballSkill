package IntentHandler;

import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;

public interface IntentHandlerInterface {

    String process(IntentRequest request, Session session);
}
