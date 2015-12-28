/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semafor;

import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionListener;

/**
 * Class managing south panel from main class: buttons and edit texts
 * The south panel will be a grid layout with lines and columns received as parameters or as the default values;
 * @author Alexandru
 */
public class SouthPanelBuilder {

    private final SouthPanel panel;

    private int lines = 4, cols = 1;
    
    public SouthPanelBuilder(ActionListener clickListener)
    {
        panel = new SouthPanel(clickListener);
        panel.setLayout(new GridLayout(lines, cols));
        
    }
    
    /**
     * Create the builder. Enhanced way to add buttons and EditTexts
     * @param clickListener click listener for buttons
     * @param lines number of lines for grid layout
     * @param cols number of columns for grid layout
     */
    public SouthPanelBuilder(ActionListener clickListener, int lines, int cols) {
        panel = new SouthPanel(clickListener);
        this.lines = lines;
        this.cols = cols;
        panel.setLayout(new GridLayout(lines, cols));
    }

    public SouthPanelBuilder  addButton(String text) {
        panel.addButton(text);
        return this;
    }

    public SouthPanelBuilder  addEditText(String text) {
        panel.addEditText(text);
        return this;
    }
    
     public SouthPanelBuilder  addEditText(EditText et) {
        panel.addEditText(et);
        return this;
    }

    public void addPanelToFrame(String position, Frame f) {
        f.add(position, panel);
    }
    
    
    /**
     * Grid layout containig buttons and edit text
     */
    private class SouthPanel extends Panel {

        private final ActionListener clickListener;

        public SouthPanel(ActionListener clickListener) {
            this.clickListener = clickListener;
        }

        private void addButton(String text) {
            Button b = new Button(text);
            b.addActionListener(clickListener);
            this.add(b);
        }

        private void addEditText(String text) {
            EditText et = new EditText(text, 1, true);
            this.add(et);
        }
        
        private void addEditText(EditText et) {
            this.add(et);
        }

    }

}
