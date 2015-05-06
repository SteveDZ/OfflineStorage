package be.ordina.offlinestorage.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import be.ordina.offlinestorage.R;
import be.ordina.offlinestorage.database.UserDbHelper;
import static be.ordina.offlinestorage.database.UserReaderContract.*;

import be.ordina.offlinestorage.database.UserEncryptedDbHelper;
import be.ordina.offlinestorage.model.User;

public class SqlLiteActivity extends ActionBarActivity {

    private UserDbHelper userDbHelper;

    private Button saveInDatabaseButton;
    private EditText firstNameField;
    private EditText lastNameField;
    private EditText emailField;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my);
//
//        SQLiteDatabase.loadLibs(this);
//        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(":memory:", "Password1!", null);
//        Cursor cursor = database.rawQuery("PRAGMA cipher_version;", null);
//        cursor.moveToFirst();
//        String version = cursor.getString(0);
//        Log.i(TAG, String.format("SQLCipher version:%s", version));
//        cursor.close();
//        database.close();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_lite);

        net.sqlcipher.database.SQLiteDatabase.loadLibs(this);

        //This should be done in the background............
        userDbHelper = new UserDbHelper(this);

        firstNameField = (EditText) findViewById(R.id.first_name_field);
        lastNameField = (EditText) findViewById(R.id.last_name_field);
        emailField = (EditText) findViewById(R.id.email_field);

        saveInDatabaseButton = (Button) findViewById(R.id.save_in_db_button);

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
    }

    private void saveUserInDatabase(User user) {
        SQLiteDatabase userDb = userDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_NAME_FIRST_NAME, user.getFirstName());
        values.put(UserEntry.COLUMN_NAME_LAST_NAME, user.getLastName());
        values.put(UserEntry.COLUMN_NAME_EMAIL, user.getEmail());

        long newRowId;
        newRowId = userDb.insert(UserEntry.TABLE_NAME, "null", values);

        user.setId(newRowId);

        Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show();
    }
}
