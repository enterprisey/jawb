package com.jawb.utils;

import javax.swing.*;
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
}
