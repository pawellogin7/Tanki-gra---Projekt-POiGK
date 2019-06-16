package tanksgame;

import java.awt.Rectangle;


public class Projectile {
    private int x, y, speedX, speedY, distanceX, distanceY; 
    private int velocity, damage, armorPen, range;
    private boolean visible;
    private int projectileType, team;
    private double projectileAngle;
    StatusEffects status = new StatusEffects();

    
    //For multiplayer only
    private boolean newProjectile;
    
    
    public Projectile(int startX, int startY, double angle, int vel, int rng, int dmg, int a_pen, int p_type, int whichTeam){
        x = startX;
        y = startY;
        velocity = vel;
        range = rng;
        damage = dmg;
        armorPen = a_pen;
        projectileAngle = angle;
        team = whichTeam;
        
        
        speedX = (int) Math.round( 1.0 * velocity * Math.cos(angle) );
        speedY = (int) Math.round( 1.0 * velocity * Math.sin(angle) );
        
        visible = true;
        distanceX = 0;
        distanceY = 0;
        projectileType = p_type;
        
        newProjectile = true;
    }
    
    public void update(int bgSpdX, int bgSpdY){
        x += speedX + bgSpdX;
        y += speedY - bgSpdY;
        distanceX += speedX;
        distanceY += speedY;
        
        int distance = (int) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
        if(distance >= range)
            visible = false;
    }
    
    public boolean checkEnemyBulletCollision(Enemy enemy) {
        if(team != 2 && visible){
            visible = false;
            return true;
        }
        return false;
    }
    
    public boolean checkPlayerBulletCollision(Player player) {
        if(team != 1 && visible){
            visible = false;
            return true;
        }
        return false;
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

    public int getArmorPen() {
        return armorPen;
    }

    public void setArmorPen(int armorPen) {
        this.armorPen = armorPen;
    }

    public int getProjectileType() {
        return projectileType;
    }

    public void setProjectileType(int projectileType) {
        this.projectileType = projectileType;
    }

    public boolean isNewProjectile() {
        return newProjectile;
    }

    public void setNewProjectile(boolean newProjectile) {
        this.newProjectile = newProjectile;
    }

    public double getProjectileAngle() {
        return projectileAngle;
    }

    public void setProjectileAngle(double projectileAngle) {
        this.projectileAngle = projectileAngle;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public StatusEffects getStatus() {
        return status;
    }

    public void setStatus(StatusEffects status) {
        this.status = status;
    }

     
    
    
}
