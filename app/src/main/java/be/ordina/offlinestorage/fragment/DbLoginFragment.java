package be.ordina.offlinestorage.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import be.ordina.offlinestorage.R;

public class DbLoginFragment extends Fragment {

    private OnLoginListener mListener;

    private EditText username;
    private EditText password;
    private Button login;

    public static DbLoginFragment newInstance() {
        DbLoginFragment fragment = new DbLoginFragment();
        return fragment;
    }

    public DbLoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment = inflater.inflate(R.layout.fragment_db_login, container, false);

        username = (EditText) fragment.findViewById(R.id.username);
        password = (EditText) fragment.findViewById(R.id.password);

        login = (Button) fragment.findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(username.getText().toString(), password.getText().toString().toCharArray());
            }
        });

        return fragment;
    }

    public void onButtonPressed(String username, char[] password) {
        if (mListener != null) {
            mListener.onLogin(username, password);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnLoginListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnLoginListener {
        // TODO: Update argument type and name
        public void onLogin(String username, char[] password);
    }

}
