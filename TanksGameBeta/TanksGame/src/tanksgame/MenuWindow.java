package tanksgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class MenuWindow{
    private int mouseX;
    private int mouseY;
    boolean mouseClicked, mouseLocked;
    private int buttonClicked;
    
    
    MenuWindow() {
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
        g2d.fillRect(0, 0, 1400, 1000);
        
        int buttonWidth = 300;
        int buttonHeight = 50;
        int buttonX = 700 - buttonWidth / 2;
        int buttonY = 200;
        Color buttonColor = Color.lightGray;
        
        drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 5, buttonColor, "Singleplayer", 1);   
        buttonY += 100; 
        
        drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 5, buttonColor, "Multiplayer", 4);   
        buttonY += 100;

        drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 5, buttonColor, "Armory", 2);   
        buttonY += 100;
        
        drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 5, buttonColor, "Save selection", 3);   
        buttonY += 100;
        
        drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 5, buttonColor, "Exit", -1);   
        buttonY += 100;

        g2d.dispose();
    }
    
    public void drawButton(Graphics2D g2d, int buttonX, int buttonY, int buttonWidth, int buttonHeight, int outlineWidth, Color buttonColor, String text, int buttonID) {
        Color outlineColor = Color.black;   
        if(mouseX >= buttonX && mouseX < buttonX + buttonWidth &&
             mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
            outlineColor = Color.red;
            if(mouseClicked)
                buttonClicked = buttonID;
        }
        else
            outlineColor = Color.black;
        
        g2d.setColor(buttonColor);
        g2d.fillRect(buttonX, buttonY, buttonWidth, buttonHeight);
        g2d.setColor(outlineColor);
        g2d.fillRect(buttonX, buttonY, buttonWidth, outlineWidth);
        g2d.fillRect(buttonX, buttonY + buttonHeight - outlineWidth, buttonWidth, outlineWidth);
        g2d.fillRect(buttonX, buttonY, outlineWidth, buttonHeight);
        g2d.fillRect(buttonX + buttonWidth - outlineWidth, buttonY, outlineWidth, buttonHeight);
        
        Font font = new Font("Arial", Font.BOLD, (int) Math.round(1.0 * (buttonHeight - 2 * outlineWidth) * 0.8));
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int stringX = buttonX + ((buttonWidth - fm.stringWidth(text)) / 2);
        int stringY = buttonY + ((buttonHeight - fm.getHeight()) / 2) + fm.getAscent();

        g2d.setColor(outlineColor);
        g2d.drawString(text, stringX, stringY);
    }

    public int getButtonClicked() {
        return buttonClicked;
    }
 
}
