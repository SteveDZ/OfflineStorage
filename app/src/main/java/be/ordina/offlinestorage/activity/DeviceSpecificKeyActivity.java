package be.ordina.offlinestorage.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import be.ordina.offlinestorage.R;

public class DeviceSpecificKeyActivity extends ActionBarActivity {

    private TextView deviceSpecificKey;

    private TelephonyManager telephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_specific_key);

        telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        deviceSpecificKey = (TextView) findViewById(R.id.device_specific_key);

        deviceSpecificKey.setText(generateDeviceSpecificKey());

    }

    private String generateDeviceSpecificKey() {
        String android_id = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        String device_id = telephonyManager.getDeviceId();
        String str1 = Build.BOARD + Build.BRAND + Build.CPU_ABI + Build.DEVICE +
                Build.DISPLAY + Build.FINGERPRINT + Build.HOST + Build.ID + Build.MANUFACTURER +
                Build.MODEL + Build.PRODUCT + Build.TAGS + Build.TYPE + Build.USER;

        String key="";

        try {
            byte[] hash = sha256(str1 + device_id + android_id);
            key = new String(Base64.encodeBase64(hash));
        }catch(NoSuchAlgorithmException | UnsupportedEncodingException exception) {
            //Whatever...
        }

        return key;
    }

    private byte[] sha256(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");

        sha.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
        byte[] digest = sha.digest();

        return digest;
    }
}
