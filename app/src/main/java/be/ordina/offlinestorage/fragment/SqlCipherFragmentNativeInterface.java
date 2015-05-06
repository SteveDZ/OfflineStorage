package be.ordina.offlinestorage.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteException;

import be.ordina.offlinestorage.R;
import be.ordina.offlinestorage.loader.UserLoader;

public class SqlCipherFragmentNativeInterface extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String ARG_PASSWORD = "password";
    private static final int LOADER_ID=0;

    private String password;

    private ListView listView;

    public static SqlCipherFragmentNativeInterface newInstance(String param1) {
        SqlCipherFragmentNativeInterface fragment = new SqlCipherFragmentNativeInterface();
        Bundle args = new Bundle();
        args.putString(ARG_PASSWORD, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public SqlCipherFragmentNativeInterface() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SQLiteDatabase.loadLibs(getActivity());

        if (getArguments() != null) {
            password = getArguments().getString(ARG_PASSWORD);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment = inflater.inflate(R.layout.fragment_sql_cipher, container, false);

        listView = (ListView) fragment.findViewById(R.id.listView);

        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Loader<Cursor> loader = null;
        try {
            loader = new UserLoader(getActivity(), password.toCharArray());
        }catch(SQLiteException exception) {
            Toast.makeText(getActivity(), "Wrong password", Toast.LENGTH_SHORT).show();
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                getActivity(),
                android.R.layout.simple_list_item_1,
                data,
                new String[]{"firstName"},
                new int[] {android.R.id.text1},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        listView.setAdapter(null);
    }
}