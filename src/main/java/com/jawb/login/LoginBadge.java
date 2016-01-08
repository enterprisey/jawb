package com.jawb.login;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

import com.jawb.Controller;
import com.jawb.models.AccountModel;

/**
 * The badge in the status bar with the login status.
 */
public class LoginBadge extends JComponent {
    static final int X_MARGIN = 5;
    static final int Y_MARGIN = 5;

    private AccountModel accountModel;

    public LoginBadge( Controller controller ) {
        accountModel = controller.getAccountModel();
    }

    public void paintComponent( Graphics g ) {
        boolean isLoggedIn = accountModel.isLoggedIn();
        String username = accountModel.getUsername();
        int numNotifications = 0;

        FontMetrics metrics = g.getFontMetrics();
        int notificationsWidth = metrics
            .stringWidth( String.format( "%,d",
                                         numNotifications ) );
        if( !isLoggedIn ) {
            username = "User:";
        }
        int usernameWidth = metrics.stringWidth( isLoggedIn ? username : "User:" );
        g.setColor( isLoggedIn ? Color.GREEN : Color.RED );
        g.fillRect( 0, 0, isLoggedIn ? ( X_MARGIN * 2 + usernameWidth ) : this.getWidth(), this.getHeight() );
        g.setColor( Color.BLACK );
        g.drawString( username, X_MARGIN, this.getHeight() - Y_MARGIN );
        if( isLoggedIn ) {
            g.setColor( numNotifications == 0 ? Color.GRAY : Color.YELLOW );
            g.fillRect( X_MARGIN * 2 + usernameWidth, 0, X_MARGIN * 2 + notificationsWidth, this.getHeight() );
            g.setColor( Color.BLACK );
            g.drawString( "" + numNotifications, X_MARGIN * 3 + usernameWidth, this.getHeight() - Y_MARGIN );
        }
    }

    public Dimension getPreferredSize() {
        boolean isLoggedIn = accountModel.isLoggedIn();
        String username = accountModel.getUsername();
        int numNotifications = 0;

        // First, get the string widths
        // From http://stackoverflow.com/a/13345819/1757964
        Font font = this.getFont();
        BufferedImage img = new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB );
        FontMetrics metrics = img.getGraphics().getFontMetrics( font );
        if( !isLoggedIn ) {
            username = "User:";
        }
        int notificationsWidth = metrics
            .stringWidth( String.format( "%,d",
                                         numNotifications ) );
        int usernameWidth = metrics.stringWidth( isLoggedIn ? username : "User:" );

        int width = X_MARGIN * 2 + usernameWidth;
        if( isLoggedIn ) {
            width += notificationsWidth + X_MARGIN * 2;
        }

        // From http://stackoverflow.com/a/12495108/1757964
        int height = Y_MARGIN * 2 + (int)font.createGlyphVector( metrics.getFontRenderContext(), username + ( isLoggedIn ? ( "" + numNotifications ) : "" ) ).getVisualBounds().getHeight();

        return new Dimension( width, height );
    }

    public Dimension getMaximumSize() {
        return getPreferredSize();
    }
}
