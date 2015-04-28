package be.ordina.offlinestorage.database;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

/**
 * Created by stevedezitter on 27/04/15.
 */
public class UserEncryptedDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="SensitiveEncryptedData.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserReaderContract.UserEntry.TABLE_NAME + " (" +
                    UserReaderContract.UserEntry._ID + " INTEGER PRIMARY KEY," +
                    UserReaderContract.UserEntry.COLUMN_NAME_FIRST_NAME + TEXT_TYPE + COMMA_SEP +
                    UserReaderContract.UserEntry.COLUMN_NAME_LAST_NAME + TEXT_TYPE + COMMA_SEP +
                    UserReaderContract.UserEntry.COLUMN_NAME_EMAIL + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserReaderContract.UserEntry.TABLE_NAME;

    public UserEncryptedDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
