package be.ordina.offlinestorage.activity;

import android.app.FragmentManager;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import be.ordina.offlinestorage.R;
import be.ordina.offlinestorage.fragment.SqlCipherFragmentNativeInterface;

public class SqlCipherActivityWithNativeInterface extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_cipher_activity_with_login);

        System.loadLibrary("local-module");

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
            .add(R.id.fragment_container, SqlCipherFragmentNativeInterface.newInstance(getPasswordFromNativeInterface()))
            .addToBackStack(null)
            .commit();
    }

    private native String getPasswordFromNativeInterface();

}
