package tanksgame;


public class TankBody {
    private int maxHp, regen, moveSpeed, armor, primaryWeaponSlots, moduleSlots;
    private int tier, cost;
    private int[] statBoostType, statBoostEffect;
    private String name;
    
    //Tank bodies are your main tanks, they give your your base passive stats
    //You have to have Tank Body equipped on eqch tank
    TankBody(int hp, int rgn, int move, int ar, int primaryS, int moduleS) {
        maxHp = hp;
        regen = rgn;
        moveSpeed = move;
        armor = ar;
        primaryWeaponSlots = primaryS;
        moduleSlots = moduleS;
        statBoostType = new int[6];
        statBoostEffect = new int[6];
    }
    
    public String getStatString(int statID) {
        String statString = "";
        int statType = statBoostType[statID];
        int statEffect = statBoostEffect[statID];
        
        boolean percent = false;
        if(statEffect != 0) {
            switch(statType) {
                case 0:
                    break;
                case 10:
                    statString += "Weapon damage";
                    percent = true;
                    break;
                case 11:
                    statString += "Weapon reload";
                    percent = true;
                    break;
                case 12:
                    statString += "Weapon accuracy";
                    percent = true;
                    break;
                case 13:
                    statString += "Weapon armor pen";
                    percent = true;
                    break;
                case 14:
                    statString += "Weapon range";
                    percent = true;
                    break;
                case 20:
                    statString += "Cooldown reduction";
                    percent = true;
                    break;
            }

            if((statEffect >= 0 && statType != 11) || (statEffect < 0 && statType == 11))
                statString += " +";
            else
                statString += " -";
            statString += Integer.toString(Math.abs(statEffect));
                if(percent)
            statString += "%";
        }
                
        return statString;
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

    public int[] getStatBoostType() {
        return statBoostType;
    }

    public void setStatBoostType(int[] statBoostType) {
        this.statBoostType = statBoostType;
    }

    public int[] getStatBoostEffect() {
        return statBoostEffect;
    }

    public void setStatBoostEffect(int[] statBoostEffect) {
        this.statBoostEffect = statBoostEffect;
    }
    
    
    
}
