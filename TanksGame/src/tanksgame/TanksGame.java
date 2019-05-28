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

    private Player player;
    private EnemyTank t1, t2;
    private Weapon machineGun, shotGun, sniperGun, doubleGun, flameGun, laserGun;
    private Ability speedBoost, sprint, reloadBoost;
    private Image image, character, background, cursor, enemyTank, playerBullet, projectileFire, projectileLaser;
    private Graphics second;
    private URL base;
    private static Background bg1, bg2;
    private int mouseX = 0;
    private int mouseY = 0;
    boolean mouseDown = false;
    boolean mouseMoving = false;
    private int cusorWidth = 16;
    private int cursorHeight = 16;
    
    
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
        
        //Weapon creation
        machineGun = new Weapon(50, 0.1, 85, 5, 800, 12, 1);
        shotGun = new Weapon(15, 0.7, 70, 5, 600, 10, 7);
        sniperGun = new Weapon(200, 1, 95, 50, 1200, 18, 1);
        doubleGun = new Weapon(70, 0.15, 90, 10, 350, 20, 2);
        flameGun = new Weapon(10, 0.1, 30, 5, 200, 20, 16);
        flameGun.setProjectileType(3);
        laserGun = new Weapon(10, 0.017, 100, 50, 800, 15, 1);
        laserGun.setProjectileType(2);
        
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
    }

    @Override
    public void start() {
        bg1 = new Background(0, 0);
        bg2 = new Background(2160, 0); 
        
        player = new Player();
        player.setWeapon(machineGun);
        player.setAbility(reloadBoost);
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
        // TODO Auto-generated method stub
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
            
            if(mouseDown)
                player.shoot(mouseX + cusorWidth / 2, mouseY + cursorHeight / 2);
                
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
        g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
        g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
        
        g.drawImage(enemyTank, t1.getCenterX() - 64, t1.getCenterY() - 32, this);
        g.drawImage(enemyTank, t2.getCenterX() - 64, t2.getCenterY() - 32, this);
        g.drawImage(character, player.getCenterX() - 64, player.getCenterY() - 32, this);
        
        int hudAbilityX = 20;
        int hudAbilityY = 890;
        int hudAbilitySize = 2;
        int abilityPercent = player.getAbility().getPercent();
        drawHud(hudAbilityX, hudAbilityY, hudAbilitySize, abilityPercent, g);
        
        ArrayList projectiles = player.getProjectiles();
	for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = (Projectile) projectiles.get(i);
            drawProjectile(p, g);
        }
        //g.setFont(new Font("Arial",Font.BOLD,20));
        //g.drawString("Mouse x: " + mouseX, 5, 140);
        //g.drawString("Mouse y: "+ mouseY, 5, 170);       
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
    
    public void keyTyped(KeyEvent e) {
        
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                player.moveUp();
                player.setMovingUp(true);;
            break;

            case KeyEvent.VK_S:
                player.moveDown();
                player.setMovingDown(true);
            break;

            case KeyEvent.VK_A:
                player.moveLeft();
                player.setMovingLeft(true);
            break;

            case KeyEvent.VK_D:
                player.moveRight();
                player.setMovingRight(true);
            break;
            
            case KeyEvent.VK_1:
                player.setWeapon(machineGun);
            break;
            
            case KeyEvent.VK_2:
                player.setWeapon(shotGun);
            break;
            
            case KeyEvent.VK_3:
                player.setWeapon(sniperGun);
            break;
            
            case KeyEvent.VK_4:
                player.setWeapon(doubleGun);
            break;
            
            case KeyEvent.VK_5:
                player.setWeapon(flameGun);
            break;
            
            case KeyEvent.VK_6:
                player.setWeapon(laserGun);
            break;

            case KeyEvent.VK_SPACE:
                player.useAbility();
            break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                player.stopUp();
            break;

            case KeyEvent.VK_S:
                player.stopDown();
            break;

            case KeyEvent.VK_A:
                player.stopLeft();
            break;

            case KeyEvent.VK_D:
                player.stopRight();
            break;

            case KeyEvent.VK_SPACE:
            break;
        }
    }
    
    public void mousePressed(MouseEvent e) {
        mouseDown = true;
    }
    public void mouseReleased(MouseEvent e) {
        mouseDown = false;
    }
    
    public void mouseEntered(MouseEvent e){
        mouseX = e.getX();
        mouseY = e.getY();
    }
    
    public void mouseClicked(MouseEvent e){}
    
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
    
    public void mouseExited(MouseEvent e){}
    
    public void mouseDragged(MouseEvent e){
        mouseX = e.getX();
        mouseY = e.getY();
        mouseDown = true;
    }
    
    
    public static Background getBg1() {
        return bg1;
    }

    public static Background getBg2() {
        return bg2;
    }
  
    
}