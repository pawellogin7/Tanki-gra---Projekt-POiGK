
package tanksgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class LevelSelectionWindow {
    private int mouseX;
    private int mouseY;
    boolean mouseClicked, mouseLocked;
    private int buttonClicked;
    private int levelCompleted;
    
    
    LevelSelectionWindow() {
        mouseLocked = true;
    }
    
    public void update(Graphics g, int mX, int mY, boolean mClick) {
        mouseX = mX;
        mouseY = mY;
        mouseClicked = mClick;
        if(!mClick)
            mouseLocked = false;
        if(mouseLocked)
            mouseClicked = false;
            
        buttonClicked = 0;
        paint(g);

        mouseClicked = false;
    }
    
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, 1400, 1000);;
        
        drawButton(g2d, 0, 130, 1400, 100, 0, Color.white, Color.white, Color.black, "Level selection");
        
        int buttonWidth = 100;
        int buttonHeight = 100;
        int buttonX = 250;
        int buttonY = 250;
        int buttonNumber = 1;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 5; j++) {
                boolean mouseInArea = false;
                Color outlineColor;
                if(mouseX >= buttonX && mouseX < buttonX + buttonWidth &&
                    mouseY >= buttonY && mouseY < buttonY + buttonHeight)
                    mouseInArea = true;
                if(mouseInArea) {
                    if(levelCompleted + 1 < buttonNumber)
                        outlineColor = Color.black;
                    else {
                        outlineColor = Color.yellow;
                        if(mouseClicked)
                            buttonClicked = buttonNumber;
                    }
                }
                else {
                    if(levelCompleted + 1 < buttonNumber)
                        outlineColor = Color.black;
                    else if(levelCompleted + 1 == buttonNumber)
                        outlineColor = Color.red;
                    else
                        outlineColor = Color.green;
                }
                
                String text = Integer.toString(buttonNumber);
                drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 5, Color.lightGray, outlineColor, Color.black, text);
                buttonNumber++;
                buttonX += 200;
            }
            buttonX = 250;
            buttonY += 200;
        }
    }
    
    public void drawButton(Graphics2D g2d, int x, int y, int width, int height, int outlineWidth, Color buttonColor, Color outlineColor, Color textColor, String text) {
        g2d.setColor(buttonColor);
        g2d.fillRect(x, y, width, height);
        g2d.setColor(outlineColor);
        g2d.fillRect(x, y, width, outlineWidth);
        g2d.fillRect(x, y + height - outlineWidth, width, outlineWidth);
        g2d.fillRect(x, y, outlineWidth, height);
        g2d.fillRect(x + width - outlineWidth, y, outlineWidth, height);
        
        Font font = new Font("Arial", Font.BOLD, (int) Math.round(1.0 * (height - 2 * outlineWidth) * 0.8));
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int stringX = x + ((width - fm.stringWidth(text)) / 2);
        int stringY = y + ((height - fm.getHeight()) / 2) + fm.getAscent();

        g2d.setColor(textColor);
        g2d.drawString(text, stringX, stringY);
    }

    public int getButtonClicked() {
        return buttonClicked;
    }

    public int getLevelCompleted() {
        return levelCompleted;
    }

    public void setLevelCompleted(int levelCompleted) {
        this.levelCompleted = levelCompleted;
    }
    
    
}
