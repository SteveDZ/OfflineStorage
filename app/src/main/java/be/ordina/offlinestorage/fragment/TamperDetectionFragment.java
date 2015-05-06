package be.ordina.offlinestorage.fragment;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import be.ordina.offlinestorage.R;
import be.ordina.offlinestorage.utils.RootDetectionUtils;
import be.ordina.offlinestorage.utils.TamperDetectionUtils;

public class TamperDetectionFragment extends Fragment {

    private TextView isInstalledThroughPlayStore, isDebuggable, isRunningOnEmulator, isRooted;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.activity_tamper_detection, container, false);

        isInstalledThroughPlayStore = (TextView) fragment.findViewById(R.id.is_installed_through_play_store);
        isDebuggable = (TextView) fragment.findViewById(R.id.is_debuggable);
        isRunningOnEmulator = (TextView) fragment.findViewById(R.id.is_running_on_emulator);
        isRooted = (TextView) fragment.findViewById(R.id.is_rooted);

        isInstalledThroughPlayStore.setText(Boolean.toString(TamperDetectionUtils.isInstalledThroughPlayStore(getActivity())));
        isDebuggable.setText(Boolean.toString(TamperDetectionUtils.isDebuggable(getActivity(), true)));
        isRunningOnEmulator.setText(Boolean.toString(TamperDetectionUtils.isRunningInEmulator()));
        isRooted.setText(Boolean.toString(RootDetectionUtils.isRooted(true)));

        return fragment;
    }
}
