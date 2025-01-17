package tanksgame;


public class Enemy {
   private int maxHealth, currentHealth, damage, speedX, speedY, centerX, centerY;
   private Background bg = TanksGame.getBg1();
    
   public void update() {
    centerX += speedX;
    speedX = bg.getSpeedX();
    centerY += speedY;
    speedY = -bg.getSpeedY();
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

    public Background getBg() {
        return bg;
    }

    public void setBg(Background bg) {
        this.bg = bg;
    }
    
   
   
}
