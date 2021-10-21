package com.one.homeserverjava.ui.Callbacks;

import com.one.homeserverjava.ui.data.Result;
import com.one.homeserverjava.ui.data.model.LoggedInUser;

public interface FirebaseCallbacks {
    void loginSuccess(Result<LoggedInUser> user);
    void loginFailed();
    void setLoggedIn(LoggedInUser user);
}
