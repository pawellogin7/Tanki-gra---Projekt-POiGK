
package tanksgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;


public class LevelWindow {
    private Image character, background, enemyTank; 
    private Image playerBullet, projectileFire, projectileLaser;
    private int currentLevel, mouseX, mouseY, buttonClicked;
    private boolean mouse1Clicked, mouse2Clicked, mouseLocked, paused, tankSelected, levelActive;
    private Equipment eq;
    private Player player;
    private static Background bg1, bg2;
    private EnemyTank t1, t2;
    
    LevelWindow(int level, Equipment equipment) {
        currentLevel = level;
        paused = true;
        tankSelected = false;
        levelActive = false;
        mouseLocked = true;
        eq = equipment; 
        player = new Player(eq, 1);
        player.setMovingDown(false);
        player.setMovingUp(false);
        player.setMovingRight(false);
        player.setMovingLeft(false);
        player.update();
        
        bg1 = new Background(0, 0);
        bg2 = new Background(2160, 1000); 
        t1 = new EnemyTank(340, 360);
        t2 = new EnemyTank(700, 360);
    }
    
    public void update(Graphics g, int mx, int my, boolean m1Click, boolean m2Click) {
        mouseX = mx;
        mouseY = my;
        mouse1Clicked = m1Click;
        mouse2Clicked = m2Click;
        if(!m1Click)
            mouseLocked = false;
        if(mouseLocked) {
            mouse1Clicked = false;
            mouse2Clicked = false;
        }
        buttonClicked = 0;
        
        paint(g);
    }

    public void paint(Graphics g) {        
        Graphics2D g2d = (Graphics2D) g.create();
        TanksGame.getBg1().update();
        TanksGame.getBg2().update();
        g.drawImage(background, TanksGame.getBg1().getBgX(), TanksGame.getBg1().getBgY(), null);
        g.drawImage(background, TanksGame.getBg2().getBgX(), TanksGame.getBg2().getBgY(), null);
        
        if(!tankSelected) {
            paused = true;
            drawTankSelection(g2d);
        }
        else {
            if(paused) {
                player.setMovingDown(false);
                player.setMovingUp(false);
                player.setMovingRight(false);
                player.setMovingLeft(false);
                player.updateBackground();
            }
            else if(!paused) { 
                if(mouse1Clicked)
                    player.shootPrimary(mouseX, mouseY);
                if(mouse2Clicked)
                    player.shootSecondary(mouseX, mouseY);
                updateProjectiles();
                player.update(); 
            }
            t1.update();
            t2.update();
            drawLevel1(g2d);
            if(paused)
                drawPauseMenu(g2d);
        }
        
    }
    

