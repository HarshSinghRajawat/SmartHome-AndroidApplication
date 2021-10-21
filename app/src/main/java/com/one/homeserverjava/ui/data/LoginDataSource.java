package com.one.homeserverjava.ui.data;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.one.homeserverjava.ui.Callbacks.FirebaseCallbacks;
import com.one.homeserverjava.ui.MainActivity;
import com.one.homeserverjava.ui.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private static final String TAG = "LoginDataSource";
    LoggedInUser user;
    FirebaseAuth auth;
    boolean status;

    public void login(FirebaseCallbacks callbacks, String username, String password) {

        auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(username,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                status=true;
                if(task.isSuccessful()){
                    Log.d(TAG, "onComplete: Success");
                    user = new LoggedInUser(auth.getCurrentUser().getUid(),auth.getCurrentUser().getEmail());
                    callbacks.setLoggedIn(user);
                    callbacks.loginSuccess(new Result.Success<>(user));
                }else {
                    Log.d(TAG, "onComplete: Failed");
                    callbacks.loginFailed();
                }
            }
        });

    }

    public void logout() {
        auth.signOut();
    }
}