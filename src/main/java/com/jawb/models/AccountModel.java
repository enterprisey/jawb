package com.jawb.models;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.jawb.login.LoginCredentials;
import net.sourceforge.jwbf.core.actions.HttpActionClient;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

import com.jawb.utils.PropertiesManager;

/**
 * Handles everything related to accounts.
 */
public class AccountModel {
    private MediaWikiBot bot;
    private LoginCredentials credentials;

    public void login( LoginCredentials credentials, Consumer<String> statusCallback ) {
        String url = credentials.getEntryPoint();
        String username = credentials.getUsername();
        String password = credentials.getPassword();
        statusCallback.accept( "Connecting to server..." );
        HttpActionClient client = HttpActionClient.builder()
                .withUrl( url )
                .withUserAgent( "JAWB", getUAVersion(), username )
                .withRequestsPerUnit( 10, TimeUnit.MINUTES )
                .build();
        statusCallback.accept( "Creating connection object..." );
        bot = new MediaWikiBot( client );
        statusCallback.accept( "Logging in..." );
        bot.login( username, password );
        statusCallback.accept( "Logged in!" );
        this.credentials = credentials;
    }

    public boolean isLoggedIn() {
        return bot != null && bot.isLoggedIn();
    }

    public LoginCredentials getCredentials() {
        if( isLoggedIn() ) {
            return credentials;
        } else {
            return null;
        }
    }

    public String getUsername() {
        if( isLoggedIn() ) {
            return bot.getUserinfo().getUsername();
        } else {
            return "";
        }
    }

    public MediaWikiBot getBot() {
        return bot;
    }

    /**
     * @return The version to be used for the user agent.
     */
    private String getUAVersion() {
        PropertiesManager pm = PropertiesManager.getInstance();
        return pm.getProperty( "application.name" ) +
            " " + pm.getProperty( "application.version" );
    }
}
