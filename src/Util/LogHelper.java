package Util;

import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletRequest;

public class LogHelper {

    public static void info(String toLog, Session session, SpeechletRequest request, String... objects) {
        String prefix = String.format("[INFO] %s sessionId: %s requestId: %s ",
                request.getTimestamp().toString(),
                session.getSessionId(),
                request.getRequestId());

        System.out.println(prefix + String.format(toLog, objects));
    }

    public static void err(String toLog, Session session, SpeechletRequest request, String... objects) {
        System.err.println(String.format("[ERROR] %s sessionId: %s requestId: %s " + toLog,
                request.getTimestamp().toString(),
                session.getSessionId(),
                request.getRequestId(),
                objects));
    }

}
