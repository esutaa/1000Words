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

        GoogleSignInOptions gso = new
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        gac = new GoogleApiClient.Builder(this)
                .enableAutoManage(MainActivity.this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(MainActivity.this, "CONNECTION FAILED", Toast.LENGTH_SHORT).show();
                    }
                } /* OnConnectionFailedListener*/)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fTransaction = manager.beginTransaction();

        //Check if user is logged in
        if (auth.getCurrentUser() == null) {
            fTransaction.add(R.id.fragment_frame, LoginFragment.newInstance());
        }
        else {
            fTransaction.add(R.id.fragment_frame, ListFragment.newInstance());
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
                if (!task.isSuccessful()) {
                    Log.d("MainActivity", "onComplete: Failed=" + task.getException().getMessage());
                    Toast.makeText(MainActivity.this, "Registration Failed. Try again?", Toast.LENGTH_SHORT).show();
                }
                else {
                    ListFragment listFragment = new ListFragment();
                    String tag = ListFragment.class.getCanonicalName();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, listFragment, tag).commit();
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
                            ListFragment listFragment = new ListFragment();
                            String tag = ListFragment.class.getCanonicalName();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,
                                    listFragment, tag).commit();
                        }

                        else{
                            Log.d("MainActivity", "onComplete: Failed=" + task.getException().getMessage());
                            Toast.makeText(MainActivity.this, "Login failed. Try again?", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Function signs user out, returning to log in page
    private void SignOut() {
        auth.signOut();
        LoginFragment loginFragment = new LoginFragment();
        String tag = DrawingFragment.class.getCanonicalName();
        this.getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_frame, loginFragment, tag)
                .addToBackStack("Login")
                .commit();
    }

}
