package edu.fsu.cs.mobile.onethousandwords;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.print.PrintHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private EditText NAME;
    private EditText EMAIL;
    private EditText PASSWORD;
    private Button CREATE_ACCOUNT;
    private TextView MEMBER_LOGIN;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        NAME = (EditText) rootView.findViewById(R.id.name);
        EMAIL = (EditText) rootView.findViewById(R.id.email);
        PASSWORD = (EditText) rootView.findViewById(R.id.password);

        CREATE_ACCOUNT = (Button) rootView.findViewById(R.id.create_account_btn);
        CREATE_ACCOUNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DrawingFragment drawingFragment = new DrawingFragment();
                String tag = DrawingFragment.class.getCanonicalName();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_frame, drawingFragment, tag)
                        .addToBackStack("register")
                        .commit();
            }
        });

        MEMBER_LOGIN = (TextView) rootView.findViewById(R.id.already_member_login);
        MEMBER_LOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginFragment loginFragment = new LoginFragment();
                String tag = LoginFragment.class.getCanonicalName();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_frame, loginFragment, tag)
                        .addToBackStack("login")
                        .commit();
            }
        });

        return rootView;
    }

}
