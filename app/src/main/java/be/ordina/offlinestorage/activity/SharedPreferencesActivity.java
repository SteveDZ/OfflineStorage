package be.ordina.offlinestorage.activity;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import be.ordina.offlinestorage.R;

public class SharedPreferencesActivity extends ActionBarActivity {

    private static final String PREFS="MyPrefsFile";

    private boolean isCheckedPreference=false;

    private EditText usernameField, passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);

        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);

        String username = prefs.getString("username", "USER");
        String password = prefs.getString("password","");

        usernameField = (EditText) findViewById(R.id.username_field);
        passwordField = (EditText) findViewById(R.id.password_field);

        usernameField.setText(username);
        passwordField.setText(password);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString("username",usernameField.getText().toString());
        editor.putString("password",passwordField.getText().toString());

        // Commit the edits!
        editor.commit();
    }
}
