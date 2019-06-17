package tanksgame;

//Status effects are buffs or debuffs give to player or enemy for short duration of time
public class StatusEffects {
    //Speed
    private boolean slowed = false; //sets move speed to 2 //ID 1
    private double slowedDuration = 0;
    private boolean paralyzed = false; //sets move speed to 0 //ID 2
    private double paralyzedDuration = 0;
    private boolean speedy = false; //increases move speed by 3 //ID 3
    private double speedyDuration = 0;
    private boolean sprint = false; //doubles move speed, cant shoot for the duration // ID 4
    private double sprintDuration = 0;
    
    //Armor
    private boolean armorBroken = false; //decreases armor by 50% //ID 10
    private double armorBrokenDuration = 0;
    private boolean fortified = false; //increases armor by 50% //ID 11
    private double fortifiedDuration = 0;
    private boolean armorBoost = false; //increases armor by 25% //ID 12
    private double armorBoostDuration = 0;
    
    //HP Regen
    private boolean regenBoost = false; //increases HP regen by 150% //ID 20
    private double regenBoostDuration = 0;
    private boolean regenReduction = false; //decreases HP regen by 100% //ID 21
    private double regenReductionDuration = 0;
    private boolean sabotage = false; //HP regen deals damage instead of healing //ID 22
    private double sabotageDuration = 0;
    
    //Damage
    private boolean damageBoost = false; //increases weapon damage by 25% //ID 30
    private double damageBoostDuration = 0;
    private boolean damageReduction = false; //decreases weapon damage by 30% //ID 31
    private double damageReductionDuration = 0;
    
    //Reload
    private boolean weaponJammed = false; //player cant shoot //ID 40
    private double weaponJammedDuration = 0; 
    private boolean reloadBoost = false; //decreases weapon reload by 30% //ID 41
    private double reloadBoostDuration = 0;
    private boolean reloadReduction = false; //increases weapon reload by 100% //ID 42
    private double reloadReductionDuration = 0;
    
    StatusEffects() {
    }
    
    public void update() {
        updateSpeedEffects();
        updateArmorEffects();
        updateRegenEffects();
        updateDamageEffects();
        updateReloadEffects();
    }
    
    public void updateSpeedEffects() {
        if(slowedDuration <= 0.0) {
            slowed = false;
            slowedDuration = 0.0;
        }
        else {
            slowed = true;
            slowedDuration -= 0.017;
        }
        
        if(paralyzedDuration <= 0.0) {
            paralyzed = false;
            paralyzedDuration = 0.0;
        }
        else {
            paralyzed = true;
            paralyzedDuration -= 0.017;
        }
        
        if(speedyDuration <= 0.0) {
            speedy = false;
            speedyDuration = 0.0;
        }
        else {
            speedy = true;
            speedyDuration -= 0.017;
        }
        
        if(sprintDuration <= 0.0) {
            sprint = false;
            sprintDuration = 0.0;
        }
        else {
            sprint = true;
            sprintDuration -= 0.017;
        }
    }
    
    public void updateArmorEffects() {
        if(armorBrokenDuration <= 0.0) {
            armorBroken = false;
            armorBrokenDuration = 0.0;
        }
        else {
            armorBroken = true;
            armorBrokenDuration -= 0.017;
        }
        
        if(armorBoostDuration <= 0.0) {
            armorBoost = false;
            armorBoostDuration = 0.0;
        }
        else {
            armorBoost = true;
            armorBoostDuration -= 0.017;
        }
        
        if(fortifiedDuration <= 0.0) {
            fortified = false;
            fortifiedDuration = 0.0;
        }
        else {
            fortified = true;
            fortifiedDuration -= 0.017;
        }   
    }
    
    public void updateRegenEffects() {
        if(regenBoostDuration <= 0.0) {
            regenBoost = false;
            regenBoostDuration = 0.0;
        }
        else {
            regenBoost = true;
            regenBoostDuration -= 0.017;
        }
        
        if(regenReductionDuration <= 0.0) {
            regenReduction = false;
            regenReductionDuration = 0.0;
        }
        else {
            regenReduction = true;
            regenReductionDuration -= 0.017;
        }
        
        if(sabotageDuration <= 0.0) {
            sabotage = false;
            sabotageDuration = 0.0;
        }
        else {
            sabotage = true;
            sabotageDuration -= 0.017;
        }   
    }
    
