package org.example.osadnici;

import java.util.HashMap;

/**
 * Represents a player in the game.
 */
public class Player {
    public int uniqueIndex;
    public int points;
    public HashMap<Material, MaterialCards> cardsList;
    public HashMap<PawnType, PawnSet> pawnList;

    /**
     * Constructs a player with the given unique index.
     *
     * @param uniqueIndex The unique index of the player that represents his position in players' list
     */
    public Player(int uniqueIndex) {
        this.uniqueIndex = uniqueIndex;
        initCards();
        giveStartCards();
        points = 0;
        initPawns();
    }

    /**
     * Gives start pawns to a player with number according to game rules
     */
    private void initPawns() {
        pawnList = new HashMap<>();
        pawnList.put(PawnType.Road, new PawnSet(15));
        pawnList.put(PawnType.Village, new PawnSet(5));
        pawnList.put(PawnType.Town, new PawnSet(4));
    }

    /**
     * Initializes empty material cards
     */
    private void initCards() {
        cardsList = new HashMap<>();
        for (var material : Material.values()) {
            if (material != Material.Desert){
                cardsList.put(material, new MaterialCards());
            }

        }
    }

    /**
     * Counts sum of all player's cards
     * @return the sum
     */
    public int countCardSum() {
        int sum = 0;
        for (var cardKey : cardsList.keySet()) {
            sum += cardsList.get(cardKey).numberOfCards;
        }
        return sum;
    }

    /**
     * Gives player number of cards of requested material.
     *
     * @param numberOfCards The number of cards a player will receive
     * @param material type of material the given cards have
     */
    public void giveCards(int numberOfCards, Material material) {
        cardsList.get(material).numberOfCards += numberOfCards;
    }

    /**
     * Gets rid of half of the cards
     */
    public void loseCards() {
        int cardSum = countCardSum();
        int remainingCards = cardSum / 2;
        for (var cardKey : cardsList.keySet()) {
            if (remainingCards == 0)
                break;

            if (cardsList.get(cardKey).numberOfCards > 0) {
                if (cardsList.get(cardKey).numberOfCards >= remainingCards) {
                    cardsList.get(cardKey).numberOfCards -= remainingCards;
                    break;
                } else {
                    remainingCards -= cardsList.get(cardKey).numberOfCards;
                    cardsList.get(cardKey).numberOfCards = 0;
                }
            }
        }
    }

