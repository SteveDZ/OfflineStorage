package be.ordina.offlinestorage.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by stevedezitter on 27/04/15.
 */
public class Settings {

    private String encryptionKey;

    public String getEncryptionKey() {
        return encryptionKey;
    }
    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("encryptionKey", encryptionKey);
        }catch(JSONException jsonException){
            //Don't do anything......
        }

        return jsonObject;
    }
}
