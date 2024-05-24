package org.example.osadnici;

public class Building {
    public PawnType type;
    public Integer owner;

    public Building(PawnType type, Integer owner){
        this.owner = owner;
        this.type = type;
    }
}
