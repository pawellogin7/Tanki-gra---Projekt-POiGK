package tanksgame;


public class Enemy {
   private int maxHealth, currentHealth, power, speedX, centerX, centerY;
   private Background bg = TanksGame.getBg1();
    
   public void update() {
    centerX += speedX;
    speedX = bg.getSpeedX();
   }

   public void die() {
   }
   public void attack() {
   }
    
}
