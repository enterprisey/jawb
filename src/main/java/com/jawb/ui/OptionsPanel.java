package com.jawb.ui;

import com.jawb.Controller;

import java.awt.*;

import javax.swing.*;

/**
 * The panel with all the options.
 */
public class OptionsPanel extends JPanel {
    private JTextField defaultEditSummary;
    private JCheckBox lock;
    private JCheckBox minorEdit;

    Controller controller;

    public OptionsPanel( Controller controller ) {
        super( new BorderLayout() );

        this.controller = controller;

        JTabbedPane tabs = new JTabbedPane();
        JPanel skipOptions = new JPanel();
        skipOptions.add( new JLabel( "Skip options" ) );
        tabs.add( "Skip", skipOptions );

        JPanel saveOptions = new JPanel( new BorderLayout() );
        saveOptions.setBorder( BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
        JPanel summaryPanel = new JPanel( new BorderLayout() );
        summaryPanel.add( new JLabel( "Default Summary" ),
                          BorderLayout.WEST );
        defaultEditSummary = new JTextField( 10 );
        summaryPanel.add( defaultEditSummary );
        saveOptions.add( summaryPanel, BorderLayout.NORTH );
        JPanel mainSaveOptions = new JPanel( new GridLayout( 1, 2 ) );
        JPanel leftSaveOptions = new JPanel();
        lock = new JCheckBox( "Lock" );
        leftSaveOptions.add( lock );
        minorEdit = new JCheckBox( "Minor edit" );
        leftSaveOptions.add( minorEdit );
        mainSaveOptions.add( leftSaveOptions );
        JPanel rightSaveOptions = new JPanel();
        rightSaveOptions.setLayout( new BoxLayout( rightSaveOptions,
                                                   BoxLayout.Y_AXIS ) );
        JPanel rightSaveOptions1 = new JPanel( new GridLayout( 2, 1, 0, 10 ) );
        JButton startButton = new JButton( "Start" );
        startButton.addActionListener( event -> new Thread( controller::handleStart ).start() );
        rightSaveOptions1.add( startButton );
        JButton stopButton = new JButton( "Stop" );
        rightSaveOptions1.add( stopButton );
        rightSaveOptions.add( rightSaveOptions1 );
        rightSaveOptions.add( Box.createVerticalStrut( 10 ) );
        JPanel rightSaveOptions2 = new JPanel( new GridLayout( 3, 2, 10, 10 ) );
        JButton previewButton = new JButton( "Preview" );
        previewButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        rightSaveOptions2.add( previewButton );
        JButton diffButton = new JButton( "Diff" );
        diffButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        rightSaveOptions2.add( diffButton );
        JButton watchButton = new JButton( "Watch" );
        watchButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        rightSaveOptions2.add( watchButton );
        JButton moveButton = new JButton( "Move" );
        moveButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        moveButton.setEnabled( false );
        rightSaveOptions2.add( moveButton );
        JButton protectButton = new JButton( "Protect" );
        protectButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        protectButton.setEnabled( false );
        rightSaveOptions2.add( protectButton );
        JButton deleteButton = new JButton( "Delete" );
        deleteButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        deleteButton.setEnabled( false );
        rightSaveOptions2.add( deleteButton );
        rightSaveOptions.add( rightSaveOptions2 );
        rightSaveOptions.add( Box.createVerticalStrut( 10 ) );
        JPanel rightSaveOptions3 = new JPanel( new GridLayout( 2, 1, 0, 10 ) );
        rightSaveOptions3.add( new JButton( "Skip" ) );
        JButton saveButton = new JButton( "Save" );
        saveButton.addActionListener( event -> new Thread( controller::handleSave ).start() );
        rightSaveOptions3.add( saveButton );
        rightSaveOptions.add( rightSaveOptions3 );
        mainSaveOptions.add( rightSaveOptions );
        saveOptions.add( mainSaveOptions, BorderLayout.CENTER );
        tabs.add( "Start", saveOptions );
        tabs.setSelectedIndex( tabs.getTabCount() - 1 );
        this.add( tabs );
    }

    public String getDefaultEditSummary() {
        return defaultEditSummary.getText();
    }
}
