package Util;

import com.amazon.speech.slu.Slot;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class FriendlyStringsTest {

    @Test(expected = NullPointerException.class)
    public void nullSlotMap() {
        FriendlyStrings.fromSlotMap(null);
    }

    @Test
    public void emptyMap() {
        String output = FriendlyStrings.fromSlotMap(Maps.newHashMap());
        assertEquals("SlotMap: {}", output);
    }

    @Test
    public void EntitiesMap() {
        HashMap<String, Slot> slotMap = Maps.newHashMap();
        slotMap.put("first", Slot.builder().withName("firstName").withValue(null).build());
        slotMap.put("second", Slot.builder().withName("secondName").withValue("     ").build());
        slotMap.put("third", Slot.builder().withName("thirdName").withValue("Value1").build());
        slotMap.put("fourth", Slot.builder().withName("fourthName").withValue("Value2").build());
        String output = FriendlyStrings.fromSlotMap(slotMap);
        assertEquals("SlotMap: {Key: third, Slot: { name: thirdName, value: Value1 }, Key: fourth, Slot: { name: fourthName, value: Value2 }, Key: first, Slot: { name: firstName, value: null or blank }, Key: second, Slot: { name: secondName, value: null or blank }}",
                output);
    }

}
