package com.jawb.ui;

import com.jawb.Controller;
import net.sourceforge.jwbf.mediawiki.actions.queries.CategoryMembersSimple;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * The panel that has a list of pages.
 */
public class MakeListPanel extends JPanel {
    private JComboBox<String> source;
    private JPanel sourceDataPanel;
    private JLabel pagesCount;
    private JTextField manualAddItemField;
    private JButton manualAddItemButton;

    private JList<String> list;
    private DefaultListModel<String> listModel;

    private JButton removeButton;
    private JButton filterButton;

    private Sources sources;

    private Controller controller;

    public MakeListPanel( Controller controller, DefaultListModel<String> listModel ) {
        super( new BorderLayout() );
        sources = new Sources();

        this.controller = controller;
        this.listModel = listModel;

        this.setBorder( BorderFactory.createEmptyBorder( 2, 0, 0, 5 ) );
        Box topPanel = new Box( BoxLayout.Y_AXIS );
        topPanel.add( new JLabel( "Make list" ) );
        JPanel sourcePanel = new JPanel( new BorderLayout() );
        sourcePanel.add( new JLabel( "Source" ), BorderLayout.WEST );
        source = new JComboBox<>( sources.getSourceNames() );
        source.addItem( "foo" );
        source.addActionListener( new SourceSelectedListener() );
        sourcePanel.add( source );
        topPanel.add( sourcePanel );
        sourceDataPanel = new JPanel( new BorderLayout() );
        topPanel.add( sourceDataPanel );
        JPanel makeListPanel = new JPanel( new BorderLayout() );
        pagesCount = new JLabel( "0 pages" );
        makeListPanel.add( pagesCount, BorderLayout.WEST );
        JButton makeListButton = new JButton( "Make list" );
        makeListButton.addActionListener( event -> {
            sources.get( (String)source.getSelectedItem() )
                    .get().stream().forEach( listModel::addElement );
            refreshPagesCountLabel();
        } );
        makeListPanel.add( makeListButton, BorderLayout.EAST );
        topPanel.add( makeListPanel );
        JPanel manualAddItem = new JPanel( new BorderLayout() );
        manualAddItemField = new JTextField( 15 );
        manualAddItem.add( manualAddItemField, BorderLayout.CENTER );
        manualAddItemButton = new JButton( "+" );
        manualAddItemButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        manualAddItemButton.setPreferredSize( new Dimension( 20, 20 ) );
        manualAddItemButton
            .addActionListener( new ButtonListener() );
        manualAddItem.add( manualAddItemButton, BorderLayout.EAST );
        topPanel.add( manualAddItem );
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

    public void refreshPagesCountLabel() {
        pagesCount.setText( String.format( "%,d page%s", listModel.getSize(),
                listModel.getSize() == 1 ? "" : "s" ) );
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
                if( index < 0 ) {
                    return;
                }

                listModel.remove( index );

                if( listModel.getSize() == 0 ) {
                    removeButton.setEnabled( false );
                }
            }
            refreshPagesCountLabel();
        }
    }

    class SourceSelectedListener implements ActionListener {

        @Override
        public void actionPerformed( ActionEvent e ) {
            Source selected = sources.get( (String)( (JComboBox)e.getSource() ).getSelectedItem() );
            sourceDataPanel.removeAll();
            sourceDataPanel.add( selected.getRequiredDataForm() );
            System.out.println( selected.getRequiredDataForm().getSize() );
            System.out.println( selected.getRequiredDataForm().getComponentCount() );
            sourceDataPanel.revalidate();
            sourceDataPanel.repaint();
        }
    }

    class Sources {
        private List<Source> sources;

        public Sources() {
            sources = new ArrayList<>();
            sources.add( new CategorySource() );
        }

        public String[] getSourceNames() {
            return (String[])sources.stream().map( Source::getName ).toArray( String[]::new );
        }

        public Source get( String name ) {
            return (Source)sources.stream()
                    .filter( source -> source.getName().equals( name ) )
                    .toArray( Source[]::new )[0];
        }
    }

    class CategorySource extends Source {
        private JTextField categoryName = new JTextField();

        public CategorySource() {
            this.name = "Category";
            JPanel form = new JPanel( new BorderLayout() );
            form.add( new JLabel( "Category" ), BorderLayout.WEST );
            categoryName = new JTextField();
            form.add( categoryName );
            this.requiredDataForm = form;
        }

        @Override
        public List<String> get() {
            CategoryMembersSimple query = new CategoryMembersSimple( controller.getAccountModel().getBot(),
                    categoryName.getText() );
            List<String> result = new ArrayList<>();
            for( String s : query ) {
                result.add( s );
            }
            return result;
        }
    }

    abstract class Source {
        protected String name;
        protected JPanel requiredDataForm;

        @SuppressWarnings( "unused" )
        abstract List<String> get();

        public String getName() {
            return name;
        }

        public JPanel getRequiredDataForm() {
            return requiredDataForm;
        }
    }
}
