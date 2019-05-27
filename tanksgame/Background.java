package tanksgame;

public class Background {
    private int bgX, bgY, speedX, speedY;
    
    public Background (int x, int y){
       bgX = x;
       bgY = y;
       speedX = 0;
       speedY = 0;
    }
    
    public void update() {
        bgX += speedX;
        bgY -= speedY;

        if (bgX <= -2160){
            bgX += 4320;
        }
    }

    public int getBgX() {
        return bgX;
    }

    public void setBgX(int bgX) {
        this.bgX = bgX;
    }

    public int getBgY() {
        return bgY;
    }

    public void setBgY(int bgY) {
        this.bgY = bgY;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }
    
    
    
}