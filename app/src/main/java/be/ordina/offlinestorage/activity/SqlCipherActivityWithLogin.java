package be.ordina.offlinestorage.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import be.ordina.offlinestorage.R;
import be.ordina.offlinestorage.fragment.DbLoginFragment;
import be.ordina.offlinestorage.fragment.SqlCipherFragment;

public class SqlCipherActivityWithLogin extends ActionBarActivity implements DbLoginFragment.OnLoginListener{

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_cipher_activity_with_login);

        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment_container, DbLoginFragment.newInstance()).addToBackStack(null).commit();
    }

    private void openEncryptedDatabaseFragment(char[] password) {
        fragmentManager.beginTransaction().replace(R.id.fragment_container, SqlCipherFragment.newInstance(password)).addToBackStack(null).commit();
    }

    @Override
    public void onLogin(String username, char[] password) {
        openEncryptedDatabaseFragment(password);
    }
}
