package com.jawb.ui;

import java.awt.*;

import javax.swing.*;

/**
 * The pane with the wikitext editor.
 */
public class EditorPanel extends JPanel {
    private JTextField editSummary;
    private JEditorPane editorPane;

    public EditorPanel() {
        super( new BorderLayout() );
        this.setBorder( BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );

        // We need this so that the slider can be dragged around
        this.setMinimumSize( new Dimension( 0, 0 ) );

        editSummary = new JTextField();
        this.add( editSummary, BorderLayout.NORTH );

        editorPane = new JEditorPane();
        editorPane.setFont( new Font( Font.MONOSPACED, Font.PLAIN, 12 ) );
        this.add( new JScrollPane( editorPane ), BorderLayout.CENTER );
    }

    public void setText( String text ) {
        editorPane.setText( text );
    }

    public void setEditSummary( String summary ) {
        editSummary.setText( summary );
    }

    public String getText() {
        return editorPane.getText();
    }

    public String getEditSummary() {
        return editSummary.getText();
    }
}
