package be.ordina.offlinestorage.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import be.ordina.offlinestorage.R;
import be.ordina.offlinestorage.utils.RootDetectionUtils;
import be.ordina.offlinestorage.utils.TamperDetectionUtils;

public class TamperDetectionActivity extends ActionBarActivity {

    private TextView isInstalledThroughPlayStore, isDebuggable, isRunningOnEmulator, isRooted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamper_detection);

        isInstalledThroughPlayStore = (TextView) findViewById(R.id.is_installed_through_play_store);
        isDebuggable = (TextView) findViewById(R.id.is_debuggable);
        isRunningOnEmulator = (TextView) findViewById(R.id.is_running_on_emulator);
        isRooted = (TextView) findViewById(R.id.is_rooted);

        isInstalledThroughPlayStore.setText(Boolean.toString(TamperDetectionUtils.isInstalledThroughPlayStore(this)));
        isDebuggable.setText(Boolean.toString(TamperDetectionUtils.isDebuggable(this, true)));
        isRunningOnEmulator.setText(Boolean.toString(TamperDetectionUtils.isRunningInEmulator()));
        isRooted.setText(Boolean.toString(RootDetectionUtils.isRooted(true)));

    }
}
