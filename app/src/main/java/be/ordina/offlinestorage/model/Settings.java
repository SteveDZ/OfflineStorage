package be.ordina.offlinestorage.model;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by stevedezitter on 27/04/15.
 */
public class Settings {

    private String privateField;

    public String getPrivateField() {
        return privateField;
    }
    public void setPrivateField(String privateField) {
        this.privateField = privateField;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("privateField", privateField);
        }catch(JSONException jsonException){
            //Don't do anything......
        }

        return jsonObject;
    }

    public static Settings fromJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            Settings settings = new Settings();
            if(jsonObject.has("privateField")){
                settings.setPrivateField(jsonObject.getString("privateField"));
            }
            return settings;
        }catch(JSONException exception){
            return null;
        }
    }
}
