package org.example.osadnici;

import java.util.HashMap;

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
    }
    private boolean roadPosValid(){
        // TODO
    }
    public void startRoad(UserInterface UI) {
        // TODO
        UI.displayBuildBoard();
        int buildPosition = UI.getBuildNumber();
        while (!roadPosValid()){
            buildPosition = UI.getBuildNumber();
        }
        board.createRoad(buildPosition);

    }
    public int startVillage(UserInterface UI) {
        // TODO
        UI.displayBuildBoard();
        int buildPosition = UI.getBuildNumber();
        while (!villagePosValid()){
            buildPosition = UI.getBuildNumber();
        }
        board.createVillage(buildPosition);
        return buildPosition;

    }

    public void giveStartCards(int position) {
    }
}
