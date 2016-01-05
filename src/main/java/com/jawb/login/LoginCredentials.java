package com.jawb.login;

/**
 * One set of login credentials. No verification code.
 */
public class LoginCredentials {
    private String entryPoint;
    private String username;
    private String password;

    public LoginCredentials( String entryPoint, String username, String password ) {
        this.entryPoint = entryPoint;
        this.username = username;
        this.password = password;
    }

    public String getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint( String entryPoint ) {
        this.entryPoint = entryPoint;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername( String username ) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }
}
