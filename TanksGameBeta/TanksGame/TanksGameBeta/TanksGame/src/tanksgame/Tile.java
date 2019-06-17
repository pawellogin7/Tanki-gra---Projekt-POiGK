
package tanksgame;

import java.awt.Image;


public class Tile {

    private int tileX, tileY, speedX, speedY, type;
    private Image tileImage;

    
    //Tiles
    public Tile(int x, int y, int typeInt) {
        tileX = x;
        tileY = y;

        type = typeInt;
    }

    public void update(int BackgroundSpeedX, int BackgroundSpeedY) {
        speedX = BackgroundSpeedX;
        speedY = -1*BackgroundSpeedY;

        tileX += speedX;
        tileY += speedY;
    }

    //Getters and setters
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
    
    

}
