package club.skilldevs.utils.texts;

import com.google.gson.stream.JsonWriter;

import java.io.IOException;

interface JsonRepresentedObject {

    public void writeJson(JsonWriter writer) throws IOException;

}
