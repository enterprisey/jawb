package com.jawb.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.jawb.Controller;
import com.jawb.utils.*;

/**
 * Manages the main window.
 */
public class MainWindow {
    private JFrame frame;
    private JSplitPane mainSplitPane;
    private JSplitPane southSplitPane;

    private MakeListPanel makeListPanel;
    private OptionsPanel optionsPanel;
    private EditorPanel editorPanel;
    private StatusBar statusBar;

    private Controller controller;
    private DefaultListModel<String> listModel;

    private static final double MAIN_SPLIT = 0.5;
    private static final double BOTTOM_SPLIT = 0.455;

    public static final String DEFAULT_TITLE = "JAutoWikiBrowser " +
            PropertiesManager.getInstance().getProperty( "application.version" );

    public MainWindow( Controller controller, DefaultListModel<String> listModel ) {
        this.controller = controller;
        this.listModel = listModel;
    }

    private void buildGui() {
        frame = new JFrame( DEFAULT_TITLE );
        JPanel mainPanel = new JPanel( new BorderLayout() );
        mainPanel.setBorder( BorderFactory.createEmptyBorder( 2, 2, 2, 2 ) );
        JPanel bottomLeftPanel = new JPanel( new BorderLayout() );
        setMakeListPanel( new MakeListPanel( listModel ) );
        bottomLeftPanel.add( getMakeListPanel(), BorderLayout.WEST );
        setOptionsPanel( new OptionsPanel( controller ) );
        bottomLeftPanel.add( getOptionsPanel(), BorderLayout.CENTER );
        setEditorPanel( new EditorPanel() );
        southSplitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT,
                                          bottomLeftPanel,
                                          getEditorPanel() );
        mainSplitPane = new JSplitPane( JSplitPane.VERTICAL_SPLIT,
                                        getDiffPanel(),
                                        southSplitPane );
        mainPanel.add( mainSplitPane, BorderLayout.CENTER );
        setStatusBar( new StatusBar( controller ) );
        mainPanel.add( getStatusBar(), BorderLayout.SOUTH );
        frame.add( mainPanel );
        frame.addComponentListener( new ComponentAdapter() {
                @Override
                public void componentResized( ComponentEvent e ) {
                    mainSplitPane.setDividerLocation( MAIN_SPLIT );
                    southSplitPane.setDividerLocation( BOTTOM_SPLIT );
                }
            } );
        frame.setBounds( 200, 25, 1133, 600 );
        frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
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
            southSplitPane.setDividerLocation( BOTTOM_SPLIT );
        }
    }

    public void setTitle( String title ) {
        if( title.equals( "" ) ) {
            title = DEFAULT_TITLE;
        }
        frame.setTitle( title );
    }

    public JFrame getFrame() {
        return frame;
    }

    public MakeListPanel getMakeListPanel() {
        return makeListPanel;
    }

    void setMakeListPanel( MakeListPanel makeListPanel ) {
        this.makeListPanel = makeListPanel;
    }

    public OptionsPanel getOptionsPanel() {
        return optionsPanel;
    }

    void setOptionsPanel( OptionsPanel optionsPanel ) {
        this.optionsPanel = optionsPanel;
    }

    public EditorPanel getEditorPanel() {
        return editorPanel;
    }

    void setEditorPanel( EditorPanel editorPanel ) {
        this.editorPanel = editorPanel;
    }

    public StatusBar getStatusBar() {
        return statusBar;
    }

    void setStatusBar( StatusBar statusBar ) {
        this.statusBar = statusBar;
    }
}
