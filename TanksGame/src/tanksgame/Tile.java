
package tanksgame;

import java.awt.Image;
import java.awt.Rectangle;


public class Tile {

    private int tileX, tileY, speedX, speedY, type;
    private Image tileImage;
    public static Rectangle rTile;

    

    public Tile(int x, int y, int typeInt) {
        tileX = x;
        tileY = y;

        type = typeInt;
        rTile = new Rectangle();
    }

    public void update(int BackgroundSpeedX, int BackgroundSpeedY) {
        speedX = BackgroundSpeedX;
        speedY = -1*BackgroundSpeedY;
        
        tileX += speedX;
        tileY += speedY;
        
        rTile.setBounds(tileX, tileY, 250, 250);
        if (rTile.intersects(Player.yellowRed) && type != 0){
            checkCollision(Player.rect,Player.rect1,Player.rect2,Player.rect3);
        }
    }

    public int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    public Image getTileImage() {
        return tileImage;
    }

    public void setTileImage(Image tileImage) {
        this.tileImage = tileImage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    public  void checkCollision(Rectangle rplayer, Rectangle rplayer1, Rectangle rplayer2, Rectangle rplayer3){
    	if (rplayer.intersects(rTile)){
            
            System.out.println("kolizja");
        }
        if (rplayer1.intersects(rTile)){
            
            System.out.println("kolizja1");
        }
        if (rplayer2.intersects(rTile)){
            
            System.out.println("kolizja2");
        }
        if (rplayer3.intersects(rTile)){
            
            System.out.println("kolizja3");
        }
    }

}
