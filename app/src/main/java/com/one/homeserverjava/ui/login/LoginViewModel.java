package com.one.homeserverjava.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.app.Application;
import android.util.Patterns;

import com.one.homeserverjava.R;
import com.one.homeserverjava.ui.Callbacks.FirebaseCallbacks;
import com.one.homeserverjava.ui.data.LoginRepository;
import com.one.homeserverjava.ui.data.Result;
import com.one.homeserverjava.ui.data.model.LoggedInUser;
import com.one.homeserverjava.ui.viewModel.BaseViewModel;

public class LoginViewModel extends BaseViewModel implements FirebaseCallbacks {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(Application application, LoginRepository loginRepository) {
        super(application);
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        loginRepository.login(this,username, password);
    }

    public boolean checkUserLogin(){
        return getRepository().preferences.getUserStatus();
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    protected void saveUser(){
        getRepository().preferences.setUserStatus( loginRepository.isLoggedIn() );
    }

    @Override
    public void loginSuccess(Result<LoggedInUser> user) {
        saveUser();
        LoggedInUser data = ((Result.Success<LoggedInUser>) user).getData();
        loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
    }

    @Override
    public void loginFailed() {
        loginResult.setValue(new LoginResult(R.string.login_failed));
    }

    @Override
    public void setLoggedIn(LoggedInUser user) {
        loginRepository.setLoggedInUser(user);
    }
}