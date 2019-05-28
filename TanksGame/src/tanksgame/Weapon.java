package tanksgame;

import java.util.Random;


public class Weapon {
    private int baseDamage, baseAccuracy, baseArmorPen, range, bullet_velocity, bullet_number;
    private int damage, accuracy, armorPen;
    private double  baseReload, reload;
    private int cooldown, projectileType;
    
    Weapon(int dmg, double rld, int acc, int a_pen, int rng, int vel, int num) {
        baseDamage = dmg;
        baseReload = rld;
        baseAccuracy = acc;
        baseArmorPen = a_pen;
        range = rng;
        bullet_velocity = vel;
        bullet_number = num;
        
        armorPen = baseArmorPen;
        damage = baseDamage;
        reload = baseReload;
        accuracy = baseAccuracy;       
        projectileType = 0;
        cooldown = 0;
    }

    public void update() {
        if(cooldown != 0) {
            cooldown -= 17;
            if(cooldown < 0)
                cooldown = 0;
        } 
    }
    
    Projectile getProjectile(int startX, int startY, double deltaX, double deltaY) {
        Random generator = new Random();
        double losuj = generator.nextDouble();
        boolean znak = generator.nextBoolean();
        double angle = 0;
        
        if(znak)
            angle = Math.atan2(deltaY , deltaX) + (1.0 *(100 - accuracy) / 100.0) * losuj * Math.PI / 2;
        else
            angle = Math.atan2(deltaY , deltaX) - (1.0 * (100 - accuracy) / 100.0) * losuj * Math.PI / 2;       
     
        cooldown = (int) (reload * 1000);
        Projectile proj = new Projectile(startX, startY, angle, bullet_velocity, range, damage, armorPen, projectileType);
        return proj;
    }
    
    Projectile getProjectileShotgun(int startX, int startY, double deltaX, double deltaY, int proj_id) {
        double angle = 0;
        if(bullet_number % 2 == 0)
        {
            double jump = 1.0 * (100 - accuracy) / 100.0 / (bullet_number - 1) * Math.PI / 2;
            angle = Math.atan2(deltaY , deltaX) - jump * 1.0 * (proj_id - bullet_number / 2 - 1);
        }
        else
        {
            double jump = 1.0 * (100 - accuracy) / 100.0 / (bullet_number - 1) * Math.PI / 2;
            angle = Math.atan2(deltaY , deltaX) - jump * 1.0 * (proj_id - (bullet_number - 1) / 2 - 1) * 1.0;
        }
        
        cooldown = (int) (reload * 1000);
        Projectile proj = new Projectile(startX, startY, angle, bullet_velocity, range, damage, armorPen, projectileType);
        return proj;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getArmorPen() {
        return armorPen;
    }

    public void setArmorPen(int armorPen) {
        this.armorPen = armorPen;
    }

    public double getReload() {
        return reload;
    }

    public void setReload(double reload) {
        this.reload = reload;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public int getBaseAccuracy() {
        return baseAccuracy;
    }

    public int getBaseArmorPen() {
        return baseArmorPen;
    }

    public double getBaseReload() {
        return baseReload;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getBullet_number() {
        return bullet_number;
    }
   
    
    public int getProjectileType() {
        return projectileType;
    }

    public void setProjectileType(int projectileType) {
        this.projectileType = projectileType;
    }    
    
}
