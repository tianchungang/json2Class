package com.ctx.lighting.json;

public class JsonProperty {
    private String key;
    private String capitalKey;
    private String type;
    private String packageName;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getCapitalKey() {
        return capitalKey;
    }

    public void setCapitalKey(String capitalKey) {
        this.capitalKey = capitalKey;
    }
}
