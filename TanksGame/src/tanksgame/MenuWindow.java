package tanksgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class MenuWindow{
    private int mouseX;
    private int mouseY;
    boolean mouseClicked;
    private int buttonClicked;
    
    private int activate;
    
    MenuWindow() {
        activate = 6;
    }
    
    public void update(Graphics g, int mX, int mY, boolean mClick) {
        if(activate == 0)
        {
            mouseX = mX;
            mouseY = mY;
            mouseClicked = mClick;
            
            buttonClicked = 0;
            paint(g);

            mouseClicked = false;
        }
        else activate--;
    }
    
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        
        int buttonWidth = 300;
        int buttonHeight = 50;
        int buttonX = 700 - buttonWidth / 2;
        int button1Y = 300;
        int button2Y = 400;
        int button3Y = 500;
        Color gray = Color.gray;
        Color black = Color.black;
        Color red = Color.red;
        
        int mouseButton = 0;
        if(mouseX >= buttonX && mouseX < buttonX + buttonWidth) {
            if(mouseY >= button1Y && mouseY < button1Y + buttonHeight)
                mouseButton = 1;
            else if(mouseY >= button2Y && mouseY < button2Y + buttonHeight)
                mouseButton = 2;
            else if(mouseY >= button3Y && mouseY < button3Y + buttonHeight)
                mouseButton = 3;
            else
                mouseButton = 0;
        }
        
        String button1Str = "Start game";
        String button2Str = "Armory";
        String button3Str = "Exit Game";
        
        drawButton(g2d, buttonX, button1Y, buttonWidth, buttonHeight, 5, gray, black, black, button1Str);
        drawButton(g2d, buttonX, button2Y, buttonWidth, buttonHeight, 5, gray, black, black, button2Str);
        drawButton(g2d, buttonX, button3Y, buttonWidth, buttonHeight, 5, gray, black, black, button3Str);
        
        switch(mouseButton) {
            case 0:
                break;
            case 1:
                drawButton(g2d, buttonX, button1Y, buttonWidth, buttonHeight, 5, gray, red, red, button1Str);
                if(mouseClicked)
                    buttonClicked = 1;
                break;
            case 2:
                drawButton(g2d, buttonX, button2Y, buttonWidth, buttonHeight, 5, gray, red, red, button2Str);
                if(mouseClicked)
                    buttonClicked = 2;
                break;
            case 3:
                drawButton(g2d, buttonX, button3Y, buttonWidth, buttonHeight, 5, gray, red, red, button3Str);
               if(mouseClicked)
                    buttonClicked = 3;
                break;
        }
        
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
