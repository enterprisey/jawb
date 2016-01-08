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

    private Controller controller;

    public StatusBar( Controller controller ) {
        super( new BorderLayout() );

        this.controller = controller;

        JPanel leftPanel = new JPanel();
        bar = new JProgressBar();
        bar.setPreferredSize( new Dimension( 100, 15 ) );
        leftPanel.add( bar );
        status = new JLabel( DEFAULT_STATUS );
        leftPanel.add( status );
        this.add( leftPanel, BorderLayout.WEST );

        JPanel rightPanel = new JPanel();
        statistics = new JLabel( getStatistics() );
        rightPanel.add( statistics );
        badge = new LoginBadge( controller );
        badge.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked( MouseEvent e ) {
                controller.handleLogin();
            }
        } );
        rightPanel.add( badge );
        this.add( rightPanel, BorderLayout.EAST );
    }

    private String getStatistics() {
        if( controller.getAccountModel().isLoggedIn() ) {
            return String.format( "Pages/min: ?  Edits/min: ?  Edits: %d  Skipped: %d, New: ?  %s",
                    controller.getStatisticsModel().getEdits(),
                    controller.getStatisticsModel().getSkipped(),
                    controller.getAccountModel().getCredentials().getEntryPoint()
                            .replace( "https://", "" )
                            .replace( ".org/w/api.php", "" ) );
        } else {
            return "Pages/min: 0   Edits/min: 0   Edits: 0   Skipped:" +
                    " 0   New: 0   en.wikipedia";
        }
    }

    public void refreshStatistics() {
        statistics.setText( getStatistics() );
    }

    public void setProgressBarIndeterminate( boolean value ) {
        bar.setIndeterminate( value );
    }

    public void setStatus( String newStatus ) {
        status.setText( newStatus.isEmpty() ? DEFAULT_STATUS : newStatus );
    }
}