    public void updateDamageEffects() {
        if(damageBoostDuration <= 0.0) {
            damageBoost = false;
            damageBoostDuration = 0.0;
        }
        else {
            damageBoost = true;
            damageBoostDuration -= 0.017;
        }
        
        if(damageReductionDuration <= 0.0) {
            damageReduction = false;
            damageReductionDuration = 0.0;
        }
        else {
            damageReduction = true;
            damageReductionDuration -= 0.017;
        } 
    }

    public void updateReloadEffects() {
        if(weaponJammedDuration <= 0.0) {
            weaponJammed = false;
            weaponJammedDuration = 0.0;
        }
        else {
            weaponJammed = true;
            weaponJammedDuration -= 0.017;
        }
        
        if(reloadBoostDuration <= 0.0) {
            reloadBoost = false;
            reloadBoostDuration = 0.0;
        }
        else {
            reloadBoost = true;
            reloadBoostDuration -= 0.017;
        }
        
        if(reloadReductionDuration <= 0.0) {
            reloadReduction = false;
            reloadReductionDuration = 0.0;
        }
        else {
            reloadReduction = true;
            reloadReductionDuration -= 0.017;
        } 
    }
    
    //Add status effects to player or enemy
    public void changeStatus(StatusEffects statusEvent) {
        //Speed
        if (statusEvent.isParalyzed())
            paralyzedDuration = statusEvent.getParalyzedDuration();
        else if (statusEvent.isSlowed())
            slowedDuration = statusEvent.getSlowedDuration();
        else if (statusEvent.isSpeedy())
            speedyDuration = statusEvent.getSpeedyDuration();
        else if (statusEvent.isSprint())
            sprintDuration = statusEvent.getSprintDuration();
        
        //Armor
        if (statusEvent.isArmorBoost())
            armorBoostDuration = statusEvent.getArmorBoostDuration();
        
        if (statusEvent.isArmorBroken())
            armorBrokenDuration = statusEvent.getArmorBrokenDuration();
        else if (statusEvent.isFortified())
            fortifiedDuration = statusEvent.getFortifiedDuration();

        //HP Regen
        if (statusEvent.isSabotage())
            sabotageDuration = statusEvent.getSabotageDuration();
        
        if (statusEvent.isRegenReduction())
            regenReductionDuration = statusEvent.getRegenReductionDuration();
        else if (statusEvent.isRegenBoost())
            regenBoostDuration = statusEvent.getRegenBoostDuration();
    
        //Damage
        if (statusEvent.isDamageReduction())
            damageReductionDuration = statusEvent.getDamageReductionDuration();
        else if (statusEvent.isDamageBoost())
            damageBoostDuration = statusEvent.getDamageBoostDuration();
          
        //Reload
        if (statusEvent.isWeaponJammed())
            weaponJammedDuration = statusEvent.getWeaponJammedDuration();
        else if (statusEvent.isReloadBoost())
            reloadBoostDuration = statusEvent.getReloadBoostDuration();
        else if (statusEvent.isReloadReduction())
            reloadReductionDuration = statusEvent.getReloadReductionDuration();
    }
    
    //Reset status effects of player or enemy
    public void resetStatus(StatusEffects statusEvent) {
        //Speed
        if (statusEvent.isParalyzed())
            paralyzedDuration = 0;
        else if (statusEvent.isSlowed())
            slowedDuration = 0;
        else if (statusEvent.isSpeedy())
            speedyDuration = 0;
        else if (statusEvent.isSprint())
            sprintDuration = 0;
        
        //Armor
        if (statusEvent.isArmorBoost())
            armorBoostDuration = 0;
        
        if (statusEvent.isArmorBroken())
            armorBrokenDuration = 0;
        else if (statusEvent.isFortified())
            fortifiedDuration = 0;

        //HP Regen
        if (statusEvent.isSabotage())
            sabotageDuration = 0;
        
        if (statusEvent.isRegenReduction())
            regenReductionDuration = 0;
        else if (statusEvent.isRegenBoost())
            regenBoostDuration = 0;
    
        //Damage
        if (statusEvent.isDamageReduction())
            damageReductionDuration = 0;
        else if (statusEvent.isDamageBoost())
            damageBoostDuration = 0;
          
        //Reload
        if (statusEvent.isWeaponJammed())
            weaponJammedDuration = 0;
        else if (statusEvent.isReloadBoost())
            reloadBoostDuration = 0;
        else if (statusEvent.isReloadReduction())
            reloadReductionDuration = 0;
    }
    
