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
import java.net.URL;
import java.util.ArrayList;

public class TanksGame extends Applet implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private MenuWindow menu;
    private ArmoryWindow armory;
    private Player player;
    private EnemyTank t1, t2;
    private Weapon machineGun, shotGun, sniperGun, doubleGun, flameGun, laserGun;
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
    
    private Equipment equipment;
    private int[] tankBodies = {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int[] primaryWeapons = {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int[] secondaryWeapons = {0, 1, 0, 0, 0, 0, 0, 0, 0};
    private int[] abilities = {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int[] modules = {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int[] playerEquipped = {1, 1, 0, 0, 1, 1, 1, 0, 0};
    
    private Weapon Demise;
    
    @Override
    public void init() {
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
	character = getImage(base, "../data/playerTank.png");
        background = getImage(base, "../data/background.png");
        cursor = getImage(base, "../data/cursor.png");
        enemyTank = getImage(base, "../data/enemyTank.png");
        playerBullet = getImage(base, "../data/playerBullet.png");
        projectileFire = getImage(base, "../data/projectileFire.png");
        projectileLaser = getImage(base, "../data/projectileLaser.png");
        
        
        menu = new MenuWindow();
        armory = new ArmoryWindow();
        equipment = new Equipment();
        for(int i = 0; i < tankBodies.length; i++) {
            equipment.setTankBodiesTier(i, tankBodies[i]);
        }
        for(int i = 0; i < primaryWeapons.length; i++) {
            equipment.setPrimaryWeaponsTier(i, primaryWeapons[i]);
        }
        for(int i = 0; i < secondaryWeapons.length; i++) {
            equipment.setSecondaryWeaponsTier(i, secondaryWeapons[i]);
        }
        for(int i = 0; i < abilities.length; i++) {
            equipment.setAbilitiesTier(i, abilities[i]);
        }
        for(int i = 0; i < modules.length; i++) {
            equipment.setModulesTier(i, modules[i]);
        }
        equipment.setBodyID(playerEquipped[0]);
        equipment.setPrimary1ID(playerEquipped[1]);
        equipment.setPrimary2ID(playerEquipped[3]);
        equipment.setPrimary2ID(playerEquipped[3]);
        equipment.setSecondaryID(playerEquipped[4]);
        equipment.setAbilityID(playerEquipped[5]);
        equipment.setModule1ID(playerEquipped[6]);
        equipment.setModule1ID(playerEquipped[7]);
        equipment.setModule1ID(playerEquipped[8]);
        
        
        //Weapon creation
        machineGun = new Weapon(50, 0.1, 85, 5, 800, 12, 1);
        shotGun = new Weapon(15, 0.7, 70, 5, 600, 10, 7);
        sniperGun = new Weapon(200, 1, 95, 50, 1200, 18, 1);
        doubleGun = new Weapon(70, 0.15, 90, 10, 350, 20, 2);
        flameGun = new Weapon(10, 0.1, 30, 5, 200, 20, 16);
        flameGun.setProjectileType(3);
        laserGun = new Weapon(10, 0.017, 100, 50, 800, 15, 1);
        laserGun.setProjectileType(2);
        Demise = new Weapon(100, 0.051, 25, 100, 1200, 20, 20);
        //Secondaryweapon creation
        slowingShot = new Weapon(10, 10, 70, 0, 800, 14, 5);
        slowingShot.getStatus().setSlowed(true);
        slowingShot.getStatus().setSlowedDuration(5);
        
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
        speedBoost = new Ability(5, 10);
        speedBoost.getStatus().setSpeedy(true);
        speedBoost.getStatus().setSpeedyDuration(speedBoost.getBaseDuration());
        
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
        bg2 = new Background(2160, 0); 
        
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
                    menu = new MenuWindow();
                    currentWindowChanged = false;
                }
                menu.update(g, mouseX, mouseY, mouse1Down);
                switch(menu.getButtonClicked()) {
                    case 0:
                        break;
                    case 1:
                        currentWindow = 11;
                        break;
                    case 2:
                        currentWindow = 1;
                        break;
                    case 3:
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
                armory.update(g, mouseX, mouseY, mouse1Down, !mouse1Down, equipment);
                equipment = armory.getEq();
                player = new Player(equipment);
                
                switch(armory.getButtonClicked()) {
                    case 0:
                        break;
                    case 1:
                        currentWindow = 0;
                        break;
                }
                if(escKey) {
                    currentWindow = 0;
                    escKey = false;
                }
                break;
            }
            
            case 11: {
                drawLevel1(g);
                if(escKey) {
                    currentWindow = 0;
                    escKey = false;
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
        //g.setFont(new Font("Arial",Font.BOLD,20));
        //g.drawString("Mouse x: " + mouseX, 5, 140);
        //g.drawString("Mouse y: "+ mouseY, 5, 170);
        
        int hudX = 320;
        int hudY = 890;
        int hudSize = 2;
        int percent = player.getAbility().getPercent();
        drawHud(hudX, hudY, hudSize, percent, g); //ability hud
        
        hudX = 380;
        percent = player.getSecondaryWeapon().getPercent();
        drawHud(hudX, hudY, hudSize, percent, g); //secondary weapon hud
        
        hudX = 440;
        percent = player.getPrimaryWeapon().getPercent();
        drawHud(hudX, hudY, hudSize, percent, g); //primary weapon hud
        
        drawPlayerHP(500, 908, 400, 20, g);
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

    public void drawHud(int hudX, int hudY, int hudSize, int hudPercent, Graphics g) {
        int size = 26 * hudSize;
        int lineSize = 2 * hudSize;
        int startPoint = hudX + size / 2;
        int pointxLeft = hudX;
        int pointxRight = hudX + 26 * hudSize - lineSize;
        int pointyUp = hudY + hudSize;
        int pointyDown = hudY + 26 * hudSize - lineSize / 2;
        
        g.setColor(Color.gray);
        g.fillRect(pointxLeft, pointyUp, size, size);
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
        g.fillRect(startX - 3, startY - 3, width + 6, heigth + 6);
        g.setColor(Color.red);
        g.fillRect(startX, startY, width, heigth);
        g.setColor(Color.green);
        g.fillRect(startX, startY, width * player.getHpPercent() / 100, heigth);
    }
    
    public void keyTyped(KeyEvent e) {
        
    }

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
    
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == e.BUTTON1) {
            mouse1Down = true;
        }
        else if(e.getButton() == e.BUTTON3) {
            mouse2Down = true;
        }
    }
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == e.BUTTON1) {
            mouse1Down = false;
        }
        else if(e.getButton() == e.BUTTON3) {
            mouse2Down = false;
        }
    }
    
    public void mouseEntered(MouseEvent e){
        mouseX = e.getX() + 16;
        mouseY = e.getY() + 16;
    }
    
    public void mouseClicked(MouseEvent e){}
    
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX() + 16;
        mouseY = e.getY() + 16;
    }
    
    public void mouseExited(MouseEvent e){}
    
    public void mouseDragged(MouseEvent e){
        mouseX = e.getX() + 16;
        mouseY = e.getY() + 16;
        if(e.getButton() == e.BUTTON1) {
            mouse1Down = true;
        }
        else if(e.getButton() == e.BUTTON3) {
            mouse2Down = true;
        }
    }
    
    
    public static Background getBg1() {
        return bg1;
    }

    public static Background getBg2() {
        return bg2;
    }

 
    
}