package com.jawb.login;

import com.jawb.utils.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Manages the login dialog.
 */
public class LoginDialog {
    private JDialog dialog;
    private JTextField entryPoint;
    private JTextField username;
    private JPasswordField password;

    public LoginDialog( JFrame frame ) {
        JComboBox site = new JComboBox<>( new String[] { "en.wikipedia.org", "test.wikipedia.org" } );
        site.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                String site = ( String ) ( (JComboBox)e.getSource() ).getSelectedItem();
                if( site.equals( "en.wikipedia.org" ) || site.equals( "test.wikipedia.org" ) ) {
                    entryPoint.setText( "https://" + site + "/w/api.php" );
                    entryPoint.setEditable( false );
                } else {
                    entryPoint.setText( "" );
                    entryPoint.setEditable( true );
                }
            }
        } );
        Helper.setComponentFontSize( site, 16 );
        site.setEditable( true );
        entryPoint = new JTextField( "https://en.wikipedia.org/w/api.php" );
        Helper.setComponentFontSize( entryPoint, 16 );
        entryPoint.setEditable( false );
        username = new JTextField();
        Helper.setComponentFontSize( username, 16 );
        password = new JPasswordField();
        Helper.setComponentFontSize( password, 16 );
        password.addActionListener( new LoginDialogListener() );
        JButton login = new JButton( "Login" );
        login.addActionListener( new LoginDialogListener() );
        JLabel siteLabel = new JLabel( "Site" );
        JLabel entryPointLabel = new JLabel( "API entry point" );
        JLabel usernameLabel = new JLabel( "Username" );
        JLabel passwordLabel = new JLabel( "Password" );

        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        panel.setLayout( layout );
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        panel.add( siteLabel, c );
        c.gridx = 1;
        panel.add( site, c );
        c.gridx = 0;
        c.gridy = 1;
        panel.add( entryPointLabel, c );
        c.gridx = 1;
        panel.add( entryPoint, c );
        c.gridx = 0;
        c.gridy = 2;
        panel.add( usernameLabel, c );
        c.gridx = 1;
        panel.add( username, c );
        c.gridx = 0;
        c.gridy = 3;
        panel.add( passwordLabel, c );
        c.gridx = 1;
        panel.add( password, c );
        c.gridy = 4;
        c.weightx = 0;
        c.weighty = 0;
        c.anchor = GridBagConstraints.EAST;
        panel.add( login, c );

        dialog = new JDialog( frame, "Login", true );
        dialog.setContentPane( panel );
        dialog.setDefaultCloseOperation( JDialog.HIDE_ON_CLOSE );
        dialog.setBounds( Helper.getCenteredBounds( 485, 300 ) );
    }

    public void show() {
        dialog.setVisible( true );
    }

    public LoginCredentials getCredentials() {
        return new LoginCredentials( entryPoint.getText(),
                                     username.getText(),
                                     new String( password.getPassword() ) );
    }

    public class LoginDialogListener implements ActionListener {
        @Override
        public void actionPerformed( ActionEvent e ) {
            dialog.setVisible( false );
        }
    }
}
