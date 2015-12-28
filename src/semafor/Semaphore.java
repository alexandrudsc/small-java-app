/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semafor;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Alexandru
 */
class Semaphore extends Canvas implements Runnable {

    private final int D, p;
    private double secWait;
    private boolean manual = true;

    Color aprins[] = new Color[]{Color.red, Color.yellow, Color.green};
    Color stins[] = new Color[]{Color.gray, Color.gray, Color.gray};
    
    // start from green (bottom circle)
    int indAprins = 2;
    
    public Semaphore(int R, int p, double secWait) {
        this.D = R + R;
        this.p = p;
        this.secWait = secWait;
    }

    @Override
    public void paint(Graphics g) {
        Color c;
        g.drawRect(0, 0, p + D + p, 4 * p + 3 * D);
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, p + D + p, 4 * p + 3 * D);

        for (int i = 0; i < 3; i++) {
            c = i == indAprins ? aprins[i] : stins[i];
            g.setColor(c);
            g.fillOval(p, (i + 1) * p + i * D, D, D);
        }
    }

    
    private void waitFor(long milisecunde) {
        try {
            Thread.sleep(milisecunde);
        } catch (InterruptedException ex) {
        }
    }

    @Override
    public void run() {
        while (true) {
            waitFor(100);
            if (!manual) {
                // este pe automat              
                changeColor();
                waitFor((long) (secWait * 1000 - 100));
            }
        }
    }
    
    /**
     * change color of circle. goes from green (2) to red (0)
     */
    void changeColor() {
        if (--indAprins == -1) {
            indAprins = 2;
        }
        repaint();
    }

    /**
     * Select the way the colors are changing: manual or auto 
     */
    void changeProccess() {
        manual = !manual;
    }
    
    
    /**
     * Check if semaphore indicates green
     */
    public boolean isGreen()
    {
        return indAprins == 2;
    }
    
    public double getSecWait() {
        return secWait;
    }
    
    public void setSecWait(double sec)
    {
        this.secWait = sec;
    }
    
}

