package org.example.osadnici;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    private List<Integer> nodeGroup;
    private Material material;
    private int number;
    public Tile(List<Integer> nodeGroup, Material material, int number){
        this.nodeGroup = nodeGroup;
        this.number = number;
        this.material = material;
    }

    public int getNumber() {
        return number;
    }

    public List<Integer> getNodeGroup() {
        return nodeGroup;
    }

    public Material getMaterial() {
        return material;
    }
}