    //Required to send bullet status effects between sockets during multiplayer matches
    public int getSingleStatusID() {
        if(slowed) {
            return 1;
        }
        else if(paralyzed) {
            return 2;
        }
        else if(speedy) {
            return 3;
        }
        else if(sprint) {
            return 4;
        }
        else if(armorBroken) {
            return 10;
        }
        else if(fortified) {
            return 11;
        }
        else if(armorBoost) {
            return 12;
        }
        else if(regenBoost) {
            return 20;
        }
        else if(regenReduction) {
            return 21;
        }
        else if(sabotage) {
            return 22;
        }
        else if(damageBoost) {
            return 30;
        }
        else if(damageReduction) {
            return 31;
        }
        else if(regenReduction) {
            return 21;
        }
        else if(weaponJammed) {
            return 40;
        }
        else if(reloadBoost) {
            return 41;
        }
        else if(reloadReduction) {
            return 42;
        }
        
        return 0;
    }
    
    public double getSingleStatusDuration(int id) {
        switch(id) {
            case 1:
                return slowedDuration;
            case 2:
                return paralyzedDuration;
            case 3:
                return speedyDuration;
            case 4:
                return sprintDuration;
            case 10:
                return armorBrokenDuration;
            case 11:
                return fortifiedDuration;
            case 12:
                return armorBoostDuration;  
            case 20:
                return regenBoostDuration;
            case 21:
                return regenReductionDuration;
            case 22:
                return sabotageDuration;    
            case 30:
                return damageBoostDuration;
            case 31:
                return damageReductionDuration;
            case 40:
                return weaponJammedDuration;
            case 41:
                return reloadBoostDuration;
            case 42:
                return reloadReductionDuration;
        }
        return 0;
    }
    
    public void setSingleStatus(int id, double duration) {
        switch(id) {
            case 1:
                slowed = true;
                slowedDuration = duration;
                break;
            case 2:
                paralyzed = true;
                paralyzedDuration = duration;
                break;
            case 3:
                speedy = true;
                speedyDuration = duration;
                break;
            case 4:
                sprint = true;
                sprintDuration = duration;
                break;
            case 10:
                armorBroken = true;
                armorBrokenDuration = duration;
                break;
            case 11:
                fortified = true;
                fortifiedDuration = duration;
                break;
            case 12:
                armorBoost = true;
                armorBoostDuration = duration;
                break;  
            case 20:
                regenBoost = true;
                regenBoostDuration = duration;
                break;
            case 21:
                regenReduction = true;
                regenReductionDuration = duration;
                break;
            case 22:
                sabotage = true;
                sabotageDuration = duration;
                break;   
            case 30:
                damageBoost = true;
                damageBoostDuration = duration;
                break;
            case 31:
                damageReduction = true;
                damageReductionDuration = duration;
                break;
            case 40:
                weaponJammed = true;
                weaponJammedDuration = duration;
                break;
            case 41:
                reloadBoost = true;
                reloadBoostDuration = duration;
                break;
            case 42:
                reloadReduction = true;
                reloadReductionDuration = duration;
                break;
        }
    }
    
    
    //Getters and setters
    
    //Speed
    public boolean isSlowed() {
        return slowed;
    }

    public void setSlowed(boolean slowed) {
        this.slowed = slowed;
    }

    public double getSlowedDuration() {
        return slowedDuration;
    }

    public void setSlowedDuration(double slowedDuration) {
        this.slowedDuration = slowedDuration;
    }

    public boolean isParalyzed() {
        return paralyzed;
    }

    public void setParalyzed(boolean paralyzed) {
        this.paralyzed = paralyzed;
    }

    public double getParalyzedDuration() {
        return paralyzedDuration;
    }

    public void setParalyzedDuration(double paralyzedDuration) {
        this.paralyzedDuration = paralyzedDuration;
    }

    public boolean isSpeedy() {
        return speedy;
    }

    public void setSpeedy(boolean speedy) {
        this.speedy = speedy;
    }

    public double getSpeedyDuration() {
        return speedyDuration;
    }

    public void setSpeedyDuration(double speedyDuration) {
        this.speedyDuration = speedyDuration;
    }

    public boolean isSprint() {
        return sprint;
    }

    public void setSprint(boolean sprint) {
        this.sprint = sprint;
    }

