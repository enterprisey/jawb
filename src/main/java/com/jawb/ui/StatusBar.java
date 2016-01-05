package com.jawb.ui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import com.jawb.login.LoginBadge;
import com.jawb.login.LoginCredentials;
import com.jawb.login.LoginDialog;
import com.jawb.models.*;
import com.jawb.utils.*;

/**
 * The status bar at the bottom of the window.
 */
public class StatusBar extends JPanel {
    static final String DEFAULT_STATUS = "JAutoWikiBrowser " +
        PropertiesManager.getInstance().getProperty( "application.version" );

    private JProgressBar bar;
    private JLabel status;
    private JLabel statistics;
    private LoginBadge badge;

    private LoginDialog dialog;

    private MainWindow mainWindow;
    private AccountModel accountModel;

    public StatusBar( MainWindow mainWindow, AccountModel accountModel ) {
        super();

        this.mainWindow = mainWindow;
        this.accountModel = accountModel;

        this.setLayout( new BoxLayout( this, BoxLayout.X_AXIS ) );
        bar = new JProgressBar();
        bar.setPreferredSize( new Dimension( 5, 15 ) );
        this.add( bar );
        status = new JLabel( DEFAULT_STATUS );
        this.add( status );
        this.add( Box.createHorizontalGlue() );
        statistics = new JLabel( getStatistics() );
        this.add( statistics );
        badge = new LoginBadge( accountModel );
        badge.addMouseListener( new LoginBadgeListener( status, bar ) );
        this.add( badge );

        dialog = new LoginDialog( mainWindow.getFrame() );
    }

    private String getStatistics() {
        return "Pages/min: 0   Edits/min: 0   Edits: 0   Skipped:" +
            " 0   New: 0   en.wikipedia";
    }

    public void setProgressBarIndeterminate( boolean value ) {
        bar.setIndeterminate( value );
    }

    public void setStatus( String newStatus ) {
        status.setText( newStatus.isEmpty() ? DEFAULT_STATUS : newStatus );
    }

    public class LoginBadgeListener extends MouseAdapter {
        private JLabel statusLabel;
        private JProgressBar progressBar;

        public LoginBadgeListener( JLabel statusLabel, JProgressBar progressBar ) {
            this.statusLabel = statusLabel;
            this.progressBar = progressBar;
        }

        public void mouseClicked( MouseEvent event ) {
            if( accountModel.isLoggedIn() ) {
                JOptionPane.showMessageDialog( mainWindow.getFrame(),
                        "Logged in to " + accountModel.getCredentials().getEntryPoint() +
                        " as " + accountModel.getUsername() + ".",
                        "Login status", JOptionPane.INFORMATION_MESSAGE );
            } else {
                dialog.show();
                LoginCredentials credentials = dialog.getCredentials();
                if( credentials.getUsername().isEmpty() || credentials.getPassword().isEmpty() ) {
                    JOptionPane.showMessageDialog( mainWindow.getFrame(),
                            "Please enter a username and password.", "Login failure", JOptionPane.ERROR_MESSAGE );
                    return;
                }
                ( new Thread( new Runnable() {
                    @Override
                    public void run() {
                        try {
                            progressBar.setIndeterminate( true );
                            mainWindow.getFrame().repaint();
                            accountModel.login( credentials, s -> statusLabel.setText( "Login: " + s ) );
                            System.out.println( "Successful login." );
                            mainWindow.getFrame().revalidate();
                            mainWindow.getFrame().repaint();
                            progressBar.setIndeterminate( false );
                        } catch ( Exception ex ){
                            String message = credentials.getUsername().length() > 0 ?
                                    "Attempted login with username " + credentials.getUsername() + " failed" :
                                    "Attempted login failed";
                            message += "\n" + ex.getMessage();
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog( mainWindow.getFrame(), message, "Login failure", JOptionPane.ERROR_MESSAGE );
                        }
                    }
                } ) ).start();
            }
        }
    }
}
