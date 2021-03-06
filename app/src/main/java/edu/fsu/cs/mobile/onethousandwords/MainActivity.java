package edu.fsu.cs.mobile.onethousandwords;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements
        LoginFragment.OnButtonClickListener, RegisterFragment.OnButtonClickListener{

    FirebaseAuth auth;
    GoogleApiClient gac;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fTransaction = manager.beginTransaction();

        //Check if user is logged in
        if (auth.getCurrentUser() == null) {
            fTransaction.add(R.id.fragment_frame, LoginFragment.newInstance());
        }
        else {  // If not, go to fragment for user
            fTransaction.add(R.id.fragment_frame, DrawingFragment.newInstance());
        }

        fTransaction.commit();

    }

    // Manages button click for register and log in fragments
    @Override
    public void onButtonClicked(View v, String email, String password) {
        int i = v.getId();

        if (i == R.id.login_btn){
            SignIn(email, password);
        }
        else if (i == R.id.create_account_btn) {
            CreateAccount(email, password);
        }
    }

    // Function creates user account
    private void CreateAccount(String email, String pwd) {

        auth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {     // Checking if registration failed
                    Log.d("MainActivity", "onComplete: Failed=" + task.getException().getMessage());
                    Toast.makeText(MainActivity.this, "Registration Failed. Try again?", Toast.LENGTH_SHORT).show();
                }
                else {  // If not, go to DrawingFragment
                    DrawingFragment DrawFragment = new DrawingFragment();
                    String tag = DrawingFragment.class.getCanonicalName();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, DrawFragment, tag).commit();
                }
            }
        });

    }

    // Function signs user in, sending them to drawing page
    private void SignIn(String email, String pwd) {
        auth.signInWithEmailAndPassword(email, pwd).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            DrawingFragment drawFragment = new DrawingFragment();
                            String tag = DrawingFragment.class.getCanonicalName();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,
                                    drawFragment, tag).commit();
                        }

                        else{   // Checking if login failed
                            Log.d("MainActivity", "onComplete: Failed=" + task.getException().getMessage());
                            Toast.makeText(MainActivity.this, "Login failed. Try again?", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
