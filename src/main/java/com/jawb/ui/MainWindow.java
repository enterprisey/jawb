package com.jawb.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.jawb.models.*;
import com.jawb.utils.*;

/**
 * Manages the main window.
 */
public class MainWindow {
    private JFrame frame;
    private JEditorPane editPane;
    private JSplitPane mainSplitPane;
    private JSplitPane bottomSplitPane;

    private MakeListPanel makeListPanel;
    private OptionsPanel optionsPanel;
    private EditorPanel editorPanel;
    private StatusBar statusBar;

    private AccountModel accountModel;
    private DefaultListModel<String> listModel;

    private static final double MAIN_SPLIT = 0.5;
    private static final double BOTTOM_SPLIT = 0.455;

    public MainWindow( AccountModel accountModel, DefaultListModel<String> listModel ) {
        this.accountModel = accountModel;
        this.listModel = listModel;
    }

    private void buildGui() {
        PropertiesManager props = PropertiesManager.getInstance();
        frame = new JFrame( "JAutoWikiBrowser " +
                            props.getProperty( "application.version" ) );
        JPanel mainPanel = new JPanel( new BorderLayout() );
        mainPanel.setBorder( BorderFactory.createEmptyBorder( 2, 2, 2, 2 ) );
        JPanel bottomLeftPanel = new JPanel( new BorderLayout() );
        setMakeListPanel( new MakeListPanel( listModel ) );
        bottomLeftPanel.add( getMakeListPanel(), BorderLayout.WEST );
        setOptionsPanel( new OptionsPanel() );
        bottomLeftPanel.add( getOptionsPanel(), BorderLayout.CENTER );
        setEditorPanel( new EditorPanel() );
        bottomSplitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT,
                                          bottomLeftPanel,
                getEditorPanel() );
        mainSplitPane = new JSplitPane( JSplitPane.VERTICAL_SPLIT,
                                        getDiffPanel(),
                                        bottomSplitPane );
        mainPanel.add( mainSplitPane, BorderLayout.CENTER );
        setStatusBar( new StatusBar( this, accountModel ) );
        mainPanel.add( getStatusBar(), BorderLayout.SOUTH );
        frame.add( mainPanel );
        frame.addComponentListener( new ComponentAdapter() {
                @Override
                public void componentResized( ComponentEvent e ) {
                    mainSplitPane.setDividerLocation( MAIN_SPLIT );
                    bottomSplitPane.setDividerLocation( BOTTOM_SPLIT );
                }
            } );
        frame.setBounds( 200, 25, 1133, 600 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }

    private JPanel getDiffPanel() {
        JPanel diff = new JPanel();
        diff.add( new JLabel( "Diff goes here" ) );
        return diff;
    }

    public void setVisible( boolean visible ) {
        if( frame == null ) {
            buildGui();
        }
        frame.setVisible( visible );
        if( visible ) {
            mainSplitPane.setDividerLocation( MAIN_SPLIT );
            bottomSplitPane.setDividerLocation( BOTTOM_SPLIT );
        }
    }

    public JFrame getFrame() {
        return frame;
    }

    MakeListPanel getMakeListPanel() {
        return makeListPanel;
    }

    void setMakeListPanel( MakeListPanel makeListPanel ) {
        this.makeListPanel = makeListPanel;
    }

    OptionsPanel getOptionsPanel() {
        return optionsPanel;
    }

    void setOptionsPanel( OptionsPanel optionsPanel ) {
        this.optionsPanel = optionsPanel;
    }

    EditorPanel getEditorPanel() {
        return editorPanel;
    }

    void setEditorPanel( EditorPanel editorPanel ) {
        this.editorPanel = editorPanel;
    }

    StatusBar getStatusBar() {
        return statusBar;
    }

    void setStatusBar( StatusBar statusBar ) {
        this.statusBar = statusBar;
    }
}
