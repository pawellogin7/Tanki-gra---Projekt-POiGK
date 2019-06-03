
package tanksgame;


public class Module {
    private int stat1Type, stat1Effect, stat2Type, stat2Effect, stat3Type, stat3Effect;
    private int tier, cost;
    private String name;
    //Stat Type:
    //1 - max HP
    //2 - regen
    //3 - movement speed
    //4 - armor
    //10 - weapon damage [%]
    //11 - weapon reload [%]
    //12 - accuracy [%]
    //13 - armor penetration [%]
    //14 - range [%]
    
    Module(int s1Type, int s1Effect, int s2Type, int s2Effect, int s3Type, int s3Effect) {
        stat1Type = s1Type;
        stat1Effect = s1Effect;
        stat2Type = s2Type;
        stat2Effect = s2Effect;
        stat3Type = s3Type;
        stat3Effect = s3Effect;
    }
    
    public String getStatString(int statID) {
        String statString = "";
        int statType = 0;
        int statEffect = 0;
        switch(statID) {
            case 1:
                statType = stat1Type;
                statEffect = stat1Effect;
                break;
            case 2:
                statType = stat2Type;
                statEffect = stat2Effect;
                break;
            case 3:
                statType = stat3Type;
                statEffect = stat3Effect;
                break;
        }
        
        boolean percent = false;
        if(statEffect != 0) {
            switch(statType) {
                case 1:
                    statString += "Max HP";
                    break;
                case 2:
                    statString += "HP regen";
                    break;
                case 3:
                    statString += "Movement speed";
                    break;
                case 4:
                    statString += "Armor";
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

    public int getStat1Type() {
        return stat1Type;
    }

    public void setStat1Type(int stat1Type) {
        this.stat1Type = stat1Type;
    }

    public int getStat1Effect() {
        return stat1Effect;
    }

    public void setStat1Effect(int stat1Effect) {
        this.stat1Effect = stat1Effect;
    }

    public int getStat2Type() {
        return stat2Type;
    }

    public void setStat2Type(int stat2Type) {
        this.stat2Type = stat2Type;
    }

    public int getStat2Effect() {
        return stat2Effect;
    }

    public void setStat2Effect(int stat2Effect) {
        this.stat2Effect = stat2Effect;
    }

    public int getStat3Type() {
        return stat3Type;
    }

    public void setStat3Type(int stat3Type) {
        this.stat3Type = stat3Type;
    }

    public int getStat3Effect() {
        return stat3Effect;
    }

    public void setStat3Effect(int stat3Effect) {
        this.stat3Effect = stat3Effect;
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
