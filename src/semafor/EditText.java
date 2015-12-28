/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semafor;

/**
 *
 * @author Alexandru
 */
import java.awt.*;

class EditText extends Panel {

    private TextField te;

    public EditText(String text, int nrcol, boolean ecou) {
        //setLayout(new GridLayout(1, 2, 10, 10));
        setLayout(new FlowLayout(FlowLayout.CENTER, 3, 1));
        add(new Label(text, Label.LEFT));
        add(te = new TextField("", nrcol));
        if (ecou == false) {
            te.setEchoChar('*');
        }
    }

    public String toString() {
        return te.getText();
    }

    public void init() {
        te.setText("");
    }
}
