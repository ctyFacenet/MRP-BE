package com.facenet.mrp.service.dto.scada;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KeyValue {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    @JsonSetter("value")
    public void setValue(JsonNode value) {
        System.err.println(value.getNodeType() + value.toString());
        if (value.isTextual()) this.value = value.textValue();
        else if (value.isNumber()) this.value = value.numberValue().toString();
    }
}
