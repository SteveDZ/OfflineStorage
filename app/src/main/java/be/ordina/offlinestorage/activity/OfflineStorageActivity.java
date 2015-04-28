package be.ordina.offlinestorage.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import be.ordina.offlinestorage.R;


public class OfflineStorageActivity extends ActionBarActivity {

    private ListView storageOptionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_storage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_offline_storage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Intent intent = null;
        switch(id){
            case R.id.action_shared_preferences:
                intent = new Intent(this, SharedPreferencesActivity.class);
                break;
            case R.id.action_internal_storage:
                intent = new Intent(this, InternalStorageActivity.class);
                break;
            case R.id.action_sql_lite:
                intent = new Intent(this, SqlLiteActivity.class);
                break;
        }

        startActivity(intent);
        return true;
    }
}
