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
import java.util.Scanner;

public class TanksGame extends Applet implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private MenuWindow menu;
    private ArmoryWindow armory;
    private LevelSelectionWindow levelSelection;
    private LevelWindow levelWindow;
    private SaveSelectionWindow saveSelection;
    private Weapon slowingShot, paralyzeShot, jamShot, stunShot, armorBreakShot;
    private Ability speedBoost, sprint, reloadBoost, regenBoost;
    private Image image, turret, character, background, cursor, enemyTank, playerBullet, projectileFire, projectileLaser;
    private Graphics second;
    private URL base;
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
    private int levelSelected = 1;
    
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
        background = getImage(base, "../data/pictures/background.png");
	character = getImage(base, "../data/pictures/playerTank.png");
        turret = getImage(base, "../data/pictures/tankTurret.png");
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
            case 0: { //Main menu window
                if(currentWindowChanged) {
                    mouse1Down = false;
                    saveFile(saveSlotSelected);
                    equipment.update();
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
            case 1: { //Armory window
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
            case 2: { //Save slot selection window
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
            case 3: { //Level selection window
                if(currentWindowChanged) {
                    levelSelection = new LevelSelectionWindow();
                    levelSelection.setLevelCompleted(equipment.getLevelCompleted());
                    currentWindowChanged = false;
                }
                levelSelection.update(g, mouseX, mouseY, mouse1Down);
                switch(levelSelection.getButtonClicked()) {
                    case 0:
                        break;
                    default:
                        currentWindow = 11;
                        currentWindowChanged = true;
                        levelSelected = levelSelection.getButtonClicked();
                        break;
                }
                if(escKey) {
                    currentWindow = 0;
                    escKey = false;
                    currentWindowChanged = true;
                }
                break;
            }
            case 11: { //Level creation window
                if(currentWindowChanged) {
                    currentWindowChanged = false;
                    levelWindow = new LevelWindow(levelSelected, equipment);
                    loadLevelPictures();
                }
                
                if(escKey) {
                    levelWindow.escPause();
                    escKey = false;
                }
                levelWindow.update(g, mouseX, mouseY, mouse1Down, mouse2Down);
                
                switch(levelWindow.getButtonClicked()) {
                    case 0:
                        break;
                    case 1:
                        currentWindow = 0;
                        currentWindowChanged = true;
                }
                   
                break;
            }
                    
        }
        
    }
    
    public void loadLevelPictures() {
        levelWindow.setBackground(background);
        levelWindow.setCharacter(character);
        levelWindow.setTurret(turret);
        levelWindow.setEnemyTank(enemyTank);
        levelWindow.setPlayerBullet(playerBullet);
        levelWindow.setProjectileFire(projectileFire);
        levelWindow.setProjectileLaser(projectileLaser);
    }
    
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                if(currentWindow == 11 && !levelWindow.isPaused())
                    levelWindow.getPlayer().moveUp();
            break;

            case KeyEvent.VK_S:
                if(currentWindow == 11 && !levelWindow.isPaused())
                    levelWindow.getPlayer().moveDown();
            break;

            case KeyEvent.VK_A:
                if(currentWindow == 11 && !levelWindow.isPaused())
                    levelWindow.getPlayer().moveLeft();
            break;

            case KeyEvent.VK_D:
                if(currentWindow == 11 && !levelWindow.isPaused())
                    levelWindow.getPlayer().moveRight();
            break;
            
            case KeyEvent.VK_1:
                if(currentWindow == 11 && !levelWindow.isPaused())
                    levelWindow.getPlayer().changeWeapon(1);
            break;
            
            case KeyEvent.VK_2:
                if(currentWindow == 11 && !levelWindow.isPaused())
                    levelWindow.getPlayer().changeWeapon(2);
            break;
            
            case KeyEvent.VK_3:
                if(currentWindow == 11 && !levelWindow.isPaused())
                    levelWindow.getPlayer().changeWeapon(3);
            break;
            
            case KeyEvent.VK_SPACE:
                if(currentWindow == 11 && !levelWindow.isPaused())
                    levelWindow.getPlayer().useAbility();
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
                if(currentWindow == 11 && !levelWindow.isPaused())
                    levelWindow.getPlayer().setMovingUp(false);
            break;

            case KeyEvent.VK_S:
                if(currentWindow == 11 && !levelWindow.isPaused())
                    levelWindow.getPlayer().setMovingDown(false);
            break;

            case KeyEvent.VK_A:
                if(currentWindow == 11 && !levelWindow.isPaused())
                    levelWindow.getPlayer().setMovingLeft(false);
            break;

            case KeyEvent.VK_D:
                if(currentWindow == 11 && !levelWindow.isPaused())
                    levelWindow.getPlayer().setMovingRight(false);
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

    
    
}