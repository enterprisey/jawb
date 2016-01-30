package com.jawb.ui;

import com.jawb.Controller;
import com.jawb.utils.Helper;

import java.awt.*;

import javax.swing.*;

/**
 * The panel with all the options.
 */
public class OptionsPanel extends JPanel {
    private JCheckBox autoTag;
    private JCheckBox applyGenFixes;
    private JCheckBox unicodifyWholePage;
    private JCheckBox findAndReplaceEnabled;
    private JCheckBox findAndReplaceSkipNo;
    private JCheckBox findAndReplaceSkipOnly;

    private JTextField defaultEditSummary;
    private JCheckBox lock;
    private JCheckBox minorEdit;

    public OptionsPanel( Controller controller ) {
        super( new BorderLayout() );

        JTabbedPane tabs = new JTabbedPane();

        tabs.add( "Options", getOptionsTab() );

        JPanel skipOptions = new JPanel();
        skipOptions.add( new JLabel( "Skip options" ) );
        tabs.add( "Skip", skipOptions );

        JPanel saveOptions = new JPanel( new BorderLayout() );
        saveOptions.setBorder( BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
        JPanel summaryPanel = new JPanel( new BorderLayout() );
        summaryPanel.add( new JLabel( "Default Summary" ), BorderLayout.WEST );
        defaultEditSummary = new JTextField( 10 );
        summaryPanel.add( defaultEditSummary );
        saveOptions.add( summaryPanel, BorderLayout.NORTH );
        JPanel mainSaveOptions = new JPanel( new GridLayout( 1, 2 ) );
        JPanel leftSaveOptions = new JPanel();
        leftSaveOptions.setLayout( new BoxLayout( leftSaveOptions, BoxLayout.Y_AXIS ) );
        lock = new JCheckBox( "Lock" );
        leftSaveOptions.add( lock );
        minorEdit = new JCheckBox( "Minor edit" );
        leftSaveOptions.add( minorEdit );
        mainSaveOptions.add( leftSaveOptions );
        JPanel rightSaveOptions = new JPanel();
        rightSaveOptions.setLayout( new BoxLayout( rightSaveOptions, BoxLayout.Y_AXIS ) );
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
        JButton skipButton = new JButton( "Skip" );
        skipButton.addActionListener( event -> new Thread( controller::handleSkip ).start() );
        rightSaveOptions3.add( skipButton );
        JButton saveButton = new JButton( "Save" );
        saveButton.addActionListener( event -> new Thread( controller::handleSave ).start() );
        rightSaveOptions3.add( saveButton );
        rightSaveOptions.add( rightSaveOptions3 );
        mainSaveOptions.add( rightSaveOptions );
        saveOptions.add( mainSaveOptions, BorderLayout.CENTER );
        tabs.add( "Start", saveOptions );

        tabs.setSelectedIndex( 0 );
        this.add( tabs );
    }

    public JComponent getOptionsTab() {
        JPanel optionsOptions = new JPanel();
        optionsOptions.setLayout( new BoxLayout( optionsOptions, BoxLayout.Y_AXIS ) );
        JPanel automatic = new JPanel( new BorderLayout() );
        automatic.setAlignmentX( Component.LEFT_ALIGNMENT );
        automatic.setBorder( Helper.getLineTitledBorder( "Automatic changes" ) );
        JPanel leftAutomatic = new JPanel( new GridLayout( 3, 1, 0, 2 ) );
        autoTag = new JCheckBox( "Auto tag" );
        autoTag.setEnabled( false );
        leftAutomatic.add( autoTag );
        applyGenFixes = new JCheckBox( "Apply general fixes" );
        applyGenFixes.setEnabled( false );
        leftAutomatic.add( applyGenFixes );
        unicodifyWholePage = new JCheckBox( "Unicodify whole page" );
        unicodifyWholePage.setEnabled( false );
        leftAutomatic.add( unicodifyWholePage );
        automatic.add( leftAutomatic, BorderLayout.WEST );
        JPanel rightAutomatic = new JPanel( new BorderLayout() );
        rightAutomatic.setBorder( BorderFactory.createEmptyBorder( 20, 5, 20, 5 ) );
        JButton genFixesSkipOptions = new JButton( "<html><center>General fixes<br>skip options</center></html>" );
        genFixesSkipOptions.setMargin( new Insets( 0, 0, 0, 0 ) );
        rightAutomatic.add( genFixesSkipOptions, BorderLayout.WEST );
        automatic.add( rightAutomatic, BorderLayout.CENTER );
        optionsOptions.add( automatic );
        JPanel findAndReplace = new JPanel( new BorderLayout() );
        findAndReplace.setAlignmentX( Component.LEFT_ALIGNMENT );
        findAndReplace.setBorder( Helper.getLineTitledBorder( "Find and replace" ) );
        findAndReplace.setLayout( new BoxLayout( findAndReplace, BoxLayout.Y_AXIS ) );
        JPanel findAndReplaceEnabledPanel = new JPanel( new BorderLayout() );
        findAndReplaceEnabledPanel.setBackground( Color.RED );
        findAndReplaceEnabledPanel.setAlignmentX( Component.LEFT_ALIGNMENT );
        findAndReplaceEnabled = new JCheckBox( "Enabled" );
        findAndReplaceEnabledPanel.add( findAndReplaceEnabled, BorderLayout.WEST );
        JPanel findAndReplaceEnabledButtons = new JPanel( new GridLayout( 3, 1, 0, 2 ) );
        JButton normalSettings = new JButton( "Normal settings" );
        normalSettings.setMargin( new Insets( 2, 0, 2, 0 ) );
        findAndReplaceEnabledButtons.add( normalSettings );
        JButton advancedSettings = new JButton( "Advanced settings" );
        advancedSettings.setMargin( new Insets( 2, 0, 2, 0 ) );
        findAndReplaceEnabledButtons.add( advancedSettings );
        JButton templateSubstitution = new JButton( "Template substitution" );
        templateSubstitution.setMargin( new Insets( 2, 0, 2, 0 ) );
        findAndReplaceEnabledButtons.add( templateSubstitution );
        JPanel findAndReplaceEnabledButtonsWrapper = new JPanel( new BorderLayout() );
        findAndReplaceEnabledButtonsWrapper.add( findAndReplaceEnabledButtons, BorderLayout.WEST );
        findAndReplaceEnabledPanel.add( findAndReplaceEnabledButtonsWrapper );
        findAndReplace.add( findAndReplaceEnabledPanel );
        JPanel findAndReplaceSkipPanel = new JPanel( new BorderLayout() );
        findAndReplaceSkipPanel.setAlignmentX( Component.LEFT_ALIGNMENT );
        findAndReplaceSkipPanel.add( new JLabel( "Skip if" ), BorderLayout.WEST );
        JPanel findAndReplaceSkipCheckboxesPanel = new JPanel( new GridLayout( 2, 1 ) );
        findAndReplaceSkipNo = new JCheckBox( "no replacement" );
        findAndReplaceSkipNo.setMargin( new Insets( 2, 0, 2, 0 ) );
        findAndReplaceSkipCheckboxesPanel.add( findAndReplaceSkipNo );
        findAndReplaceSkipOnly  = new JCheckBox( "only minor replacement made" );
        findAndReplaceSkipOnly.setMargin( new Insets( 2, 0, 2, 0 ) );
        findAndReplaceSkipCheckboxesPanel.add( findAndReplaceSkipOnly );
        findAndReplaceSkipPanel.add( findAndReplaceSkipCheckboxesPanel );
        findAndReplace.add( findAndReplaceSkipPanel, BorderLayout.SOUTH );
        optionsOptions.add( findAndReplace );
        optionsOptions.setMaximumSize( optionsOptions.getPreferredSize() );
        return optionsOptions;
    }

    public String getDefaultEditSummary() {
        return defaultEditSummary.getText();
    }
}
