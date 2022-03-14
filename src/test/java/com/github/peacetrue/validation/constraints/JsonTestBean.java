package com.github.peacetrue.validation.constraints;

import com.github.peacetrue.validation.constraints.json.JSON;
import com.github.peacetrue.validation.constraints.json.JSONType;
import lombok.Getter;

/**
 * @author peace
 **/
@Getter
public class JsonTestBean {
    @JSON
    private String json;
    @JSON(types = JSONType.OBJECT)
    private String jsonObject;
    @JSON(types = JSONType.ARRAY)
    private String jsonArray;
    @JSON(types = JSONType.VALUE)
    private String jsonValue;

    public JsonTestBean setJson(String json) {
        this.json = json;
        return this;
    }

    public JsonTestBean setJsonObject(String jsonObject) {
        this.jsonObject = jsonObject;
        return this;
    }

    public JsonTestBean setJsonArray(String jsonArray) {
        this.jsonArray = jsonArray;
        return this;
    }

    public JsonTestBean setJsonValue(String jsonValue) {
        this.jsonValue = jsonValue;
        return this;
    }
}
