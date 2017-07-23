package edu.fsu.cs.mobile.onethousandwords;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginFragment loginFragment = new LoginFragment();
        String tag = LoginFragment.class.getCanonicalName();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, loginFragment, tag).commit();


    }


}
