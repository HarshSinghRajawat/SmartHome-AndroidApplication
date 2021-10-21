package com.one.homeserverjava.ui.login;


/**
 * Authentication result : success (user details) or error message.
 */
public class LoginResult {
    private LoggedInUserView success;
    private Integer error;

    LoginResult(Integer error) {
        this.error = error;
    }

    LoginResult( LoggedInUserView success) {
        this.success = success;
    }

    LoggedInUserView getSuccess() {
        return success;
    }

    Integer getError() {
        return error;
    }
}