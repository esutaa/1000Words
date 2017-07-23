package edu.fsu.cs.mobile.onethousandwords;


import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private EditText EMAIL;
    private EditText PASSWORD;
    private Button LOGIN;
    private TextView CREATE;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        EMAIL = (EditText) rootView.findViewById(R.id.email);
        PASSWORD = (EditText) rootView.findViewById(R.id.password);
        LOGIN = (Button) rootView.findViewById(R.id.login_btn);

        LOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DrawingFragment drawingFragment = new DrawingFragment();
                String tag = DrawingFragment.class.getCanonicalName();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_frame, drawingFragment, tag)
                        .addToBackStack("login")
                        .commit();
            }
        });

        CREATE = (TextView) rootView.findViewById(R.id.create_account_txt);
        CREATE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RegisterFragment registerFragment = new RegisterFragment();
                String tag = RegisterFragment.class.getCanonicalName();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_frame, registerFragment, tag)
                        .addToBackStack("register")
                        .commit();
            }
        });


        return rootView;
    }

}
