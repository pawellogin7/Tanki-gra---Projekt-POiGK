package tanksgame;


public class Ability {
    private double baseDuration, baseCooldown; 
    private double cooldown, duration, silencedTime;
    private StatusEffects status = new StatusEffects();
    private int tier, cost;
    private String name, statusName;
    
    //Ability has status effect, duration and cooldown
    Ability(double dur, double cool) {
        baseDuration = dur;
        baseCooldown = cool;
        cooldown = 0;
        duration = 0;
    }
    
    public void update() {
        if(duration != 0) {
            duration -= 0.017;
            if(duration < 0)
                duration = 0;
        }
        
        if(cooldown != 0 && duration == 0) {
            cooldown -= 0.017;
            if(cooldown < 0)
                cooldown = 0;
        }
    }
    
    StatusEffects effects() {
        cooldown = baseCooldown;
        duration = baseDuration;
        return status;
    }
    
    int getPercent() {
        int percent = 0;
        if(duration == 0)
            percent = (int) Math.floor(1.0 * (baseCooldown - cooldown) / baseCooldown * 100);
        else
            percent =  100 - (int) Math.floor(1.0 * (baseDuration - duration) / baseDuration * 100);
        return percent;
    }

    //Getters and setters
    public double getBaseDuration() {
        return baseDuration;
    }

    public void setBaseDuration(double baseDuration) {
        this.baseDuration = baseDuration;
    }

    public double getBaseCooldown() {
        return baseCooldown;
    }

    public void setBaseCooldown(double baseCooldown) {
        this.baseCooldown = baseCooldown;
    }

    public double getCooldown() {
        return cooldown;
    }

    public void setCooldown(double cooldown) {
        this.cooldown = cooldown;
    }

    public double getSilencedTime() {
        return silencedTime;
    }

    public void setSilencedTime(double silencedTime) {
        this.silencedTime = silencedTime;
    }

    public StatusEffects getStatus() {
        return status;
    }

    public void setStatus(StatusEffects status) {
        this.status = status;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    
    
    
    
}
