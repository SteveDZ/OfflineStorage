package be.ordina.offlinestorage.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.widget.Toast;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteException;

import java.util.ArrayList;
import java.util.List;

import be.ordina.offlinestorage.database.UserEncryptedDbHelper;
import be.ordina.offlinestorage.model.User;

/**
 * Created by stevedezitter on 02/05/15.
 */
public class UserLoader extends SqlLiteCursorLoader {

    private UserEncryptedDbHelper dbHelper;
    private SQLiteDatabase database;
    private char[] password;

    public UserLoader(Context context, char[] password){
        super(context);
        this.dbHelper = new UserEncryptedDbHelper(context);
        this.database = dbHelper.getReadableDatabase(password);
        this.password = password;
    }

    @Override
    public Cursor loadCursor() {
        return database.rawQuery("select * from user", null);
    }
}
