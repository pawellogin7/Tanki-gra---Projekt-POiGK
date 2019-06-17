package tanksgame;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;


public class Enemy {
    private int centerX, centerY, speedX, speedY;
    private double bodyRotationAngle, turretRotationAngle;
    
    private int baseMaxHP, maxHP, HP;
    private int baseRegen, regen;
    private int baseArmor, armor;
    private int baseSpeed, speed;
    private double regenCooldown;
    
    
    
    private Weapon weapon;
    private double trackingModeChance, modeChange, modeChangeCooldown;
    private boolean trackingMode, dead;
   
    private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
    private StatusEffects status = new StatusEffects();
    
    Enemy(int x, int y, TankBody body, Weapon weapon1) {
        centerX = x;
        centerY = y;
        bodyRotationAngle = 0;
        turretRotationAngle = 0;
        weapon = weapon1;
        
        dead = false;
        trackingMode = false;
        trackingModeChance = 0.5;
        modeChange = 0.0;
        modeChangeCooldown = 3.0;
        
        baseMaxHP = body.getMaxHp();
        if(baseMaxHP < 1)
            baseMaxHP = 1;
        maxHP = baseMaxHP;
        HP = maxHP;
            
        baseRegen = body.getRegen();
        if(baseRegen < 0)
            baseRegen = 0;
        regen = baseRegen;
        regenCooldown = 0.0;
            
        baseSpeed = body.getMoveSpeed();
        if(baseSpeed < 2)
            baseSpeed = 2;    
        speed = baseSpeed;
            
        baseArmor = body.getArmor();
        if(baseArmor < 0)
            baseArmor = 0;
        armor = baseArmor;
    }
   
    //update methods
    public void update(int bgSpdX, int bgSpdY, int playerX, int playerY) {
        speedX = bgSpdX;
        centerX += speedX;
        speedY = -bgSpdY;
        centerY += speedY;
        
        updateRegen();
        updateStatusEffects();
        updateShootingMode();
        weapon.update();
        aim(playerX, playerY);
    }
    
    //Changing shooting mode
    private void updateShootingMode() {
        if(modeChange >= modeChangeCooldown) {
            Random generator = new Random();
            double losuj = generator.nextDouble();
            if(losuj <= trackingModeChance)
                trackingMode = true;
            else
                trackingMode = false;
            modeChange = 0;
        }
        else
            modeChange += 0.017;
    }
    
    //Enemy aims turret at player
    private void aim(int playerCenterX, int playerCenterY) {
        int deltaX = playerCenterX - centerX;
        int deltaY = playerCenterY - centerY;
        double distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2) );
        if(trackingMode) {
            double time = distance / weapon.getBullet_velocity();
            deltaX -= (int) (speedX * time * 0.8);
            deltaY -= (int) (speedY * time * 0.8);
        }
        turretRotationAngle = Math.atan2(deltaY, deltaX);
        
        if(distance < (weapon.getRange() * 1.3) + 200) {
            shoot();
        }      
    }
    
    //Enemy shoots if player is in range
    public void shoot() {
        if(weapon.getCooldown() == 0.0 && !status.isWeaponJammed())
        {
            int gunX = centerX + (int) Math.round(93.0 * Math.cos(turretRotationAngle) );
            int gunY = centerY + (int) Math.round(93.0 * Math.sin(turretRotationAngle) );
                
            switch (weapon.getBullet_number()) {
                case 0:
                    break;
                case 1:
                    projectiles.add(weapon.getProjectile(gunX, gunY, turretRotationAngle, 2));
                    break;
                default:
                    for(int i = 1; i <= weapon.getBullet_number(); i++)
                        projectiles.add(weapon.getProjectileShotgun(gunX, gunY, turretRotationAngle, 2, i));
                    break;
            }
        }
    }
    
    //Health regeneration
    public void updateRegen() {
            if(Math.abs(1.0 * regen * regenCooldown) > 1.0 * maxHP / 100) {
                HP += 1.0 * regen * regenCooldown;
                if(HP > maxHP)
                    HP = maxHP;
                else if(HP <= 0)
                    dead = true;
                regenCooldown = 0.0;
            }
            regenCooldown += 0.017;    
        }
   
     //Updating status effects
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
                regen = (int) (baseRegen * 2.5);
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
    
    //Deals damage when enemy collides with bullet
    public void dealDamage(Projectile p) {
       int projectileDamage = p.getDamage();
       int damageReduction = armor - p.getArmorPen();
       
       if(damageReduction < 0)
           damageReduction = 0;
       if(damageReduction > projectileDamage / 2)
           damageReduction = projectileDamage / 2;
       
       HP += -projectileDamage + damageReduction;
       status.changeStatus(p.getStatus());
       
       if(HP <= 0)
           dead = true;
    }
    
    //Checks if enemy collides with bullet
    public boolean projectileInArea(Projectile p) {
            int projectileX = p.getX();
            int projectileY = p.getY();
            
            if(bodyRotationAngle == 0 || bodyRotationAngle == 180) {
                if(projectileX > (centerX - 64) && projectileX < (centerX + 64)) {
                    if(projectileY > (centerY - 32) && projectileY < (centerY + 32)) {
                        return true;
                    }
                }
            }
            else if(bodyRotationAngle == 90 || bodyRotationAngle == -90) {
                if(projectileX > (centerX - 32) && projectileX < (centerX + 32)) {
                    if(projectileY > (centerY - 64) && projectileY < (centerY + 64)) {
                        return true;
                    }
                }
            }
            else if(bodyRotationAngle == 45 || bodyRotationAngle == -135) {
                double b1 = 32 * Math.sqrt(2);
                double b2 = 64 * Math.sqrt(2);
                projectileX -= centerX;
                projectileY -= centerY;
                
                if(projectileY >= (int) (projectileX - b1) && projectileY <= (int) (projectileX + b1)) {
                    if(projectileY >= (int) (-projectileX - b2) && projectileY <= (int) (-projectileX + b2)) {
                        return true;
                    }
                }
            }
            else if(bodyRotationAngle == -45 || bodyRotationAngle == 135) {
                double b1 = 32 * Math.sqrt(2);
                double b2 = 64 * Math.sqrt(2);
                projectileX -= centerX;
                projectileY -= centerY;
                
                if(projectileY >= (int) (-projectileX - b1) && projectileY <= (int) (-projectileX + b1)) {
                    if(projectileY >= (int) (projectileX - b2) && projectileY <= (int) (+ projectileX + b2)) {
                        return true;
                    }
                }
            }
            return false;
        }

    public int getHpPercent() {
        int percent = 0;
        percent = (int) Math.floor(1.0 * HP / maxHP * 100);
        if(percent < 1)
            percent = 1;
        if(dead)
            percent = 0;
        return percent;
    }

    //getters and setters
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

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

   
}
