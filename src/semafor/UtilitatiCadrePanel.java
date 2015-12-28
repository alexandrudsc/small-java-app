/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semafor;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Alexandru
 */
public class UtilitatiCadrePanel {

    public static Frame initFrame(String titlu, int w, int h) {
        Frame f = new Frame(titlu);
        f.setSize(w, h);
        f.setLayout(new BorderLayout());
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        return f;
    }

    public static Panel douaButoane(String text1, String text2, ActionListener listener) {

        Panel grup = new Panel();
        Button b1, b2;

        grup.setLayout(new GridLayout(2, 1));
        grup.add(b1 = new Button(text1));
        b1.addActionListener(listener);
        grup.add(b2 = new Button(text2));
        b2.addActionListener(listener);
        return grup;

    }

}
