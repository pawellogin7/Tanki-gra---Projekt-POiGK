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
   
   private int money;
   
   private TankBody nullBody = new TankBody(1, 1, 1, 1, 1, 1);
   private Weapon nullWeapon = new Weapon(1, 1, 1, 1, 1, 1, 0);
   private Ability nullAbility = new Ability(1, 1);
   private Module nullModule = new Module(0, 0, 0, 0, 0, 0);
   private int bodyID, primary1ID, primary2ID, primary3ID, secondaryID, abilityID, module1ID, module2ID, module3ID;
   
   Equipment() {
        money = 30000;
        
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
                addSecondaryWeapon(new Weapon(10, 10, 70, 0, 800, 14, 5), i, "Slowing shot", 0, 2000, 0);
                break;  
            case 1:
                addSecondaryWeapon(new Weapon(10, 10, 70, 0, 800, 14, 5), i, "Slowing shot", 1, 2000, 0);
                break;
            case 2:
                addSecondaryWeapon(new Weapon(10, 10, 70, 0, 800, 14, 5), i, "Slowing shot", 2, 2000, 0);
                break;  
            case 3:
                addSecondaryWeapon(new Weapon(10, 10, 70, 0, 800, 14, 5), i, "Slowing shot", 3, 2000, 0);
                break;
            case 4:
                addSecondaryWeapon(new Weapon(10, 10, 70, 0, 800, 14, 5), i, "Slowing shot", 4, 2000, 0);
                break;
        }
        i++;
    }
    
    private void addSecondaryWeapon(Weapon weapon, int id, String name, int tier, int cost, int projType) {
        weapon.setTier(tier);
        weapon.setName(name);
        weapon.setCost(cost);
        weapon.setProjectileType(projType);
        secondaryWeapons.set(id, weapon);

   }
    
    private void createAbilities() {
        int i = 1;
        switch(abilitiesTier[i]) {
            case 0:
                addAbility(new Ability(5, 10), i, "Speed boost", 0, 2000);
                break;  
            case 1:
                addAbility(new Ability(5, 10), i, "Speed boost", 1, 2000);
                break;
            case 2:
                addAbility(new Ability(5, 10), i, "Speed boost", 2, 2000);
                break;  
            case 3:
                addAbility(new Ability(5, 10), i, "Speed boost", 3, 2000);
                break;
            case 4:
                addAbility(new Ability(5, 10), i, "Speed boost", 4, 2000);
                break;
        }
        i++;
    }
    
    private void addAbility(Ability ability, int id, String name, int tier, int cost) {
        ability.setTier(tier);
        ability.setName(name);
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

    public int getBodyID() {
        return bodyID;
    }

    public void setBodyID(int bodyID) {
        this.bodyID = bodyID;
    }

    public int getPrimary1ID() {
        return primary1ID;
    }

    public void setPrimary1ID(int primary1ID) {
        this.primary1ID = primary1ID;
    }

    public int getPrimary2ID() {
        return primary2ID;
    }

    public void setPrimary2ID(int primary2ID) {
        this.primary2ID = primary2ID;
    }

    public int getPrimary3ID() {
        return primary3ID;
    }

    public void setPrimary3ID(int primary3ID) {
        this.primary3ID = primary3ID;
    }

    public int getSecondaryID() {
        return secondaryID;
    }

    public void setSecondaryID(int secondaryID) {
        this.secondaryID = secondaryID;
    }

    public int getAbilityID() {
        return abilityID;
    }

    public void setAbilityID(int abilityID) {
        this.abilityID = abilityID;
    }

    public int getModule1ID() {
        return module1ID;
    }

    public void setModule1ID(int module1ID) {
        this.module1ID = module1ID;
    }

    public int getModule2ID() {
        return module2ID;
    }

    public void setModule2ID(int module2ID) {
        this.module2ID = module2ID;
    }
    
    public int getModule3ID() {
        return module3ID;
    }

    public void setModule3ID(int module3ID) {
        this.module3ID = module3ID;
    }
   
    
   
   
   
   
   
}
