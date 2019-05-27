package tanksgame;

import java.util.Random;


public class Weapon {
    private int damage, accuracy, armor_pen, range, bullet_velocity, bullet_number;
    private double  reload;
    private int cooldown, projectileType;
    
    Weapon(int dmg, double rld, int acc, int a_pen, int rng, int vel, int num) {
        damage = dmg;
        reload = rld;
        accuracy = acc;
        armor_pen = a_pen;
        range = rng;
        bullet_velocity = vel;
        bullet_number = num;
        
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
            angle = Math.atan2(deltaY , deltaX) + ((100 - accuracy) / 100.0) * losuj * Math.PI / 2;
        else
            angle = Math.atan2(deltaY , deltaX) - ((100 - accuracy) / 100.0) * losuj * Math.PI / 2;       
     
        cooldown = (int) (reload * 1000);
        Projectile proj = new Projectile(startX, startY, angle, bullet_velocity, range, damage, armor_pen, projectileType);
        return proj;
    }
    
    Projectile getProjectileShotgun(int startX, int startY, double deltaX, double deltaY, int proj_id) {
        double angle = 0;
        if(bullet_number % 2 == 0)
        {
            double jump = (100 - accuracy) / 100.0 / (bullet_number - 1) * Math.PI / 2;
            angle = Math.atan2(deltaY , deltaX) - jump * (proj_id - bullet_number / 2 - 1);
        }
        else
        {
            double jump = (100 - accuracy) / 100.0 / (bullet_number - 1) * Math.PI / 2;
            angle = Math.atan2(deltaY , deltaX) - jump * (proj_id - (bullet_number - 1) / 2 - 1);
        }
        
        cooldown = (int) (reload * 1000);
        Projectile proj = new Projectile(startX, startY, angle, bullet_velocity, range, damage, armor_pen, projectileType);
        return proj;
    }
       
    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public double getReload() {
        return reload;
    }

    public void setReload(double reload) {
        this.reload = reload;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getArmor_pen() {
        return armor_pen;
    }

    public void setArmor_pen(int armor_pen) {
        this.armor_pen = armor_pen;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
    
    public int getBullet_velocity() {
        return bullet_velocity;
    }

    public void setBullet_velocity(int bullet_velocity) {
        this.bullet_velocity = bullet_velocity;
    }

    public int getBullet_number() {
        return bullet_number;
    }

    public void setBullet_number(int bullet_number) {
        this.bullet_number = bullet_number;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getProjectileType() {
        return projectileType;
    }

    public void setProjectileType(int projectileType) {
        this.projectileType = projectileType;
    }
    
    
    
    
    
}
