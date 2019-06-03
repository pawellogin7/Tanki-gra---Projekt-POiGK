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
        int buttonY = 250;
        Color buttonColor = Color.lightGray;
        Color outlineColor = Color.black;   
        if(mouseX >= buttonX && mouseX < buttonX + buttonWidth &&
             mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
            outlineColor = Color.red;
            if(mouseClicked)
                buttonClicked = 1;
        }
        else
            outlineColor = Color.black;
        drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 5, buttonColor, outlineColor, outlineColor, "Start game");   
        buttonY += 100;     

        outlineColor = Color.black;   
        if(mouseX >= buttonX && mouseX < buttonX + buttonWidth &&
             mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
            outlineColor = Color.red;
            if(mouseClicked)
                buttonClicked = 2;
        }
        else
            outlineColor = Color.black;
        drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 5, buttonColor, outlineColor, outlineColor, "Armory");   
        buttonY += 100;
        
        outlineColor = Color.black;   
        if(mouseX >= buttonX && mouseX < buttonX + buttonWidth &&
             mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
            outlineColor = Color.red;
            if(mouseClicked)
                buttonClicked = 3;
        }
        else
            outlineColor = Color.black;
        drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 5, buttonColor, outlineColor, outlineColor, "Save selection");   
        buttonY += 100;
        
        outlineColor = Color.black;   
        if(mouseX >= buttonX && mouseX < buttonX + buttonWidth &&
             mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
            outlineColor = Color.red;
            if(mouseClicked)
                buttonClicked = 4;
        }
        else
            outlineColor = Color.black;
        drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 5, buttonColor, outlineColor, outlineColor, "Exit");   
        buttonY += 100;

        g2d.dispose();
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
 
}
