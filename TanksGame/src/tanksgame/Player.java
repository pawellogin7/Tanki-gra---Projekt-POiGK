package tanksgame;

import java.awt.Graphics;
import java.util.ArrayList;
/**
 *
 * @author Paweł
 */
public class Player {
        //In Java, Class Variables should be private so that only its methods can change them.
	private int centerX = 200;
	private int centerY = 200;
	private int speedX = 0;
	private int speedY = 0;
        
        private boolean movingLeft = false;
        private boolean movingRight = false;
        private boolean movingUp = false;
        private boolean movingDown = false;

        private static Background bg1 = TanksGame.getBg1();                 
        private static Background bg2 = TanksGame.getBg2();
        private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
        
	public void update() {

		// Moves Character or Scrolls Background accordingly.
                if (speedX == 0) {
                    bg1.setSpeedX(0);
                    bg2.setSpeedX(0);
                }
                else {
                    bg1.setSpeedX(speedX);
                    bg2.setSpeedX(speedX);
                }
                
                if (speedY == 0) {
                    bg1.setSpeedY(0);
                    bg2.setSpeedY(0);
                }
                else {
                    bg1.setSpeedY(speedY);
                    bg2.setSpeedY(speedY);
                }                
	}

        public void shoot(double mouseX, double mouseY) {
            int gunX = centerX + 50;
            int gunY = centerY - 25;
            double deltaX = gunX - mouseX;
            double deltaY = gunY - mouseY;
            double angle = Math.atan2(deltaY, deltaX);
                       
            Projectile p = new Projectile(gunX, gunY, angle);
            projectiles.add(p);
        }
        
	public void moveRight() {
		speedX = -6;
	}

	public void moveLeft() {
		speedX = 6;
	}
        
        public void moveUp() {
		speedY = -6;
	}
        
        public void moveDown() {
		speedY = 6;
	}

        public void stopRight() {
            setMovingRight(false);
            stop();
        }

        public void stopLeft() {
            setMovingLeft(false);
            stop();
        }
        
        public void stopUp() {
            setMovingRight(false);
            stop();
        }

        public void stopDown() {
            setMovingLeft(false);
            stop();
        }
        
        private void stop() {
            if (isMovingRight() == false && isMovingLeft() == false) {
                speedX = 0;
            }

            if (isMovingRight() == false && isMovingLeft() == true) {
                moveLeft();
            }

            if (isMovingRight() == true && isMovingLeft() == false) {
                moveRight();
            }
            
            
            if (isMovingUp() == false && isMovingDown() == false) {
                speedY = 0;
            }

            if (isMovingUp() == false && isMovingDown() == true) {
                moveDown();
            }

            if (isMovingUp() == true && isMovingDown() == false) {
                moveUp();
            }
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

    
    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public boolean isMovingUp() {
        return movingUp;
    }

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    public boolean isMovingDown() {
        return movingDown;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }
    
    public ArrayList getProjectiles() {
	return projectiles;
    } 
        
} 
