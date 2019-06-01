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
    private int activeTank;
    private int activeTab;
    private int activeComponent;
    private int componentNumber;
    private int primaryWeaponNumber;
    private int moduleNumber;
    
    
    private Equipment eq;
    
    int activate;
    
    ArmoryWindow() {
        activeTank = 1;
        activeTab = 1;
        activeComponent = 0;
        componentNumber = 20;
        primaryWeaponNumber = 1;
        moduleNumber = 1;
        mouseLocked = false;
        
        activeTab = 1;
        activate = 6;
    }
    
    public void update(Graphics g, int mX, int mY, boolean mClick, boolean mouseUp, Equipment equipment) {
        if(activate == 0)
        {
            mouseX = mX;
            mouseY = mY;
            mouseClicked = mClick;
            if(mouseUp)
                mouseLocked = false;
            if(mouseLocked)
                mouseClicked = false;
            buttonClicked = 0;
            eq = equipment;
            primaryWeaponNumber = eq.getTankBodies().get(eq.getBodyID()).getPrimaryWeaponSlots();
            moduleNumber = eq.getTankBodies().get(eq.getBodyID()).getModuleSlots();

            switch(activeTab) {
                case 1:
                    componentNumber = eq.getTankBodiesTier().length - 1;
                    break;
                case 2:
                    componentNumber = eq.getPrimaryWeaponsTier().length - 1;
                    break;
                case 3:
                    componentNumber = eq.getPrimaryWeaponsTier().length - 1;
                    break;
                case 4:
                    componentNumber = eq.getPrimaryWeaponsTier().length - 1;
                    break;
                case 5:
                    componentNumber = eq.getSecondaryWeaponsTier().length - 1;
                    break;
                case 6:
                    componentNumber = eq.getAbilitiesTier().length - 1;
                    break;
                case 7:
                    componentNumber = eq.getModulesTier().length - 1;
                    break;
                case 8:
                    componentNumber = eq.getModulesTier().length - 1;
                    break;
                case 9:
                    componentNumber = eq.getModulesTier().length - 1;
                    break;
            }
            paint(g);

            mouseClicked = false;
        }
        else
            activate--;
    }
    
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, 1400, 1000);
        g2d.setColor(Color.black);
        g2d.fillRect(0, 145, 1400, 5);
        g2d.fillRect(295, 150, 5, 1000);
        g2d.fillRect(1095, 150, 5, 1000);
        
        drawTanks(g2d);
        drawComponents(g2d);
        drawTabs(g2d);
        if(activeComponent != 0)
            drawStats(g2d);
        
        g2d.dispose();
        }
        
        private void drawTanks(Graphics2D g2d) {
            
            
            int buttonX = 1250;
            int buttonY = 50;
            int buttonWidth = 100;
            int buttonHeight = 50;
            Color outlineColor = Color.black;
            if(mouseX >= buttonX && mouseX < buttonX + buttonWidth
                && mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
                outlineColor = Color.yellow;
                if(mouseClicked)
                    buttonClicked = 1;
            }
                
            drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 3, Color.red, outlineColor);
        }
    
        
        //-----------------------------------------------------------------------
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
    
        
    //------------------------------------------------------------------------
    private void drawTabs(Graphics2D g2d) {
            int tabButtonWidth = 67;
            int tabButtonHeight = 67;
            int tabButtonX;
            int tabButtonY;
            Color gray = Color.lightGray;
            Color outlineColor = Color.black;
            Color tierColor = Color.lightGray;

            
            //Bodies tab
            tabButtonX = (295 - tabButtonWidth) / 2;
            tabButtonY = 197;
            if(activeTab == 1)
                outlineColor = Color.red;
            else if(mouseX >= tabButtonX && mouseX < tabButtonX + tabButtonWidth 
                     && mouseY >= tabButtonY && mouseY < tabButtonY + tabButtonHeight) {
                if(mouseClicked == true) {
                    activeTab = 1;
                    outlineColor = Color.red;
                    activeComponent = 0;
                }
                else if(activeTab != 1) {
                    outlineColor = Color.yellow;
                }   
            }
            if(eq.getBodyID() == 0)
                tierColor = Color.lightGray;
            else {
                switch(eq.getTankBodies().get(eq.getBodyID()).getTier()) {
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
            drawButton(g2d, tabButtonX, tabButtonY, tabButtonWidth, tabButtonHeight, 3, tierColor, outlineColor);
        
            
            //Primary weapons tab
            tierColor = Color.lightGray;
            outlineColor = Color.black;
            int eqSlots = primaryWeaponNumber;
            tabButtonX = (295 - tabButtonWidth * eqSlots) / (eqSlots + 1);
            tabButtonY += 125;
            int jump = tabButtonX;
            for(int i = 1; i <= eqSlots; i++) {
                outlineColor = Color.black;
                if(activeTab == 1 + i)
                    outlineColor = Color.red;
                else if(mouseX >= tabButtonX && mouseX < tabButtonX + tabButtonWidth 
                     && mouseY >= tabButtonY && mouseY < tabButtonY + tabButtonHeight) {
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
                        if(eq.getPrimary1ID() == 0)
                            weaponTier = 0;
                        else
                            weaponTier = eq.getPrimaryWeapons().get(eq.getPrimary1ID()).getTier();
                        break;
                    case 2:
                        if(eq.getPrimary2ID() == 0)
                            weaponTier = 0;
                        else
                            weaponTier = eq.getPrimaryWeapons().get(eq.getPrimary2ID()).getTier();
                        break;
                    case 3:
                        if(eq.getPrimary3ID() == 0)
                            weaponTier = 0;
                        else
                            weaponTier = eq.getPrimaryWeapons().get(eq.getPrimary3ID()).getTier();
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
                drawButton(g2d, tabButtonX, tabButtonY, tabButtonWidth, tabButtonHeight, 3, tierColor, outlineColor);
                tabButtonX += (jump + tabButtonWidth);
            }
            
            //Secondary weapons tab
            tierColor = Color.lightGray;
            outlineColor = Color.black;
            tabButtonX = (295 - tabButtonWidth) / 2;
            tabButtonY += 125;
            if(activeTab == 5)
                    outlineColor = Color.red;
            else if(mouseX >= tabButtonX && mouseX < tabButtonX + tabButtonWidth 
                 && mouseY >= tabButtonY && mouseY < tabButtonY + tabButtonHeight) {
                if(mouseClicked == true) {
                    activeTab = 5;
                    outlineColor = Color.red;
                    activeComponent = 0;
                }
                else if(activeTab != 5) {
                    outlineColor = Color.yellow;
                }   
            }
            if(eq.getSecondaryID() == 0)
                tierColor = Color.lightGray;
            else {
                switch(eq.getSecondaryWeapons().get(eq.getSecondaryID()).getTier()) {
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
            drawButton(g2d, tabButtonX, tabButtonY, tabButtonWidth, tabButtonHeight, 3, tierColor, outlineColor);
            
            //Abilities tab
            tierColor = Color.lightGray;
            outlineColor = Color.black;
            tabButtonX = (295 - tabButtonWidth) / 2;
            tabButtonY += 125;
            if(activeTab == 6)
                    outlineColor = Color.red;
            else if(mouseX >= tabButtonX && mouseX < tabButtonX + tabButtonWidth 
                 && mouseY >= tabButtonY && mouseY < tabButtonY + tabButtonHeight) {
                if(mouseClicked == true) {
                    activeTab = 6;
                    outlineColor = Color.red;
                    activeComponent = 0;
                }
                else if(activeTab != 6) {
                    outlineColor = Color.yellow;
                }   
            }
            if(eq.getAbilityID() == 0)
                tierColor = Color.lightGray;
            else {
                switch(eq.getAbilities().get(eq.getAbilityID()).getTier()) {
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
            drawButton(g2d, tabButtonX, tabButtonY, tabButtonWidth, tabButtonHeight, 3, tierColor, outlineColor);
            
            //Modules tab
            outlineColor = Color.black;
            eqSlots = moduleNumber;
            tabButtonX = (295 - tabButtonWidth * eqSlots) / (eqSlots + 1);
            tabButtonY += 125;
            jump = tabButtonX;
            for(int i = 1; i <= eqSlots; i++) {
                outlineColor = Color.black;
                if(activeTab == 6 + i)
                    outlineColor = Color.red;
                else if(mouseX >= tabButtonX && mouseX < tabButtonX + tabButtonWidth 
                     && mouseY >= tabButtonY && mouseY < tabButtonY + tabButtonHeight) {
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
                        if(eq.getModule1ID() == 0)
                            moduleTier = 0;
                        else
                            moduleTier = eq.getModules().get(eq.getModule1ID()).getTier();
                        break;
                    case 2:
                        if(eq.getModule2ID() == 0)
                            moduleTier = 0;
                        else
                            moduleTier = eq.getModules().get(eq.getModule2ID()).getTier();
                        break;
                    case 3:
                        if(eq.getModule3ID() == 0)
                            moduleTier = 0;
                        else
                            moduleTier = eq.getModules().get(eq.getModule3ID()).getTier();
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
                drawButton(g2d, tabButtonX, tabButtonY, tabButtonWidth, tabButtonHeight, 3, tierColor, outlineColor);
                tabButtonX += (jump + tabButtonWidth);
            }
            
            Color black = Color.black;
            int stringY = 170;
            drawCenteredString(g2d, 0, stringY, 300, 22, black, "Tank Body");
            stringY += 125;
            drawCenteredString(g2d, 0, stringY, 300, 22, black, "Primary Weapons");
            stringY += 125;
            drawCenteredString(g2d, 0, stringY, 300, 22, black, "Secondary Weapon");
            stringY += 125;
            drawCenteredString(g2d, 0, stringY, 300, 22, black, "Ability");
            stringY += 125;
            drawCenteredString(g2d, 0, stringY, 300, 22, black, "Modules");
    }
    
    
    //----------------------------------------------------------------------------
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
                g2d.drawString("Damage: " + Integer.toString(weapon.getDamage()), 1120, stringY);
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
                g2d.drawString("Damage: " + Integer.toString(weapon.getDamage()), 1120, stringY);
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
                g2d.drawString("Damage: " + Integer.toString(weapon.getDamage()), 1120, stringY);
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
                g2d.drawString("Damage: " + Integer.toString(weapon.getDamage()), 1120, stringY);
                stringY += 35;
                g2d.drawString("Effect: ", 1120, stringY);
                stringY += 35;
                g2d.drawString("Duration: ", 1120, stringY);
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
                g2d.drawString("Effect: ", 1120, stringY);
                stringY += 35;
                g2d.drawString("Duration: ", 1120, stringY);
                stringY += 35;
                g2d.drawString("Cooldown: ", 1120, stringY);
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
                        if(activeComponent == eq.getBodyID()) {
                            componentEquipped = true;
                            componentLocked = true;
                        }
                        if(mouseClicked && mouseInArea) {
                            if(!componentEquipped)
                                eq.setBodyID(activeComponent);
                            mouseLocked = true;
                        }
                        break;
                    case 2:
                        boolean slot1 = false;
                        boolean slot2 = false;
                        boolean slot3 = false;
                        if(activeComponent == eq.getPrimary1ID()) {
                            componentEquipped = true;
                            componentLocked = true;
                            slot1 = true;
                        }
                        else if(activeComponent == eq.getPrimary2ID()) {
                            componentEquipped = true;
                            slot2 = true;
                        }
                        else if(activeComponent == eq.getPrimary3ID()) {
                            componentEquipped = true;
                            slot3 = true;
                        }
                        if(mouseClicked && mouseInArea) {
                            if(slot1) {
                            }
                            else if(slot2) {
                                eq.setPrimary2ID(0);
                            }
                            else if(slot3) {
                                eq.setPrimary3ID(0);
                            }
                            else if(!componentEquipped){
                                eq.setPrimary1ID(activeComponent);
                            }
                            mouseLocked = true;
                        }
                        break;
                    case 3:
                        slot1 = false;
                        slot2 = false;
                        slot3 = false;
                        if(activeComponent == eq.getPrimary1ID()) {
                            componentEquipped = true;
                            componentLocked = true;
                            slot1 = true;
                        }
                        else if(activeComponent == eq.getPrimary2ID()) {
                            componentEquipped = true;
                            slot2 = true;
                        }
                        else if(activeComponent == eq.getPrimary3ID()) {
                            componentEquipped = true;
                            slot3 = true;
                        }
                        if(mouseClicked && mouseInArea) {
                            if(slot1) {
                            }
                            else if(slot2) {
                                eq.setPrimary2ID(0);
                            }
                            else if(slot3) {
                                eq.setPrimary3ID(0);
                            }
                            else if(!componentEquipped){
                                eq.setPrimary2ID(activeComponent);
                            }
                            mouseLocked = true;
                        }
                        break;
                    case 4:
                        slot1 = false;
                        slot2 = false;
                        slot3 = false;
                        if(activeComponent == eq.getPrimary1ID()) {
                            componentEquipped = true;
                            componentLocked = true;
                            slot1 = true;
                        }
                        if(activeComponent == eq.getPrimary2ID()) {
                            componentEquipped = true;
                            slot2 = true;
                        }
                        else if(activeComponent == eq.getPrimary3ID()) {
                            componentEquipped = true;
                            slot3 = true;
                        }
                        if(mouseClicked && mouseInArea) {
                            if(slot1) {
                            }
                            else if(slot2) {
                                eq.setPrimary2ID(0);
                            }
                            else if(slot3) {
                                eq.setPrimary3ID(0);
                            }
                            else if(!componentEquipped){
                                eq.setPrimary3ID(activeComponent);
                            }
                            mouseLocked = true;
                        }
                        break;
                    case 5:
                        if(activeComponent == eq.getSecondaryID()) {
                            componentEquipped = true;
                        }
                        if(mouseClicked && mouseInArea) {
                            if(componentEquipped)
                                eq.setSecondaryID(0);
                            else
                                eq.setSecondaryID(activeComponent);
                            mouseLocked = true;
                        }
                        break;
                    case 6:
                        if(activeComponent == eq.getAbilityID()) {
                            componentEquipped = true;
                        }
                        if(mouseClicked && mouseInArea) {
                            if(componentEquipped)
                                eq.setAbilityID(0);
                            else
                                eq.setAbilityID(activeComponent);
                            mouseLocked = true;
                        }
                        break;
                    case 7:
                        slot1 = false;
                        slot2 = false;
                        slot3 = false;
                        if(activeComponent == eq.getModule1ID()) {
                            componentEquipped = true;
                            slot1 = true;
                        }
                        if(activeComponent == eq.getModule2ID()) {
                            componentEquipped = true;
                            slot2 = true;
                        }
                        else if(activeComponent == eq.getModule3ID()) {
                            componentEquipped = true;
                            slot3 = true;
                        }
                        if(mouseClicked && mouseInArea) {
                            if(slot1) {
                                eq.setModule1ID(0);
                            }
                            else if(slot2) {
                                eq.setModule2ID(0);
                            }
                            else if(slot3) {
                                eq.setModule3ID(0);
                            }
                            else if(!componentEquipped){
                                eq.setModule1ID(activeComponent);
                            }
                            mouseLocked = true;
                        }
                        break;
                    case 8:
                        slot1 = false;
                        slot2 = false;
                        slot3 = false;
                        if(activeComponent == eq.getModule1ID()) {
                            componentEquipped = true;
                            slot1 = true;
                        }
                        if(activeComponent == eq.getModule2ID()) {
                            componentEquipped = true;
                            slot2 = true;
                        }
                        else if(activeComponent == eq.getModule3ID()) {
                            componentEquipped = true;
                            slot3 = true;
                        }
                        if(mouseClicked && mouseInArea) {
                            if(slot1) {
                                eq.setModule1ID(0);
                            }
                            else if(slot2) {
                                eq.setModule2ID(0);
                            }
                            else if(slot3) {
                                eq.setModule3ID(0);
                            }
                            else if(!componentEquipped){
                                eq.setModule2ID(activeComponent);
                            }
                            mouseLocked = true;
                        }
                        break;
                    case 9:
                        slot1 = false;
                        slot2 = false;
                        slot3 = false;
                        if(activeComponent == eq.getModule1ID()) {
                            componentEquipped = true;
                            slot1 = true;
                        }
                        if(activeComponent == eq.getModule2ID()) {
                            componentEquipped = true;
                            slot2 = true;
                        }
                        else if(activeComponent == eq.getModule3ID()) {
                            componentEquipped = true;
                            slot3 = true;
                        }
                        if(mouseClicked && mouseInArea) {
                            if(slot1) {
                                eq.setModule1ID(0);
                            }
                            else if(slot2) {
                                eq.setModule2ID(0);
                            }
                            else if(slot3) {
                                eq.setModule3ID(0);
                            }
                            else if(!componentEquipped){
                                eq.setModule3ID(activeComponent);
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
        
        //Money string
        stringY = 900;
        Font font1 = new Font("Arial", Font.BOLD, 28 );
        g2d.setFont(font1);
        g2d.setColor(Color.yellow);
        g2d.drawString("Money: " + Integer.toString(eq.getMoney()) + "$", 1120, stringY);
        
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