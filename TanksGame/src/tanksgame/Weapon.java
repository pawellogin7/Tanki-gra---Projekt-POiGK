package tanksgame;

import java.util.Random;


public class Weapon {
    private int baseDamage, baseAccuracy, baseArmorPen, range, bullet_velocity, bullet_number;
    private int damage, accuracy, armorPen;
    private double  baseReload, reload, cooldown;
    private int projectileType, tier, cost;
    private double statusDuration;
    private StatusEffects status = new StatusEffects();
    private String name, statusName;
    
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
            cooldown -= 0.017;
            if(cooldown < 0)
                cooldown = 0;
        } 
    }
    
    Projectile getProjectile(int startX, int startY, double turretAngle, int team) {
        Random generator = new Random();
        double losuj = generator.nextDouble();
        boolean znak = generator.nextBoolean();
        double angle = 0;
        
        if(znak)
            angle = turretAngle + (1.0 *(100 - accuracy) / 100.0) * losuj * Math.PI;
        else
            angle = turretAngle - (1.0 * (100 - accuracy) / 100.0) * losuj * Math.PI;       
     
        cooldown = reload;
        Projectile proj = new Projectile(startX, startY, angle, bullet_velocity, range, damage, armorPen, projectileType, team);
        return proj;
    }
    
    Projectile getProjectileShotgun(int startX, int startY, double turretAngle, int team, int proj_id) {
        double angle = 0;
        if(bullet_number % 2 == 0)
        {
            double jump = 1.0 * (100 - accuracy) / 100.0 / (bullet_number - 1) * Math.PI;
            angle = turretAngle - jump * 1.0 * (proj_id - bullet_number / 2 - 1);
        }
        else
        {
            double jump = 1.0 * (100 - accuracy) / 100.0 / (bullet_number - 1) * Math.PI;
            angle = turretAngle - jump * 1.0 * (proj_id - (bullet_number - 1) / 2 - 1) * 1.0;
        }
        
        cooldown = reload;
        Projectile proj = new Projectile(startX, startY, angle, bullet_velocity, range, damage, armorPen, projectileType, team);
        return proj;
    }
    
    int getPercent() {
        int percent = 0;
        percent = (int) Math.floor(1.0 * (reload - cooldown) / reload * 100);

        return percent;
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

    public void setCooldown(double cooldown) {
        this.cooldown = cooldown;
    }
    
    public double getCooldown() {
        return cooldown;
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

    public StatusEffects getStatus() {
        return status;
    }

    public void setStatus(StatusEffects status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    public void setBaseAccuracy(int baseAccuracy) {
        this.baseAccuracy = baseAccuracy;
    }

    public void setBaseArmorPen(int baseArmorPen) {
        this.baseArmorPen = baseArmorPen;
    }

    public void setBullet_number(int bullet_number) {
        this.bullet_number = bullet_number;
    }

    public void setBaseReload(double baseReload) {
        this.baseReload = baseReload;
    }

    public double getStatusDuration() {
        return statusDuration;
    }

    public void setStatusDuration(double statusDuration) {
        this.statusDuration = statusDuration;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
    
    
    
}
