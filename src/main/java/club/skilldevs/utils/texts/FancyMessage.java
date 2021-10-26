package club.skilldevs.utils.texts;

import club.skilldevs.utils.ChatUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.logging.Level;

import static club.skilldevs.utils.texts.TextualComponent.rawText;

public class FancyMessage implements JsonRepresentedObject, Cloneable, Iterable<MessagePart>, ConfigurationSerializable {

    private static JsonParser _stringParser = new JsonParser();

    static {
        ConfigurationSerialization.registerClass(FancyMessage.class);
    }

    private List<MessagePart> messageParts;
    private String jsonString;
    private boolean dirty;


    public FancyMessage(final String firstPartText) {
        this(rawText(firstPartText));
    }

    public FancyMessage(final TextualComponent firstPartText) {
        messageParts = new ArrayList<MessagePart>();
        messageParts.add(new MessagePart(firstPartText));
        jsonString = null;
        dirty = false;
    }


    public FancyMessage() {
        this((TextualComponent) null);
    }

    @SuppressWarnings("unchecked")
    public static FancyMessage deserialize(Map<String, Object> serialized) {
        FancyMessage msg = new FancyMessage();
        msg.messageParts = (List<MessagePart>) serialized.get("messageParts");
        msg.jsonString = serialized.containsKey("JSON") ? serialized.get("JSON").toString() : null;
        msg.dirty = !serialized.containsKey("JSON");
        return msg;
    }

