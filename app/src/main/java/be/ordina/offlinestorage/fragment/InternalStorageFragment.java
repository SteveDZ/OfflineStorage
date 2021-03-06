package be.ordina.offlinestorage.fragment;

import android.app.Fragment;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import be.ordina.offlinestorage.R;
import be.ordina.offlinestorage.model.Settings;

public class InternalStorageFragment extends Fragment {

    private static final String FILENAME = "app_private_file";

    private EditText privateField;
    private Button saveSettingsButton;

    private Settings settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settings = readFromFile();
        if(settings == null) {
            settings = new Settings();
        }

        setRetainInstance(true);
    }

    private Settings readFromFile() {
        try {
            FileInputStream fis = getActivity().openFileInput(FILENAME);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));

            String line="";
            StringBuilder builder = new StringBuilder();
            while((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }

            return Settings.fromJson(builder.toString());
        }catch(IOException exception){
            //BLAHliuhlfksjhdf
            return null;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.activity_internal_storage, container, false);

        privateField = (EditText)fragment.findViewById(R.id.private_key_field);
        privateField.setText(settings.getPrivateField());

        saveSettingsButton = (Button)fragment.findViewById(R.id.save_settings_button);
        saveSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.setPrivateField(privateField.getText().toString());
                saveSettingsToFile();
            }
        });

        return fragment;
    }

    private void saveSettingsToFile() {
        try {
            FileOutputStream fos = getActivity().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(settings.toJson().toString().getBytes());
        }catch (IOException ioException) {

        }finally{
            Toast.makeText(getActivity(), "Settings saved to file", Toast.LENGTH_SHORT).show();
        }
    }
}
