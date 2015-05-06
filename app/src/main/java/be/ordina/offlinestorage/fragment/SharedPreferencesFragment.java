package be.ordina.offlinestorage.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import be.ordina.offlinestorage.R;

public class SharedPreferencesFragment extends Fragment {

    private static final String PREFS="MyPrefsFile";

    private EditText usernameField, passwordField;
    private Button saveButton;

    String username, password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getActivity().getSharedPreferences(PREFS, Context.MODE_PRIVATE);

        username = prefs.getString("username", "USER");
        password = prefs.getString("password","");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.activity_shared_preferences, container, false);

        usernameField = (EditText) fragment.findViewById(R.id.username_field);
        passwordField = (EditText) fragment.findViewById(R.id.password_field);
        saveButton = (Button) fragment.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // We need an Editor object to make preference changes.
                // All objects are from android.context.Context
                SharedPreferences settings = getActivity().getSharedPreferences(PREFS, 0);
                SharedPreferences.Editor editor = settings.edit();

                editor.putString("username",usernameField.getText().toString());
                editor.putString("password",passwordField.getText().toString());

                // Commit the edits!
                editor.commit();
            }
        });

        usernameField.setText(username);
        passwordField.setText(password);

        return fragment;
    }
}
