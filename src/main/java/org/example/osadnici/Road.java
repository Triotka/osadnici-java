package org.example.osadnici;

public class Road {
    public int startPosition;
    public int endPosition;
    public int owner;
    public Road(int start, int end, int owner){
        startPosition = start;
        endPosition = end;
        owner = owner;
    }
}
