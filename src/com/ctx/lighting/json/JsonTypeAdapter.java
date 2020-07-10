package com.ctx.lighting.json;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JsonTypeAdapter  extends TypeAdapter<JsonData> {

    private final Gson gson;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    JsonTypeAdapter(Gson gson) {
        this.gson = gson;
    }

    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        @SuppressWarnings("unchecked")
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            if (type.getRawType() == JsonData.class) {
                return (TypeAdapter<T>) new JsonTypeAdapter(gson);
            }
            return null;
        }
    };


    @Override
    public void write(JsonWriter jsonWriter, JsonData jsonData) throws IOException {
    }

    @Override
    public JsonData read(JsonReader jsonReader) throws IOException {
        JsonData jsonData = new JsonData();
        jsonData.setData(readInternal(jsonReader));
        return jsonData;
    }

    private Object readInternal(JsonReader in) throws IOException {
        JsonToken token = in.peek();
        switch (token) {
            case BEGIN_ARRAY:
                List<Object> list = new ArrayList<Object>();
                in.beginArray();
                while (in.hasNext()) {
                    list.add(readInternal(in));
                }
                in.endArray();
                return list;

            case BEGIN_OBJECT:
                Map<String, Object> map = new LinkedTreeMap<String, Object>();
                in.beginObject();
                while (in.hasNext()) {
                    map.put(in.nextName(), readInternal(in));
                }
                in.endObject();
                return map;

            case STRING:
                String nextString = in.nextString();
                if(isDate(nextString)){
                    return toDate(nextString);
                }
                return nextString;

            case NUMBER:
                String numberStr = in.nextString();
                if (numberStr.contains(".") || numberStr.contains("e")
                        || numberStr.contains("E")) {
                    return Double.parseDouble(numberStr);
                }
                return Long.parseLong(numberStr);

            case BOOLEAN:
                return in.nextBoolean();

            case NULL:
                in.nextNull();
                return null;

            default:
                throw new IllegalStateException();
        }
    }

    private boolean isDate(String dateStr){
        boolean isDate = false;
        try {
            df.parse(dateStr);
            isDate = true;
        } catch (ParseException ignored) {
        }

        return isDate;
    }

    private Date toDate(String dateStr){
        Date date = null;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}
