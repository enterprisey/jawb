package com.jawb;

import javax.swing.*;

import com.jawb.models.*;
import com.jawb.ui.*;

import net.sourceforge.jwbf.JWBF;

public class Main {
    static final String URL = "https://en.wikipedia.org/w/api.php";

    private MainWindow window;
    private AccountModel accountModel;
    private DefaultListModel<String> listModel;

    public static void main( String[] args ) {
        ( new Main() ).go();
    }

    public void go() {
        JWBF.printVersion();
        accountModel = new AccountModel();
        listModel = new DefaultListModel<>();
        window = new MainWindow( accountModel, listModel );
        Listeners.initialize( window, accountModel, listModel );
        window.setVisible( true );
    }
}
