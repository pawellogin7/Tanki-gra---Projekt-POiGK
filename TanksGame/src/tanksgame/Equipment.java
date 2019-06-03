package tanksgame;

import java.util.ArrayList;


public class Equipment {
   private ArrayList<TankBody> tankBodies;
   private int[] tankBodiesTier;
   private ArrayList<Weapon> primaryWeapons;
   private int[] primaryWeaponsTier;
   private ArrayList<Weapon> secondaryWeapons;
   private int[] secondaryWeaponsTier;
   private ArrayList<Ability> abilities;
   private int[] abilitiesTier;
   private ArrayList<Module> modules;
   private int[] modulesTier;
   
   private int[] tankSlot1;
   private int[] tankSlot2;
   private int[] tankSlot3;
   
   private int money;
   private int levelCompleted;
   
   private TankBody nullBody = new TankBody(1, 1, 1, 1, 1, 1);
   private Weapon nullWeapon = new Weapon(1, 1, 1, 1, 1, 1, 0);
   private Ability nullAbility = new Ability(1, 1);
   private Module nullModule = new Module(0, 0, 0, 0, 0, 0);
   
   Equipment() {       
        tankBodiesTier = new int[11];
        nullBody.setName("Null Weapon");
        nullBody.setTier(0);
        tankBodies = new ArrayList<TankBody>();
        for(int i = 0; i < tankBodiesTier.length; i++) {
            tankBodies.add(nullBody);
        }
        
        primaryWeaponsTier = new int[21];
        nullWeapon.setName("Null Weapon");
        nullWeapon.setTier(0);
        primaryWeapons = new ArrayList<Weapon>();
        for(int i = 0; i < primaryWeaponsTier.length; i++) {
            primaryWeapons.add(nullWeapon);
        }
        
        secondaryWeaponsTier = new int[11];
        secondaryWeapons = new ArrayList<Weapon>();
        for(int i = 0; i < secondaryWeaponsTier.length; i++) {
            secondaryWeapons.add(nullWeapon);
        }
        
        abilitiesTier = new int[16];
        nullAbility.setName("Null Ability");
        nullAbility.setTier(0);
        abilities = new ArrayList<Ability>();
        for(int i = 0; i < abilitiesTier.length; i++) {
            abilities.add(nullAbility);
        }
        
        modulesTier = new int[21];
        nullModule.setName("Null Ability");
        nullModule.setTier(0);
        modules = new ArrayList<Module>();
        for(int i = 0; i < modulesTier.length; i++) {
            modules.add(nullModule);
        }
        
        tankSlot1 = new int[9];
        tankSlot2 = new int[9];
        tankSlot3 = new int[9];
        for(int i = 0; i < 9; i++) {
            tankSlot1[i] = 0;
            tankSlot2[i] = 0;
            tankSlot3[i] = 0;
        }
        
        update();
   }
   
   public void update() {
       createTankBodies();
       createPrimaryWeapons();
       createSecondaryWeapons();
       createAbilities();
       createModules();
   }
   
    private void createTankBodies() {
        int i = 1;
        switch(tankBodiesTier[i]) {
            case 0:
                addBody(new TankBody(500, 5, 3, 20, 1, 1), i, "Basic body", 0, 2000);
                break;  
            case 1:
                addBody(new TankBody(500, 5, 3, 20, 1, 1), i, "Basic body", 1, 2000);
                break;
            case 2:
                addBody(new TankBody(600, 7, 4, 25, 1, 2), i, "Basic body", 2, 2000);
                break;  
            case 3:
                addBody(new TankBody(700, 10, 4, 30, 2, 2), i, "Basic body", 3, 2000);
                break;
            case 4:
                addBody(new TankBody(800, 14, 5, 40, 3, 3), i, "Basic body", 4, 2000);
                break;
        }
        i++;
    }
    
    private void addBody(TankBody body, int id, String name, int tier, int cost) {
        body.setTier(tier);
        body.setName(name);
        body.setCost(cost);
        tankBodies.set(id, body);

   }
   
