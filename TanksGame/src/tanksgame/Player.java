package tanksgame;

import java.awt.Graphics;
import java.util.ArrayList;
/**
 *
 * @author Paweł
 */
public class Player {
        //In Java, Class Variables should be private so that only its methods can change them.
	private int centerX = 700;
	private int centerY = 500;  
	private int speedX = 0;
	private int speedY = 0;
        
        private int baseMaxHP, maxHP, HP;
        private int baseRegen, regen;
        private int baseArmor, armor;
        private int baseSpeed, speed;
        
        
        private boolean movingLeft = false;
        private boolean movingRight = false;
        private boolean movingUp = false;
        private boolean movingDown = false;

        private static Background bg1 = TanksGame.getBg1();                 
        private static Background bg2 = TanksGame.getBg2();
        private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
        private Weapon weapon = new Weapon(1, 1, 1, 1, 1, 1, 0);
        private StatusEffects status = new StatusEffects();
        private Ability ability = new Ability(0, 0);
        
        Player() {
            baseMaxHP = 500;
            maxHP = baseMaxHP;
            HP = baseMaxHP;
            
            baseRegen = 20;
            regen = baseRegen;
            
            baseArmor = 20;
            armor = baseRegen;
            
            baseSpeed = 5;
            speed = baseSpeed;
        }
        
	public void update() {
            weapon.update();
            status.update();
            ability.update();
            updateStatusEffects();
            updateSpeed();
            
            // Moves Character or Scrolls Background accordingly.
            bg1.setSpeedX(speedX);
            bg2.setSpeedX(speedX);
            bg1.setSpeedY(speedY);
            bg2.setSpeedY(speedY);
            
	}

        public void shoot(double mouseX, double mouseY) {
            if(weapon.getCooldown() == 0 && !status.isWeaponJammed())
            {
                int gunX = centerX + 53;
                int gunY = centerY - 9;
                double deltaX = gunX - mouseX;
                double deltaY = gunY - mouseY;
                
                if(weapon.getBullet_number() == 0) {
                }
                else if(weapon.getBullet_number() == 1)
                     projectiles.add(weapon.getProjectile(gunX, gunY, deltaX, deltaY));
                else {
                    for(int i = 1; i <= weapon.getBullet_number(); i++)
                        projectiles.add(weapon.getProjectileShotgun(gunX, gunY, deltaX, deltaY, i));
                }
            }
        }
        
        public void useAbility() {
            if(ability.getCooldown() == 0 && ability.getDuration() == 0)
                status.changeStatus(ability.effects());
            else if(ability.getDuration() != 0) {
                status.resetStatus(ability.effects());
                ability.setDuration(0);
            }
        }
        
        public void updateStatusEffects() {
            //Speed
            if(status.isParalyzed())
                speed = 0;
            else if(status.isSlowed())
                speed = 2;
            else if(status.isSprint())
                speed = baseSpeed * 2;
            else if(status.isSpeedy())
                speed = baseSpeed + 3;
            else
                speed = baseSpeed;            
            //Armor
            if(status.isArmorBoost())
                armor = (int) Math.round(baseArmor * 1.25);
            else
                armor = baseArmor;
            
            if(status.isArmorBroken())
                armor = (int) Math.round(baseArmor * 0.5);
            else if(status.isFortified())
                armor = (int) Math.round(baseArmor * 1.5);
            else
                armor = armor;
            //HP Regen
            if(status.isRegenReduction())
                regen = 0;
            else if(status.isRegenBoost())
                regen = baseRegen * 3;
            else
                regen = baseRegen;
            
            if(status.isSabotage())
                regen = -regen;
            else
                regen = regen;
            //Damage
            if(status.isDamageReduction())
                weapon.setDamage((int) Math.round(0.7 * weapon.getBaseDamage()));
            else if(status.isDamageBoost())
                weapon.setDamage((int) Math.round(1.2 * weapon.getBaseDamage()));
            else
                weapon.setDamage(weapon.getBaseDamage());
            //Reload
            if(status.isWeaponJammed()){
            }
            else if(status.isReloadReduction())
                weapon.setReload(2.0 * weapon.getBaseReload());
            else if(status.isReloadBoost())
                weapon.setReload(0.7 * weapon.getBaseReload());
            else
                weapon.setReload(weapon.getBaseReload());
            
        }
        
        public void updateSpeed() {
            if(!movingRight && !movingLeft)
                speedX = 0;
            else if(movingLeft && (movingUp || movingDown) )
                speedX = (int) Math.ceil(speed / 1.4);
            else if(movingRight && (movingUp || movingDown) )
                speedX = (int) -Math.ceil(speed / 1.4);
            else if(movingLeft )
                speedX = speed;
            else if(movingRight )
                speedX = -speed;
            
            if(!movingUp && !movingDown)
                speedY = 0;
            else if(movingDown && (movingRight || movingLeft) )
                speedY = (int) Math.ceil(speed / 1.4);
            else if(movingUp && (movingRight || movingLeft) )
                speedY = (int) -Math.ceil(speed / 1.4);
            else if(movingDown )
                speedY = speed;
            else if(movingUp )
                speedY = -speed;
        }
        
	public void moveRight() {
            setMovingRight(true);
            setMovingLeft(false);
	}

	public void moveLeft() {
            setMovingLeft(true);
            setMovingRight(false);
	}
        
        public void moveUp() {
            setMovingUp(true);
            setMovingDown(false);
	}
        
        public void moveDown() {
            setMovingDown(true);
            setMovingUp(false);
	}

        public void stopRight() {
            setMovingRight(false);
        }

        public void stopLeft() {
            setMovingLeft(false);
        }
        
        public void stopUp() {
            setMovingUp(false);
        }

        public void stopDown() {
            setMovingDown(false);
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

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon playerWeapon) {
        this.weapon = playerWeapon;
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }
    
    
    
} 
