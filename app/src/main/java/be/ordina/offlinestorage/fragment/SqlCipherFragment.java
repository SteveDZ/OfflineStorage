package be.ordina.offlinestorage.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteException;

import java.util.ArrayList;
import java.util.List;

import be.ordina.offlinestorage.R;
import be.ordina.offlinestorage.loader.UserLoader;
import be.ordina.offlinestorage.model.User;

public class SqlCipherFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final String ARG_PASSWORD = "password";
    private static final int LOADER_ID=0;

    private char[] password;

    private List<User> users;
    private ArrayAdapter usersAdapter;

    private ListView listView;

    public static SqlCipherFragment newInstance(char[] password) {
        SqlCipherFragment fragment = new SqlCipherFragment();
        Bundle args = new Bundle();
        args.putCharArray(ARG_PASSWORD, password);
        fragment.setArguments(args);
        return fragment;
    }

    public SqlCipherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SQLiteDatabase.loadLibs(getActivity());

        if (getArguments() != null) {
            password = getArguments().getCharArray(ARG_PASSWORD);
        }
        users = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment = inflater.inflate(R.layout.fragment_sql_cipher, container, false);

        listView = (ListView) fragment.findViewById(R.id.listView);
        usersAdapter = new ArrayAdapter<User>(getActivity(), android.R.layout.simple_list_item_1, users);
        listView.setAdapter(usersAdapter);

        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
//            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Loader<Cursor> loader = null;
        try {
            loader = new UserLoader(getActivity(), password);
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