   private void createPrimaryWeapons() {
        int i = 1;
        switch(primaryWeaponsTier[i]) {
            case 0:
                addPrimaryWeapon(new Weapon(80, 0.15, 85, 5, 800, 12, 1), i, "Machinegun", 0, 2000, 0);
                break;  
            case 1:
                addPrimaryWeapon(new Weapon(80, 0.15, 85, 5, 800, 12, 1), i, "Machinegun", 1, 2000, 0);
                break;
            case 2:
                addPrimaryWeapon(new Weapon(80, 0.15, 85, 5, 800, 12, 1), i, "Machinegun", 2, 2000, 0);
                break;  
            case 3:
                addPrimaryWeapon(new Weapon(80, 0.15, 85, 5, 800, 12, 1), i, "Machinegun", 3, 2000, 0);
                break;
            case 4:
                addPrimaryWeapon(new Weapon(80, 0.15, 85, 5, 800, 12, 1), i, "Machinegun", 4, 2000, 0);
                break;
        }
        
        i++;
        
        switch(primaryWeaponsTier[i]) {
            case 0:
                addPrimaryWeapon(new Weapon(15, 0.7, 70, 5, 600, 10, 7), i, "Shotgun", 0, 5000, 0);
                break;
            case 1:
                addPrimaryWeapon(new Weapon(15, 0.7, 70, 5, 600, 10, 7), i, "Shotgun", 1, 5000, 0);
                break;
            case 2:
                addPrimaryWeapon(new Weapon(15, 0.7, 70, 5, 600, 10, 7), i, "Shotgun", 2, 5000, 0);
                break;
            case 3:
                addPrimaryWeapon(new Weapon(15, 0.7, 70, 5, 600, 10, 7), i, "Shotgun", 3, 5000, 0);
                break;
            case 4:
                addPrimaryWeapon(new Weapon(15, 0.7, 70, 5, 600, 10, 7), i, "Shotgun", 4, 5000, 0);
                break;
            
        }
        i++;
        
        switch(primaryWeaponsTier[i]) {
            case 0:
                addPrimaryWeapon(new Weapon(200, 1, 95, 50, 1200, 18, 1), i, "Sniper Rifle", 0, 7000, 0);
                break;
            case 1:
                addPrimaryWeapon(new Weapon(200, 1, 95, 50, 1200, 18, 1), i, "Sniper Rifle", 1, 7000, 0);
                break;    
            case 2:
                addPrimaryWeapon(new Weapon(200, 1, 95, 50, 1200, 18, 1), i, "Sniper Rifle", 2, 7000, 0);
                break;
            case 3:
                addPrimaryWeapon(new Weapon(200, 1, 95, 50, 1200, 18, 1), i, "Sniper Rifle", 3, 7000, 0);
                break;
            case 4:
                addPrimaryWeapon(new Weapon(200, 1, 95, 50, 1200, 18, 1), i, "Sniper Rifle", 4, 7000, 0);
                break;
        }
        i++;
        
        
        switch(primaryWeaponsTier[i]) {
            case 0:
                addPrimaryWeapon(new Weapon(70, 0.15, 90, 10, 350, 20, 2), i, "Double shot", 0, 3000, 0);
                break;
            case 1:
                addPrimaryWeapon(new Weapon(70, 0.15, 90, 10, 350, 20, 2), i, "Double shot", 1, 3000, 0);
                break;   
            case 2:
                addPrimaryWeapon(new Weapon(70, 0.15, 90, 10, 350, 20, 2), i, "Double shot", 2, 3000, 0);
                break;
            case 3:
                addPrimaryWeapon(new Weapon(70, 0.15, 90, 10, 350, 20, 2), i, "Double shot", 3, 3000, 0);
                break;
            case 4:
                addPrimaryWeapon(new Weapon(70, 0.15, 90, 10, 350, 20, 2), i, "Double shot", 4, 3000, 0);
                break;
        }
        i++;
          
        switch(primaryWeaponsTier[i]) {
            case 0:
                addPrimaryWeapon(new Weapon(10, 0.1, 30, 5, 200, 20, 16), i, "Flamethrower", 0, 1000, 3);
                break;
            case 1:
                addPrimaryWeapon(new Weapon(10, 0.1, 30, 5, 200, 20, 16), i, "Flamethrower", 1, 1000, 3);
                break;    
            case 2:
                addPrimaryWeapon(new Weapon(10, 0.1, 30, 5, 200, 20, 16), i, "Flamethrower", 2, 1000, 3);
                break;
            case 3:
                addPrimaryWeapon(new Weapon(10, 0.1, 30, 5, 200, 20, 16), i, "Flamethrower", 3, 1000, 3);
                break;
            case 4:
                addPrimaryWeapon(new Weapon(10, 0.1, 30, 5, 200, 20, 16), i, "Flamethrower", 4, 1000, 3);
                break;
        }
        i++;


        switch(primaryWeaponsTier[i]) {
            case 0:
                addPrimaryWeapon(new Weapon(10, 0.017, 100, 50, 800, 15, 1), i, "Laser", 0, 12000, 2);;
                break;
            case 1:
                addPrimaryWeapon(new Weapon(10, 0.017, 100, 50, 800, 15, 1), i, "Laser", 1, 12000, 2);;
                break;    
            case 2:
                addPrimaryWeapon(new Weapon(10, 0.017, 100, 50, 800, 15, 1), i, "Laser", 2, 12000, 2);;
                break;
            case 3:
                addPrimaryWeapon(new Weapon(10, 0.017, 100, 50, 800, 15, 1), i, "Laser", 3, 12000, 2);;
                break;
            case 4:
                addPrimaryWeapon(new Weapon(10, 0.017, 100, 50, 800, 15, 1), i, "Laser", 4, 12000, 2);;
                break;
        }
        i++;
    }

