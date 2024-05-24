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
        giveStartCards();
        points = 0;
    }
    private void initPawns(){
        pawnList = new HashMap<PawnType, PawnSet>();
        pawnList.put(PawnType.Road, new PawnSet(15));
        pawnList.put(PawnType.Village, new PawnSet( 5));
        pawnList.put(PawnType.Town, new PawnSet(4));
    }
    private void initCards() {
        cardsList = new HashMap<Material, MaterialCards>();
        for(var material : Material.values()){
            cardsList.put(material, new MaterialCards());
        }

    }
    public int countCardSum(){
        int sum = 0;
        for (var cardKey : cardsList.keySet()){
            sum += cardsList.get(cardKey).numberOfCards;
        }
        return sum;
    }
    public void giveCards(int numberOfCards, Material material){
        cardsList.get(material).numberOfCards += numberOfCards;
    }
    public void loseCards(){
        int cardSum = countCardSum();
        int remainingCards = cardSum / 2;;
        for (var cardKey : cardsList.keySet()){
            if (remainingCards == 0)
                break;

            if (cardsList.get(cardKey).numberOfCards > 0){
                if (cardsList.get(cardKey).numberOfCards >= remainingCards){
                    cardsList.get(cardKey).numberOfCards -= remainingCards;
                    break;
                }
                else{
                    remainingCards -= cardsList.get(cardKey).numberOfCards;
                    cardsList.get(cardKey).numberOfCards = 0;
                }
            }
        }
    }
    private boolean villagePosValid(){
        return true;
    }
    private boolean roadPosValid(){
        return true;
    }


    private boolean buyVillage(){
        if (cardsList.get(Material.Brick).numberOfCards < 1){
            return false;
        }
        if (cardsList.get(Material.Wood).numberOfCards < 1){
            return false;
        }
        if (cardsList.get(Material.Sheep).numberOfCards < 1){
            return false;
        }
        if (cardsList.get(Material.Wheat).numberOfCards < 1){
            return false;
        }

        cardsList.get(Material.Brick).numberOfCards --;
        cardsList.get(Material.Wood).numberOfCards --;
        cardsList.get(Material.Sheep).numberOfCards --;
        cardsList.get(Material.Wheat).numberOfCards --;
        return true;
    }
    private boolean buyTown(){
        if (cardsList.get(Material.Stone).numberOfCards < 3){
            return false;
        }
        if (cardsList.get(Material.Wheat).numberOfCards < 2){
            return false;
        }

        cardsList.get(Material.Stone).numberOfCards -= 3;
        cardsList.get(Material.Wheat).numberOfCards -= 2;
        return true;
    }
    private boolean buyRoad(){
        if (cardsList.get(Material.Brick).numberOfCards < 1){
            return false;
        }
        if (cardsList.get(Material.Wood).numberOfCards < 1){
            return false;
        }

        cardsList.get(Material.Brick).numberOfCards -= 1;
        cardsList.get(Material.Wood).numberOfCards -= 1;
        return true;
    }
    private void evaluateWinner(UserInterface UI){
        if (points == 10){
            UI.printWinner();
        }
    }
    private void buildTown(UserInterface UI, Board board){
        UI.displayBuildBoard();
        int buildPosition = UI.getBuildNumber("town");
        if (buildPosition == -1) { // exit command
            return;
        }
        while (!villagePosValid()){
            buildPosition = UI.getBuildNumber("town");
        }
        board.createTown(buildPosition);
        pawnList.get(PawnType.Village).numberOfPawns ++;
        pawnList.get(PawnType.Town).numberOfPawns --;
        points++;
        evaluateWinner(UI);
    }
    private void buildRoad(UserInterface UI, Board board){
        UI.displayBuildBoard();
        var position = UI.getRoadNumbers();
        if (position.start == -1 || position.end == -1) { // exit command
            return;
        }
        while (!villagePosValid()){
            position = UI.getRoadNumbers();
            if (position.start == -1 || position.end == -1) { // exit command
                return;
            }
        }
        board.createRoad(position, uniqueIndex);
        pawnList.get(PawnType.Road).numberOfPawns --;
    }
    private void buildVillage(UserInterface UI, Board board){
        UI.displayBuildBoard();
        int buildPosition = UI.getBuildNumber("village");
        if (buildPosition == -1) { // exit command
            return;
        }
        while (!villagePosValid()){
            buildPosition = UI.getBuildNumber("village");
        }
        board.createVillage(buildPosition, uniqueIndex);
        pawnList.get(PawnType.Village).numberOfPawns --;
        points++;
        evaluateWinner(UI);

    }
    public void startBuy(UserInterface UI, Board board){
        String request = UI.getBuyRequest();
        switch (request){
            case "stop":
                UI.unsuccessfulBuying();
                return;
            case "village":
                if (buyVillage()){
                    UI.successfulBuying();
                    buildVillage(UI, board);
                }
                else{
                    UI.unsuccessfulBuying();

                }
            case "town":
                if (buyTown()){
                    UI.successfulBuying();
                    buildTown(UI, board);
                }
                else{
                    UI.unsuccessfulBuying();

                }case "road":
                if (buyRoad()){
                    UI.successfulBuying();
                    buildRoad(UI, board);
                }
                else{
                    UI.unsuccessfulBuying();

                }
        }
        UI.unsuccessfulBuying();
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
    Position roadPosition = UI.getRoadNumbers();
    while (!roadPosValid()){
        roadPosition = UI.getRoadNumbers();
    }
    board.createRoad(roadPosition, uniqueIndex);
    pawnList.get(PawnType.Road).numberOfPawns --;

}
public int startVillage(UserInterface UI, Board board) {
    UI.displayBuildBoard();
    int buildPosition = UI.getBuildNumber("village");
    while (!villagePosValid()){
        buildPosition = UI.getBuildNumber("village");
    }
    board.createVillage(buildPosition, uniqueIndex);
    pawnList.get(PawnType.Village).numberOfPawns --;
    return buildPosition;

}

public void giveStartCards() {
        for (var material: Material.values()){
            cardsList.get(material).numberOfCards = 1;
        }
}
}
