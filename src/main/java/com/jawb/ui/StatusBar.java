package com.jawb.ui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import com.jawb.Controller;
import com.jawb.login.LoginBadge;
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

    public StatusBar( Controller controller ) {
        super();

        this.setLayout( new BoxLayout( this, BoxLayout.X_AXIS ) );
        bar = new JProgressBar();
        bar.setPreferredSize( new Dimension( 5, 15 ) );
        this.add( bar );
        status = new JLabel( DEFAULT_STATUS );
        this.add( status );
        this.add( Box.createHorizontalGlue() );
        statistics = new JLabel( getStatistics() );
        this.add( statistics );
        badge = new LoginBadge( controller );
        badge.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked( MouseEvent e ) {
                controller.handleLogin();
            }
        } );
        this.add( badge );
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
}
