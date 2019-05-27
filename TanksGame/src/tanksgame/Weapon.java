package tanksgame;

import java.util.Random;


public class Weapon {
    int damage, fire_rate, accuracy, armor_pen, range, bullet_velocity, bullet_number;
    
    Weapon(int dmg, int frate, int acc, int apen, int rng, int vel, int num)
    {
        damage = dmg;
        fire_rate = frate;
        accuracy = acc;
        armor_pen = apen;
        range = rng;
        bullet_velocity = vel;
        bullet_number = num;
    }

    Projectile getProjectile(int startX, int startY, double angle)
    {
        Random generator = new Random(101 - accuracy);
        Random znak_gen = new Random();
        double losuj = generator.nextInt();
        boolean znak = znak_gen.nextBoolean();

        if(znak)
            angle += losuj / 100 * 2 * Math.PI;
        else
            angle -= losuj / 100 * 2 * Math.PI;        
     
        Projectile proj = new Projectile(startX, startY, angle);
        return proj;
    }
    
    Projectile getProjectileShotgun(int startX, int startY, double angle, int proj_id)
    {
        double ang = 0;
        
        if(bullet_number % 2 == 0)
        {
            if(proj_id <= bullet_number / 2)
                ang = -accuracy * 2 / bullet_number * proj_id;
            else
                ang = accuracy * 2 / bullet_number *(proj_id - bullet_number / 2);
        }
        else
            if(proj_id <= (bullet_number - 1) / 2)
                ang = -accuracy * 2 / bullet_number * proj_id;
            else if(proj_id == (bullet_number - 1) / 2  + 1)
                ang = 0;
            else
                ang = accuracy * 2 / bullet_number *(proj_id - (bullet_number - 1) / 2 - 1);
            
        angle += ang;
        Projectile proj = new Projectile(startX, startY, angle);
        return proj;
    }
    
    
    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getFire_rate() {
        return fire_rate;
    }

    public void setFire_rate(int fire_rate) {
        this.fire_rate = fire_rate;
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
    
    
    
    
}
