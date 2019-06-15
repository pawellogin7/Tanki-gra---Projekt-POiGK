package tanksgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class MultiplayerMenuWindow {
    private int mouseX;
    private int mouseY;
    boolean mouseClicked, mouseLocked;
    private int buttonClicked;   
    
    MultiplayerMenuWindow() {
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
        
        int buttonWidth = 300;
        int buttonHeight = 70;
        int buttonX = 550;
        int buttonY = 300;
        drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 5, Color.lightGray, "Host game", 1);
        
        buttonY += 200;
        drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 5, Color.lightGray, "Join game", 2);

    }       


    
    public void drawButton(Graphics2D g2d, int buttonX, int buttonY, int buttonWidth, int buttonHeight, int outlineWidth, Color buttonColor, String text, int buttonID) {
        
        Color outlineColor = Color.black;
        if(mouseX >= buttonX && mouseX < buttonX + buttonWidth &&
             mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
            outlineColor = Color.yellow;
            if(mouseClicked) {
                buttonClicked = buttonID;
                mouseLocked = true;
            }
        }
        
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
    
    public void drawCenteredString(Graphics2D g2d, int x, int y, int width, int height, Color textColor, String text) {       
        Font font = new Font("Arial", Font.BOLD, height );
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