    private void addPrimaryWeapon(Weapon weapon, int id, String name, int tier, int cost, int projType) {
        weapon.setTier(tier);
        weapon.setName(name);
        weapon.setCost(cost);
        weapon.setProjectileType(projType);
        primaryWeapons.set(id, weapon);

   }
    
    private void createSecondaryWeapons() {
        int i = 1;
        switch(secondaryWeaponsTier[i]) {
            case 0:
                addSecondaryWeapon(new Weapon(10, 10, 70, 0, 800, 14, 5), i, "Slowing shot", 2, "Slowed", 0, 2000, 0);
                break;  
            case 1:
                addSecondaryWeapon(new Weapon(10, 10, 70, 0, 800, 14, 5), i, "Slowing shot", 3, "Slowed", 1, 2000, 0);
                break;
            case 2:
                addSecondaryWeapon(new Weapon(10, 10, 70, 0, 800, 14, 5), i, "Slowing shot", 4, "Slowed", 2, 2000, 0);
                break;  
            case 3:
                addSecondaryWeapon(new Weapon(10, 10, 70, 0, 800, 14, 5), i, "Slowing shot", 5, "Slowed", 3, 2000, 0);
                break;
            case 4:
                addSecondaryWeapon(new Weapon(10, 10, 70, 0, 800, 14, 5), i, "Slowing shot", 6, "Slowed", 4, 2000, 0);
                break;
            
        }
        secondaryWeapons.get(i).getStatus().setSlowed(true);
        secondaryWeapons.get(i).getStatus().setSlowedDuration(secondaryWeapons.get(i).getStatusDuration());
        
        i++;
    }
    
    private void addSecondaryWeapon(Weapon weapon, int id, String name, double duration, String statusName, int tier, int cost, int projType) {
        weapon.setTier(tier);
        weapon.setName(name);
        weapon.setCost(cost);
        weapon.setStatusName(statusName);
        weapon.setStatusDuration(duration);
        weapon.setProjectileType(projType);
        secondaryWeapons.set(id, weapon);

   }
    
    private void createAbilities() {
        int i = 1;
        switch(abilitiesTier[i]) {
            case 0:
                addAbility(new Ability(3, 14), i, "Speed boost", "Speed boost", 0, 2000);
                break;  
            case 1:
                addAbility(new Ability(3, 14), i, "Speed boost", "Speed boost", 1, 2000);
                break;
            case 2:
                addAbility(new Ability(4, 14), i, "Speed boost", "Speed boost", 2, 2000);
                break;  
            case 3:
                addAbility(new Ability(5, 14), i, "Speed boost", "Speed boost", 3, 2000);
                break;
            case 4:
                addAbility(new Ability(6, 10), i, "Speed boost", "Speed boost", 4, 2000);
                break;
        }
        abilities.get(i).getStatus().setSpeedy(true);
        abilities.get(i).getStatus().setSpeedyDuration(abilities.get(i).getDuration());
        i++;
    }
    
