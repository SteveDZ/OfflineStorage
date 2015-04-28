package be.ordina.offlinestorage.database;

import android.provider.BaseColumns;

/**
 * Created by stevedezitter on 27/04/15.
 */
public final class UserReaderContract {
    public UserReaderContract() {}

    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_FIRST_NAME = "firstName";
        public static final String COLUMN_NAME_LAST_NAME = "lastName";
        public static final String COLUMN_NAME_EMAIL = "email";
    }
}
