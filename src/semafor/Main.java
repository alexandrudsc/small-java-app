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
import java.awt.event.*;

// TODO: change human color
public class Main implements ActionListener {

    // semaphore
    private final Semaphore s;
    
    // string resources
    private static final String strSchimba = "Schimba Culoarea";
    private static final String strManual = "Manual";
    private static final String strAutomat = "Automat";
    private static final String strFrequency = "Perioada";
    private static final String strCross = "Treci strada";
    
    // main frame
    Frame f;
    
    // score management panel
    RightPanel eastPanel;
    
    // input text
    EditText input;
    
    Frame getFrame() {
        return f;
    }

    public Main(int R, int p) {
        int latime = 2 * (p + R) < 100 ? 100 : 2 * (p + R);
        int inaltime = 4 * p + 6 * R + 140;

        // create main frame
        f = UtilitatiCadrePanel.initFrame("Semafor", latime + RightPanel.PANEL_WIDTH + 20, inaltime);
        // add the semaphore to the frame

        f.add("Center", s = new Semaphore(R, p, (double) 1));
      
        // input for seconds
        input = new EditText(strFrequency, 1, true);
                    
        // the action buttons builder
        SouthPanelBuilder southPanelBuilder = new SouthPanelBuilder(this, 4, 1);
        // build the south panel: three buttons, one editText
        southPanelBuilder       
                .addButton(strCross)
                .addEditText(input)
                .addButton(strSchimba)
                .addButton(strAutomat);
        // add south panel to main frame
        southPanelBuilder.addPanelToFrame("South", f);
        
        // create east panel = score and human
        eastPanel = new RightPanel();
        eastPanel.setPreferredSize(new Dimension(RightPanel.PANEL_WIDTH,  RightPanel.PANEL_HEIGHT));
        f.add("East", eastPanel);
        
        f.setVisible(true);

    }

    public static void main(String[] args) {
        Main demo = new Main(50, 10);
        new Thread(demo.s).start(); //porneste semaforul      
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Button btn = (Button) e.getSource();
        String numeButon = btn.getLabel();
        switch (numeButon) {
            case strSchimba:
                s.changeColor();
                break;
            case strManual:
                btn.setLabel(strAutomat); //schimba textul de pe buton
                s.changeProccess();
                break;
            case strAutomat:
                btn.setLabel(strManual); //schimba textul de pe buton
                s.setSecWait(readSeconds());
                s.changeProccess();
                break;
            case strCross:
                // if semaphore is green and "Move" button was not pressed, mark as success and animate
                if (s.isGreen() && !eastPanel.isMoving()){
                    eastPanel.addSuccess();
                    eastPanel.moveAndExit((long) (s.getSecWait() * 1000));
                }
                else{
                    // if it was not green or already in move, mark as fail
                    eastPanel.addFail();
                    eastPanel.animateFail((long) (s.getSecWait() * 1000));
                }
                eastPanel.repaint();
                break;
        }
    }
    
    private double readSeconds()
    {
        double s;
        try{
            s = Double.parseDouble(input.toString());
        }
        catch (NumberFormatException e)
        {
            s = 1;
        }
        System.out.println("" + s);
        // do not return zero or negative
        return s <= 1 ? 1 : s;
    }
}
