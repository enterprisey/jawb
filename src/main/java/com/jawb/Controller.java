package com.jawb;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;

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
    private StatisticsModel statisticsModel;

    public static void main( String[] args ) {
        ( new Controller() ).go();
    }

    private void go() {
        JWBF.printVersion();
        accountModel = new AccountModel();
        listModel = new DefaultListModel<>();
        editorModel = new EditorModel();
        statisticsModel = new StatisticsModel();

        setMetalBold();

        mainWindow = new MainWindow( this, listModel );
        loginDialog = new LoginDialog( mainWindow.getFrame() );
        mainWindow.setVisible( true );
    }

    public void handleLogin() {
        if( accountModel.isLoggedIn() ) {
            JOptionPane.showMessageDialog( mainWindow.getFrame(),
                    "Logged in to " + accountModel.getCredentials().getEntryPoint() +
                            " as " + accountModel.getUsername() +
                            ". (In order to log out, you need to restart the program.",
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
                    SwingUtilities.invokeLater( () -> mainWindow.getStatusBar().setProgressBarIndeterminate( true ) );
                    accountModel.login( credentials, mainWindow.getStatusBar()::setStatus );
                    SwingUtilities.invokeLater( () -> mainWindow.getStatusBar().setStatus( "Processing successful login..." ) );
                } catch ( Exception ex ) {
                    String message = credentials.getUsername().length() > 0 ?
                            "Attempted login with username " + credentials.getUsername() + " failed" :
                            "Attempted login failed";
                    message += ":\n" + ex.getMessage();
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog( mainWindow.getFrame(), message, "Login failure", JOptionPane.ERROR_MESSAGE );
                    mainWindow.getStatusBar().setStatus( "Login: Login failure (" + ex.getMessage() + ")" );
                } finally {
                    SwingUtilities.invokeLater( () -> {
                        mainWindow.getStatusBar().refreshStatistics();
                        mainWindow.getFrame().revalidate();
                        mainWindow.getFrame().repaint();
                        mainWindow.getStatusBar().setStatus( "" );
                        mainWindow.getStatusBar().setProgressBarIndeterminate( false );
                    } );
                }
            } ) ).start();
        }
    }

    public void handleStart() {
        if ( !accountModel.isLoggedIn() ) {
            JOptionPane.showMessageDialog( mainWindow.getFrame(), "Log in first!" );
            return;
        }

        nextArticle();
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
        statisticsModel.incrementEdits();
        nextArticle();
    }

    public void handleSkip() {
        if ( !accountModel.isLoggedIn() ) {
            JOptionPane.showMessageDialog( mainWindow.getFrame(), "Log in first!" );
            return;
        }

        statisticsModel.incrementSkipped();
        nextArticle();
    }

    private void nextArticle() {
        if ( listModel.isEmpty() ) {
            mainWindow.getEditorPanel().setText( "" );
            mainWindow.setTitle( "" );
            mainWindow.getStatusBar().setStatus( "No articles left in the list." );
        } else {
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
                mainWindow.setTitle( MainWindow.DEFAULT_TITLE + " - " + nextArticle.getTitle() );
                mainWindow.getStatusBar().setStatus( "" );
            } );
        }
        SwingUtilities.invokeLater( mainWindow.getStatusBar()::refreshStatistics );
    }

    public AccountModel getAccountModel() {
        return accountModel;
    }

    public StatisticsModel getStatisticsModel() {
        return statisticsModel;
    }


    /**
     * Since the Metal theme, by default, makes everything bold, this turns that off.
     * @link https://docs.oracle.com/javase/7/docs/api/javax/swing/plaf/metal/DefaultMetalTheme.html
     */
    private void setMetalBold() {
        // turn off bold fonts
        UIManager.put( "swing.boldMetal", Boolean.FALSE );

        // re-install the Metal Look and Feel
        try {
            UIManager.setLookAndFeel( new MetalLookAndFeel() );
        } catch ( UnsupportedLookAndFeelException e ) {
            e.printStackTrace();
        }
    }
}