    private void addAbility(Ability ability, int id, String name, String statusName, int tier, int cost) {
        ability.setTier(tier);
        ability.setName(name);
        ability.setStatusName(statusName);
        ability.setCost(cost);
        abilities.set(id, ability);

   }
    
    private void createModules() {
        int i = 1;
        switch(modulesTier[i]) {
            case 0:
                addModule(new Module(1, 50, 0, 0, 0, 0), i, "Health module", 0, 2000);
                break;  
            case 1:
                addModule(new Module(1, 50, 0, 0, 0, 0), i, "Health module", 1, 2000);
                break;
            case 2:
                addModule(new Module(1, 50, 10, 15, 0, 0), i, "Health module", 2, 2000);
                break;  
            case 3:
                addModule(new Module(1, 50, 10, 10, 11, -10), i, "Health module", 3, 2000);
                break;
            case 4:
                addModule(new Module(1, 50, 2, 10, 3, 3), i, "Health module", 4, 2000);
                break;
        }
        i++;
    }
    
    private void addModule(Module module, int id, String name, int tier, int cost) {
        module.setTier(tier);
        module.setName(name);
        module.setCost(cost);
        modules.set(id, module);
   }
   
    public ArrayList<Weapon> getPrimaryWeapons() {
        return primaryWeapons;
    }

    public void setPrimaryWeapons(ArrayList<Weapon> primaryWeapons) {
        this.primaryWeapons = primaryWeapons;
    }

    public int[] getPrimaryWeaponsTier() {
        return primaryWeaponsTier;
    }

    public void setPrimaryWeaponsTier(int index, int value) {
        this.primaryWeaponsTier[index] = value;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public ArrayList<TankBody> getTankBodies() {
        return tankBodies;
    }

    public void setTankBodies(ArrayList<TankBody> tankBodies) {
        this.tankBodies = tankBodies;
    }

    public int[] getTankBodiesTier() {
        return tankBodiesTier;
    }

    public void setTankBodiesTier(int index, int value) {
        this.tankBodiesTier[index] = value;
    }

    public ArrayList<Weapon> getSecondaryWeapons() {
        return secondaryWeapons;
    }

    public void setSecondaryWeapons(ArrayList<Weapon> secondaryWeapons) {
        this.secondaryWeapons = secondaryWeapons;
    }

    public int[] getSecondaryWeaponsTier() {
        return secondaryWeaponsTier;
    }

    public void setSecondaryWeaponsTier(int index, int value) {
        this.secondaryWeaponsTier[index] = value;
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(ArrayList<Ability> abilities) {
        this.abilities = abilities;
    }

    public int[] getAbilitiesTier() {
        return abilitiesTier;
    }

    public void setAbilitiesTier(int index, int value) {
        this.abilitiesTier[index] = value;
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public void setModules(ArrayList<Module> modules) {
        this.modules = modules;
    }

    public int[] getModulesTier() {
        return modulesTier;
    }

    public void setModulesTier(int index, int value) {
        this.modulesTier[index] = value;
    }

    public int getLevelCompleted() {
        return levelCompleted;
    }

    public void setLevelCompleted(int levelCompleted) {
        this.levelCompleted = levelCompleted;
    }

    public int[] getTankSlot1() {
        return tankSlot1;
    }

    public void setTankSlot1(int index, int value) {
        this.tankSlot1[index] = value;
    }

    public int[] getTankSlot2() {
        return tankSlot2;
    }

    public void setTankSlot2(int index, int value) {
       this.tankSlot2[index] = value;
    }

    public int[] getTankSlot3() {
        return tankSlot3;
    }

    public void setTankSlot3(int index, int value) {
        this.tankSlot3[index] = value;
    }

   
}