    /**
     * Checks if two build sites (buildings) are connected by a road.
     *
     * @param firstBuildingIndex  the index of the first build site (building)
     * @param secondBuildingIndex the index of the second build site (building)
     * @param board               the game board
     * @return true if the buildings are connected by a road, false otherwise
     */
    private boolean connectedByRoad(int firstBuildingIndex, int secondBuildingIndex, Board board) {
        // make sure first is lower index
        if (firstBuildingIndex > secondBuildingIndex) {
            int temp = firstBuildingIndex;
            firstBuildingIndex = secondBuildingIndex;
            secondBuildingIndex = temp;
        }

        var roadList = board.roads.get(firstBuildingIndex);
        if (roadList == null) {
            return false;
        }
        for (var road : roadList) {
            if (road.endPosition == secondBuildingIndex && road.owner == uniqueIndex) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if building a town at a particular position is valid according to rules
     * @param buildPosition the position to build the town
     * @param board the game board
     * @return true if building the town is valid, false otherwise
     */
    private boolean townPosValid(int buildPosition, Board board) {
        if (buildPosition < 1 || buildPosition >= board.buildings.size()) {
            return false;
        }
        var building = board.buildings.get(buildPosition);
        // building already exists here
        return building.owner == this.uniqueIndex;
    }

    /**
     * Checks if building a village at a particular position is valid.
     *
     * @param buildPosition the position to build the village
     * @param board         the game board
     * @param isStartRound  indicates if it's the start of the game round
     * @return true if building the village is valid, false otherwise
     */
    private boolean villagePosValid(int buildPosition, Board board, boolean isStartRound) {
        if (buildPosition < 1 || buildPosition >= board.buildings.size()) {
            return false;
        }
        var building = board.buildings.get(buildPosition);
        if (building.owner != null) { // building already exists here
            return false;
        }
        var neighbours = board.neighbourNodes.get(buildPosition);
        for (var neighbourIndex : neighbours) {
            // other building is too close
            var neighbour = board.buildings.get(neighbourIndex);
            if (neighbour.owner != null) {
                return false;
            }
        }
        if (!isStartRound) {
            for (var neighbourIndex : neighbours) {
                // connected by road
                if (connectedByRoad(neighbourIndex, buildPosition, board)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * Checks if building a road at a particular position is valid.
     *
     * @param position the position to build the road
     * @param board    the game board
     * @return true if building the road is valid, false otherwise
     */
    private boolean roadPosValid(Position position, Board board) {
        if (position.start < 1 || position.end < 1 || position.start > 54 || position.end > 54) {
            return false;
        }

        if (position.start > position.end) {
            int temp = position.start;
            position.start = position.end;
            position.end = temp;
        }

        if (!board.neighbourNodes.get(position.start).contains(position.end)) { // nodes are not neighbours
            return false;
        }
        var listRoad = board.roads.get(position.start);

        if (listRoad != null) {
            for (var road : listRoad) {
                if (road.endPosition == position.end) { // road already exists
                    return false;
                }
            }
        }

        // player's building is not present
        var startOwner = board.buildings.get(position.start).owner;
        var endOwner = board.buildings.get(position.end).owner;
        if ((startOwner == null || startOwner != uniqueIndex) && (endOwner == null || endOwner != uniqueIndex)) {
            // players road connected
            var startNeighbours = board.neighbourNodes.get(position.start);
            var endNeighbours = board.neighbourNodes.get(position.end);

            for (var neighbour : startNeighbours) {
                if (neighbour != position.end && connectedByRoad(position.start, neighbour, board)) {
                    return true;
                }
            }
            for (var neighbour : endNeighbours) {
                if (neighbour != position.start && connectedByRoad(position.end, neighbour, board)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * Attempts to buy a village.
     *
     * @return true if the purchase is successful, false otherwise
     */
    public boolean buyVillage() {
        if (cardsList.get(Material.Brick).numberOfCards < 1) {
            return false;
        }
        if (cardsList.get(Material.Wood).numberOfCards < 1) {
            return false;
        }
        if (cardsList.get(Material.Sheep).numberOfCards < 1) {
            return false;
        }
        if (cardsList.get(Material.Wheat).numberOfCards < 1) {
            return false;
        }

        cardsList.get(Material.Brick).numberOfCards--;
        cardsList.get(Material.Wood).numberOfCards--;
        cardsList.get(Material.Sheep).numberOfCards--;
        cardsList.get(Material.Wheat).numberOfCards--;
        return true;
    }

    /**
     * Attempts to buy a town.
     *
     * @return true if the purchase is successful, false otherwise
     */
    public boolean buyTown() {
        if (cardsList.get(Material.Stone).numberOfCards < 3) {
            return false;
        }
        if (cardsList.get(Material.Wheat).numberOfCards < 2) {
            return false;
        }

        cardsList.get(Material.Stone).numberOfCards -= 3;
        cardsList.get(Material.Wheat).numberOfCards -= 2;
        return true;
    }

    /**
     * Attempts to buy a road.
     *
     * @return true if the purchase is successful, false otherwise
     */
    public boolean buyRoad() {
        if (cardsList.get(Material.Brick).numberOfCards < 1) {
            return false;
        }
        if (cardsList.get(Material.Wood).numberOfCards < 1) {
            return false;
        }

        cardsList.get(Material.Brick).numberOfCards -= 1;
        cardsList.get(Material.Wood).numberOfCards -= 1;
        return true;
    }

    /**
     * Evaluates if the player has won the game and returns result
     * @return true if the player has won otherwise false
     *
     */
    public boolean evaluateWinner() {
        return points == 10;
    }

    /**
     * Builds a town on the game board if possible and returns true. If not returns false.
     *
     * @param buildPosition    the position on the board where to build a town
     * @param board the game board
     * @return boolean to say if building was successful
     */
    public boolean buildTown(int buildPosition, Board board) {

        if (!townPosValid(buildPosition, board)) {
            return false;
        }
        board.createTown(buildPosition);
        pawnList.get(PawnType.Village).numberOfPawns++;
        pawnList.get(PawnType.Town).numberOfPawns--;
        points++;
        return true;
    }

    /**
     * Builds a road on the game board.
     * @param position    the position on the board where to build a road with start and end
     * @param board the game board
     * @return boolean to say if building was successful
     */
    public boolean buildRoad(Position position, Board board) {
        if (!roadPosValid(position, board)) {
            return false;
        }
        board.createRoad(position, uniqueIndex);
        pawnList.get(PawnType.Road).numberOfPawns--;
        return true;
    }

    /**
     * Builds a village on the game board if possible and returns true. If not returns false.
     *
     * @param buildPosition    the position on the board where to build a village
     * @param board the game board
     * @return boolean to say if building was successful
     */
    public boolean buildVillage(int buildPosition, Board board) {
        if (!villagePosValid(buildPosition, board, false)) {
            return false;
        }
        board.createVillage(buildPosition, uniqueIndex);
        pawnList.get(PawnType.Village).numberOfPawns--;
        points++;
        return true;
    }



    /**
     * Checks if player has at least one card of requested material
     *
     * @param material  requested material
     * @return true if player has at least one card of requested material otherwise false
     */
    private boolean hasResources(Material material) {
        return cardsList.get(material).numberOfCards > 0;
    }

    /**
     * Proceeds a selling interaction between a buyer and a seller.
     *
     * @param receivedMaterial material to be received by currentPlayer
     * @param soldMaterial material to be sold by currentPlayer
     * @param buyer player with whom the interaction is happening
     */
    public void sell(Material receivedMaterial, Material soldMaterial, Player buyer) {
        buyer.cardsList.get(soldMaterial).numberOfCards++;
        buyer.cardsList.get(receivedMaterial).numberOfCards--;
        this.cardsList.get(soldMaterial).numberOfCards--;
        this.cardsList.get(receivedMaterial).numberOfCards++;
    }

    /**
     * Builds a road on the game board as part of init building at the start of game.
     *
     * @param board       the game board
     * @return            boolean if building start road was successful or not
     */
    public boolean startRoad(Position roadPosition, Board board) {
       if (!roadPosValid(roadPosition, board)) {
           return false;
      }
        board.createRoad(roadPosition, uniqueIndex);
        pawnList.get(PawnType.Road).numberOfPawns--;
        return true;
    }

    /**
     * Builds a village on the game board as part of init building at the start of game.
     *
     * @param board       the game board
     * @return            boolean if building start Village was successful or not
     */
    public boolean startVillage(int buildPosition, Board board) {

        if (!villagePosValid(buildPosition, board, true)) {
            return false;
        }
        board.createVillage(buildPosition, uniqueIndex);
        pawnList.get(PawnType.Village).numberOfPawns--;
        points++;
        return true;
    }

    /**
     * Gives the initial set of cards to the player.
     * One of each material
     */
    public void giveStartCards() {
        for (var material : Material.values()) {
            if (material != Material.Desert) {
                cardsList.get(material).numberOfCards = 1;
            }
        }
    }

    /**
     * Gets the player's points.
     *
     * @return the player's points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Gets the player's cards list.
     *
     * @return the player's cards list
     */
    public HashMap<Material, MaterialCards> getCardsList() {
        return cardsList;
    }

    /**
     * Gets the player's pawn list.
     *
     * @return the player's pawn list
     */
    public HashMap<PawnType, PawnSet> getPawnList() {
        return pawnList;
    }

    /**
     * Gets the player's unique index.
     *
     * @return the player's unique index
     */
    public int getUniqueIndex() {
        return uniqueIndex;
    }
}