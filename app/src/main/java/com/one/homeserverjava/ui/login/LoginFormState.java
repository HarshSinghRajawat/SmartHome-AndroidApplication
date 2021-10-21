package com.one.homeserverjava.ui.login;


/**
 * Data validation state of the login form.
 */
class LoginFormState {
    private Integer usernameError;
    private Integer passwordError;
    private boolean isDataValid;

    LoginFormState(Integer usernameError, Integer passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    LoginFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    Integer getUsernameError() {
        return usernameError;
    }

    Integer getPasswordError() {
        return passwordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}