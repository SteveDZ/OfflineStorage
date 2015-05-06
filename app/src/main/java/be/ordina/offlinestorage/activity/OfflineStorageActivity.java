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
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import be.ordina.offlinestorage.R;
import be.ordina.offlinestorage.fragment.DbLoginFragment;
import be.ordina.offlinestorage.fragment.DeviceSpecificKeyFragment;
import be.ordina.offlinestorage.fragment.EncryptedInternalStorageFragment;
import be.ordina.offlinestorage.fragment.InternalStorageFragment;
import be.ordina.offlinestorage.fragment.SharedPreferencesFragment;
import be.ordina.offlinestorage.fragment.SqlCipherFragment;
import be.ordina.offlinestorage.fragment.SqlCipherFragmentHardcodedPassword;
import be.ordina.offlinestorage.fragment.SqlLiteFragment;
import be.ordina.offlinestorage.fragment.TamperDetectionFragment;


public class OfflineStorageActivity extends ActionBarActivity implements DbLoginFragment.OnLoginListener{

    static {
        System.loadLibrary("local-module");
    }

    private ListView storageOptionsList;

    private List<String> navDrawerItems;

    private DrawerLayout drawerLayout;
    private ListView drawer;

    private native String getPasswordFromNativeInterface();

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
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        String message = "";

        Fragment fragment = null;
        switch(position){
            case 0:
                fragment = new SharedPreferencesFragment();
//                message = "action_shared_preferences";
                break;
            case 1:
                fragment = new InternalStorageFragment();
//                message = "action_internal_storage";
                break;
            case 2:
                fragment = new EncryptedInternalStorageFragment();
//                message = "action_encrypted_internal_storage";
                break;
            case 3:
                fragment = new SqlLiteFragment();
//                message = "action_sql_lite";
                break;
            case 4:
                fragment = new SqlCipherFragmentHardcodedPassword();
//                message = "action_sql_cipher";
                break;
            case 5:
                fragment = new DbLoginFragment();
//                message = "action_sql_cipher_fragments";
                break;
            case 6:
                fragment = SqlCipherFragment.newInstance(getPasswordFromNativeInterface().toCharArray());
//                message = "action_sql_cipher_fragments_jni";
                break;
            case 7:
                fragment = new DeviceSpecificKeyFragment();
//                message = "action_device_specific_key";
                break;
            case 8:
                fragment = new TamperDetectionFragment();
//                message = "action_tamper_detection";
                break;
        }

//        Toast.makeText(OfflineStorageActivity.this, message, Toast.LENGTH_SHORT).show();

        // Create a new fragment and specify the planet to show based on position
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

    @Override
    public void onLogin(String username, char[] password) {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = SqlCipherFragment.newInstance(password);
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }
}
