package com.jawb.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * The panel that has a list of pages.
 */
public class MakeListPanel extends JPanel {
    private JTextField manualAddItemField;
    private JButton manualAddItemButton;

    private JList<String> list;
    private DefaultListModel<String> listModel;

    private JButton removeButton;
    private JButton filterButton;

    public MakeListPanel( DefaultListModel<String> listModel ) {
        super( new BorderLayout() );

        this.listModel = listModel;

        this.setBorder( BorderFactory.createEmptyBorder( 2, 0, 0, 5 ) );
        JPanel topPanel = new JPanel( new BorderLayout() );
        topPanel.add( new JLabel( "Make list" ), BorderLayout.NORTH );
        manualAddItemField = new JTextField( 15 );
        topPanel.add( manualAddItemField, BorderLayout.CENTER );
        manualAddItemButton = new JButton( "+" );
        manualAddItemButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        manualAddItemButton.setPreferredSize( new Dimension( 20, 20 ) );
        manualAddItemButton
            .addActionListener( new ButtonListener() );
        topPanel.add( manualAddItemButton, BorderLayout.EAST );
        this.add( topPanel, BorderLayout.NORTH );
        list = new JList<>( listModel );
        JScrollPane scrollPane = new JScrollPane( list );
        scrollPane.setPreferredSize( new Dimension( 0, 0 ) );
        this.add( scrollPane, BorderLayout.CENTER );
        JPanel bottomPanel = new JPanel( new GridLayout( 1, 2, 15, 0 ) );
        removeButton = new JButton( "Remove" );
        removeButton.setEnabled( false );
        removeButton.addActionListener( new ButtonListener() );
        bottomPanel.add( removeButton );
        filterButton = new JButton( "Filter" );
        bottomPanel.add( filterButton );
        this.add( bottomPanel, BorderLayout.SOUTH );
    }

    /**
     * Removes the top item on the list and puts it in the text box.
     */
    public void shiftUp() {
        String page = listModel.firstElement();
        listModel.remove( 0 );
        manualAddItemField.setText( page );
    }

    public class ButtonListener implements ActionListener {
        public void actionPerformed( ActionEvent event ) {
            String buttonText = ( (JButton)event.getSource() ).getText();
            if( buttonText.equals( "+" ) ) {
                String item = manualAddItemField.getText();
                if( item.equals( "" ) ) {
                    Toolkit.getDefaultToolkit().beep();
                } else {
                    listModel.addElement( item );
                    manualAddItemField.setText( "" );
                    removeButton.setEnabled( true );
                }
            } else if( buttonText.equals( "Remove" ) ) {
                int index = list.getSelectedIndex();
                listModel.remove( index );

                if( listModel.getSize() == 0 ) {
                    removeButton.setEnabled( false );
                }
            }
        }
    }
}
