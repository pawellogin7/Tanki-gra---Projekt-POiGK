package tanksgame;

import java.util.ArrayList;


public class Enemy {
   private int centerX, centerY;
   private double bodyRotationAngle, turretRotationAngle;
   private int maxHealth, currentHealth, damage, speedX, speedY;
   
   private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
    
   Enemy() {
       bodyRotationAngle = 0;
       turretRotationAngle = 0;
   }
   
   public void update(int bgSpdX, int bgSpdY) {
    speedX = bgSpdX;
    centerX += speedX;
    speedY = -bgSpdY;
    centerY += speedY;
   }

   public void die() {
   }
   public void attack() {
   }

   
    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public double getBodyRotationAngle() {
        return bodyRotationAngle;
    }

    public void setBodyRotationAngle(double bodyRotationAngle) {
        this.bodyRotationAngle = bodyRotationAngle;
    }

    public double getTurretRotationAngle() {
        return turretRotationAngle;
    }

    public void setTurretRotationAngle(double turretRotationAngle) {
        this.turretRotationAngle = turretRotationAngle;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(ArrayList<Projectile> projectiles) {
        this.projectiles = projectiles;
    }

    
   
   
}
