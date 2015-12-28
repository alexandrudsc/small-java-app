/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semafor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * Class managing right panel of main frame: score and human
 *
 * @author Alexandru
 */
public class RightPanel extends Panel {

    // panel dimensions
    public static final int PANEL_WIDTH = 530;
    public static final int PANEL_HEIGHT = 430;
    
    // human dimenssions
    private static final int HEAD_RADIUS = 30;
    private static final int BODY_HEIGHT = 60;
    private static final int BODY_WIDTH = 60;
    private static final int LEG_HEIGHT = 20;
    private static final int LEG_WIDTH = 10;
    private static final int LEG_PADDING = 10;
    
    private static final int BORDER_TICKNESS = 2;
    
    // human default coords
    private int initialX;
    private int initialY;

    // human coords
    private int humanX;
    private int humanY;
    
    // human color
    private Color borderColor = Color.BLACK;
    private Color fillColor = Color.WHITE;
    
    // timer for color change
    private java.util.Timer timerColorChange;
    
    // used to detect first draw to get initial dimensions of Grpahics object. 
    private boolean isFirstDraw = true;
    // mark if moving or not. Allow to animate
    private boolean bIsMoving = false;
    
    // timerMove used to animate
    Timer timerMove;
    
    // score fails and successes
    private int scoreP = 0;
    private int scoreN = 0;

    // score labels
    private static final String successes = "Succese: ";
    private static final String fails = "Esecuri: ";

    public RightPanel() {
        super();
        init();
    }

    public RightPanel(LayoutManager layout) {
        super(layout);
        init();
    }

    private void init() {
        initialX = this.getWidth() / 4;
        initialY = this.getHeight() / 2;

        humanX = initialX;
        humanY = initialY;
        
        // create timerMove for scheduling color change at fail
        timerColorChange = new java.util.Timer();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (!bIsMoving && isFirstDraw) {
            init();
            isFirstDraw = false;
        }
        // draw score panel
        drawScore(g);

        // draw human
        drawHuman(g);

    }

    /**
     * Draws the score within a rectangle Used for free access to north east
     * region
     *
     * @param g Graphics object from on paint
     */
    private void drawScore(Graphics g) {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.setColor(Color.BLACK);

        // draw rect top rigth
        g.drawRoundRect(PANEL_WIDTH - (successes + scoreP).length() * 12, 5, (successes + scoreP).length() * 11, 80, 10, 10);
        // success score
        g.drawString((successes + scoreP), PANEL_WIDTH - (successes + scoreP).length() * 11, 30);
        // fails score
        g.drawString((fails + scoreN), PANEL_WIDTH - (successes + scoreP).length() * 11, 50);
    }

    /**
     * Draws the score within a rectangle Used for free access to north east
     * region
     *
     * @param g Graphics object from on paint
     */
    private void drawHuman(Graphics g) {
        // use 2d graphics for thickness
        Graphics2D g2d = (Graphics2D) g;
        
        // draw borders
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(BORDER_TICKNESS));
        
        // head borderColor
        g2d.drawOval(humanX, humanY, HEAD_RADIUS, HEAD_RADIUS);        
        // body borderColor
        g2d.drawRect(humanX + (HEAD_RADIUS - BODY_WIDTH) / 2, humanY + HEAD_RADIUS, BODY_WIDTH, BODY_HEIGHT);
        // right leg borderColor
        g2d.drawRect(humanX + (HEAD_RADIUS - BODY_WIDTH) / 2 + LEG_PADDING, humanY + HEAD_RADIUS + BODY_HEIGHT, LEG_WIDTH, LEG_HEIGHT);
        // left leg borderColor
        g2d.drawRect( humanX + HEAD_RADIUS  - LEG_PADDING / 2 , humanY + HEAD_RADIUS + BODY_HEIGHT, LEG_WIDTH, LEG_HEIGHT);

        // draw the fill
        g2d.setColor(fillColor);
        // head fill
        g2d.fillOval(humanX + BORDER_TICKNESS, 
                    humanY + BORDER_TICKNESS, 
                    HEAD_RADIUS - 2 * BORDER_TICKNESS, 
                    HEAD_RADIUS - 2 * BORDER_TICKNESS);
        
        // body fill
        g2d.fillRect(humanX + (HEAD_RADIUS - BODY_WIDTH) / 2 + BORDER_TICKNESS, 
                    humanY + HEAD_RADIUS + BORDER_TICKNESS,                   
                    BODY_WIDTH - 2 * BORDER_TICKNESS, 
                    BODY_HEIGHT - 2 * BORDER_TICKNESS);
        // right leg fill
        g2d.fillRect(humanX + (HEAD_RADIUS - BODY_WIDTH) / 2 + LEG_PADDING + BORDER_TICKNESS, 
                    humanY + HEAD_RADIUS + BODY_HEIGHT + BORDER_TICKNESS, 
                    LEG_WIDTH - 2 * BORDER_TICKNESS, 
                    LEG_HEIGHT - 2 * BORDER_TICKNESS);

        // left leg fill
        g2d.fillRect( humanX + HEAD_RADIUS  - LEG_PADDING / 2 + BORDER_TICKNESS, 
                    humanY + HEAD_RADIUS + BODY_HEIGHT + BORDER_TICKNESS,  
                    LEG_WIDTH - 2 * BORDER_TICKNESS, 
                    LEG_HEIGHT - 2 * BORDER_TICKNESS);

    }

    // execute moving animation
    public void moveAndExit(long millis) {
        bIsMoving = true;
        int timerDelay = (int) (millis / this.getX());
        
        this.setFillColor(Color.GREEN);
        
        // cancel any color change animation
        timerColorChange.cancel();
        
        timerMove = new Timer(timerDelay, new TimerTickMove());
        timerMove.start();
    }
    
    // execute color change animation
    public void animateFail(long millis)
    {
        this.setFillColor(Color.RED);
        // cancel any previous tasks
        timerColorChange.purge();
        
        // schdule rechanging color to default WHITE
        timerColorChange.schedule(new TimerTaskColorChange(Color.WHITE), millis);
    }
    
    public void setFillColor (Color c)
    {
        this.fillColor = c;
    }
    
    public void setBorderColor (Color c)
    {
        this.borderColor = c;
    }
    
    public void addSuccess() {
        scoreP++;
    }

    public void addFail() {
        scoreN++;
    }

    public int getScoreP() {
        return scoreP;
    }

    public int getScoreN() {
        return scoreN;
    }

    public boolean isMoving() {
        return bIsMoving;
    }
    
    
    // Tick event for timer which controls moving animation of human
    private class TimerTickMove implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            humanX += 5;
            if (humanX / 2 > RightPanel.this.getWidth()) {
                timerMove.stop();
                bIsMoving = false;
                isFirstDraw = true;
                RightPanel.this.setFillColor(Color.WHITE);
                RightPanel.this.repaint();
                return;
            }
        
            RightPanel.this.repaint();
        }
    }
    
    // task to be scheduled to change the color back to default, when fails occurs
    private class TimerTaskColorChange extends java.util.TimerTask
    {
        private final Color color;
        
        public TimerTaskColorChange(Color color) {
            this.color = color;
        }
            
        @Override
        public void run() {
            RightPanel.this.setFillColor(this.color);
            RightPanel.this.repaint();
        } 
    }
}
