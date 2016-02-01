package com.jawb.utils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public abstract class Helper {
    public static void setComponentFontSize(
            JComponent component,
            int newSize ) {
        Font currentFont = component.getFont();
        Font newFont = new Font( currentFont.getFontName(),
                currentFont.getStyle(),
                newSize );
        component.setFont( newFont );
    }

    public static Rectangle getCenteredBounds( Dimension size ) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Point location = new Point( ( screen.width - size.width ) / 2,
                ( screen.height - size.height ) / 2 );
        return new Rectangle( location, size );
    }

    public static Rectangle getCenteredBounds( int width, int height ) {
        return getCenteredBounds( new Dimension( width, height ) );
    }

    /**
     * Makes a titled border using a black line border.
     * @param title The title that will appear in the border.
     * @return A titled border with the given title.
     */
    public static Border getLineTitledBorder( String title ) {
        Border lineBorder = BorderFactory.createLineBorder( Color.BLACK );
        return BorderFactory.createTitledBorder( lineBorder, title );
    }

    /**
     * Wraps a JComponent in a BorderLayout so that it's squeezed to the left.
     * @param component The component to wrap.
     * @return A component that wraps the given component.
     */
    public static JPanel leftWrapper( JComponent component ) {
        JPanel wrapper = new JPanel( new BorderLayout() );
        wrapper.setBackground( new Color( 0, 0, 0, 0.25f ) );
        wrapper.add( component, BorderLayout.WEST );
        wrapper.setMaximumSize( component.getPreferredSize() );
        return wrapper;
    }

    public static void makeNiy( JComponent component ) {
        component.setEnabled( false );
        if( component instanceof AbstractButton ) {
            AbstractButton button = ( AbstractButton ) component;
            button.setText( button.getText() + " (NIY)" );
        }
    }
}
