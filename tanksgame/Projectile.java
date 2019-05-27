package tanksgame;


public class Projectile {
    private int x, y, speedX, speedY, distanceX, distanceY, velocity, damage, armor_pen, range;
    boolean visible;
    private Background bg = TanksGame.getBg1();
    
    public Projectile(int startX, int startY, double angle, int vel, int rng){
        x = startX;
        y = startY;
        velocity = vel;
        range = rng;
        
        speedX = (int) Math.round( velocity * Math.cos(angle) );
        speedY = (int) Math.round( velocity * Math.sin(angle) );
        
        visible = true;
        distanceX = 0;
        distanceY = 0;
    }
    
    public void update(){
        x += -speedX + bg.getSpeedX();
        y += -speedY - bg.getSpeedY();
        distanceX += speedX;
        distanceY += speedY;
        int distance = (int) Math.round(Math.sqrt(distanceX*distanceX + distanceY*distanceY));
        if (distance > range) {
            visible = false;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    
    
}