    public static FancyMessage deserialize(String json) {
        JsonObject serialized = _stringParser.parse(json).getAsJsonObject();
        JsonArray extra = serialized.getAsJsonArray("extra");
        FancyMessage returnVal = new FancyMessage();
        returnVal.messageParts.clear();
        for (JsonElement mPrt : extra) {
            MessagePart component = new MessagePart();
            JsonObject messagePart = mPrt.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : messagePart.entrySet()) {

                if (TextualComponent.isTextKey(entry.getKey())) {

                    Map<String, Object> serializedMapForm = new HashMap<String, Object>();
                    serializedMapForm.put("key", entry.getKey());
                    if (entry.getValue().isJsonPrimitive()) {

                        serializedMapForm.put("value", entry.getValue().getAsString());
                    } else {

                        for (Map.Entry<String, JsonElement> compositeNestedElement : entry.getValue().getAsJsonObject().entrySet()) {
                            serializedMapForm.put("value." + compositeNestedElement.getKey(), compositeNestedElement.getValue().getAsString());
                        }
                    }
                    component.text = TextualComponent.deserialize(serializedMapForm);
                } else if (MessagePart.stylesToNames.inverse().containsKey(entry.getKey())) {
                    if (entry.getValue().getAsBoolean()) {
                        component.styles.add(MessagePart.stylesToNames.inverse().get(entry.getKey()));
                    }
                } else if (entry.getKey().equals("color")) {
                    component.color = ChatColor.valueOf(entry.getValue().getAsString().toUpperCase());
                } else if (entry.getKey().equals("clickEvent")) {
                    JsonObject object = entry.getValue().getAsJsonObject();
                    component.clickActionName = object.get("action").getAsString();
                    component.clickActionData = object.get("value").getAsString();
                } else if (entry.getKey().equals("hoverEvent")) {
                    JsonObject object = entry.getValue().getAsJsonObject();
                    component.hoverActionName = object.get("action").getAsString();
                    if (object.get("value").isJsonPrimitive()) {

                        component.hoverActionData = new JsonString(object.get("value").getAsString());
                    } else {


                        component.hoverActionData = deserialize(object.get("value").toString());
                    }
                } else if (entry.getKey().equals("insertion")) {
                    component.insertionData = entry.getValue().getAsString();
                } else if (entry.getKey().equals("with")) {
                    for (JsonElement object : entry.getValue().getAsJsonArray()) {
                        if (object.isJsonPrimitive()) {
                            component.translationReplacements.add(new JsonString(object.getAsString()));
                        } else {


                            component.translationReplacements.add(deserialize(object.toString()));
                        }
                    }
                }
            }
            returnVal.messageParts.add(component);
        }
        return returnVal;
    }

    @Override
    public FancyMessage clone() throws CloneNotSupportedException {
        FancyMessage instance = (FancyMessage) super.clone();
        instance.messageParts = new ArrayList<MessagePart>(messageParts.size());
        for (int i = 0; i < messageParts.size(); i++) {
            instance.messageParts.add(i, messageParts.get(i).clone());
        }
        instance.dirty = false;
        instance.jsonString = null;
        return instance;
    }

    public FancyMessage text(String text) {
        MessagePart latest = latest();
        latest.text = rawText(text);
        dirty = true;
        return this;
    }

    public FancyMessage text(TextualComponent text) {
        MessagePart latest = latest();
        latest.text = text;
        dirty = true;
        return this;
    }

    public FancyMessage color(final ChatColor color) {
        if (!color.isColor()) {
            throw new IllegalArgumentException(color.name() + " is not a color");
        }
        latest().color = color;
        dirty = true;
        return this;
    }

    public FancyMessage style(ChatColor... styles) {
        for (final ChatColor style : styles) {
            if (!style.isFormat()) {
                throw new IllegalArgumentException(style.name() + " is not a style");
            }
        }
        latest().styles.addAll(Arrays.asList(styles));
        dirty = true;
        return this;
    }

    public FancyMessage file(final String path) {
        onClick("open_file", path);
        return this;
    }

    public FancyMessage link(final String url) {
        onClick("open_url", url);
        return this;
    }

    public FancyMessage suggest(final String command) {
        onClick("suggest_command", command);
        return this;
    }

    public FancyMessage insert(final String command) {
        latest().insertionData = command;
        dirty = true;
        return this;
    }

    public FancyMessage command(final String command) {
        onClick("run_command", command);
        return this;
    }

    public FancyMessage achievementTooltip(final String name) {
        onHover("show_achievement", new JsonString("achievement." + name));
        return this;
    }

    public FancyMessage tooltip(final String text) {
        onHover("show_text", new JsonString(ChatUtils.translate(text)));
        return this;
    }

    public FancyMessage tooltip(final Iterable<String> lines) {
        tooltip(ArrayWrapper.toArray(lines, String.class));
        return this;
    }

    public FancyMessage tooltip(final String... lines) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            builder.append(lines[i]);
            if (i != lines.length - 1) {
                builder.append('\n');
            }
        }
        tooltip(builder.toString());
        return this;
    }

    public FancyMessage formattedTooltip(FancyMessage text) {
        for (MessagePart component : text.messageParts) {
            if (component.clickActionData != null && component.clickActionName != null) {
                throw new IllegalArgumentException("The tooltip text cannot have click data.");
            } else if (component.hoverActionData != null && component.hoverActionName != null) {
                throw new IllegalArgumentException("The tooltip text cannot have a tooltip.");
            }
        }
        onHover("show_text", text);
        return this;
    }

    public FancyMessage formattedTooltip(FancyMessage... lines) {
        if (lines.length < 1) {
            onHover(null, null);
            return this;
        }

        FancyMessage result = new FancyMessage();
        result.messageParts.clear();

        for (int i = 0; i < lines.length; i++) {
            try {
                for (MessagePart component : lines[i]) {
                    if (component.clickActionData != null && component.clickActionName != null) {
                        throw new IllegalArgumentException("The tooltip text cannot have click data.");
                    } else if (component.hoverActionData != null && component.hoverActionName != null) {
                        throw new IllegalArgumentException("The tooltip text cannot have a tooltip.");
                    }
                    if (component.hasText()) {
                        result.messageParts.add(component.clone());
                    }
                }
                if (i != lines.length - 1) {
                    result.messageParts.add(new MessagePart(rawText("\n")));
                }
            } catch (CloneNotSupportedException e) {
                Bukkit.getLogger().log(Level.WARNING, "Failed to clone object", e);
                return this;
            }
        }
        return formattedTooltip(result.messageParts.isEmpty() ? null : result);
    }

    public FancyMessage formattedTooltip(final Iterable<FancyMessage> lines) {
        return formattedTooltip(ArrayWrapper.toArray(lines, FancyMessage.class));
    }

    public FancyMessage translationReplacements(final String... replacements) {
        for (String str : replacements) {
            latest().translationReplacements.add(new JsonString(str));
        }
        dirty = true;

        return this;
    }

    public FancyMessage translationReplacements(final FancyMessage... replacements) {
        for (FancyMessage str : replacements) {
            latest().translationReplacements.add(str);
        }

        dirty = true;

        return this;
    }

    public FancyMessage translationReplacements(final Iterable<FancyMessage> replacements) {
        return translationReplacements(ArrayWrapper.toArray(replacements, FancyMessage.class));
    }

    public FancyMessage then(final String text) {
        return then(rawText(ChatUtils.translate(text)));
    }

    public FancyMessage then(final TextualComponent text) {
        if (!latest().hasText()) {
            throw new IllegalStateException("previous message part hasData no text");
        }
        messageParts.add(new MessagePart(text));
        dirty = true;
        return this;
    }

    public FancyMessage then() {
        if (!latest().hasText()) {
            throw new IllegalStateException("previous message part hasData no text");
        }
        messageParts.add(new MessagePart());
        dirty = true;
        return this;
    }

    @Override
    public void writeJson(JsonWriter writer) throws IOException {
        if (messageParts.size() == 1) {
            latest().writeJson(writer);
        } else {
            writer.beginObject().name("text").value("").name("extra").beginArray();
            for (final MessagePart part : this) {
                part.writeJson(writer);
            }
            writer.endArray().endObject();
        }
    }

    public String toJSONString() {
        if (!dirty && jsonString != null) {
            return jsonString;
        }
        StringWriter string = new StringWriter();
        JsonWriter json = new JsonWriter(string);
        try {
            writeJson(json);
            json.close();
        } catch (IOException e) {
            throw new RuntimeException("invalid message");
        }
        jsonString = string.toString();
        dirty = false;
        return jsonString;
    }

    public String send(Player player) {
        send(player, toJSONString());
        return jsonString;
    }

    private void send(CommandSender sender, String jsonString) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(toOldMessageFormat());
            return;
        }
        Player player = (Player) sender;
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tellraw " + player.getName() + " " + jsonString);
    }

    public void send(CommandSender sender) {
        send(sender, toJSONString());
    }

    public void send(final Iterable<? extends CommandSender> senders) {
        String string = toJSONString();
        for (final CommandSender sender : senders) {
            send(sender, string);
        }
    }

    public String toOldMessageFormat() {
        StringBuilder result = new StringBuilder();
        for (MessagePart part : this) {
            result.append(part.color == null ? "" : part.color);
            for (ChatColor formatSpecifier : part.styles) {
                result.append(formatSpecifier);
            }
            result.append(part.text);
        }
        return result.toString();
    }

    private MessagePart latest() {
        return messageParts.get(messageParts.size() - 1);
    }

    private void onClick(final String name, final String data) {
        final MessagePart latest = latest();
        latest.clickActionName = name;
        latest.clickActionData = data;
        dirty = true;
    }

    private void onHover(final String name, final JsonRepresentedObject data) {
        final MessagePart latest = latest();
        latest.hoverActionName = name;
        latest.hoverActionData = data;
        dirty = true;
    }

    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("messageParts", messageParts);

        return map;
    }

    public Iterator<MessagePart> iterator() {
        return messageParts.iterator();
    }

}
