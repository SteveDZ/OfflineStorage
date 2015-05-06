package be.ordina.offlinestorage.fragment;

import android.app.Fragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.sqlcipher.database.SQLiteDatabase;

import be.ordina.offlinestorage.R;
import be.ordina.offlinestorage.database.UserEncryptedDbHelper;
import be.ordina.offlinestorage.database.UserReaderContract.UserEntry;
import be.ordina.offlinestorage.model.User;

public class SqlCipherFragmentHardcodedPassword extends Fragment {

    //    private UserDbHelper userDbHelper;
    private UserEncryptedDbHelper userDbHelper;

    private Button saveInDatabaseButton;
    private EditText firstNameField;
    private EditText lastNameField;
    private EditText emailField;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SQLiteDatabase.loadLibs(getActivity());

        //This should be done in the background............
        userDbHelper = new UserEncryptedDbHelper(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.activity_sql_lite, container, false);

        firstNameField = (EditText) fragment.findViewById(R.id.first_name_field);
        lastNameField = (EditText) fragment.findViewById(R.id.last_name_field);
        emailField = (EditText) fragment.findViewById(R.id.email_field);

        saveInDatabaseButton = (Button) fragment.findViewById(R.id.save_in_db_button);
        saveInDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInDatabase(
                        new User(firstNameField.getText().toString(),
                                lastNameField.getText().toString(),
                                emailField.getText().toString())
                );
            }
        });

        return fragment;
    }

    private void saveUserInDatabase(User user) {
//        SQLiteDatabase userDb = userDbHelper.getWritableDatabase();
        SQLiteDatabase userDb = userDbHelper.getWritableDatabase("password123");

        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_NAME_FIRST_NAME, user.getFirstName());
        values.put(UserEntry.COLUMN_NAME_LAST_NAME, user.getLastName());
        values.put(UserEntry.COLUMN_NAME_EMAIL, user.getEmail());

        long newRowId;
        newRowId = userDb.insert(UserEntry.TABLE_NAME, "null", values);

        user.setId(newRowId);

        Toast.makeText(getActivity(), user.toString(), Toast.LENGTH_SHORT).show();
    }
}
