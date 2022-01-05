package People;

import Misc.City;
import Schools.HighSchool;
import Schools.University;

import java.util.*;

public class Player extends Person {
    // Define enum of position
    public enum Position {
        QB, RB, WR, TE, FB, T, G, C, //OFFENSE
        DE, DT, NT, LB, CB, S, ATH, //DEFENSE AND ATHLETE
        P, K
    }
    // General info fields
    public String name; public University university; public HighSchool hs;
    public int age; public Position position; double avgRating;

    // Private fields
    private List<Traits> traits = new ArrayList<Traits>();

    // Attribute fields
    private double speed = 1;
    private double agility = 1;
    private double acceleration = 1;
    private double strength = 1;
    private double jumping = 1;
    private double aggressiveness = 1;

    // Mentality
    private double composure = 1;
    private double vision = 1;

    // QB Stats
    private double arm_power = 1;
    private double accuracy = 1;

    // WR Stats
    private double catching = 1;

    // Kicking Stats
    private double kick_power = 1;
    private double kick_accuracy = 1;

    // Defensive Stats
    private double tackling = 1;
    private double pass_rush = 1;
    private double run_defense = 1;

    // Blocking Stats
    private double run_block = 1;
    private double pass_block = 1;

    // Constructors
    public Player() {
        super();
        this.position = Position.ATH;
    }

    public Player(String name, int age, HighSchool hs, City hometown) {
        super(name, age, hs, hometown);
        this.position = Position.ATH;
    }

    public Player(String name, int age, HighSchool hs, City hometown, Position pos) {
        super(name, age, hs, hometown);
        this.position = pos;
    }

    public Player(String name, int age, HighSchool hs, City hometown, String pos) {
        super(name, age, hs, hometown);
        try {
            this.position = Position.valueOf(pos);
        } catch (Exception e) {
            System.out.println("Invalid position; setting to athlete");
            this.position = Position.ATH;
        }
    }

    public Player(String name, int age, University university, HighSchool hs, City hometown) {
        super(name, age, university, hs, hometown);
        this.position = Position.ATH;
    }

    public Player(String name, int age, University university, HighSchool hs, City hometown, Position pos) {
        super(name, age, university, hs, hometown);
        this.position = pos;
    }

    public Player(String name, int age, University university, HighSchool hs, City hometown, String pos) {
        super(name, age, university, hs, hometown);
        try {
            this.position = Position.valueOf(pos);
        } catch (Exception e) {
            System.out.println("Invalid position; setting to athlete");
            this.position = Position.ATH;
        }
    }

