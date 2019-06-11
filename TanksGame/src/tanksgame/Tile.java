/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tanksgame;

import java.awt.Image;

/**
 *
 * @author raffi
 */
public class Tile {

    private int tileX, tileY, speedX, speedY, type;
    public Image tileImage;

    

    public Tile(int x, int y, int typeInt) {
        tileX = x * 250;
        tileY = y * 250;

        type = typeInt;

        if (type == 1) {
            tileImage = TanksGame.wall;
        } else if (type == 2) {

            tileImage = TanksGame.container;
        }

    }

    public void update(int BackgroundSpeedX, int BackgroundSpeedY) {
        speedX = BackgroundSpeedX;
        speedY = -1*BackgroundSpeedY;

        tileX += speedX;
        tileY += speedY;
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

}
