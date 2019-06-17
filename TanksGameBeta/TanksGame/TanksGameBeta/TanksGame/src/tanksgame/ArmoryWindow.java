package tanksgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class ArmoryWindow{
    private int mouseX;
    private int mouseY;
    boolean mouseClicked, mouseLocked;
    private int buttonClicked;
    private int activeTankSlot;
    private int activeTab;
    private int activeComponent;
    private int componentNumber;
    private int primaryWeaponNumber;
    private int moduleNumber;
    private int[] tankEq;
    
    private Equipment eq;

    //Armory is where you can buy items and equip your tanks
    ArmoryWindow() {
        activeTankSlot = 1;
        activeTab = 1;
        activeComponent = 0;
        componentNumber = 20;
        primaryWeaponNumber = 1;
        moduleNumber = 1;
        mouseLocked = true;
        tankEq = new int[9];
    }
    
    public void update(Graphics g, int mX, int mY, boolean mClick, Equipment equipment) {
            mouseX = mX;
            mouseY = mY;
            mouseClicked = mClick;
            if(!mClick)
                mouseLocked = false;
            if(mouseLocked)
                mouseClicked = false;
            buttonClicked = 0;
            eq = equipment;
            
            //Load current tank slot items to window
            switch(activeTankSlot) {
                case 1:
                    tankEq = eq.getTankSlot1();
                    break;
                case 2:
                    tankEq = eq.getTankSlot2();
                    break;
                case 3:
                    tankEq = eq.getTankSlot3();
                    break;
            }
            
            
            
            primaryWeaponNumber = eq.getTankBodies().get(tankEq[0]).getPrimaryWeaponSlots();
            moduleNumber = eq.getTankBodies().get(tankEq[0]).getModuleSlots();

            switch(activeTab) {
                case 1:
                    componentNumber = eq.getTankBodiesTier().length - 1;
                    if(activeComponent == 0)
                        activeComponent = tankEq[0];
                    break;
                case 2:
                    componentNumber = eq.getPrimaryWeaponsTier().length - 1;
                    if(activeComponent == 0)
                        activeComponent = tankEq[1];
                    break;
                case 3:
                    componentNumber = eq.getPrimaryWeaponsTier().length - 1;
                    if(activeComponent == 0)
                        activeComponent = tankEq[2];
                    break;
                case 4:
                    componentNumber = eq.getPrimaryWeaponsTier().length - 1;
                    if(activeComponent == 0)
                        activeComponent = tankEq[3];
                    break;
                case 5:
                    componentNumber = eq.getSecondaryWeaponsTier().length - 1;
                    if(activeComponent == 0)
                        activeComponent = tankEq[4];
                    break;
                case 6:
                    componentNumber = eq.getAbilitiesTier().length - 1;
                    if(activeComponent == 0)
                        activeComponent = tankEq[5];
                    break;
                case 7:
                    componentNumber = eq.getModulesTier().length - 1;
                    if(activeComponent == 0)
                        activeComponent = tankEq[6];
                    break;
                case 8:
                    componentNumber = eq.getModulesTier().length - 1;
                    if(activeComponent == 0)
                        activeComponent = tankEq[7];
                    break;
                case 9:
                    componentNumber = eq.getModulesTier().length - 1;
                    if(activeComponent == 0)
                        activeComponent = tankEq[8];
                    break;
            }
            paint(g);

            mouseClicked = false;    
    }
    
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, 1400, 1000);
        g2d.setColor(Color.black);
        g2d.fillRect(0, 145, 1400, 5);
        g2d.fillRect(295, 150, 5, 1000);
        g2d.fillRect(1095, 150, 5, 1000);
        
        
        drawComponents(g2d);
        drawTabs(g2d);
        if(activeComponent != 0)
            drawStats(g2d);
        
        //Saving chosen items to current slot
        //Has to be done before drawTanks
        switch(activeTankSlot) {
                case 1:
                    for(int i = 0; i < 9; i++)
                        eq.setTankSlot1(i, tankEq[i]);
                    break;
                case 2:
                    for(int i = 0; i < 9; i++)
                        eq.setTankSlot2(i, tankEq[i]);
                    break;
                case 3:
                    for(int i = 0; i < 9; i++)
                        eq.setTankSlot3(i, tankEq[i]);
                    break;
        }
        
        drawTanks(g2d);
        //Money string
        Font font1 = new Font("Arial", Font.BOLD, 28 );
        g2d.setFont(font1);
        g2d.setColor(Color.yellow);
        g2d.drawString("Money: " + Integer.toString(eq.getMoney()) + "$", 1120, 900);
        
        g2d.dispose();
        }
        
        //Draws tank slot selection buttons
        private void drawTanks(Graphics2D g2d) {
            
            
            int buttonX = 100;
            int buttonY = 30;
            int buttonWidth = 200;
            int buttonHeight = 80;
            Color outlineColor = Color.black;
            if(activeTankSlot == 1)
               outlineColor = Color.red; 
            if(mouseX >= buttonX && mouseX < buttonX + buttonWidth
                && mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
                outlineColor = Color.yellow;
                if(mouseClicked) 
                    activeTankSlot = 1;
            }
            drawTextButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 4, Color.lightGray, outlineColor, outlineColor, "Tank 1");
            
            buttonX += 350;
            outlineColor = Color.black;
            if(activeTankSlot == 2)
               outlineColor = Color.red; 
            if(mouseX >= buttonX && mouseX < buttonX + buttonWidth
                && mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
                outlineColor = Color.yellow;
                if(mouseClicked) 
                    activeTankSlot = 2;
            }
            drawTextButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 4, Color.lightGray, outlineColor, outlineColor, "Tank 2");
            
            buttonX += 350;
            outlineColor = Color.black;
            if(activeTankSlot == 3)
               outlineColor = Color.red; 
            if(mouseX >= buttonX && mouseX < buttonX + buttonWidth
                && mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
                outlineColor = Color.yellow;
                if(mouseClicked) 
                    activeTankSlot = 3;
            }
            drawTextButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 4, Color.lightGray, outlineColor, outlineColor, "Tank 3");
            
            buttonX = 1200;
            buttonY = 50;
            buttonWidth = 100;
            buttonHeight = 50;
            outlineColor = Color.black;
            if(mouseX >= buttonX && mouseX < buttonX + buttonWidth
                && mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
                outlineColor = Color.yellow;
                if(mouseClicked)
                    buttonClicked = 1;
            }  
            drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 3, Color.red, outlineColor);
        }
    
        
        //Draws all items in currently chosen tab
        private void drawComponents(Graphics2D g2d) {            
            int componentDrawn = 1;
            int buttonWidth = 100;
            int buttonHeight = 100;
            int buttonX = 350;
            int buttonY = 200;

            for(int i = 0; i < 4; i++) {
                for(int j = 0; j < 5; j++) {
                    if(componentDrawn <= componentNumber) {
                        Color outlineColor = Color.black;
                        if(componentDrawn == activeComponent)
                            outlineColor = Color.red;
                        if(mouseX >= buttonX && mouseX < buttonX + buttonWidth
                                && mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
                            if(mouseClicked) {
                                activeComponent = componentDrawn;
                                outlineColor = Color.red;
                            }
                            else if(activeComponent != componentDrawn) {
                                outlineColor = Color.yellow;
                            }
                        }
                        
                        int tier = 0;
                        switch(activeTab) {
                            case 1:
                                tier = eq.getTankBodiesTier()[componentDrawn];
                                break;
                            case 2:
                                tier = eq.getPrimaryWeaponsTier()[componentDrawn];
                                break;
                            case 3:
                                tier = eq.getPrimaryWeaponsTier()[componentDrawn];
                                break;
                            case 4:
                                tier = eq.getPrimaryWeaponsTier()[componentDrawn];
                                break;
                            case 5:
                                tier = eq.getSecondaryWeaponsTier()[componentDrawn];
                                break;
                            case 6:
                                tier = eq.getAbilitiesTier()[componentDrawn];
                                break;
                            case 7:
                                tier = eq.getModulesTier()[componentDrawn];
                                break;
                            case 8:
                                tier = eq.getModulesTier()[componentDrawn];
                                break;
                            case 9:
                                tier = eq.getModulesTier()[componentDrawn];
                                break;
                        }
                        Color buttonColor = Color.lightGray;
                        switch(tier) {
                            case 0:
                                buttonColor = Color.lightGray;
                                break;
                            case 1:
                                buttonColor = Color.green;
                                break;
                            case 2:
                                buttonColor = Color.blue;
                                break;
                            case 3:
                                buttonColor = Color.magenta;
                                break;
                            case 4:
                                buttonColor = Color.orange;
                                break;
                        }

                        drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 4, buttonColor, outlineColor);
                        componentDrawn++;
                        buttonX += 150;
                    }
                    else
                        break;
                }
                buttonY += 150;
                buttonX = 350;
            }  
    }
    
        
    //Draws tab you can select
    //Tabs drawn may depend on number of certain slots on selected TankBody
    private void drawTabs(Graphics2D g2d) {
            int buttonWidth = 67;
            int buttonHeight = 67;
            int buttonX;
            int buttonY;
            Color outlineColor = Color.black;
            Color tierColor = Color.lightGray;

            
            //Bodies tab
            buttonX = (295 - buttonWidth) / 2;
            buttonY = 186;
            if(activeTab == 1)
                outlineColor = Color.red;
            else if(mouseX >= buttonX && mouseX < buttonX + buttonWidth 
                     && mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
                if(mouseClicked == true) {
                    activeTab = 1;
                    outlineColor = Color.red;
                    activeComponent = 0;
                }
                else if(activeTab != 1) {
                    outlineColor = Color.yellow;
                }   
            }
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
            int eqSlots = primaryWeaponNumber;
            buttonX = (295 - buttonWidth * eqSlots) / (eqSlots + 1);
            buttonY += 105;
            int jump = buttonX;
            for(int i = 1; i <= eqSlots; i++) {
                outlineColor = Color.black;
                if(activeTab == 1 + i)
                    outlineColor = Color.red;
                else if(mouseX >= buttonX && mouseX < buttonX + buttonWidth 
                     && mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
                    if(mouseClicked == true) {
                        activeTab = 1 + i;
                        outlineColor = Color.red;
                        activeComponent = 0;
                    }
                    else if(activeTab != 1 + i) {
                        outlineColor = Color.yellow;
                    } 
                }
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
            buttonX = (295 - buttonWidth) / 2;
            buttonY += 105;
            if(activeTab == 5)
                    outlineColor = Color.red;
            else if(mouseX >= buttonX && mouseX < buttonX + buttonWidth 
                 && mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
                if(mouseClicked == true) {
                    activeTab = 5;
                    outlineColor = Color.red;
                    activeComponent = 0;
                }
                else if(activeTab != 5) {
                    outlineColor = Color.yellow;
                }   
            }
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
            buttonX = (295 - buttonWidth) / 2;
            buttonY += 105;
            if(activeTab == 6)
                    outlineColor = Color.red;
            else if(mouseX >= buttonX && mouseX < buttonX + buttonWidth 
                 && mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
                if(mouseClicked == true) {
                    activeTab = 6;
                    outlineColor = Color.red;
                    activeComponent = 0;
                }
                else if(activeTab != 6) {
                    outlineColor = Color.yellow;
                }   
            }
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
            eqSlots = moduleNumber;
            buttonX = (295 - buttonWidth * eqSlots) / (eqSlots + 1);
            buttonY += 105;
            jump = buttonX;
            for(int i = 1; i <= eqSlots; i++) {
                outlineColor = Color.black;
                if(activeTab == 6 + i)
                    outlineColor = Color.red;
                else if(mouseX >= buttonX && mouseX < buttonX + buttonWidth 
                     && mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
                    if(mouseClicked == true) {
                        activeTab = 6 + i;
                        outlineColor = Color.red;
                        activeComponent = 0;
                    }
                    else if(activeTab != 6 + i) {
                        outlineColor = Color.yellow;
                    }   
                }
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
            int stringY = 160;
            drawCenteredString(g2d, 0, stringY, 300, 22, black, "Tank Body");
            stringY += 105;
            drawCenteredString(g2d, 0, stringY, 300, 22, black, "Primary Weapons");
            stringY += 105;
            drawCenteredString(g2d, 0, stringY, 300, 22, black, "Secondary Weapon");
            stringY += 105;
            drawCenteredString(g2d, 0, stringY, 300, 22, black, "Ability");
            stringY += 105;
            drawCenteredString(g2d, 0, stringY, 300, 22, black, "Modules");
            //Tank stats text
            int stringX = 10;
            g2d.setFont(new Font("Bold", Font.BOLD, 20));
            stringY += 150;
            g2d.drawString("Tank Stats:", stringX, stringY);
            stringY += 25;
            g2d.setFont(new Font("Bold", Font.BOLD, 16));
            int value = 0;
            value = eq.getTankBodies().get(tankEq[0]).getMaxHp() + checkTankStats(1);
            g2d.drawString("HP:" + Integer.toString(value), stringX, stringY);
            stringY += 22;
            value = eq.getTankBodies().get(tankEq[0]).getRegen() + checkTankStats(2);
            g2d.drawString("HP regen:" + Integer.toString(value), stringX, stringY);
            stringY += 22;
            value = eq.getTankBodies().get(tankEq[0]).getMoveSpeed() + checkTankStats(3);
            g2d.drawString("Movement speed:" + Integer.toString(value), stringX, stringY);
            stringY += 22;
            value = eq.getTankBodies().get(tankEq[0]).getArmor() + checkTankStats(4);
            g2d.drawString("Armor:" + Integer.toString(value), stringX, stringY);
            if(checkTankStats(10) != 0) {
                stringY += 22;
                g2d.drawString("Damage boost: " + Integer.toString(checkTankStats(10))+ "%", stringX, stringY);
            }
            if(checkTankStats(11) != 0) {
                stringY += 22;
                g2d.drawString("Reload boost: " + Integer.toString(checkTankStats(11)) + "%", stringX, stringY);
            }
            if(checkTankStats(12) != 0) {
                stringY += 22;
                g2d.drawString("Accuracy boost: " + Integer.toString(checkTankStats(12)) + "%", stringX, stringY);
            }
            if(checkTankStats(13) != 0) {
                stringY += 22;
                g2d.drawString("Armor pen boost: " + Integer.toString(checkTankStats(13)) + "%", stringX, stringY);
            }
            if(checkTankStats(14) != 0) {
                stringY += 22;
                g2d.drawString("Range boost: " + Integer.toString(checkTankStats(14)) + "%", stringX, stringY);
            }
            if(checkTankStats(20) != 0) {
                stringY += 22;
                g2d.drawString("Cooldown reduction: " + Integer.toString(checkTankStats(20)) + "%", stringX, stringY);
            }
    }
    
    private int checkTankStats(int statType) {
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
    
    
    //Drawing stats of currentlu selected item
    private void drawStats(Graphics2D g2d) {
        Color black = Color.black;
        Color gray = Color.lightGray;
        int stringY = 170;

        Color tierColor = Color.lightGray;
        int tier = 0;
        int cost = 0;
        //Drawing item name stats and, picture
        Font font = new Font("Arial", Font.BOLD, 18 );
        g2d.setFont(font);
        switch(activeTab) {
            case 1:
                TankBody body = eq.getTankBodies().get(activeComponent);
                tier = body.getTier();
                cost = body.getCost();
                switch(body.getTier()) {
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
                drawCenteredString(g2d, 1100, stringY, 300, 26, black, body.getName());
                stringY += 35;
                drawCenteredString(g2d, 1100, stringY, 300, 22, tierColor, "Tier " + Integer.toString(body.getTier()) );
                stringY += 190;
                g2d.setColor(black);
                g2d.drawString("Max HP: " + Integer.toString(body.getMaxHp()), 1120, stringY);
                stringY += 35;
                g2d.drawString("HP regen: " + Integer.toString(body.getRegen()), 1120, stringY);
                stringY += 35;
                g2d.drawString("Armor: " + Integer.toString(body.getArmor()), 1120, stringY);
                stringY += 35;
                g2d.drawString("Movement speed: " + Integer.toString(body.getMoveSpeed()), 1120, stringY);
                for(int i = 0; i < 6; i++) {
                    int type = body.getStatBoostType()[i];
                    if(type != 0) {
                        stringY += 35;
                        g2d.drawString(body.getStatString(i), 1120, stringY);
                    }    
                    
                }
                break;
            case 2:
                Weapon weapon = eq.getPrimaryWeapons().get(activeComponent);
                tier = weapon.getTier();
                cost = weapon.getCost();
                switch(weapon.getTier()) {
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
                drawCenteredString(g2d, 1100, stringY, 300, 26, black, weapon.getName());
                stringY += 35;
                drawCenteredString(g2d, 1100, stringY, 300, 22, tierColor, "Tier " + Integer.toString(weapon.getTier()) );
                stringY += 190;
                g2d.setColor(black);
                if(weapon.getBullet_number() == 1)
                    g2d.drawString("Damage: " + Integer.toString(weapon.getDamage()), 1120, stringY);
                else
                    g2d.drawString("Damage: " + Integer.toString(weapon.getDamage()) + " x " + Integer.toString(weapon.getBullet_number()), 1120, stringY);
                stringY += 35;
                g2d.drawString("Reload: " + Double.toString(weapon.getReload()), 1120, stringY);
                stringY += 35;
                g2d.drawString("Accuracy: " + Integer.toString(weapon.getAccuracy()) + "%", 1120, stringY);
                stringY += 35;
                g2d.drawString("Armor pen: " + Integer.toString(weapon.getArmorPen()), 1120, stringY);
                stringY += 35;
                g2d.drawString("Range: " + Integer.toString(weapon.getRange()), 1120, stringY);
                stringY += 35;
                g2d.drawString("Bullet velocity: " + Integer.toString(weapon.getBullet_velocity()), 1120, stringY);
                break;
            case 3:   
                weapon = eq.getPrimaryWeapons().get(activeComponent);
                tier = weapon.getTier();
                cost = weapon.getCost();
                switch(weapon.getTier()) {
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
                drawCenteredString(g2d, 1100, stringY, 300, 26, black, weapon.getName());
                stringY += 35;
                drawCenteredString(g2d, 1100, stringY, 300, 22, tierColor, "Tier " + Integer.toString(weapon.getTier()) );
                stringY += 190;
                g2d.setColor(black);
                if(weapon.getBullet_number() == 1)
                    g2d.drawString("Damage: " + Integer.toString(weapon.getDamage()), 1120, stringY);
                else
                    g2d.drawString("Damage: " + Integer.toString(weapon.getDamage()) + " x " + Integer.toString(weapon.getBullet_number()), 1120, stringY);
                stringY += 35;
                g2d.drawString("Reload: " + Double.toString(weapon.getReload()), 1120, stringY);
                stringY += 35;
                g2d.drawString("Accuracy: " + Integer.toString(weapon.getAccuracy()) + "%", 1120, stringY);
                stringY += 35;
                g2d.drawString("Armor pen: " + Integer.toString(weapon.getArmorPen()), 1120, stringY);
                stringY += 35;
                g2d.drawString("Range: " + Integer.toString(weapon.getRange()), 1120, stringY);
                stringY += 35;
                g2d.drawString("Bullet velocity: " + Integer.toString(weapon.getBullet_velocity()), 1120, stringY);
                break;
            case 4:   
                weapon = eq.getPrimaryWeapons().get(activeComponent);
                tier = weapon.getTier();
                cost = weapon.getCost();
                switch(weapon.getTier()) {
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
                drawCenteredString(g2d, 1100, stringY, 300, 26, black, weapon.getName());
                stringY += 35;
                drawCenteredString(g2d, 1100, stringY, 300, 22, tierColor, "Tier " + Integer.toString(weapon.getTier()) );
                stringY += 190;
                g2d.setColor(black);
                if(weapon.getBullet_number() == 1)
                    g2d.drawString("Damage: " + Integer.toString(weapon.getDamage()), 1120, stringY);
                else
                    g2d.drawString("Damage: " + Integer.toString(weapon.getDamage()) + " x " + Integer.toString(weapon.getBullet_number()), 1120, stringY);
                stringY += 35;
                g2d.drawString("Reload: " + Double.toString(weapon.getReload()), 1120, stringY);
                stringY += 35;
                g2d.drawString("Accuracy: " + Integer.toString(weapon.getAccuracy()) + "%", 1120, stringY);
                stringY += 35;
                g2d.drawString("Armor pen: " + Integer.toString(weapon.getArmorPen()), 1120, stringY);
                stringY += 35;
                g2d.drawString("Range: " + Integer.toString(weapon.getRange()), 1120, stringY);
                stringY += 35;
                g2d.drawString("Bullet velocity: " + Integer.toString(weapon.getBullet_velocity()), 1120, stringY);
                break;
           case 5:
                weapon = eq.getSecondaryWeapons().get(activeComponent);
                tier = weapon.getTier();
                cost = weapon.getCost();
                switch(weapon.getTier()) {
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
                drawCenteredString(g2d, 1100, stringY, 300, 26, black, weapon.getName());
                stringY += 35;
                drawCenteredString(g2d, 1100, stringY, 300, 22, tierColor, "Tier " + Integer.toString(weapon.getTier()) );
                stringY += 190;
                g2d.setColor(black);
                if(weapon.getBullet_number() == 1)
                    g2d.drawString("Damage: " + Integer.toString(weapon.getDamage()), 1120, stringY);
                else
                    g2d.drawString("Damage: " + Integer.toString(weapon.getDamage()) + " x " + Integer.toString(weapon.getBullet_number()), 1120, stringY);
                stringY += 35;
                g2d.drawString("Effect: " + weapon.getStatusName(), 1120, stringY);
                stringY += 35;
                g2d.drawString("Duration: " + Double.toString(weapon.getStatusDuration()), 1120, stringY);
                stringY += 35;
                g2d.drawString("Cooldown: " + Double.toString(weapon.getReload()), 1120, stringY);
                stringY += 35;
                g2d.drawString("Accuracy: " + Integer.toString(weapon.getAccuracy()) + "%", 1120, stringY);
                stringY += 35;
                g2d.drawString("Range: " + Integer.toString(weapon.getRange()), 1120, stringY);
                stringY += 35;
                g2d.drawString("Bullet velocity: " + Integer.toString(weapon.getBullet_velocity()), 1120, stringY);
                break;
            case 6:
                Ability ability = eq.getAbilities().get(activeComponent);
                tier = ability.getTier();
                cost = ability.getCost();
                switch(ability.getTier()) {
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
                drawCenteredString(g2d, 1100, stringY, 300, 26, black, ability.getName());
                stringY += 35;
                drawCenteredString(g2d, 1100, stringY, 300, 22, tierColor, "Tier " + Integer.toString(ability.getTier()) );
                stringY += 190;
                g2d.setColor(black);
                g2d.drawString("Effect: " + ability.getStatusName(), 1120, stringY);
                stringY += 35;
                g2d.drawString("Duration: " + Double.toString(ability.getBaseDuration()), 1120, stringY);
                stringY += 35;
                g2d.drawString("Cooldown: " + Double.toString(ability.getBaseCooldown()), 1120, stringY);
                break;
            case 7:
                Module module = eq.getModules().get(activeComponent);
                tier = module.getTier();
                cost = module.getCost();
                switch(module.getTier()) {
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
                drawCenteredString(g2d, 1100, stringY, 300, 26, black, module.getName());
                stringY += 35;
                drawCenteredString(g2d, 1100, stringY, 300, 22, tierColor, "Tier " + Integer.toString(module.getTier()) );
                stringY += 190;
                g2d.setColor(black);
                g2d.drawString("Stat change: ", 1120, stringY);
                if(module.getStat1Type() != 0) {
                    stringY += 35;
                    g2d.drawString(module.getStatString(1), 1120, stringY);
                }
                if(module.getStat2Type() != 0) {
                    stringY += 35;
                    g2d.drawString(module.getStatString(2), 1120, stringY);
                }
                if(module.getStat3Type() != 0) {
                    stringY += 35;
                    g2d.drawString(module.getStatString(3), 1120, stringY);
                }
                break;
            case 8:
                module = eq.getModules().get(activeComponent);
                tier = module.getTier();
                cost = module.getCost();
                switch(module.getTier()) {
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
                drawCenteredString(g2d, 1100, stringY, 300, 26, black, module.getName());
                stringY += 35;
                drawCenteredString(g2d, 1100, stringY, 300, 22, tierColor, "Tier " + Integer.toString(module.getTier()) );
                stringY += 190;
                g2d.setColor(black);
                g2d.drawString("Stat change: ", 1120, stringY);
                if(module.getStat1Type() != 0) {
                    stringY += 35;
                    g2d.drawString(module.getStatString(1), 1120, stringY);   
                }
                if(module.getStat2Type() != 0) {
                    stringY += 35;
                    g2d.drawString(module.getStatString(2), 1120, stringY);
                }
                if(module.getStat3Type() != 0) {
                    stringY += 35;
                    g2d.drawString(module.getStatString(3), 1120, stringY);
                }
                break;
            case 9:
                module = eq.getModules().get(activeComponent);
                tier = module.getTier();
                cost = module.getCost();
                switch(module.getTier()) {
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
                drawCenteredString(g2d, 1100, stringY, 300, 26, black, module.getName());
                stringY += 35;
                drawCenteredString(g2d, 1100, stringY, 300, 22, tierColor, "Tier " + Integer.toString(module.getTier()) );
                stringY += 190;
                g2d.setColor(black);
                g2d.drawString("Stat change: ", 1120, stringY);
                if(module.getStat1Type() != 0) {
                    stringY += 35;
                    g2d.drawString(module.getStatString(1), 1120, stringY);
                }
                if(module.getStat2Type() != 0) {
                    stringY += 35;
                    g2d.drawString(module.getStatString(2), 1120, stringY);
                }
                if(module.getStat3Type() != 0) {
                    stringY += 35;
                    g2d.drawString(module.getStatString(3), 1120, stringY);
                }
                break;
        }
        
        
        //Buy/upgrade button
        int buttonWidth = 100;
        int buttonHeight = 100;
        int buttonX = (300 - buttonWidth) / 2 + 1100;
        int buttonY = 240;
        drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 4, tierColor, black);

        buttonWidth = 250;
        buttonHeight = 80;
        buttonX = (300 - buttonWidth) / 2 + 1100;
        buttonY = stringY + 40;
        Color darkGreen = new Color(0, 70, 0);
        Color buttonColor;
        Color outlineColor;
        Color textColor;
        
        boolean textActive = false;
        if(eq.getMoney() >= cost && tier != 4) {
            if(mouseX >= buttonX && mouseX < buttonX + buttonWidth 
                     && mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
                buttonColor = Color.green;
                outlineColor = Color.yellow;
                textColor = Color.yellow;
                textActive = true;
                if(mouseClicked) {
                    eq.setMoney(eq.getMoney() - cost);
                    switch(activeTab) {
                        case 1:
                            eq.setTankBodiesTier(activeComponent, tier + 1);
                            break;
                        case 2:
                            eq.setPrimaryWeaponsTier(activeComponent, tier + 1);
                            break;
                        case 3:
                            eq.setPrimaryWeaponsTier(activeComponent, tier + 1);
                            break;
                        case 4:
                            eq.setPrimaryWeaponsTier(activeComponent, tier + 1);
                            break;
                        case 5:
                            eq.setSecondaryWeaponsTier(activeComponent, tier + 1);
                            break;
                        case 6:
                            eq.setAbilitiesTier(activeComponent, tier + 1);
                            break;
                        case 7:
                            eq.setModulesTier(activeComponent, tier + 1);
                            break;
                        case 8:
                            eq.setModulesTier(activeComponent, tier + 1);
                            break;
                        case 9:
                            eq.setModulesTier(activeComponent, tier + 1);
                            break;
                    }
                    mouseLocked = true;
                }          
            }
            else { 
                buttonColor = Color.green;
                outlineColor = Color.black;
                textColor = Color.black;
            }
        }
        else {
            if(mouseX >= buttonX && mouseX < buttonX + buttonWidth 
                     && mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
                buttonColor = darkGreen;
                outlineColor = Color.red;
                textColor = Color.red;
                textActive = true;
            }
            else { 
                buttonColor = darkGreen;
                outlineColor = Color.black;
                textColor = Color.darkGray;
            }
        }
        if(tier == 4) {
            buttonColor = Color.green;
            outlineColor = Color.black;
            textColor = Color.black;
        }
        
        drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 6, buttonColor, outlineColor);
        if(tier == 4)
            drawCenteredString(g2d, buttonX, buttonY + buttonHeight / 4, buttonWidth, 40, textColor, "Max tier");
        else if(textActive)
            drawCenteredString(g2d, buttonX, buttonY + buttonHeight / 4, buttonWidth, 40, textColor, Integer.toString(cost) + "$");
        else if(tier == 0)
            drawCenteredString(g2d, buttonX, buttonY + buttonHeight / 4, buttonWidth, 40, textColor, "Buy"); 
        else
            drawCenteredString(g2d, buttonX, buttonY + buttonHeight / 4, buttonWidth, 40, textColor, "Upgrade");
        
        
        //Equip button
        if(tier != 0) {
            buttonWidth = 200;
            buttonHeight = 50;
            buttonX = (300 - buttonWidth) / 2 + 1100;
            buttonY += 110;
            boolean componentEquipped = false;
            boolean componentLocked = false;
            boolean mouseInArea = false;
            
            if(mouseX >= buttonX && mouseX < buttonX + buttonWidth 
                     && mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
                mouseInArea = true;
            }
            
                buttonColor = gray;
                outlineColor = Color.green;
                switch(activeTab) {
                    case 1:
                        if(activeComponent == tankEq[0]) {
                            componentEquipped = true;
                            componentLocked = true;
                        }
                        if(mouseClicked && mouseInArea) {
                            if(!componentEquipped)
                                tankEq[0] = activeComponent;
                            mouseLocked = true;
                        }
                        break;
                    case 2:
                        boolean slot1 = false;
                        boolean slot2 = false;
                        boolean slot3 = false;
                        if(activeComponent == tankEq[1]) {
                            componentEquipped = true;
                            componentLocked = true;
                            slot1 = true;
                        }
                        else if(activeComponent == tankEq[2]) {
                            componentEquipped = true;
                            slot2 = true;
                        }
                        else if(activeComponent == tankEq[3]) {
                            componentEquipped = true;
                            slot3 = true;
                        }
                        if(mouseClicked && mouseInArea) {
                            if(slot1) {
                            }
                            else if(slot2) {
                                tankEq[2] = 0;
                            }
                            else if(slot3) {
                                tankEq[3] = 0;
                            }
                            else if(!componentEquipped){
                                tankEq[1] = activeComponent;
                            }
                            mouseLocked = true;
                        }
                        break;
                    case 3:
                        slot1 = false;
                        slot2 = false;
                        slot3 = false;
                        if(activeComponent == tankEq[1]) {
                            componentEquipped = true;
                            componentLocked = true;
                            slot1 = true;
                        }
                        else if(activeComponent == tankEq[2]) {
                            componentEquipped = true;
                            slot2 = true;
                        }
                        else if(activeComponent == tankEq[3]) {
                            componentEquipped = true;
                            slot3 = true;
                        }
                        if(mouseClicked && mouseInArea) {
                            if(slot1) {
                            }
                            else if(slot2) {
                                tankEq[2] = 0;
                            }
                            else if(slot3) {
                                tankEq[3] = 0;
                            }
                            else if(!componentEquipped){
                                tankEq[2] = activeComponent;
                            }
                            mouseLocked = true;
                        }
                        break;
                    case 4:
                        slot1 = false;
                        slot2 = false;
                        slot3 = false;
                        if(activeComponent == tankEq[1]) {
                            componentEquipped = true;
                            componentLocked = true;
                            slot1 = true;
                        }
                        if(activeComponent == tankEq[1]) {
                            componentEquipped = true;
                            slot2 = true;
                        }
                        else if(activeComponent == tankEq[1]) {
                            componentEquipped = true;
                            slot3 = true;
                        }
                        if(mouseClicked && mouseInArea) {
                            if(slot1) {
                            }
                            else if(slot2) {
                                tankEq[2] = 0;
                            }
                            else if(slot3) {
                                tankEq[3] = 0;
                            }
                            else if(!componentEquipped){
                                tankEq[3] = activeComponent;
                            }
                            mouseLocked = true;
                        }
                        break;
                    case 5:
                        if(activeComponent == tankEq[4]) {
                            componentEquipped = true;
                        }
                        if(mouseClicked && mouseInArea) {
                            if(componentEquipped)
                                tankEq[4] = 0;
                            else
                                tankEq[4] = activeComponent;
                            mouseLocked = true;
                        }
                        break;
                    case 6:
                        if(activeComponent == tankEq[5]) {
                            componentEquipped = true;
                        }
                        if(mouseClicked && mouseInArea) {
                            if(componentEquipped)
                                tankEq[5] = 0;
                            else
                                tankEq[5] = activeComponent;
                            mouseLocked = true;
                        }
                        break;
                    case 7:
                        slot1 = false;
                        slot2 = false;
                        slot3 = false;
                        if(activeComponent == tankEq[6]) {
                            componentEquipped = true;
                            slot1 = true;
                        }
                        if(activeComponent == tankEq[7]) {
                            componentEquipped = true;
                            slot2 = true;
                        }
                        else if(activeComponent == tankEq[8]) {
                            componentEquipped = true;
                            slot3 = true;
                        }
                        if(mouseClicked && mouseInArea) {
                            if(slot1) {
                                tankEq[6] = 0;
                            }
                            else if(slot2) {
                                tankEq[7] = 0;
                            }
                            else if(slot3) {
                                tankEq[8] = 0;
                            }
                            else if(!componentEquipped){
                                tankEq[6] = activeComponent;
                            }
                            mouseLocked = true;
                        }
                        break;
                    case 8:
                        slot1 = false;
                        slot2 = false;
                        slot3 = false;
                        if(activeComponent == tankEq[6]) {
                            componentEquipped = true;
                            slot1 = true;
                        }
                        if(activeComponent == tankEq[7]) {
                            componentEquipped = true;
                            slot2 = true;
                        }
                        else if(activeComponent == tankEq[8]) {
                            componentEquipped = true;
                            slot3 = true;
                        }
                        if(mouseClicked && mouseInArea) {
                            if(slot1) {
                                tankEq[6] = 0;
                            }
                            else if(slot2) {
                                tankEq[7] = 0;
                            }
                            else if(slot3) {
                                tankEq[8] = 0;
                            }
                            else if(!componentEquipped){
                                tankEq[7] = activeComponent;
                            }
                            mouseLocked = true;
                        }
                        break;
                    case 9:
                        slot1 = false;
                        slot2 = false;
                        slot3 = false;
                        if(activeComponent == tankEq[6]) {
                            componentEquipped = true;
                            slot1 = true;
                        }
                        if(activeComponent == tankEq[7]) {
                            componentEquipped = true;
                            slot2 = true;
                        }
                        else if(activeComponent == tankEq[8]) {
                            componentEquipped = true;
                            slot3 = true;
                        }
                        if(mouseClicked && mouseInArea) {
                            if(slot1) {
                                tankEq[6] = 0;
                            }
                            else if(slot2) {
                                tankEq[7] = 0;
                            }
                            else if(slot3) {
                                tankEq[8] = 0;
                            }
                            else if(!componentEquipped){
                                tankEq[8] = activeComponent;
                            }
                            mouseLocked = true;
                        }
                        break;
                }
                
            String equipString = "";
            if(componentEquipped && !componentLocked && mouseInArea) {
                equipString = "Unequip";
                outlineColor = Color.yellow;
            }
            else if(componentEquipped) {
                equipString = "Equipped";
                buttonColor = gray;
                outlineColor = Color.black;
            }
            else {
                equipString = "Equip";
                if(mouseInArea)
                    outlineColor = Color.yellow;
                else
                    outlineColor = Color.black;
            }
            drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 4, buttonColor, outlineColor);
            drawCenteredString(g2d, buttonX, buttonY + buttonHeight / 4, buttonWidth, 26, outlineColor, equipString);
        }       
    }
    

    //Buttons and strings
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
        
        Font font = new Font("Arial", Font.BOLD, (int) Math.round(1.0 * (height - 2 * outlineWidth) * 0.8));
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int stringX = x + ((width - fm.stringWidth(text)) / 2);
        int stringY = y + ((height - fm.getHeight()) / 2) + fm.getAscent();

        g2d.setColor(textColor);
        g2d.drawString(text, stringX, stringY);
    }

    //getters and setters
    public int getButtonClicked() {
        return buttonClicked;
    }
    
    public Equipment getEq() {
        return eq;
    }

    public void setEq(Equipment eq) {
        this.eq = eq;
    }
    
    
    
    
}