    private void updateProjectiles(){ 
        ArrayList projectiles = player.getProjectiles();
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = (Projectile) projectiles.get(i);
            if (p.isVisible() == true) {
		p.update();
            }
            else {
		projectiles.remove(i);
            }
        }
    }
    
    public void drawLevel(Graphics2D g2d) {
        
        
    }
    
    public void drawLevel1(Graphics2D g2d) {       
        g2d.drawImage(enemyTank, t1.getCenterX() - 64, t1.getCenterY() - 32, null);
        g2d.drawImage(enemyTank, t2.getCenterX() - 64, t2.getCenterY() - 32, null);
        g2d.drawImage(character, player.getCenterX() - 64, player.getCenterY() - 32, null);
        
        ArrayList projectiles = player.getProjectiles();
	for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = (Projectile) projectiles.get(i);
            drawProjectile(p, g2d);
        }
        
        Color tierColor = Color.lightGray;
        int hudX = 370;
        int hudY = 890;
        int hudSize = 2;
        int percent = player.getAbility().getPercent();
        if(player.isAbilityChosen()) {
            switch(player.getAbility().getTier()) {
                case 0:
                    tierColor = Color.lightGray;
                    break;
                case 1:
                    tierColor = Color.green;
                    break;
                case 2:
                    tierColor = Color.blue;
                    break;
                case 3:
                    tierColor = Color.magenta;
                    break;
                case 4:
                    tierColor = Color.orange;
                    break;
            }
            drawHud(hudX, hudY, hudSize, percent, g2d, tierColor); //ability hud
        }
        
        hudX = 430;
        if(player.isSecondaryChosen()) {
            percent = player.getSecondaryWeapon().getPercent();
            switch(player.getSecondaryWeapon().getTier()) {
                case 0:
                    tierColor = Color.lightGray;
                    break;
                case 1:
                    tierColor = Color.green;
                    break;
                case 2:
                    tierColor = Color.blue;
                    break;
                case 3:
                    tierColor = Color.magenta;
                    break;
                case 4:
                    tierColor = Color.orange;
                    break;
            }
            drawHud(hudX, hudY, hudSize, percent, g2d, tierColor); //secondary weapon hud
        }
        
        
        //primary weapons hud
        hudX = 920;
        switch(player.getPrimaryWeapon1().getTier()) {
            case 0:
                tierColor = Color.lightGray;
                break;
            case 1:
                tierColor = Color.green;
                break;
            case 2:
                tierColor = Color.blue;
                break;
            case 3:
                tierColor = Color.magenta;
                break;
            case 4:
                tierColor = Color.orange;
                break;
        }
        if(player.getActiveWeapon() == 1)
            drawHud(hudX, hudY, hudSize, 100, g2d, tierColor);
        else
            drawHud(hudX, hudY, hudSize, 0, g2d, tierColor);
        
        if(player.getWeaponsNumber() >= 2) {
            hudX += 60;
            switch(player.getPrimaryWeapon2().getTier()) {
                case 0:
                    tierColor = Color.lightGray;
                    break;
                case 1:
                    tierColor = Color.green;
                    break;
                case 2:
                    tierColor = Color.blue;
                    break;
                case 3:
                    tierColor = Color.magenta;
                    break;
                case 4:
                    tierColor = Color.orange;
                    break;
            }
            if(player.getActiveWeapon() == 2)
                drawHud(hudX, hudY, hudSize, 100, g2d, tierColor);
            else
                drawHud(hudX, hudY, hudSize, 0, g2d, tierColor);
        }
        
        if(player.getWeaponsNumber() >= 3) {
            hudX += 60;
            switch(player.getPrimaryWeapon3().getTier()) {
                case 0:
                    tierColor = Color.lightGray;
                    break;
                case 1:
                    tierColor = Color.green;
                    break;
                case 2:
                    tierColor = Color.blue;
                    break;
                case 3:
                    tierColor = Color.magenta;
                    break;
                case 4:
                    tierColor = Color.orange;
                    break;
            }
            if(player.getActiveWeapon() == 3)
                drawHud(hudX, hudY, hudSize, 100, g2d, tierColor);
            else
                drawHud(hudX, hudY, hudSize, 0, g2d, tierColor);
        }
        
        
        drawPlayerHP(500, 903, 400, 30, g2d);
    }
    
    public void drawProjectile(Projectile p, Graphics2D g2d) {
        int typ = p.getProjectileType();
        switch(typ)
        {
            case 0:
              g2d.drawImage(playerBullet, p.getX(), p.getY(), null);
              break;
            case 1:
              break;
            case 2:
              g2d.drawImage(projectileLaser, p.getX(), p.getY(), null);
              break;
            case 3:
              g2d.drawImage(projectileFire, p.getX(), p.getY(), null);
              break;
        }
    }

    public void drawHud(int hudX, int hudY, int hudSize, int hudPercent, Graphics2D g2d, Color tierColor) {
        int size = 26 * hudSize;
        int lineSize = 2 * hudSize;
        int startPoint = hudX + size / 2;
        int pointxLeft = hudX;
        int pointxRight = hudX + 26 * hudSize - lineSize;
        int pointyUp = hudY + hudSize;
        int pointyDown = hudY + 26 * hudSize - lineSize / 2;
        
        g2d.setColor(tierColor);
        g2d.fillRect(pointxLeft, pointyUp, size, size);
        g2d.setColor(Color.black);
        g2d.fillRect(pointxLeft, pointyUp, 26 * hudSize, lineSize);
        g2d.fillRect(pointxRight, pointyUp, lineSize, 26 * hudSize);
        g2d.fillRect(pointxLeft, pointyDown, 26 * hudSize, lineSize);
        g2d.fillRect(pointxLeft, pointyUp, lineSize, 26 * hudSize);
        
        g2d.setColor(Color.red);
        if(hudPercent <= 13) {
            g2d.fillRect(startPoint, pointyUp, hudPercent * hudSize, lineSize);
        }
        else if(hudPercent > 13 && hudPercent <= 37) {
            hudPercent -= 13;
            g2d.fillRect(startPoint, pointyUp, 13 * hudSize, lineSize);
            g2d.fillRect(pointxRight, pointyUp, lineSize, hudPercent * hudSize);
        }
        else if(hudPercent > 37 && hudPercent <= 63) {
            hudPercent -= 37;
            g2d.fillRect(startPoint, pointyUp, 13 * hudSize, lineSize);
            g2d.fillRect(pointxRight, pointyUp, lineSize, 26 * hudSize);
            g2d.fillRect(pointxRight - hudPercent * hudSize + lineSize, pointyDown, hudPercent * hudSize, lineSize);
        }
        else if(hudPercent > 63 && hudPercent <= 87) {
            hudPercent -= 63;
            g2d.fillRect(startPoint, pointyUp, 13 * hudSize, lineSize);
            g2d.fillRect(pointxRight, pointyUp, lineSize, 26 * hudSize);
            g2d.fillRect(pointxLeft, pointyDown, 26 * hudSize, lineSize);
            g2d.fillRect(pointxLeft, pointyDown - hudPercent * hudSize, lineSize, hudPercent * hudSize + hudSize);
        }
        else if(hudPercent > 87 && hudPercent <= 100) {
            hudPercent -= 87;
            g2d.fillRect(startPoint, pointyUp, 13 * hudSize, lineSize);
            g2d.fillRect(pointxRight, pointyUp, lineSize, 26 * hudSize);
            g2d.fillRect(pointxLeft, pointyDown, 26 * hudSize, lineSize);
            g2d.fillRect(pointxLeft, pointyUp, lineSize, 26 * hudSize);
            g2d.fillRect(pointxLeft, pointyUp, hudPercent * hudSize + hudSize , lineSize);
        }
    }
       
    public void drawPlayerHP(int startX, int startY, int width, int heigth, Graphics g) {
        g.setColor(Color.black);
        g.fillRect(startX - 5, startY - 5, width + 10, heigth + 10);
        g.setColor(Color.darkGray);
        g.fillRect(startX, startY, width, heigth);
        int percent =  player.getHpPercent(); 
        g.setColor(new Color((int) (2.55 * (100 - percent)), 255 - (int) (2.55 * (100 - percent)), 0));
        g.fillRect(startX, startY, width * percent / 100, heigth);
    }
    
    
    public void drawTankSelection(Graphics2D g2d) {
        int startX = 200;
        int startY = 50;
        drawButton(g2d, startX, startY, 1000, 900, 10, Color.white, Color.black);
        g2d.fillRect(530, startY, 10, 900);
        g2d.fillRect(860, startY, 10, 900);
        
        for(int k = 1; k <= 3; k++) {
            int[] tankEq = new int[9];
            int buttonX = 0;
            int buttonY = startY + 15;
            int buttonWidth = 67;
            int buttonHeight = 67;
            switch(k) {
                case 1:
                    tankEq = eq.getTankSlot1();
                    startX = 230;
                    break;
                case 2:
                    tankEq = eq.getTankSlot2();
                    startX = 560;
                    break;
                case 3:
                    tankEq = eq.getTankSlot3();
                    startX = 890;
                    break;
            }
            
            Color outlineColor = Color.black;
            Color tierColor = Color.lightGray;

            
            //Bodies tab
            buttonX = startX + (280 - buttonWidth) / 2;
            buttonY = startY + 32;
            if(tankEq[0] == 0)
                tierColor = Color.lightGray;
            else {
                switch(eq.getTankBodies().get(tankEq[0]).getTier()) {
                        case 0:
                            tierColor = Color.lightGray;
                            break;
                        case 1:
                            tierColor = Color.green;
                            break;
                        case 2:
                            tierColor = Color.blue;
                            break;
                        case 3:
                            tierColor = Color.magenta;
                            break;
                        case 4:
                            tierColor = Color.orange;
                            break;
                }
            }
            drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 3, tierColor, outlineColor);
        
            
            //Primary weapons tab
            tierColor = Color.lightGray;
            outlineColor = Color.black;
            int eqSlots = eq.getTankBodies().get(tankEq[0]).getPrimaryWeaponSlots();
            buttonX = startX + (280 - buttonWidth * eqSlots) / (eqSlots + 1);
            buttonY += 105;
            int jump = buttonX - startX;
            for(int i = 1; i <= eqSlots; i++) {
                outlineColor = Color.black;
                int weaponTier = 0;
                switch(i) {
                    case 1:
                        if(tankEq[1] == 0)
                            weaponTier = 0;
                        else
                            weaponTier = eq.getPrimaryWeapons().get(tankEq[1]).getTier();
                        break;
                    case 2:
                        if(tankEq[2] == 0)
                            weaponTier = 0;
                        else
                            weaponTier = eq.getPrimaryWeapons().get(tankEq[2]).getTier();
                        break;
                    case 3:
                        if(tankEq[3] == 0)
                            weaponTier = 0;
                        else
                            weaponTier = eq.getPrimaryWeapons().get(tankEq[3]).getTier();
                        break;
                }
                switch(weaponTier) {
                    case 0:
                        tierColor = Color.lightGray;
                        break;
                    case 1:
                        tierColor = Color.green;
                        break;
                    case 2:
                        tierColor = Color.blue;
                        break;
                    case 3:
                        tierColor = Color.magenta;
                        break;
                    case 4:
                        tierColor = Color.orange;
                        break;
                }
                drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 3, tierColor, outlineColor);
                buttonX += (jump + buttonWidth);
            }
            
            //Secondary weapons tab
            tierColor = Color.lightGray;
            outlineColor = Color.black;
            buttonX = startX + (280 - buttonWidth) / 2;
            buttonY += 105;
            if(tankEq[4] == 0)
                tierColor = Color.lightGray;
            else {
                switch(eq.getSecondaryWeapons().get(tankEq[4]).getTier()) {
                        case 0:
                            tierColor = Color.lightGray;
                            break;
                        case 1:
                            tierColor = Color.green;
                            break;
                        case 2:
                            tierColor = Color.blue;
                            break;
                        case 3:
                            tierColor = Color.magenta;
                            break;
                        case 4:
                            tierColor = Color.orange;
                            break;
                }
            }
            drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 3, tierColor, outlineColor);
            
            //Abilities tab
            tierColor = Color.lightGray;
            outlineColor = Color.black;
            buttonX = startX + (280 - buttonWidth) / 2;
            buttonY += 105;
            if(tankEq[5] == 0)
                tierColor = Color.lightGray;
            else {
                switch(eq.getAbilities().get(tankEq[5]).getTier()) {
                        case 0:
                            tierColor = Color.lightGray;
                            break;
                        case 1:
                            tierColor = Color.green;
                            break;
                        case 2:
                            tierColor = Color.blue;
                            break;
                        case 3:
                            tierColor = Color.magenta;
                            break;
                        case 4:
                            tierColor = Color.orange;
                            break;
                }
            }
            drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 3, tierColor, outlineColor);
            
            //Modules tab
            outlineColor = Color.black;
            eqSlots = eq.getTankBodies().get(tankEq[0]).getModuleSlots();
            buttonX = startX + (280 - buttonWidth * eqSlots) / (eqSlots + 1);
            buttonY += 105;
            jump = buttonX - startX;
            for(int i = 1; i <= eqSlots; i++) {
                outlineColor = Color.black;
                int moduleTier = 0;
                switch(i) {
                    case 1:
                        if(tankEq[6] == 0)
                            moduleTier = 0;
                        else
                            moduleTier = eq.getModules().get(tankEq[6]).getTier();
                        break;
                    case 2:
                        if(tankEq[7] == 0)
                            moduleTier = 0;
                        else
                            moduleTier = eq.getModules().get(tankEq[7]).getTier();
                        break;
                    case 3:
                        if(tankEq[8] == 0)
                            moduleTier = 0;
                        else
                            moduleTier = eq.getModules().get(tankEq[8]).getTier();
                        break;
                }
                switch(moduleTier) {
                    case 0:
                        tierColor = Color.lightGray;
                        break;
                    case 1:
                        tierColor = Color.green;
                        break;
                    case 2:
                        tierColor = Color.blue;
                        break;
                    case 3:
                        tierColor = Color.magenta;
                        break;
                    case 4:
                        tierColor = Color.orange;
                        break;
                }
                drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 3, tierColor, outlineColor);
                buttonX += (jump + buttonWidth);
            }
            
            Color black = Color.black;
            //Tab text
            int stringY = startY + 10;
            int stringX = startX;
            drawCenteredString(g2d, stringX, stringY, 280, 20, black, "Tank Body");
            stringY += 105;
            drawCenteredString(g2d, stringX, stringY, 280, 20, black, "Primary Weapons");
            stringY += 105;
            drawCenteredString(g2d, stringX, stringY, 280, 20, black, "Secondary Weapon");
            stringY += 105;
            drawCenteredString(g2d, stringX, stringY, 280, 20, black, "Ability");
            stringY += 105;
            drawCenteredString(g2d, stringX, stringY, 280, 20, black, "Modules");
            //Tank stats text
            stringX = startX + 10;
            g2d.setFont(new Font("Bold", Font.BOLD, 20));
            stringY += 125;
            g2d.drawString("Tank Stats:", stringX, stringY);
            stringY += 25;
            g2d.setFont(new Font("Bold", Font.BOLD, 16));
            int value = 0;
            value = eq.getTankBodies().get(tankEq[0]).getMaxHp() + checkTankStats(1, tankEq);
            g2d.drawString("HP:" + Integer.toString(value), stringX, stringY);
            stringY += 22;
            value = eq.getTankBodies().get(tankEq[0]).getRegen() + checkTankStats(2, tankEq);
            g2d.drawString("HP regen:" + Integer.toString(value), stringX, stringY);
            stringY += 22;
            value = eq.getTankBodies().get(tankEq[0]).getMoveSpeed() + checkTankStats(3, tankEq);
            g2d.drawString("Movement speed:" + Integer.toString(value), stringX, stringY);
            stringY += 22;
            value = eq.getTankBodies().get(tankEq[0]).getArmor() + checkTankStats(4, tankEq);
            g2d.drawString("Armor:" + Integer.toString(value), stringX, stringY);
            if(checkTankStats(10, tankEq) != 0) {
                stringY += 22;
                g2d.drawString("Damage boost: " + Integer.toString(checkTankStats(10, tankEq))+ "%", stringX, stringY);
            }
            if(checkTankStats(11, tankEq) != 0) {
                stringY += 22;
                g2d.drawString("Reload boost: " + Integer.toString(checkTankStats(11, tankEq)) + "%", stringX, stringY);
            }
            if(checkTankStats(12, tankEq) != 0) {
                stringY += 22;
                g2d.drawString("Accuracy boost: " + Integer.toString(checkTankStats(12, tankEq)) + "%", stringX, stringY);
            }
            if(checkTankStats(13, tankEq) != 0) {
                stringY += 22;
                g2d.drawString("Armor pen boost: " + Integer.toString(checkTankStats(13, tankEq)) + "%", stringX, stringY);
            }
            if(checkTankStats(14, tankEq) != 0) {
                stringY += 22;
                g2d.drawString("Range boost: " + Integer.toString(checkTankStats(14, tankEq)) + "%", stringX, stringY);
            }
            if(checkTankStats(20, tankEq) != 0) {
                stringY += 22;
                g2d.drawString("Cooldown reduction: " + Integer.toString(checkTankStats(20, tankEq)) + "%", stringX, stringY);
            }
            
            
            //Select button
            buttonY = 855;
            buttonWidth = 270;
            buttonHeight = 70;
            buttonX = startX + 280 - buttonWidth;
            outlineColor = Color.black;
            if(mouseX >= buttonX && mouseX < buttonX + buttonWidth 
                 && mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
                outlineColor = Color.red;
                if(mouse1Clicked == true) {
                    tankSelected = true;
                    player = new Player(eq, k);
                    paused = false;
                    mouseLocked = true;
                }
            }
            drawTextButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 5, Color.white, outlineColor, outlineColor, "Select tank " + Integer.toString(k));
            
        }
    }
    
    private int checkTankStats(int statType, int[] tankEq) {
            int statEffect = 0;
            for(int i = 0; i < 6; i++) {
                if(eq.getTankBodies().get(tankEq[0]).getStatBoostType()[i] == statType) {
                    statEffect += eq.getTankBodies().get(tankEq[0]).getStatBoostEffect()[i];
                }
            }
            
            for(int i = 6; i <= 8; i++) {
                if(eq.getModules().get(tankEq[i]).getStat1Type() == statType)
                  statEffect += eq.getModules().get(tankEq[i]).getStat1Effect(); 
                else if(eq.getModules().get(tankEq[i]).getStat2Type() == statType)
                  statEffect += eq.getModules().get(tankEq[i]).getStat2Effect();
                else if(eq.getModules().get(tankEq[i]).getStat3Type() == statType)
                  statEffect += eq.getModules().get(tankEq[i]).getStat3Effect();
            }
            
            return statEffect;
        }
    
    //---------------------------------------------------------------------
    public void escPause() {
        if(tankSelected) {
            if(paused)
                paused = false;
            else
                paused = true;
        }
        else
            buttonClicked = 1;
    }
    
    private void drawPauseMenu(Graphics2D g2d) {
        drawButton(g2d, 500, 350, 400, 250, 10, Color.white, Color.black);
        
        int buttonWidth = 300;
        int buttonHeight = 50;
        int buttonX = 550;
        int buttonY = 400;
        Color buttonColor = Color.lightGray;
        Color outlineColor = Color.black;
        
        if(mouseX >= buttonX && mouseX < buttonX + buttonWidth 
             && mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
            outlineColor = Color.red;
            if(mouse1Clicked == true)
                paused = false;
        }
        drawTextButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 5, buttonColor, outlineColor, outlineColor, "Resume");
        
        buttonY += 100;
        outlineColor = Color.black;
        if(mouseX >= buttonX && mouseX < buttonX + buttonWidth 
             && mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
            outlineColor = Color.red;
            if(mouse1Clicked == true)
                buttonClicked = 1;
        }
        drawTextButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 5, buttonColor, outlineColor, outlineColor, "Exit to menu");
    }
    
    //-----------------------------------------------------------------------------
    public void drawButton(Graphics2D g2d, int x, int y, int width, int height, int outlineWidth, Color buttonColor, Color outlineColor) {
        g2d.setColor(buttonColor);
        g2d.fillRect(x, y, width, height);
        g2d.setColor(outlineColor);
        g2d.fillRect(x, y, width, outlineWidth);
        g2d.fillRect(x, y + height - outlineWidth, width, outlineWidth);
        g2d.fillRect(x, y, outlineWidth, height);
        g2d.fillRect(x + width - outlineWidth, y, outlineWidth, height);     
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
    
    public void drawTextButton(Graphics2D g2d, int x, int y, int width, int height, int outlineWidth, Color buttonColor, Color outlineColor, Color textColor, String text) {
        g2d.setColor(buttonColor);
        g2d.fillRect(x, y, width, height);
        g2d.setColor(outlineColor);
        g2d.fillRect(x, y, width, outlineWidth);
        g2d.fillRect(x, y + height - outlineWidth, width, outlineWidth);
        g2d.fillRect(x, y, outlineWidth, height);
        g2d.fillRect(x + width - outlineWidth, y, outlineWidth, height);
        
        Font font = new Font("Arial", Font.BOLD, (int) Math.round(1.0 * (height - 2 * outlineWidth) * 0.7));
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int stringX = x + ((width - fm.stringWidth(text)) / 2);
        int stringY = y + ((height - fm.getHeight()) / 2) + fm.getAscent();

        g2d.setColor(textColor);
        g2d.drawString(text, stringX, stringY);
    }

    public void setPlayerBullet(Image playerBullet) {
        this.playerBullet = playerBullet;
    }

    public void setProjectileFire(Image projectileFire) {
        this.projectileFire = projectileFire;
    }

    public void setProjectileLaser(Image projectileLaser) {
        this.projectileLaser = projectileLaser;
    }

    public void setCharacter(Image character) {
        this.character = character;
    }

    public void setBackground(Image background) {
        this.background = background;
    }

    public void setEnemyTank(Image enemyTank) {
        this.enemyTank = enemyTank;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isMouseLocked() {
        return mouseLocked;
    }

    public void setMouseLocked(boolean mouseLocked) {
        this.mouseLocked = mouseLocked;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getButtonClicked() {
        return buttonClicked;
    }

    public void setButtonClicked(int buttonClicked) {
        this.buttonClicked = buttonClicked;
    }
    
    
    
}
