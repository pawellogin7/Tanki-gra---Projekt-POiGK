package tanksgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class TanksGame extends Applet implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private MenuWindow menu;
    private ArmoryWindow armory;
    private LevelSelectionWindow levelSelection;
    private SaveSelectionWindow saveSelection;
    private Player player;
    private EnemyTank t1, t2;
    private Weapon slowingShot, paralyzeShot, jamShot, stunShot, armorBreakShot;
    private Ability speedBoost, sprint, reloadBoost, regenBoost;
    private Image image, character, background, cursor, enemyTank, playerBullet, projectileFire, projectileLaser;
    private Graphics second;
    private URL base;
    private static Background bg1, bg2;
    private int mouseX = 0;
    private int mouseY = 0;
    boolean mouse1Down = false;
    boolean mouse2Down = false;
    boolean mouseMoving = false;
    private int cusorWidth = 16;
    private int cursorHeight = 16;
    private int currentWindow = 0;
    private boolean currentWindowChanged = false;
    private boolean escKey = false;
    private int saveSlotSelected = 1;
    
    private Equipment equipment;
    
    @Override
    public void init(){
        setSize(1400, 1000);
        setBackground(Color.BLACK);
        setFocusable(true);
        Frame frame = (Frame) this.getParent().getParent();
        frame.setTitle("Tanks Alpha");
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        
        try {
            base = getDocumentBase();
	} 
        catch (Exception e) {
            // TODO: handle exception
	}

        
	//Image Setups     
	character = getImage(base, "../data/pictures/playerTank.png");
        cursor = getImage(base, "../data/pictures/cursor.png");
        enemyTank = getImage(base, "../data/pictures/enemyTank.png");
        playerBullet = getImage(base, "../data/pictures/playerBullet.png");
        projectileFire = getImage(base, "../data/pictures/projectileFire.png");
        projectileLaser = getImage(base, "../data/pictures/projectileLaser.png");
        
        equipment = new Equipment();
        loadFile(saveSlotSelected);
        equipment.update();
        
        menu = new MenuWindow();
        armory = new ArmoryWindow();
              
        
        
        //Secondaryweapon creation
       
        paralyzeShot = new Weapon(10, 10, 100, 0, 800, 16, 1);
        paralyzeShot.getStatus().setParalyzed(true);
        paralyzeShot.getStatus().setParalyzedDuration(3);
        
        jamShot = new Weapon(10, 10, 100, 0, 800, 16, 1);
        jamShot.getStatus().setWeaponJammed(true);
        jamShot.getStatus().setWeaponJammedDuration(3);
        
        stunShot = new Weapon(10, 10, 100, 0, 800, 16, 1);
        stunShot.getStatus().setParalyzed(true);
        stunShot.getStatus().setParalyzedDuration(1.5);
        stunShot.getStatus().setWeaponJammed(true);
        stunShot.getStatus().setWeaponJammedDuration(1.5);
        
        armorBreakShot = new Weapon(10, 10, 50, 0, 400, 14, 9);
        armorBreakShot.getStatus().setArmorBroken(true);
        armorBreakShot.getStatus().setArmorBrokenDuration(5);
        
        //Ability creation
        
        sprint = new Ability(5, 10);
        sprint.getStatus().setSprint(true);
        sprint.getStatus().setSprintDuration(sprint.getBaseDuration());
        sprint.getStatus().setWeaponJammed(true);
        sprint.getStatus().setWeaponJammedDuration(sprint.getBaseDuration());
        
        reloadBoost = new Ability(5, 10);
        reloadBoost.getStatus().setReloadBoost(true);
        reloadBoost.getStatus().setReloadBoostDuration(reloadBoost.getBaseDuration());
        
        regenBoost = new Ability(8, 10);
        regenBoost.getStatus().setRegenBoost(true);
        regenBoost.getStatus().setRegenBoostDuration(regenBoost.getBaseDuration());
        
    }
    
    @Override
    public void start() {
        bg1 = new Background(0, 0);
        bg2 = new Background(2160, 1000); 
        
        player = new Player(equipment);
        t1 = new EnemyTank(340, 360);
        t2 = new EnemyTank(700, 360);
        
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
            cursor, new Point(0,0),"custom cursor"));
        
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
    }

    @Override
    public void destroy() {
        System.exit(1);
    }

    
    @Override
    public void run() {
        while (true) {
            ArrayList projectiles = player.getProjectiles();
            for (int i = 0; i < projectiles.size(); i++) {
		Projectile p = (Projectile) projectiles.get(i);
		if (p.isVisible() == true) {
			p.update();
		} else {
			projectiles.remove(i);
		}
            }
            
            if(mouse1Down)
                player.shootPrimary(mouseX, mouseY);
            if(mouse2Down)
                player.shootSecondary(mouseX, mouseY);
                
            bg1.update();
            bg2.update();
            player.update();
            t1.update();
            t2.update();

            
            repaint();
            try {
                Thread.sleep(17);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    
    @Override
    public void update(Graphics g) {
        if (image == null) {
            image = createImage(this.getWidth(), this.getHeight());
            second = image.getGraphics();
	}

        second.setColor(getBackground());
	second.fillRect(0, 0, getWidth(), getHeight());
	second.setColor(getForeground());
	paint(second);

	g.drawImage(image, 0, 0, this);
    }

    
    @Override
    public void paint(Graphics g) { 
        
        switch(currentWindow) {
            case 0: {
                if(currentWindowChanged) {
                    mouse1Down = false;
                    saveFile(saveSlotSelected);
                    menu = new MenuWindow();
                    currentWindowChanged = false;
                }
                menu.update(g, mouseX, mouseY, mouse1Down);
                switch(menu.getButtonClicked()) {
                    case 0:
                        break;
                    case 1:
                        currentWindow = 3;
                        currentWindowChanged = true;
                        break;
                    case 2:
                        currentWindow = 1;
                        currentWindowChanged = true;
                        break;
                    case 3:
                        currentWindow = 2;
                        currentWindowChanged = true;
                        break;
                    case 4:
                        destroy();
                }
                if(escKey) {
                    destroy();
                    escKey = false;
                }
                    
                break;
            }
            case 1: {
                if(currentWindowChanged) {
                    armory = new ArmoryWindow();
                    currentWindowChanged = false;
                }
                equipment.update();
                armory.setEq(equipment);
                armory.update(g, mouseX, mouseY, mouse1Down, equipment);
                equipment = armory.getEq();
                
                switch(armory.getButtonClicked()) {
                    case 0:
                        break;
                    case 1:
                        currentWindow = 0;
                        currentWindowChanged = true;
                        break;
                }
                if(escKey) {
                    currentWindow = 0;
                    escKey = false;
                    currentWindowChanged = true;
                }
                break;
            }
            case 2: {
                if(currentWindowChanged) {
                    saveSelection = new SaveSelectionWindow(saveSlotSelected);
                    currentWindowChanged = false;
                }
                saveSelection.update(g, mouseX, mouseY, mouse1Down);
                saveSlotSelected = saveSelection.getSaveSlotSelected();
                    
                
                switch(saveSelection.getButtonClicked()) {
                    case 0:
                        break;
                    case 1:
                        loadFile(saveSlotSelected);
                        currentWindow = 0;
                        currentWindowChanged = true;
                        break;
                }
                if(escKey) {
                    loadFile(saveSlotSelected);
                    currentWindow = 0;
                    escKey = false;
                    currentWindowChanged = true;
                }
                break;
            }
            case 3: {
                if(currentWindowChanged) {
                    levelSelection = new LevelSelectionWindow();
                    levelSelection.setLevelCompleted(equipment.getLevelCompleted());
                    currentWindowChanged = false;
                }
                levelSelection.update(g, mouseX, mouseY, mouse1Down);
                switch(levelSelection.getButtonClicked()) {
                    case 0:
                        break;
                    case 1:
                        currentWindow = 11;
                        currentWindowChanged = true;
                        break;
                }
                if(escKey) {
                    currentWindow = 0;
                    escKey = false;
                    currentWindowChanged = true;
                }
                break;
            }
            case 11: {
                if(currentWindowChanged) {
                    player = new Player(equipment);
                    currentWindowChanged = false;
                }
                drawLevel1(g);
                if(escKey) {
                    currentWindow = 0;
                    escKey = false;
                    currentWindowChanged = true;
                }
                break;
            }
                    
        }
        
    }
    
    
    
    public void drawLevel1(Graphics g) {
        
        g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
        g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
        
        g.drawImage(enemyTank, t1.getCenterX() - 64, t1.getCenterY() - 32, this);
        g.drawImage(enemyTank, t2.getCenterX() - 64, t2.getCenterY() - 32, this);
        g.drawImage(character, player.getCenterX() - 64, player.getCenterY() - 32, this);
        
        ArrayList projectiles = player.getProjectiles();
	for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = (Projectile) projectiles.get(i);
            drawProjectile(p, g);
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
            drawHud(hudX, hudY, hudSize, percent, g, tierColor); //ability hud
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
            drawHud(hudX, hudY, hudSize, percent, g, tierColor); //secondary weapon hud
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
            drawHud(hudX, hudY, hudSize, 100, g, tierColor);
        else
            drawHud(hudX, hudY, hudSize, 0, g, tierColor);
        
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
                drawHud(hudX, hudY, hudSize, 100, g, tierColor);
            else
                drawHud(hudX, hudY, hudSize, 0, g, tierColor);
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
                drawHud(hudX, hudY, hudSize, 100, g, tierColor);
            else
                drawHud(hudX, hudY, hudSize, 0, g, tierColor);
        }
        
        
        drawPlayerHP(500, 903, 400, 30, g);
    }
    
    public void drawProjectile(Projectile p, Graphics g) {
        int typ = p.getProjectileType();
        switch(typ)
        {
            case 0:
              g.drawImage(playerBullet, p.getX(), p.getY(), this);
              break;
            case 1:
              break;
            case 2:
              g.drawImage(projectileLaser, p.getX(), p.getY(), this);
              break;
            case 3:
              g.drawImage(projectileFire, p.getX(), p.getY(), this);
              break;
        }
    }

    public void drawHud(int hudX, int hudY, int hudSize, int hudPercent, Graphics g, Color tierColor) {
        int size = 26 * hudSize;
        int lineSize = 2 * hudSize;
        int startPoint = hudX + size / 2;
        int pointxLeft = hudX;
        int pointxRight = hudX + 26 * hudSize - lineSize;
        int pointyUp = hudY + hudSize;
        int pointyDown = hudY + 26 * hudSize - lineSize / 2;
        
        g.setColor(tierColor);
        g.fillRect(pointxLeft, pointyUp, size, size);
        g.setColor(Color.black);
        g.fillRect(pointxLeft, pointyUp, 26 * hudSize, lineSize);
        g.fillRect(pointxRight, pointyUp, lineSize, 26 * hudSize);
        g.fillRect(pointxLeft, pointyDown, 26 * hudSize, lineSize);
        g.fillRect(pointxLeft, pointyUp, lineSize, 26 * hudSize);
        
        g.setColor(Color.red);
        if(hudPercent <= 13) {
            g.fillRect(startPoint, pointyUp, hudPercent * hudSize, lineSize);
        }
        else if(hudPercent > 13 && hudPercent <= 37) {
            hudPercent -= 13;
            g.fillRect(startPoint, pointyUp, 13 * hudSize, lineSize);
            g.fillRect(pointxRight, pointyUp, lineSize, hudPercent * hudSize);
        }
        else if(hudPercent > 37 && hudPercent <= 63) {
            hudPercent -= 37;
            g.fillRect(startPoint, pointyUp, 13 * hudSize, lineSize);
            g.fillRect(pointxRight, pointyUp, lineSize, 26 * hudSize);
            g.fillRect(pointxRight - hudPercent * hudSize + lineSize, pointyDown, hudPercent * hudSize, lineSize);
        }
        else if(hudPercent > 63 && hudPercent <= 87) {
            hudPercent -= 63;
            g.fillRect(startPoint, pointyUp, 13 * hudSize, lineSize);
            g.fillRect(pointxRight, pointyUp, lineSize, 26 * hudSize);
            g.fillRect(pointxLeft, pointyDown, 26 * hudSize, lineSize);
            g.fillRect(pointxLeft, pointyDown - hudPercent * hudSize, lineSize, hudPercent * hudSize + hudSize);
        }
        else if(hudPercent > 87 && hudPercent <= 100) {
            hudPercent -= 87;
            g.fillRect(startPoint, pointyUp, 13 * hudSize, lineSize);
            g.fillRect(pointxRight, pointyUp, lineSize, 26 * hudSize);
            g.fillRect(pointxLeft, pointyDown, 26 * hudSize, lineSize);
            g.fillRect(pointxLeft, pointyUp, lineSize, 26 * hudSize);
            g.fillRect(pointxLeft, pointyUp, hudPercent * hudSize + hudSize , lineSize);
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
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                player.moveUp();
            break;

            case KeyEvent.VK_S:
                player.moveDown();
            break;

            case KeyEvent.VK_A:
                player.moveLeft();
            break;

            case KeyEvent.VK_D:
                player.moveRight();
            break;
            
            case KeyEvent.VK_1:
                player.changeWeapon(1);
            break;
            
            case KeyEvent.VK_2:
                player.changeWeapon(2);
            break;
            
            case KeyEvent.VK_3:
                player.changeWeapon(3);
            break;
            
            case KeyEvent.VK_SPACE:
                player.useAbility();
            break;
            
            case KeyEvent.VK_ESCAPE:
                escKey = true;
            break;
            
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                player.setMovingUp(false);
            break;

            case KeyEvent.VK_S:
                player.setMovingDown(false);
            break;

            case KeyEvent.VK_A:
                player.setMovingLeft(false);
            break;

            case KeyEvent.VK_D:
                player.setMovingRight(false);
            break;

            case KeyEvent.VK_SPACE:
            break;
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            mouse1Down = true;
        }
        else if(e.getButton() == MouseEvent.BUTTON3) {
            mouse2Down = true;
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            mouse1Down = false;
        }
        else if(e.getButton() == MouseEvent.BUTTON3) {
            mouse2Down = false;
        }
    }
    
    @Override
    public void mouseEntered(MouseEvent e){
        mouseX = e.getX() + 16;
        mouseY = e.getY() + 16;
    }
    
    @Override
    public void mouseClicked(MouseEvent e){}
    
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX() + 16;
        mouseY = e.getY() + 16;
    }
    
    @Override
    public void mouseExited(MouseEvent e){}
    
    @Override
    public void mouseDragged(MouseEvent e){
        mouseX = e.getX() + 16;
        mouseY = e.getY() + 16;
        if(e.getButton() == MouseEvent.BUTTON1) {
            mouse1Down = true;
        }
        else if(e.getButton() == MouseEvent.BUTTON3) {
            mouse2Down = true;
        }
    }
    
    public void loadFile(int savefileID) {
        String savefilePath = "";
        boolean fileInvalid = false;
        switch(savefileID) {
            case 1:
                savefilePath = "data/savefiles/savefile1.txt";
                break;
            case 2:
                savefilePath = "data/savefiles/savefile2.txt";
                break;
            case 3:
                savefilePath = "data/savefiles/savefile3.txt";
                break;
        }
        try {
            File file = new File(savefilePath);
            Scanner in = new Scanner(file);
            String line = in.nextLine();
            int levelCompleted = Integer.parseInt(line);
            if(levelCompleted >= 0 && levelCompleted <= 15)
                equipment.setLevelCompleted(levelCompleted);
            else
                fileInvalid = true;
            line = in.nextLine();
            int money = Integer.parseInt(line);
            if(money >= 0 && money <= 999999)
                equipment.setMoney(money);
            else
                fileInvalid = true;
            
            line = in.nextLine();
            for(int i = 0; i < line.length(); i++) {
                int value = Character.getNumericValue(line.charAt(i));
                if(value >= 0 && value <= 4)
                    equipment.setTankBodiesTier(i, value);
                else {
                    fileInvalid = true;
                    break;
                }      
            }
            line = in.nextLine();
            for(int i = 0; i < line.length(); i++) {
                int value = Character.getNumericValue(line.charAt(i));
                if(value >= 0 && value <= 4)
                    equipment.setPrimaryWeaponsTier(i, value);
                else {
                    fileInvalid = true;
                    break;
                }      
            }
            line = in.nextLine();
            for(int i = 0; i < line.length(); i++) {
                int value = Character.getNumericValue(line.charAt(i));
                if(value >= 0 && value <= 4)
                    equipment.setSecondaryWeaponsTier(i, value);
                else {
                    fileInvalid = true;
                    break;
                }      
            }
            line = in.nextLine();
            for(int i = 0; i < line.length(); i++) {
                int value = Character.getNumericValue(line.charAt(i));
                if(value >= 0 && value <= 4)
                    equipment.setAbilitiesTier(i, value);
                else {
                    fileInvalid = true;
                    break;
                }      
            }
            line = in.nextLine();
            for(int i = 0; i < line.length(); i++) {
                int value = Character.getNumericValue(line.charAt(i));
                if(value >= 0 && value <= 4)
                    equipment.setModulesTier(i, value);
                else {
                    fileInvalid = true;
                    break;
                }      
            }
            
            for(int j = 1; j <= 3; j++) {
                line = in.nextLine();
                if(line.length() == 18) {
                    for(int i = 0; i < line.length() / 2 - 1; i++) {
                        int value = 10 * Character.getNumericValue(line.charAt(2 * i)) + Character.getNumericValue(line.charAt(2 * i + 1));
                        if((i == 0 || i == 1) && value == 0)
                            value = 1;
                        if(value >= 0 && value <= 21) {
                            switch(j) {
                                case 1:
                                    equipment.setTankSlot1(i, value);
                                    break;
                                case 2:
                                    equipment.setTankSlot2(i, value);
                                    break;
                                case 3:
                                    equipment.setTankSlot3(i, value);
                                    break;
                            }
                        }
                        else {
                            fileInvalid = true;
                            break;
                        }      
                    }
                }
                else {
                    fileInvalid = true;
                    break;
                }
            }
            
            if(fileInvalid){
                file.delete();
                try{ 
                  file.createNewFile();  
                }
                catch (IOException exc){
                } 
            }
            
            in.close();
        }
        catch (FileNotFoundException ex) {
            File file = new File(savefilePath);
            try{ 
                file.createNewFile();
                fileInvalid = true;
            }
            catch (IOException exc){
            }
        }
        
        if(fileInvalid) {
            try {
                File file = new File(savefilePath);
                PrintWriter writer = new PrintWriter(file);
                writer.println("0");
                writer.println("10000");
                writer.println("01000000000");
                writer.println("010000000000000000000");
                writer.println("00000000000");
                writer.println("0000000000000000");
                writer.println("000000000000000000000");
                writer.println("010100000000000000");
                writer.println("010100000000000000");
                writer.println("010100000000000000");
                writer.close();
                loadFile(saveSlotSelected);
            }
            catch (FileNotFoundException ex){   
            }
        }
    }
    
    public void saveFile(int savefileID) {
        String savefilePath = "";
        switch(savefileID) {
            case 1:
                savefilePath = "data/savefiles/savefile1.txt";
                break;
            case 2:
                savefilePath = "data/savefiles/savefile2.txt";
                break;
            case 3:
                savefilePath = "data/savefiles/savefile3.txt";
                break;
        }
        
        File file = new File(savefilePath);
        file.delete();  
            
        try {
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file);
            String textline = "";
            textline = Integer.toString(equipment.getLevelCompleted());
            writer.println(textline);
            textline = Integer.toString(equipment.getMoney());
            writer.println(textline);
            textline = "";
            for(int i = 0; i < equipment.getTankBodiesTier().length; i++) {
                textline += Integer.toString(equipment.getTankBodiesTier()[i]);
            }
            writer.println(textline);
            textline = "";
            for(int i = 0; i < equipment.getPrimaryWeaponsTier().length; i++) {
                textline += Integer.toString(equipment.getPrimaryWeaponsTier()[i]);
            }
            writer.println(textline);
            textline = "";
            for(int i = 0; i < equipment.getSecondaryWeaponsTier().length; i++) {
                textline += Integer.toString(equipment.getSecondaryWeaponsTier()[i]);
            }
            writer.println(textline);
            textline = "";
            for(int i = 0; i < equipment.getAbilitiesTier().length; i++) {
                textline += Integer.toString(equipment.getAbilitiesTier()[i]);
            }
            writer.println(textline);
            textline = "";
            for(int i = 0; i < equipment.getModulesTier().length; i++) {
                textline += Integer.toString(equipment.getModulesTier()[i]);
            }
            writer.println(textline);
            
            for(int j = 1; j <= 3; j++) {
                textline = "";
                for(int i = 0; i < 9; i++) {
                    int value = 0;
                    switch(j) {
                        case 1:
                            value = equipment.getTankSlot1()[i];
                            break;
                        case 2:
                            value = equipment.getTankSlot2()[i];
                            break;
                        case 3:
                            value = equipment.getTankSlot3()[i];
                            break;   
                    }
                    if(value <= 9)
                       textline += "0" + Integer.toString(value);
                    else
                       textline += Integer.toString(value); 
                }
                writer.println(textline);
            }
            
            writer.close();
        }
        catch (IOException ex){
        }
    }
    
    public static Background getBg1() {
        return bg1;
    }

    public static Background getBg2() {
        return bg2;
    }

 
    
}