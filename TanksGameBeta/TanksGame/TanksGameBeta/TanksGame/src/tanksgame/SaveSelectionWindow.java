
package tanksgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class SaveSelectionWindow {
    private int mouseX;
    private int mouseY;
    boolean mouseClicked, mouseLocked;
    private int buttonClicked;
    private int saveSlotSelected;
    
    //Window, where you can choose your savefile
    SaveSelectionWindow(int save) {
        saveSlotSelected = save;
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
        
        drawButton(g2d, 0, 130, 1400, 100, 0, Color.white, Color.white, Color.black, "Save slot selection");
        
        int buttonWidth = 220;
        int buttonHeight = 100;
        int buttonX = 200;
        int buttonY = 280;

        Color outlineColor = Color.black;
        if(mouseX >= buttonX && mouseX < buttonX + buttonWidth &&
             mouseY >= buttonY && mouseY < buttonY + buttonHeight) {  
            outlineColor = Color.yellow;
            if(mouseClicked) {
                saveSlotSelected = 1;
                mouseLocked = true;
            }
        }
        else if(saveSlotSelected == 1)
            outlineColor = Color.red;
        else 
            outlineColor = Color.black;
        drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 5, Color.lightGray, outlineColor, outlineColor, "Slot 1");
        buttonX += 400;
        
        outlineColor = Color.black;
        if(mouseX >= buttonX && mouseX < buttonX + buttonWidth &&
             mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
            outlineColor = Color.yellow;
            if(mouseClicked) {
                saveSlotSelected = 2;
                mouseLocked = true;
            };
        }
        else if(saveSlotSelected == 2)
            outlineColor = Color.red;
        else 
            outlineColor = Color.black;   
        drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 5, Color.lightGray, outlineColor, outlineColor, "Slot 2");
        buttonX += 400;
        
        outlineColor = Color.black;
        if(mouseX >= buttonX && mouseX < buttonX + buttonWidth &&
             mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
            outlineColor = Color.yellow;
            if(mouseClicked) {
                saveSlotSelected = 3;
                mouseLocked = true;
            }
        }
        else if(saveSlotSelected == 3)
            outlineColor = Color.red;
        else 
            outlineColor = Color.black;     
        drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 5, Color.lightGray, outlineColor, outlineColor, "Slot 3");
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

    public int getSaveSlotSelected() {
        return saveSlotSelected;
    }

    public void setSaveSlotSelected(int saveSlotSelected) {
        this.saveSlotSelected = saveSlotSelected;
    }

    
    
    
}
