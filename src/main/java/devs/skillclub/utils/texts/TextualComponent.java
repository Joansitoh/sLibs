package devs.skillclub.utils.texts;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.gson.stream.JsonWriter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class TextualComponent implements Cloneable {

    static {
        ConfigurationSerialization.registerClass(ArbitraryTextTypeComponent.class);
        ConfigurationSerialization.registerClass(ComplexTextTypeComponent.class);
    }

    static TextualComponent deserialize(Map<String, Object> map) {
        if (map.containsKey("key") && map.size() == 2 && map.containsKey("value")) {

            return ArbitraryTextTypeComponent.deserialize(map);
        } else if (map.size() >= 2 && map.containsKey("key") && !map.containsKey("value")) {

            return ComplexTextTypeComponent.deserialize(map);
        }
        return null;
    }

    static boolean isTextKey(String key) {
        return key.equals("translate") || key.equals("text") || key.equals("score") || key.equals("selector");
    }

    static boolean isTranslatableText(TextualComponent component) {
        return component instanceof ComplexTextTypeComponent && ((ComplexTextTypeComponent) component).getKey().equals("translate");
    }

    public static TextualComponent rawText(String textValue) {
        return new ArbitraryTextTypeComponent("text", textValue);
    }

    public static TextualComponent localizedText(String translateKey) {
        return new ArbitraryTextTypeComponent("translate", translateKey);
    }

    private static void throwUnsupportedSnapshot() {
        throw new UnsupportedOperationException("This feature is only supported in snapshot releases.");
    }

    public static TextualComponent objectiveScore(String scoreboardObjective) {
        return objectiveScore("*", scoreboardObjective);
    }

    public static TextualComponent objectiveScore(String playerName, String scoreboardObjective) {
        throwUnsupportedSnapshot();

        return new ComplexTextTypeComponent("score", ImmutableMap.<String, String>builder()
                .put("name", playerName)
                .put("objective", scoreboardObjective)
                .build());
    }

    public static TextualComponent selector(String selector) {
        throwUnsupportedSnapshot();

        return new ArbitraryTextTypeComponent("selector", selector);
    }

    @Override
    public String toString() {
        return getReadableString();
    }

    public abstract String getKey();

    public abstract String getReadableString();

    @Override
    public abstract TextualComponent clone() throws CloneNotSupportedException;

    public abstract void writeJson(JsonWriter writer) throws IOException;

    private static final class ArbitraryTextTypeComponent extends TextualComponent implements ConfigurationSerializable {

        private String _key;
        private String _value;

        public ArbitraryTextTypeComponent(String key, String value) {
            setKey(key);
            setValue(value);
        }

        public static ArbitraryTextTypeComponent deserialize(Map<String, Object> map) {
            return new ArbitraryTextTypeComponent(map.get("key").toString(), map.get("value").toString());
        }

        @Override
        public String getKey() {
            return _key;
        }

        public void setKey(String key) {
            Preconditions.checkArgument(key != null && !key.isEmpty(), "The key must be specified.");
            _key = key;
        }

        public String getValue() {
            return _value;
        }

        public void setValue(String value) {
            Preconditions.checkArgument(value != null, "The value must be specified.");
            _value = value;
        }

        @Override
        public TextualComponent clone() throws CloneNotSupportedException {

            return new ArbitraryTextTypeComponent(getKey(), getValue());
        }

        @Override
        public void writeJson(JsonWriter writer) throws IOException {
            writer.name(getKey()).value(getValue());
        }

        @SuppressWarnings("serial")
        public Map<String, Object> serialize() {
            return new HashMap<String, Object>() {{
                put("key", getKey());
                put("value", getValue());
            }};
        }

        @Override
        public String getReadableString() {
            return getValue();
        }
    }

    private static final class ComplexTextTypeComponent extends TextualComponent implements ConfigurationSerializable {

        private String _key;
        private Map<String, String> _value;

        public ComplexTextTypeComponent(String key, Map<String, String> values) {
            setKey(key);
            setValue(values);
        }

        public static ComplexTextTypeComponent deserialize(Map<String, Object> map) {
            String key = null;
            Map<String, String> value = new HashMap<String, String>();
            for (Map.Entry<String, Object> valEntry : map.entrySet()) {
                if (valEntry.getKey().equals("key")) {
                    key = (String) valEntry.getValue();
                } else if (valEntry.getKey().startsWith("value.")) {
                    value.put(((String) valEntry.getKey()).substring(6), valEntry.getValue().toString());
                }
            }
            return new ComplexTextTypeComponent(key, value);
        }

        @Override
        public String getKey() {
            return _key;
        }

        public void setKey(String key) {
            Preconditions.checkArgument(key != null && !key.isEmpty(), "The key must be specified.");
            _key = key;
        }

        public Map<String, String> getValue() {
            return _value;
        }

        public void setValue(Map<String, String> value) {
            Preconditions.checkArgument(value != null, "The value must be specified.");
            _value = value;
        }

        @Override
        public TextualComponent clone() throws CloneNotSupportedException {

            return new ComplexTextTypeComponent(getKey(), getValue());
        }

        @Override
        public void writeJson(JsonWriter writer) throws IOException {
            writer.name(getKey());
            writer.beginObject();
            for (Map.Entry<String, String> jsonPair : _value.entrySet()) {
                writer.name(jsonPair.getKey()).value(jsonPair.getValue());
            }
            writer.endObject();
        }

        @SuppressWarnings("serial")
        public Map<String, Object> serialize() {
            return new HashMap<String, Object>() {{
                put("key", getKey());
                for (Entry<String, String> valEntry : getValue().entrySet()) {
                    put("value." + valEntry.getKey(), valEntry.getValue());
                }
            }};
        }

        @Override
        public String getReadableString() {
            return getKey();
        }
    }
}
