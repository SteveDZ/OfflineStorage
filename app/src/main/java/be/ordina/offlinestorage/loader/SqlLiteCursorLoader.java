package be.ordina.offlinestorage.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import net.sqlcipher.Cursor;

/**
 * Created by stevedezitter on 02/05/15.
 */
public abstract class SqlLiteCursorLoader extends AsyncTaskLoader<Cursor> {

    private Cursor cursor;

    public SqlLiteCursorLoader(Context context) {
        super(context);
    }

    public abstract Cursor loadCursor();

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = loadCursor();
        if(cursor != null) {
            //ensure that data is available in memory when data is requested on main thread
            cursor.getCount();
        }
        return cursor;
    }

    @Override
    public void deliverResult(Cursor data) {
        Cursor oldCursor = cursor;
        cursor = data;

        if(isStarted()) {
            super.deliverResult(data);
        }

        if(oldCursor!=null && oldCursor != data && !oldCursor.isClosed()) {
            oldCursor.close();
        }
    }

    @Override
    protected void onStartLoading() {
        if(cursor != null) {
            deliverResult(cursor);
        }
        if(takeContentChanged() || cursor == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void onCanceled(Cursor data) {
        if(cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    @Override
    protected void onReset() {
        super.onReset();

        //Ensure the loader is stopped
        onStopLoading();

        if(cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        cursor = null;
    }
}
