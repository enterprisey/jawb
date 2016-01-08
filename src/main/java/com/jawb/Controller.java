package com.jawb;

import javax.swing.*;

import com.jawb.login.LoginCredentials;
import com.jawb.login.LoginDialog;
import com.jawb.models.*;
import com.jawb.ui.*;
import com.jawb.utils.*;

import net.sourceforge.jwbf.JWBF;
import net.sourceforge.jwbf.core.contentRep.Article;

public class Controller {
    private MainWindow mainWindow;
    private LoginDialog loginDialog;

    private AccountModel accountModel;
    private DefaultListModel<String> listModel;
    private EditorModel editorModel;

    public static void main( String[] args ) {
        ( new Controller() ).go();
    }

    private void go() {
        JWBF.printVersion();
        accountModel = new AccountModel();
        listModel = new DefaultListModel<>();
        editorModel = new EditorModel();

        mainWindow = new MainWindow( this, listModel );
        loginDialog = new LoginDialog( mainWindow.getFrame() );
        mainWindow.setVisible( true );
    }

    public void handleLogin() {
        if( accountModel.isLoggedIn() ) {
            JOptionPane.showMessageDialog( mainWindow.getFrame(),
                    "Logged in to " + accountModel.getCredentials().getEntryPoint() +
                            " as " + accountModel.getUsername() + ".",
                    "Login status", JOptionPane.INFORMATION_MESSAGE );
        } else {
            loginDialog.show();
            LoginCredentials credentials = loginDialog.getCredentials();
            if( credentials.getUsername().isEmpty() || credentials.getPassword().isEmpty() ) {
                StringBuilder message = new StringBuilder( "Please enter a " );
                if( credentials.getUsername().isEmpty() ) message.append( "username" );
                if( credentials.getUsername().isEmpty() && credentials.getPassword().isEmpty() ) message.append( " and " );
                if( credentials.getPassword().isEmpty() ) message.append( "password" );
                message.append( "." );
                JOptionPane.showMessageDialog( mainWindow.getFrame(),
                        message.toString(), "Login failure", JOptionPane.ERROR_MESSAGE );
                return;
            }
            ( new Thread( () -> {
                try {
                    mainWindow.getStatusBar().setProgressBarIndeterminate( true );
                    mainWindow.getFrame().repaint();
                    accountModel.login( credentials, mainWindow.getStatusBar()::setStatus );
                    System.out.println( "Successful login." );
                } catch ( Exception ex ){
                    String message = credentials.getUsername().length() > 0 ?
                            "Attempted login with username " + credentials.getUsername() + " failed" :
                            "Attempted login failed";
                    message += ":\n" + ex.getMessage();
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog( mainWindow.getFrame(), message, "Login failure", JOptionPane.ERROR_MESSAGE );
                    mainWindow.getStatusBar().setStatus( "Login: Login failure (" + ex.getMessage() + ")" );
                } finally {
                    mainWindow.getFrame().revalidate();
                    mainWindow.getFrame().repaint();
                    mainWindow.getStatusBar().setProgressBarIndeterminate( false );
                }
            } ) ).start();
        }
    }

    public void handleStart() {
        if ( !accountModel.isLoggedIn() ) {
            JOptionPane.showMessageDialog( mainWindow.getFrame(), "Log in first!" );
            return;
        }

        if ( listModel.isEmpty() ) {
            JOptionPane.showMessageDialog( mainWindow.getFrame(), "Add pages to the list first!" );
            return;
        }

        SwingUtilities.invokeLater( () -> {
            mainWindow.getStatusBar().setProgressBarIndeterminate( true );
            mainWindow.getStatusBar().setStatus( "Loading article..." );
        } );
        Article nextArticle = accountModel.getBot().getArticle( listModel.firstElement() );
        mainWindow.getEditorPanel().setText( nextArticle.getText() );
        mainWindow.getEditorPanel().setEditSummary( mainWindow.getOptionsPanel().getDefaultEditSummary() );
        mainWindow.getMakeListPanel().shiftUp();
        editorModel.setCurrentArticle( nextArticle );
        SwingUtilities.invokeLater( () -> {
            mainWindow.getStatusBar().setProgressBarIndeterminate( false );
            mainWindow.getStatusBar().setStatus( "" );
        } );
    }

    public void handleSave() {
        if ( !accountModel.isLoggedIn() ) {
            JOptionPane.showMessageDialog( mainWindow.getFrame(), "Log in first!" );
            return;
        }

        SwingUtilities.invokeLater( () -> {
            mainWindow.getStatusBar().setProgressBarIndeterminate( true );
            mainWindow.getStatusBar().setStatus( "Saving article..." );
        } );
        Article currentArticle = editorModel.getCurrentArticle();
        currentArticle.setEditSummary( mainWindow.getEditorPanel().getEditSummary() +
                                       Configurables.EDIT_SUMMARY_SUFFIX );
        currentArticle.setText( mainWindow.getEditorPanel().getText() );
        currentArticle.save();
        mainWindow.getEditorPanel().setText( "" );
        SwingUtilities.invokeLater( () -> {
            mainWindow.getStatusBar().setProgressBarIndeterminate( false );
            mainWindow.getStatusBar().setStatus( "" );
        } );
    }

    public AccountModel getAccountModel() {
        return accountModel;
    }
}
