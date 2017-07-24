package edu.fsu.cs.mobile.onethousandwords;


import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private EditText EMAIL;
    private EditText PASSWORD;
    private Button LOGIN;
    private TextView CREATE;

    private OnButtonClickListener bcl;


    public LoginFragment () {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
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
                if (EMAIL.getText().toString().trim().equals("") || PASSWORD.getText().toString().trim().equals("")) {
                    if (EMAIL.getText().toString().trim().equals("")) {
                        EMAIL.setError("Please enter your email");
                    }

                    if (PASSWORD.getText().toString().trim().equals("")) {
                        PASSWORD.setError("Please enter a password");
                    }

                    Toast.makeText(getActivity(), "One or more field is empty", Toast.LENGTH_SHORT).show();
                }

                else{
                    bcl.onButtonClicked(v, EMAIL.getText().toString(), PASSWORD.getText().toString());
                }
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

    // This calls to send information from Login Fragment to MainActivity
    public interface OnButtonClickListener {
        void onButtonClicked(View button, String email, String password);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnButtonClickListener) {
            bcl = (OnButtonClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLoginListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        bcl = null;
    }

    //TODO: Make validation forms
}
