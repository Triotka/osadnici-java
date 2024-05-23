package org.example.osadnici;

import java.util.HashMap;
import java.util.List;

public class Player {
    public int uniqueIndex;
    public int points;
    public HashMap<Material, MaterialCards> cardsList;
    public HashMap<PawnType, PawnSet> pawnList;
    public Player(int uniqueIndex){
        this.uniqueIndex = uniqueIndex;
        initCards();
        points = 0;
    }
    private void initPawns(){
        // TODO change according to rules
        pawnList = new HashMap<PawnType, PawnSet>();
        pawnList.put(PawnType.Road, new PawnSet(15));
        pawnList.put(PawnType.Village, new PawnSet( 10));
        pawnList.put(PawnType.Town, new PawnSet(10));
    }
    private void initCards() {
        cardsList = new HashMap<Material, MaterialCards>();
        for(var material : Material.values()){
            cardsList.put(material, new MaterialCards());
        }

    }
    private boolean villagePosValid(){
        // TODO
        return false;
    }
    private boolean roadPosValid(){
        // TODO
        return false;
    }

    public void startBuy(UserInterface UI, Board board){

    }
    private boolean hasResources(Material material) {
        return cardsList.get(material).numberOfCards > 0;
    }
    public void startSell(UserInterface UI, List<Player> players){
        int buyerIndex =  UI.getBuyerNumber(players.size());
        if (buyerIndex < 0 || buyerIndex >= players.size()) {
            UI.showInvalidSell();
            return;
        }
        try{
            var recievedMaterial =  Material.valueOf(UI.getMaterial("receive"));
            var soldMaterial =  Material.valueOf(UI.getMaterial("sell"));
            var buyer =  players.get(buyerIndex);
            if (!buyer.hasResources(recievedMaterial) ||  !this.hasResources(soldMaterial)) {
                UI.showInvalidSell();
            }
            else{
                buyer.cardsList.get(soldMaterial).numberOfCards ++;
                buyer.cardsList.get(recievedMaterial).numberOfCards --;
                this.cardsList.get(soldMaterial).numberOfCards --;
                this.cardsList.get(recievedMaterial).numberOfCards ++;
                UI.showValidSell();

            }
        }
        catch(Exception e){
            UI.showInvalidSell();
            return;
        }
}


public void startRoad(UserInterface UI, Board board) {
    UI.displayBuildBoard();
    int buildPosition = UI.getBuildNumber("road");
    while (!roadPosValid()){
        buildPosition = UI.getBuildNumber("road");
    }
    board.createRoad(buildPosition);
    pawnList.get(PawnType.Road).numberOfPawns --;

}
public int startVillage(UserInterface UI, Board board) {
    UI.displayBuildBoard();
    int buildPosition = UI.getBuildNumber("village");
    while (!villagePosValid()){
        buildPosition = UI.getBuildNumber("village");
    }
    board.createVillage(buildPosition);
    pawnList.get(PawnType.Village).numberOfPawns --;
    return buildPosition;

}

public void giveStartCards(int position) {
}
}
