package be.ordina.offlinestorage.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import be.ordina.offlinestorage.R;
import be.ordina.offlinestorage.fragment.SqlCipherFragment;
import be.ordina.offlinestorage.fragment.SqlCipherFragmentHardcodedPassword;


public class OfflineStorageActivity extends ActionBarActivity {

    private ListView storageOptionsList;

    private List<String> navDrawerItems;

    private DrawerLayout drawerLayout;
    private ListView drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_storage);

        navDrawerItems = Arrays.asList(getResources().getStringArray(R.array.nav_drawer_items));

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer = (ListView) findViewById(R.id.left_drawer);

        drawer.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, navDrawerItems));

        drawer.setOnItemClickListener(new DrawerItemClickListener());
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position, id);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position, long id) {
        // Create a new fragment and specify the planet to show based on position
        Fragment fragment = new SqlCipherFragmentHardcodedPassword();
//        Bundle args = new Bundle();
//        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
//        fragment.setArguments(args);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        drawer.setItemChecked(position, true);
        setTitle(navDrawerItems.get(position));
        drawerLayout.closeDrawer(drawer);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_offline_storage, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        Intent intent = null;
//        switch(id){
//            case R.id.action_shared_preferences:
//                intent = new Intent(this, SharedPreferencesActivity.class);
//                break;
//            case R.id.action_internal_storage:
//                intent = new Intent(this, InternalStorageActivity.class);
//                break;
//            case R.id.action_sql_lite:
//                intent = new Intent(this, SqlLiteActivity.class);
//                break;
//            case R.id.action_sql_cipher:
//                intent = new Intent(this, SqlCipherActivity.class);
//                break;
//            case R.id.action_sql_cipher_fragments:
//                intent = new Intent(this, SqlCipherActivityWithLogin.class);
//                break;
//            case R.id.action_sql_cipher_fragments_jni:
//                intent = new Intent(this, SqlCipherActivityWithNativeInterface.class);
//                break;
//            case R.id.action_tamper_detection:
//                intent = new Intent(this, TamperDetectionActivity.class);
//                break;
//            case R.id.action_device_specific_key:
//                intent = new Intent(this, DeviceSpecificKeyActivity.class);
//                break;
//            case R.id.action_encrypted_internal_storage:
//                intent = new Intent(this, EncryptedInternalStorageActivity.class);
//                break;
//        }
//
//        startActivity(intent);
//        return true;
//    }
}
