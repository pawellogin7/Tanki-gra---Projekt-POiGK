package tanksgame;


public class Projectile {
    private int x, y, speedX, speedY, distanceX, distanceY, velocity, damage, armor_pen, range;
    private boolean visible;
    private int projectileType;
    private double projectileRotateAngle;
    private Background bg = TanksGame.getBg1();
    
    public Projectile(int startX, int startY, double angle, int vel, int rng, int dmg, int a_pen, int p_type){
        x = startX;
        y = startY;
        velocity = vel;
        range = rng;
        projectileRotateAngle = angle;
        
        speedX = (int) Math.round( 1.0 * velocity * Math.cos(angle) );
        speedY = (int) Math.round( 1.0 * velocity * Math.sin(angle) );
        
        visible = true;
        distanceX = 0;
        distanceY = 0;
        projectileType = p_type;
    }
    
    public void update(){
        x += speedX + bg.getSpeedX();
        y += speedY - bg.getSpeedY();
        distanceX += speedX;
        distanceY += speedY;
        int distance = (int) Math.round(Math.sqrt(1.0 * distanceX*distanceX + 1.0 * distanceY*distanceY));
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

    public int getArmor_pen() {
        return armor_pen;
    }

    public void setArmor_pen(int armor_pen) {
        this.armor_pen = armor_pen;
    }

    public int getProjectileType() {
        return projectileType;
    }

    public void setProjectileType(int projectileType) {
        this.projectileType = projectileType;
    }

    public double getProjectileRotateAngle() {
        return projectileRotateAngle;
    }

    public void setProjectileRotateAngle(double projectileRotateAngle) {
        this.projectileRotateAngle = projectileRotateAngle;
    }
     
    
}
