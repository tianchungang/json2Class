package com.ctx.lighting.json;

import java.util.ArrayList;
import java.util.List;

public class JsonEntity {
    private List<JsonProperty> properties = new ArrayList<>();
    private String name;
    private int index;

    public List<JsonProperty> getProperties() {
        return properties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void add(JsonProperty jsonProperty){
        properties.add(jsonProperty);
    }
}
