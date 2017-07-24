package edu.fsu.cs.mobile.onethousandwords;


import android.content.Context;
import android.os.Bundle;
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
public class RegisterFragment extends Fragment {

    private EditText NAME;
    private EditText EMAIL;
    private EditText PASSWORD;
    private Button CREATE_ACCOUNT;
    private TextView MEMBER_LOGIN;

    private OnButtonClickListener bcl;


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
                if (NAME.getText().toString().trim().equals("") || EMAIL.getText().toString().trim().equals("")
                        || PASSWORD.getText().toString().trim().equals("")) {

                    if (NAME.getText().toString().trim().equals("")) {
                        NAME.setError("Please enter your name");
                    }

                    if (EMAIL.getText().toString().trim().equals("")) {
                        EMAIL.setError("Please enter an email");
                    }

                    if (PASSWORD.getText().toString().trim().equals("")) {
                        PASSWORD.setError("Please enter a password");
                    }

                    Toast.makeText(getActivity(), "One or more field is empty", Toast.LENGTH_SHORT).show();
                }

                else {
                    bcl.onButtonClicked(v, EMAIL.getText().toString(), PASSWORD.getText().toString());
                }

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

    // This calls to send information from Register Fragment to MainActivity when clicked
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
                    + " must implement OnRegisterListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        bcl = null;
    }

    //TODO: MAKE VALIDATION FORMS
}
