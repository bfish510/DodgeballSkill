package Util;

import com.amazon.speech.slu.Slot;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Map;

public class FriendlyStrings {

    private static String base = "SlotMap: {%s}";
    private static Joiner joiner = Joiner.on(", ");

    public static String fromSlotMap(Map<String, Slot> slots) {
        Validate.notNull(slots);

        ArrayList<String> entities = Lists.newArrayList();
        for (String key : slots.keySet()) {
            Slot slot = slots.get(key);
            entities.add(String.format("Key: %s, Slot: { name: %s, value: %s }",
                    key, slot.getName(),
                    StringUtils.isBlank(slot.getValue()) ? "null or blank" : slot.getValue()));
        }

        return String.format(base, joiner.join(entities));
    }
}
