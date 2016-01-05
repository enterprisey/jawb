package com.jawb.ui;

import com.jawb.ui.*;
import com.jawb.models.AccountModel;
import net.sourceforge.jwbf.core.contentRep.Article;

import javax.swing.*;

/**
 * Holds all the event listeners that interact multiple panels in the main window and/or a model.
 */
public class Listeners {
    private static MainWindow mainWindow;
    private static AccountModel accountModel;
    private static DefaultListModel<String> listModel;

    private static boolean fieldsInitialized;

    private Listeners() {}

    public static void initialize(
            MainWindow arg0,
            AccountModel arg1,
            DefaultListModel<String> arg2
    ) {
        if( fieldsInitialized ) {
            throw new IllegalStateException( "Listeners is being initialized twice!" );
        } else {
            mainWindow = arg0;
            accountModel = arg1;
            listModel = arg2;
        }
    }

    /**
     * Gives the component a listener.
     */
    public static void add( JComponent component ) {
        switch( component.getClass().getSimpleName() ) {
        case "JButton":
            addListenerToButton( (JButton)component );
        }
    }

    private static void addListenerToButton( JButton button ) {
        switch( button.getText() ) {
        case "Start":
            button.addActionListener( event -> {
                ( new Thread( () -> {
                    if ( accountModel.isLoggedIn() ) {
                        if ( !listModel.isEmpty() ) {
                            SwingUtilities.invokeLater( () -> {
                                mainWindow.getStatusBar().setProgressBarIndeterminate( true );
                                mainWindow.getStatusBar().setStatus( "Loading article..." );
                            } );
                            Article nextArticle = accountModel.getBot().getArticle( listModel.firstElement() );
                            mainWindow.getEditorPanel().setText( nextArticle.getText() );
                            mainWindow.getMakeListPanel().shiftUp();
                            SwingUtilities.invokeLater( () -> {
                                mainWindow.getStatusBar().setProgressBarIndeterminate( false );
                                mainWindow.getStatusBar().setStatus( "" );
                            } );
                        } else {
                            JOptionPane.showMessageDialog( mainWindow.getFrame(), "Add pages to the list first!" );
                        }
                    } else {
                        JOptionPane.showMessageDialog( mainWindow.getFrame(), "Log in first!" );
                    }

                } ) ).start();
            } );
        }
    }
}
