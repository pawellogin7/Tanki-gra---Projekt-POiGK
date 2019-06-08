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
        private double regenCooldown;
        private int activeWeapon, weaponsNumber, activeTankSlot;
        private boolean secondaryChosen, abilityChosen;
        
        private boolean movingLeft = false;
        private boolean movingRight = false;
        private boolean movingUp = false;
        private boolean movingDown = false;

        private static Background bg1 = TanksGame.getBg1();                 
        private static Background bg2 = TanksGame.getBg2();
        private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
        
        private TankBody body;
        private Weapon nullWeapon = new Weapon(1, 1, 1, 1, 1, 1, 0);
        private Weapon primaryWeapon = nullWeapon;
        private Weapon primaryWeapon1 = nullWeapon;
        private Weapon primaryWeapon2 = nullWeapon;
        private Weapon primaryWeapon3 = nullWeapon;
        private Weapon secondaryWeapon = nullWeapon;
        private StatusEffects status = new StatusEffects();
        private Ability ability = new Ability(0, 0);
        private Module module1 = new Module(0, 0, 0, 0, 0, 0);
        private Module module2 = new Module(0, 0, 0, 0, 0, 0);
        private Module module3 = new Module(0, 0, 0, 0, 0, 0);
        
        Player(Equipment eq, int tankSlot) {
            activeTankSlot = tankSlot;
            int[] tankEq = new int[9];
            switch(activeTankSlot) {
                case 1:
                    tankEq = eq.getTankSlot1();
                    break;
                case 2:
                    tankEq = eq.getTankSlot2();
                    break;
                case 3:
                    tankEq = eq.getTankSlot3();
                    break;
            }
            body = eq.getTankBodies().get(tankEq[0]);
            primaryWeapon1 = eq.getPrimaryWeapons().get(tankEq[1]);
            primaryWeapon2 = eq.getPrimaryWeapons().get(tankEq[2]);
            primaryWeapon3 = eq.getPrimaryWeapons().get(tankEq[3]);
            secondaryWeapon = eq.getSecondaryWeapons().get(tankEq[4]);
            if(tankEq[4] == 0)
                secondaryChosen = false;
            else
                secondaryChosen = true;
            secondaryWeapon.setCooldown(0);
            ability = eq.getAbilities().get(tankEq[5]);
            ability.setDuration(0);
            ability.setCooldown(0);
            if(tankEq[5] == 0)
                abilityChosen = false;
            else
                abilityChosen = true;
            module1 = eq.getModules().get(tankEq[6]);
            module2 = eq.getModules().get(tankEq[7]);
            module3 = eq.getModules().get(tankEq[8]);
            
            baseMaxHP = body.getMaxHp() + checkModules(1);
            if(baseMaxHP < 1)
                baseMaxHP = 1;
            maxHP = baseMaxHP;
            HP = maxHP;
            
            baseRegen = body.getRegen() + checkModules(2);
            if(baseRegen < 0)
                baseRegen = 0;
            regen = baseRegen;
            regenCooldown = 0.0;
            
            baseSpeed = body.getMoveSpeed() + checkModules(3);
            if(baseSpeed < 2)
                baseSpeed = 2;    
            speed = baseSpeed;
            
            baseArmor = body.getArmor() + checkModules(4);
            if(baseArmor < 0)
                baseArmor = 0;
            armor = baseArmor;
            
            double damageBoost = 1.0 + (1.0 * checkModules(10) / 100);
            double reloadBoost = 1.0 - (1.0 * checkModules(11) / 100);
            double accuracyBoost = (1.0 * checkModules(12) / 100);
            double armorPenBoost = 1.0 + (1.0 * checkModules(13) / 100);
            double rangeBoost = 1.0 + (1.0 * checkModules(14) / 100);
            
            primaryWeapon1.setBaseDamage((int) (1.0 * primaryWeapon1.getBaseDamage() * damageBoost));
            primaryWeapon1.setBaseReload(1.0 * primaryWeapon1.getBaseReload() * reloadBoost);
            primaryWeapon1.setBaseAccuracy((int) (1.0 * primaryWeapon1.getBaseAccuracy() * accuracyBoost));
            primaryWeapon1.setBaseArmorPen((int) (1.0 * primaryWeapon1.getBaseArmorPen() * armorPenBoost));
            primaryWeapon1.setRange((int) (1.0 * primaryWeapon1.getRange() * rangeBoost));
            
            primaryWeapon2.setBaseDamage((int) (1.0 * primaryWeapon2.getBaseDamage() * damageBoost));
            primaryWeapon2.setBaseReload(1.0 * primaryWeapon2.getBaseReload() * reloadBoost);
            primaryWeapon2.setBaseAccuracy((int) (1.0 * primaryWeapon2.getBaseAccuracy() * accuracyBoost));
            primaryWeapon2.setBaseArmorPen((int) (1.0 * primaryWeapon2.getBaseArmorPen() * armorPenBoost));
            primaryWeapon2.setRange((int) (1.0 * primaryWeapon2.getRange() * rangeBoost));
            
            primaryWeapon3.setBaseDamage((int) (1.0 * primaryWeapon3.getBaseDamage() * damageBoost));
            primaryWeapon3.setBaseReload(1.0 * primaryWeapon3.getBaseReload() * reloadBoost);
            primaryWeapon3.setBaseAccuracy((int) (1.0 * primaryWeapon3.getBaseAccuracy() * accuracyBoost));
            primaryWeapon3.setBaseArmorPen((int) (1.0 * primaryWeapon3.getBaseArmorPen() * armorPenBoost));
            primaryWeapon3.setRange((int) (1.0 * primaryWeapon3.getRange() * rangeBoost));
            
            primaryWeapon = primaryWeapon1;
            activeWeapon = 1;
            weaponsNumber = body.getPrimaryWeaponSlots();
            if(weaponsNumber == 3) {
                if(tankEq[2] == 0 && tankEq[3] != 0) {
                    primaryWeapon2 = primaryWeapon3;
                    weaponsNumber = 2;
                }
                else if(tankEq[2] != 0 && tankEq[3] == 0) {
                    weaponsNumber = 2;
                }
                else if(tankEq[2] == 0 && tankEq[3] == 0) {
                    weaponsNumber = 1;
                }
            }
            else if(weaponsNumber == 2) {
                if(tankEq[2] == 0) {
                    weaponsNumber = 1;
                }
            }
        }
        
        private int checkModules(int statType) {
            int statEffect = 0;
            if(body.getModuleSlots() >= 1) {
                if(module1.getStat1Type() == statType)
                    statEffect += module1.getStat1Effect();
                else if(module1.getStat2Type() == statType)
                    statEffect += module1.getStat2Effect();
                else if(module1.getStat3Type() == statType)
                    statEffect += module1.getStat3Effect();
            }
            
            if(body.getModuleSlots() >= 2) {
                if(module2.getStat1Type() == statType)
                    statEffect += module2.getStat1Effect();
                else if(module2.getStat2Type() == statType)
                    statEffect += module2.getStat2Effect();
                else if(module2.getStat3Type() == statType)
                    statEffect += module2.getStat3Effect();
            }
            
            if(body.getModuleSlots() >= 3) {
                if(module3.getStat1Type() == statType)
                    statEffect += module3.getStat1Effect();
                else if(module3.getStat2Type() == statType)
                    statEffect += module3.getStat2Effect();
                else if(module3.getStat3Type() == statType)
                    statEffect += module3.getStat3Effect();
            }
            
            return statEffect;
        }
        
	public void update() {
            primaryWeapon.update();
            primaryWeapon1.update();
            primaryWeapon2.update();
            primaryWeapon3.update();
            secondaryWeapon.update();
            status.update();
            ability.update();
            updateStatusEffects();
            updateSpeed();
            updateRegen();
            
            // Moves Character or Scrolls Background accordingly.
            bg1.setSpeedX(speedX);
            bg2.setSpeedX(speedX);
            bg1.setSpeedY(speedY);
            bg2.setSpeedY(speedY);
            
	}
        
        public void updateBackground() {
            updateSpeed();
            bg1.setSpeedX(speedX);
            bg2.setSpeedX(speedX);
            bg1.setSpeedY(speedY);
            bg2.setSpeedY(speedY);
        }
        
        public void changeWeapon(int key) {
            switch(key) {
                case 1:
                    primaryWeapon = primaryWeapon1;
                    activeWeapon = 1;
                    break;
                case 2:
                    if(weaponsNumber >= 2) {
                        primaryWeapon = primaryWeapon2;
                        activeWeapon = 2;
                    }
                    break;
                case 3:
                    if(weaponsNumber >= 3) {
                        primaryWeapon = primaryWeapon3;
                        activeWeapon = 3;
                    }
                    break;
            }
        }

        public void shootPrimary(double mouseX, double mouseY) {
            if(primaryWeapon.getCooldown() == 0.0 && !status.isWeaponJammed())
            {
                int gunX = centerX + 53;
                int gunY = centerY - 9;
                double deltaX = gunX - mouseX;
                double deltaY = gunY - mouseY;
                
                if(primaryWeapon.getBullet_number() == 0) {
                }
                else if(primaryWeapon.getBullet_number() == 1)
                     projectiles.add(primaryWeapon.getProjectile(gunX, gunY, deltaX, deltaY));
                else {
                    for(int i = 1; i <= primaryWeapon.getBullet_number(); i++)
                        projectiles.add(primaryWeapon.getProjectileShotgun(gunX, gunY, deltaX, deltaY, i));
                }
            }
        }
        
        public void shootSecondary(double mouseX, double mouseY) {
            if(secondaryWeapon.getCooldown() == 0)
            {
                int gunX = centerX + 53;
                int gunY = centerY - 9;
                double deltaX = gunX - mouseX;
                double deltaY = gunY - mouseY;
                
                if(secondaryWeapon.getBullet_number() == 0) {
                }
                else if(secondaryWeapon.getBullet_number() == 1)
                     projectiles.add(secondaryWeapon.getProjectile(gunX, gunY, deltaX, deltaY));
                else {
                    for(int i = 1; i <= secondaryWeapon.getBullet_number(); i++)
                        projectiles.add(secondaryWeapon.getProjectileShotgun(gunX, gunY, deltaX, deltaY, i));
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
                regen = (int) (baseRegen * 2.5);
            else
                regen = baseRegen;
            
            if(status.isSabotage())
                regen = -regen;
            else
                regen = regen;
            //Damage
            if(status.isDamageReduction())
                primaryWeapon.setDamage((int) Math.round(0.7 * primaryWeapon.getBaseDamage()));
            else if(status.isDamageBoost())
                primaryWeapon.setDamage((int) Math.round(1.2 * primaryWeapon.getBaseDamage()));
            else
                primaryWeapon.setDamage(primaryWeapon.getBaseDamage());
            //Reload
            if(status.isWeaponJammed()){
            }
            else if(status.isReloadReduction())
                primaryWeapon.setReload(2.0 * primaryWeapon.getBaseReload());
            else if(status.isReloadBoost())
                primaryWeapon.setReload(0.7 * primaryWeapon.getBaseReload());
            else
                primaryWeapon.setReload(primaryWeapon.getBaseReload());
            
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
        
        public void updateRegen() {
            if(Math.abs(1.0 * regen * regenCooldown) > 1.0 * maxHP / 100) {
                HP += 1.0 * regen * regenCooldown;
                if(HP > maxHP)
                    HP = maxHP;
                regenCooldown = 0.0;
            }
            regenCooldown += 0.017;    
        }
        
        public int getHpPercent() {
            int percent = 0;
            percent = (int) Math.floor(1.0 * HP / maxHP * 100);
            if(percent < 1)
                percent = 1;
            return percent;
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

    public Weapon getPrimaryWeapon() {
        return primaryWeapon;
    }

    public void setPrimaryWeapon(Weapon playerWeapon) {
        this.primaryWeapon = playerWeapon;
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    public Weapon getSecondaryWeapon() {
        return secondaryWeapon;
    }

    public void setSecondaryWeapon(Weapon secondaryWeapon) {
        this.secondaryWeapon = secondaryWeapon;
    }

    public int getActiveWeapon() {
        return activeWeapon;
    }

    public void setActiveWeapon(int activeWeapon) {
        this.activeWeapon = activeWeapon;
    }

    public int getWeaponsNumber() {
        return weaponsNumber;
    }

    public void setWeaponsNumber(int weaponsNumber) {
        this.weaponsNumber = weaponsNumber;
    }

    public Weapon getPrimaryWeapon1() {
        return primaryWeapon1;
    }

    public void setPrimaryWeapon1(Weapon primaryWeapon1) {
        this.primaryWeapon1 = primaryWeapon1;
    }

    public Weapon getPrimaryWeapon2() {
        return primaryWeapon2;
    }

    public void setPrimaryWeapon2(Weapon primaryWeapon2) {
        this.primaryWeapon2 = primaryWeapon2;
    }

    public Weapon getPrimaryWeapon3() {
        return primaryWeapon3;
    }

    public void setPrimaryWeapon3(Weapon primaryWeapon3) {
        this.primaryWeapon3 = primaryWeapon3;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getBaseRegen() {
        return baseRegen;
    }

    public void setBaseRegen(int baseRegen) {
        this.baseRegen = baseRegen;
    }

    public int getRegen() {
        return regen;
    }

    public void setRegen(int regen) {
        this.regen = regen;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(int baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getRegenCooldown() {
        return regenCooldown;
    }

    public void setRegenCooldown(double regenCooldown) {
        this.regenCooldown = regenCooldown;
    }

    public StatusEffects getStatus() {
        return status;
    }

    public void setStatus(StatusEffects status) {
        this.status = status;
    }

    public int getActiveTankSlot() {
        return activeTankSlot;
    }

    public boolean isAbilityChosen() {
        return abilityChosen;
    }

    public boolean isSecondaryChosen() {
        return secondaryChosen;
    }

    
    
} 
