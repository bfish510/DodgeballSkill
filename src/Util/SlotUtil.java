package Util;

import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;

import java.util.Optional;

public class SlotUtil {
    public static Optional<String> getSlotContents(IntentRequest request, String field) {
        Slot slot = request.getIntent().getSlot(field);
        return slot == null || slot.getValue() == null ? Optional.empty() : Optional.of(slot.getValue());
    }
}
