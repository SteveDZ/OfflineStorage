package be.ordina.offlinestorage.activity;

import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import be.ordina.offlinestorage.R;
import be.ordina.offlinestorage.model.Settings;

public class EncryptedInternalStorageActivity extends ActionBarActivity {

    private static final String FILENAME = "encrypted_private_file";
    private static final int ITERATION_COUNT = 1000;
    private static final int KEYLENGTH = 256;

    static {
        System.loadLibrary("local-module");
    }

    private EditText privateField;
    private Button storeFileButton;

    private SecureRandom random;

    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_storage);

        random = new SecureRandom();

        settings = readSettingsFromFile();
        privateField = (EditText) findViewById(R.id.private_key_field);
        if (settings != null) {
            privateField.setText(settings.getPrivateField());
        }

        storeFileButton = (Button) findViewById(R.id.save_settings_button);
        storeFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings = new Settings();
                settings.setPrivateField(privateField.getText().toString());
                byte[] settingsJson = settings.toJson().toString().getBytes();


                writeEncryptedFile(settingsJson);
//                writeBytesToFile(Base64.encodeBase64(encryptedBytes));
            }
        });
    }

    private Settings readSettingsFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            String[] fields = builder.toString().split("]");
            byte[] cipherBytes = Base64.decodeBase64(fields[0].getBytes());
            byte[] iv = Base64.decodeBase64(fields[1].getBytes());
            byte[] salt = Base64.decodeBase64(fields[2].getBytes());

            byte[] keyBytes = generateKey(salt);
            SecretKey key = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParams = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, key, ivParams);
            byte[] plaintext = cipher.doFinal(cipherBytes);
            String plainrStr = new String(plaintext);

            return Settings.fromJson(plainrStr);
        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | IllegalBlockSizeException
                | BadPaddingException
                | InvalidKeyException
                | InvalidAlgorithmParameterException
                | IOException exception) {

        }

        return new Settings();
    }

    private void writeBytesToFile(byte[] fileBytes) {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, MODE_PRIVATE);
            fos.write(fileBytes);
        } catch (IOException ioException) {

        } finally {
            Toast.makeText(this, "Settings saved to file", Toast.LENGTH_SHORT).show();
        }
    }

    private native String getPasswordFromNativeInterface();

    private void writeEncryptedFile(byte[] text) {
        try {
            byte[] salt = generateSalt();
            byte[] keyBytes = generateKey(salt);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            //Initialisation vector
            byte[] iv = new byte[cipher.getBlockSize()];
            random.nextBytes(iv);

            IvParameterSpec ivParams = new IvParameterSpec(iv);
            SecretKey key = new SecretKeySpec(keyBytes, "AES");

            cipher.init(Cipher.ENCRYPT_MODE, key, ivParams);
            byte[] ciphertext = cipher.doFinal(text);
            byte seperator = (byte) ']';

            byte[] fileBytes = createFileString(ciphertext, iv, salt);

            writeBytesToFile(fileBytes);
        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | InvalidKeyException
                | IllegalBlockSizeException
                | BadPaddingException
                | InvalidAlgorithmParameterException
                | IOException exception) {

        }
    }

    private byte[] generateSalt() {
        int saltLength = KEYLENGTH / 8; // same size as key output
        byte[] salt = new byte[saltLength];
        random.nextBytes(salt);

        return salt;
    }

    private byte[] generateKey(byte[] salt) {
        char[] password = getPasswordFromNativeInterface().toCharArray();

        KeySpec keySpec = new PBEKeySpec(password, salt,
                ITERATION_COUNT, KEYLENGTH);

        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] keyBytes = keyFactory.generateSecret(keySpec).getEncoded();
            SecretKey key = new SecretKeySpec(keyBytes, "AES");

            return key.getEncoded();
        } catch (NoSuchAlgorithmException
                | InvalidKeySpecException exception) {
            return null;
        }
    }

    private byte[] createFileString(byte[] ciphertext, byte[] iv, byte[] salt) throws IOException{
        byte[] cipherBase64 = Base64.encodeBase64(ciphertext);
        byte[] ivBase64 = Base64.encodeBase64(iv);
        byte[] saltBase64 = Base64.encodeBase64(salt);

        byte seperator = (byte)']';

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        baos.write(cipherBase64);
        baos.write(seperator);
        baos.write(ivBase64);
        baos.write(seperator);
        baos.write(saltBase64);

        return baos.toByteArray();
    }
}