package be.ordina.offlinestorage.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import be.ordina.offlinestorage.R;
import be.ordina.offlinestorage.model.Settings;

public class InternalStorageActivity extends ActionBarActivity {

    private static final String FILENAME = "app_private_file";

    private EditText privateField;
    private Button saveSettingsButton;

    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_storage);

        settings = new Settings();

        privateField = (EditText)findViewById(R.id.private_key_field);
        saveSettingsButton = (Button)findViewById(R.id.save_settings_button);
        saveSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.setPrivateField(privateField.getText().toString());
                saveSettingsToFile();
            }
        });
    }

    private void saveSettingsToFile() {
        try {
            Charset charset = Charset.forName("UTF-16");
            FileOutputStream fos = openFileOutput(FILENAME, MODE_PRIVATE);
            fos.write(settings.toJson().toString().getBytes(charset));
        }catch (IOException ioException) {

        }finally{
            Toast.makeText(this, "Settings saved to file", Toast.LENGTH_SHORT).show();
        }
    }
}