    // Trait adder functions
    public void addTrait(String trait) {
        try {
            Traits tmpTrait = Traits.valueOf(trait);
            addTrait(tmpTrait);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Function to calculate rating
    public double getRating() {

        // First calculate different ratings
        double physicalRating = (this.speed + this.agility + this.acceleration + this.jumping + this.strength)/5;
        double qbRating = (this.arm_power + this.accuracy)/2;
        double wrRating = (this.catching);
        double kickingRating = (this.kick_accuracy + this.kick_power)/2;
        double defensiveRating = (this.tackling + this.run_defense + this.pass_rush)/3;
        double blockingRating = (this.pass_block + this.run_block);

        // Calculate ratings for each position TODO: Balance
        switch (this.position){
            case ATH:
                // Take max of block, defense, qb and wr ratings
                List<Double> ratings = Arrays.asList(blockingRating, defensiveRating, wrRating, qbRating);
                Collections.sort(ratings);
                double maxAthRating = ratings.get(ratings.size() - 1);
                this.avgRating = (physicalRating + maxAthRating) / 2;
                break;
            case C: case T: case G:
                double wBl = 1.0; double wStr = 0.8;
                this.avgRating = (wBl * blockingRating + wStr * this.strength)/(wBl + wStr);
                break;
            case K:
                double wKS = 0.5; double wKA = 1.0;
                this.avgRating = (wKS * this.kick_power + wKA * this.kick_accuracy);
                break;
            case P:
                wKS = 1.0; wKA = 0.5;
                this.avgRating = (wKS * this.kick_power + wKA * this.kick_accuracy);
                break;
            case QB:
                double wQB = 1.0; double wPhy = 0.9;
                double updatedPhysical = (0.4 * this.speed + 0.7 * this.agility + 0.3 * this.acceleration + 0.1 * this.jumping + 0.7 * this.strength)/2.2;
                this.avgRating = (wQB * qbRating + wPhy * updatedPhysical)/(wQB + wPhy);
                break;
            case RB:
                double wWR = 0.3; wBl = 0.8; wPhy = 1.0;
                double updatedBlocking = (1.0 * this.run_block + 0.05 * this.pass_block)/(1.05);
                this.avgRating = (wWR * wrRating + wBl * updatedBlocking + wPhy * physicalRating)/(wWR + wBl + wPhy);
            case WR:
                wWR = 1.2; wBl = 0.6; wPhy = 0.8;
                updatedBlocking = (0.05 * this.run_block + 1.0 * this.pass_block)/(1.05);
                this.avgRating = (wWR * wrRating + wBl * updatedBlocking + wPhy * physicalRating)/(wWR + wBl + wPhy);
                break;
            case TE:
                wWR = 1.0; wBl = 0.8; wPhy = 0.8;
                updatedBlocking = (0.8 * this.run_block + 1.0 * this.pass_block)/(1.3);
                this.avgRating = (wWR * wrRating + wBl * updatedBlocking + wPhy * physicalRating)/(wWR + wBl + wPhy);
                break;
            case FB:
                wWR = 0.1; wBl = 1.2; wPhy = 1.0;
                updatedBlocking = (1.0 * this.run_block + 1.0 * this.pass_block)/(2.0);
                this.avgRating = (wWR * wrRating + wBl * updatedBlocking + wPhy * physicalRating)/(wWR + wBl + wPhy);
                break;
            case CB:
                wPhy = 1.0; double wDef = 1.1;
                double updatedDefense = (0.6 * this.tackling + 0.4 * this.run_defense + 0.5 * this.pass_rush)/1.5;
                this.avgRating = (wPhy * physicalRating + wDef * updatedDefense)/(wPhy + wDef);
                break;
            case S:
                wPhy = 1.0; wDef = 1.1;
                updatedDefense = (0.6 * this.tackling + 0.4 * this.run_defense + 0.7 * this.pass_rush)/1.7;
                this.avgRating = (wPhy * physicalRating + wDef * updatedDefense)/(wPhy + wDef);
                break;
            case DE: case DT: case NT:
                wPhy = 1.5; wDef = 1.0;
                updatedPhysical = (0.2 * this.speed + 0.4 * this.agility + 0.4 * this.acceleration + 0.1 * this.jumping + 1.2 * this.strength)/2.3;
                this.avgRating = (wPhy * updatedPhysical + wDef * defensiveRating)/(wPhy + wDef);
                break;
            case LB:
                wPhy = 1.2; wDef = 2.0;
                updatedDefense = (1.2 * this.tackling + 0.7 * this.run_defense + 0.7 * this.pass_rush)/2.6;
                this.avgRating = (wPhy * physicalRating + wDef * updatedDefense)/(wPhy + wDef);
        }

        return this.avgRating;
    }

    // Function to set attributes with map
    public void setAttributes(Map attributes) {
        if (attributes.containsKey("speed")) { setSpeed((double) attributes.get("speed")); }
        if (attributes.containsKey("agility")) { setAgility((double) attributes.get("agility")); }
        if (attributes.containsKey("acceleration")) { setAcceleration((double) attributes.get("acceleration")); }
        if (attributes.containsKey("strength")) { setStrength((double) attributes.get("strength")); }
        if (attributes.containsKey("jumping")) { setJumping((double) attributes.get("jumping")); }
        if (attributes.containsKey("aggressiveness")) { setAggressiveness((double) attributes.get("aggressiveness")); }
        if (attributes.containsKey("composure")) { setComposure((double) attributes.get("composure")); }
        if (attributes.containsKey("vision")) { setVision((double) attributes.get("vision")); }
        if (attributes.containsKey("arm_power")) { setArm_power((double) attributes.get("arm_power")); }
        if (attributes.containsKey("accuracy")) { setAccuracy((double) attributes.get("accuracy")); }
        if (attributes.containsKey("catching")) { setCatching((double) attributes.get("catching")); }
        if (attributes.containsKey("kick_power")) { setKick_power((double) attributes.get("kick_power")); }
        if (attributes.containsKey("kick_accuracy")) { setKick_accuracy((double) attributes.get("kick_accuracy"));}
        if (attributes.containsKey("tackling")) { setTackling((double) attributes.get("tackling")); }
        if (attributes.containsKey("pass_rush")) { setPass_rush((double) attributes.get("pass_rush")); }
        if (attributes.containsKey("run_defense")) { setRun_defense((double) attributes.get("run_defense")); }
        if (attributes.containsKey("run_block")) { setRun_block((double) attributes.get("run_block")); }
        if (attributes.containsKey("pass_block")) { setPass_block((double) attributes.get("pass_block")); }
















    }

    // Function to add trait and apply its effects on rating
    public void addTrait(Traits trait) {
        switch (trait) {
            case CALM:
                this.setComposure(this.composure + 15.0);
                break;
            case BRAVE:
                this.setAggressiveness(this.aggressiveness + 15.0);
                this.setComposure(this.composure + 10.0);
                break;
            case ANXIOUS:
                this.setComposure(this.composure - 10.0);
                break;
            case INTELLIGENT:
                this.setVision(this.vision + 25.0);
                break;
            case LEADER:
                this.setComposure(this.composure + 20.0);
                this.setVision(this.vision + 10.0);
                break;
            case COWARDLY:
                this.setComposure(this.composure - 15.0);
                break;
            case AMBITIOUS:
                this.setAggressiveness(this.aggressiveness + 10.0);
                break;


        }
        this.traits.add(trait);
    }

    public void addTrait(List<Traits> traits) {
        for (int i = 0; i < traits.size(); i++) {
            this.addTrait(traits.get(i));
        }
    }

    // Attribute setters (update overalls after updating ratings as well)
    public void setSpeed(double speed) { this.speed = speed; this.getRating(); }
    public void setAgility(double agility) { this.agility = agility; this.getRating(); }
    public void setAcceleration(double accel) { this.acceleration = accel; this.getRating(); }
    public void setStrength(double strength) { this.strength = strength; this.getRating(); }
    public void setJumping(double jumping) { this.jumping = jumping; this.getRating(); }
    public void setAggressiveness(double aggressiveness) { this.aggressiveness = aggressiveness; this.getRating(); }
    public void setComposure(double composure) { this.composure = composure; this.getRating(); }
    public void setVision(double vision) { this.vision = vision; this.getRating(); }
    public void setArm_power(double arm_power) { this.arm_power = arm_power; this.getRating(); }
    public void setAccuracy(double accuracy) { this.accuracy = accuracy; this.getRating(); }
    public void setCatching(double catching) { this.catching = catching; this.getRating(); }
    public void setKick_power(double kick_power) { this.kick_power = kick_power; this.getRating(); }
    public void setKick_accuracy(double kick_accuracy) { this.kick_accuracy = kick_accuracy; this.getRating(); }
    public void setTackling(double tackling) { this.tackling = tackling; this.getRating(); }
    public void setRun_defense(double run_defense) { this.run_defense = run_defense; this.getRating(); }
    public void setRun_block(double run_block) { this.run_block = run_block; this.getRating(); }
    public void setPass_rush(double pass_rush) { this.pass_rush = pass_rush; this.getRating(); }
    public void setPass_block(double pass_block) { this.pass_block = pass_block; this.getRating(); }

}
