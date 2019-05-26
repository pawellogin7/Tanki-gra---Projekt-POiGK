package tanksgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
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
    private Image image, character, background, cursor;
    private Graphics second;
    private URL base;
    private static Background bg1, bg2;
    private int mouseX = 0;
    private int mouseY = 0;
    boolean mouseDown = false;
    
    @Override
    public void init() {
        setSize(800, 480);
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

	// Image Setups        
	character = getImage(base, "../data/character.png");
        background = getImage(base, "../data/background.png");
        cursor = getImage(base, "../data/cursor.png");
    }

    @Override
    public void start() {
        bg1 = new Background(0, 0);
        bg2 = new Background(2160, 0); 
        
        player = new Player(); 
        
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
                player.shoot(mouseX, mouseY);
                       
            bg1.update();
            bg2.update();
            player.update();
            
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
        
        ArrayList projectiles = player.getProjectiles();
	for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = (Projectile) projectiles.get(i);
            g.setColor(Color.YELLOW);
            g.fillRect(p.getX(), p.getY(), 5, 5);
        }
        
        g.drawImage(character, player.getCenterX() - 61, player.getCenterY() - 63, this);
        
       
        g.setFont(new Font("Arial",Font.BOLD,20));
        g.drawString("Wsp x: " + player.getCenterX(), 5, 50);
        g.drawString("Wsp y: "+ player.getCenterY(), 5, 80);
        g.drawString("Mouse x: " + mouseX, 5, 140);
        g.drawString("Mouse y: "+ mouseY, 5, 170);
        
    }

    public void keyTyped(KeyEvent e) {
        
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                player.moveUp();
                player.setMovingUp(true);;
            break;

            case KeyEvent.VK_DOWN:
                player.moveDown();
                player.setMovingDown(true);
            break;

            case KeyEvent.VK_LEFT:
                player.moveLeft();
                player.setMovingLeft(true);
            break;

            case KeyEvent.VK_RIGHT:
                player.moveRight();
                player.setMovingRight(true);
            break;

            case KeyEvent.VK_SPACE:
            break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                player.stopUp();
            break;

            case KeyEvent.VK_DOWN:
                player.stopDown();
            break;

            case KeyEvent.VK_LEFT:
                player.stopLeft();
            break;

            case KeyEvent.VK_RIGHT:
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
    
    public void mouseDragged(MouseEvent e){}
    
    
    public static Background getBg1() {
        return bg1;
    }

    public static Background getBg2() {
        return bg2;
    }
  
    
}