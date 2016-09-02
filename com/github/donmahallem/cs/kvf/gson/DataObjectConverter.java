package com.github.donmahallem.cs.kvf.gson;

import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class DataObjectConverter extends TypeAdapter<DataObject> {

    private final boolean mNumberConversion;

    public DataObjectConverter() {
        this(false);
    }

    public DataObjectConverter(boolean numberConversion) {
        this.mNumberConversion = numberConversion;
    }

    @Override
    public void write(JsonWriter jsonWriter, DataObject dataObject) throws IOException {
        if (dataObject == null) {
            jsonWriter.nullValue();
            return;
        }
        jsonWriter.beginObject();
        Object value = null;
        for (String name : dataObject.mData.keySet()) {
            jsonWriter.name(name);
            value = dataObject.get(name);
            if (value == null) {
                jsonWriter.nullValue();
            } else if (value instanceof DataObject) {
                write(jsonWriter, (DataObject) value);
            } else if (value instanceof Float) {
                jsonWriter.value((Float) value);
            } else if (value instanceof Integer) {
                jsonWriter.value((Integer) value);
            } else if (value instanceof String) {
                jsonWriter.value((String) value);
            } else {
                throw new JsonParseException("Unknown type");
            }
        }
        jsonWriter.endObject();
    }

    @Override
    public DataObject read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() != JsonToken.BEGIN_OBJECT) {
            throw new JsonParseException("expected object");
        }
        DataObject dataObject = new DataObject();
        jsonReader.beginObject();
        String name = null;
        String value = null;
        while (jsonReader.hasNext()) {
            name = jsonReader.nextName();
            switch (jsonReader.peek()) {
                case BEGIN_OBJECT:
                    if (dataObject.mData.containsKey(name)) {
                        if (dataObject.mData.get(name) instanceof DataObject) {
                            ((DataObject) dataObject.mData.get(name)).mData.putAll(this.read(jsonReader).mData);
                        } else {
                            throw new JsonParseException("original '" + name + "' is not of type DataObject");
                        }
                    } else {
                        dataObject.mData.put(name, this.read(jsonReader));
                    }
                    break;
                case STRING:
                    value = jsonReader.nextString();
                    if (this.mNumberConversion) {
                        if (value.matches("[0-9]*")) {
                            dataObject.mData.put(name, Integer.parseInt(value));
                        } else if (value.matches("[0-9]*\\.[0-9]*")) {
                            dataObject.mData.put(name, Float.parseFloat(value));
                        } else {
                            dataObject.mData.put(name, value);
                        }
                    } else
                        dataObject.mData.put(name, value);
                    break;
                default:
                    System.out.println("Miss: " + jsonReader.peek());
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();
        return dataObject;
    }
}
