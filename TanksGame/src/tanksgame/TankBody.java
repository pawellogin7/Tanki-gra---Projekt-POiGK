package tanksgame;


public class TankBody {
    private int maxHp, regen, moveSpeed, armor, primaryWeaponSlots, moduleSlots;
    private int tier, cost;
    private String name;
    
    TankBody(int hp, int rgn, int move, int ar, int primaryS, int moduleS) {
        maxHp = hp;
        regen = rgn;
        moveSpeed = move;
        armor = ar;
        primaryWeaponSlots = primaryS;
        moduleSlots = moduleS;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getRegen() {
        return regen;
    }

    public void setRegen(int regen) {
        this.regen = regen;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getPrimaryWeaponSlots() {
        return primaryWeaponSlots;
    }

    public void setPrimaryWeaponSlots(int primaryWeaponSlots) {
        this.primaryWeaponSlots = primaryWeaponSlots;
    }

    public int getModuleSlots() {
        return moduleSlots;
    }

    public void setModuleSlots(int moduleSlots) {
        this.moduleSlots = moduleSlots;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
