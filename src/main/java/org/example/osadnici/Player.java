package org.example.osadnici;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Player {
    private int uniqueIndex;
    public Dictionary<Material, MaterialCards> cardsList;
    public Player(int uniqueIndex){
        this.uniqueIndex = uniqueIndex;
        initCards();
    }
    private void initCards() {
        cardsList = new Hashtable<Material, MaterialCards>();
        for(var material : Material.values()){
            cardsList.put(material, new MaterialCards());
        }

    }

}
