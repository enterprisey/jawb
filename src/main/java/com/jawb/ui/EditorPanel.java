package com.jawb.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * The pane with the wikitext editor.
 */
public class EditorPanel extends JPanel {
    private JEditorPane editorPane;

    public EditorPanel() {
        super( new BorderLayout() );
        this.setBorder( BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );

        // We need this so that the slider can be dragged around
        this.setMinimumSize( new Dimension( 0, 0 ) );

        editorPane = new JEditorPane();
        editorPane.setFont( new Font( Font.MONOSPACED, Font.PLAIN, 12 ) );
        this.add( new JScrollPane( editorPane ) );
    }

    public void setText( String text ) {
        editorPane.setText( text );
    }
}