    public double getSprintDuration() {
        return sprintDuration;
    }

    public void setSprintDuration(double sprintDuration) {
        this.sprintDuration = sprintDuration;
    }

    //Armor
    public boolean isArmorBroken() {
        return armorBroken;
    }

    public void setArmorBroken(boolean armorBroken) {
        this.armorBroken = armorBroken;
    }

    public double getArmorBrokenDuration() {
        return armorBrokenDuration;
    }

    public void setArmorBrokenDuration(double armorBrokenDuration) {
        this.armorBrokenDuration = armorBrokenDuration;
    }

    public boolean isFortified() {
        return fortified;
    }

    public void setFortified(boolean fortified) {
        this.fortified = fortified;
    }

    public double getFortifiedDuration() {
        return fortifiedDuration;
    }

    public void setFortifiedDuration(double fortifiedDuration) {
        this.fortifiedDuration = fortifiedDuration;
    }

    public boolean isArmorBoost() {
        return armorBoost;
    }

    public void setArmorBoost(boolean armorBoost) {
        this.armorBoost = armorBoost;
    }

    public double getArmorBoostDuration() {
        return armorBoostDuration;
    }

    public void setArmorBoostDuration(double armorBoostDuration) {
        this.armorBoostDuration = armorBoostDuration;
    }
    
    //HP regen
    public boolean isRegenBoost() {    
        return regenBoost;
    }

    public void setRegenBoost(boolean regenBoost) {
        this.regenBoost = regenBoost;
    }

    public double getRegenBoostDuration() {
        return regenBoostDuration;
    }

    public void setRegenBoostDuration(double regenBoostDuration) {
        this.regenBoostDuration = regenBoostDuration;
    }

    public boolean isRegenReduction() {
        return regenReduction;
    }

    public void setRegenReduction(boolean regenReduction) {
        this.regenReduction = regenReduction;
    }

    public double getRegenReductionDuration() {
        return regenReductionDuration;
    }

    public void setRegenReductionDuration(double regenReductionDuration) {
        this.regenReductionDuration = regenReductionDuration;
    }

    public boolean isSabotage() {
        return sabotage;
    }

    public void setSabotage(boolean sabotage) {
        this.sabotage = sabotage;
    }

    public double getSabotageDuration() {
        return sabotageDuration;
    }

    public void setSabotageDuration(double sabotageDuration) {
        this.sabotageDuration = sabotageDuration;
    }

    

    //Damage
    public boolean isDamageBoost() {
        return damageBoost;
    }

    public void setDamageBoost(boolean damageBoost) {
        this.damageBoost = damageBoost;
    }

    public double getDamageBoostDuration() {
        return damageBoostDuration;
    }

    public void setDamageBoostDuration(double damageBoostDuration) {
        this.damageBoostDuration = damageBoostDuration;
    }

    public boolean isDamageReduction() {
        return damageReduction;
    }

    public void setDamageReduction(boolean damageReduction) {
        this.damageReduction = damageReduction;
    }

    public double getDamageReductionDuration() {
        return damageReductionDuration;
    }

    public void setDamageReductionDuration(double damageReductionDuration) {
        this.damageReductionDuration = damageReductionDuration;
    }
    
    
    
    //Reload
    public boolean isWeaponJammed() {
        return weaponJammed;
    }

    public void setWeaponJammed(boolean weaponJammed) {
        this.weaponJammed = weaponJammed;
    }

    public double getWeaponJammedDuration() {
        return weaponJammedDuration;
    }

    public void setWeaponJammedDuration(double weaponJammedDuration) {
        this.weaponJammedDuration = weaponJammedDuration;
    }

    public boolean isReloadBoost() {
        return reloadBoost;
    }

    public void setReloadBoost(boolean reloadBoost) {
        this.reloadBoost = reloadBoost;
    }

    public double getReloadBoostDuration() {
        return reloadBoostDuration;
    }

    public void setReloadBoostDuration(double reloadBoostDuration) {
        this.reloadBoostDuration = reloadBoostDuration;
    }

    public boolean isReloadReduction() {
        return reloadReduction;
    }

    public void setReloadReduction(boolean reloadReduction) {
        this.reloadReduction = reloadReduction;
    }

    public double getReloadReductionDuration() {
        return reloadReductionDuration;
    }

    public void setReloadReductionDuration(double reloadReductionDuration) {
        this.reloadReductionDuration = reloadReductionDuration;
    }

  
}
