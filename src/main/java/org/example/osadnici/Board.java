package org.example.osadnici;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents the game board for the game.
 * This class manages the tiles, roads, buildings, and their relationships on the board.
 */
public class Board {
    /** Mapping of dice numbers to corresponding tiles. */
    public HashMap<Integer, List<Tile>> numbersToTiles;

    /** Mapping of nodes to their neighboring nodes. */
    public HashMap<Integer, List<Integer>> neighbourNodes;

    /** List of buildings on the board. */
    public List<Building> buildings;

    /** Mapping of start positions to corresponding roads. */
    public HashMap<Integer, List<Road>> roads;

    /** List of all tiles on the board. */
    public List<Tile> tilesList;

    /**
     * Constructs a new Board and initializes the tiles, neighbors, and build sites.
     */
    public Board() {
        tilesList = new ArrayList<>();
        numbersToTiles = new HashMap<>();
        neighbourNodes = new HashMap<>();
        roads = new HashMap<>();
        createTiles();
        createNeighbours();
        createBuildSites();
    }

    /**
     * Initializes the neighbor relationships between nodes.
     */
    private void createNeighbours() {
        List<Integer> neighbours = List.of(2, 9);
        this.neighbourNodes.put(1, neighbours);
        neighbours = List.of(1, 3);
        this.neighbourNodes.put(2, neighbours);
        neighbours = List.of(2, 4, 11);
        this.neighbourNodes.put(3, neighbours);
        neighbours = List.of(3, 5);
        this.neighbourNodes.put(4, neighbours);
        neighbours = List.of(4, 6, 13);
        this.neighbourNodes.put(5, neighbours);
        neighbours = List.of(5, 7);
        this.neighbourNodes.put(6, neighbours);
        neighbours = List.of(6, 15);
        this.neighbourNodes.put(7, neighbours);
        neighbours = List.of(9, 18);
        this.neighbourNodes.put(8, neighbours);
        neighbours = List.of(8, 10, 1);
        this.neighbourNodes.put(9, neighbours);
        neighbours = List.of(9, 11, 20);
        this.neighbourNodes.put(10, neighbours);
        neighbours = List.of(10, 12, 3);
        this.neighbourNodes.put(11, neighbours);
        neighbours = List.of(11, 13, 22);
        this.neighbourNodes.put(12, neighbours);
        neighbours = List.of(12, 14, 5);
        this.neighbourNodes.put(13, neighbours);
        neighbours = List.of(13, 15, 24);
        this.neighbourNodes.put(14, neighbours);
        neighbours = List.of(14, 16, 7);
        this.neighbourNodes.put(15, neighbours);
        neighbours = List.of(15, 26);
        this.neighbourNodes.put(16, neighbours);
        neighbours = List.of(18, 53);
        this.neighbourNodes.put(17, neighbours);
        neighbours = List.of(17, 19, 8);
        this.neighbourNodes.put(18, neighbours);
        neighbours = List.of(18, 20, 28);
        this.neighbourNodes.put(19, neighbours);
        neighbours = List.of(19, 21, 10);
        this.neighbourNodes.put(20, neighbours);
        neighbours = List.of(20, 22, 30);
        this.neighbourNodes.put(21, neighbours);
        neighbours = List.of(21, 23, 12);
        this.neighbourNodes.put(22, neighbours);
        neighbours = List.of(22, 24, 32);
        this.neighbourNodes.put(23, neighbours);
        neighbours = List.of(23, 25, 14);
        this.neighbourNodes.put(24, neighbours);
        neighbours = List.of(24, 26, 34);
        this.neighbourNodes.put(25, neighbours);
        neighbours = List.of(25, 48, 16);
        this.neighbourNodes.put(26, neighbours);
        neighbours = List.of(28, 53, 54);
        this.neighbourNodes.put(27, neighbours);
        neighbours = List.of(27, 29, 19);
        this.neighbourNodes.put(28, neighbours);
        neighbours = List.of(28, 30, 36);
        this.neighbourNodes.put(29, neighbours);
        neighbours = List.of(29, 31, 21);
        this.neighbourNodes.put(30, neighbours);
        neighbours = List.of(30, 32, 38);
        this.neighbourNodes.put(31, neighbours);
        neighbours = List.of(31, 33, 23);
        this.neighbourNodes.put(32, neighbours);
        neighbours = List.of(32, 34, 40);
        this.neighbourNodes.put(33, neighbours);
        neighbours = List.of(33, 50, 25);
        this.neighbourNodes.put(34, neighbours);
        neighbours = List.of(54, 36, 41);
        this.neighbourNodes.put(35, neighbours);
        neighbours = List.of(35, 37, 29);
        this.neighbourNodes.put(36, neighbours);
        neighbours = List.of(36, 38, 43);
        this.neighbourNodes.put(37, neighbours);
        neighbours = List.of(37, 39, 31);
        this.neighbourNodes.put(38, neighbours);
        neighbours = List.of(38, 40, 45);
        this.neighbourNodes.put(39, neighbours);
        neighbours = List.of(39, 52, 33);
        this.neighbourNodes.put(40, neighbours);
        neighbours = List.of(35, 42);
        this.neighbourNodes.put(41, neighbours);
        neighbours = List.of(41, 43);
        this.neighbourNodes.put(42, neighbours);
        neighbours = List.of(42, 44, 37);
        this.neighbourNodes.put(43, neighbours);
        neighbours = List.of(43, 45);
        this.neighbourNodes.put(44, neighbours);
        neighbours = List.of(39, 44, 46);
        this.neighbourNodes.put(45, neighbours);
        neighbours = List.of(45, 47);
        this.neighbourNodes.put(46, neighbours);
        neighbours = List.of(46, 52);
        this.neighbourNodes.put(47, neighbours);
        neighbours = List.of(26, 49);
        this.neighbourNodes.put(48, neighbours);
        neighbours = List.of(50, 48);
        this.neighbourNodes.put(49, neighbours);
        neighbours = List.of(49, 51, 34);
        this.neighbourNodes.put(50, neighbours);
        neighbours = List.of(50, 52);
        this.neighbourNodes.put(51, neighbours);
        neighbours = List.of(40, 51, 47);
        this.neighbourNodes.put(52, neighbours);
        neighbours = List.of(17, 27);
        this.neighbourNodes.put(53, neighbours);
        neighbours = List.of(27, 35);
        this.neighbourNodes.put(54, neighbours);
    }

    /**
     * Initializes the tiles with materials and numbers.
     */
    private void createTiles() {
        Material[] materials = {Material.Brick, Material.Wheat, Material.Stone,
                Material.Wood, Material.Sheep, Material.Brick, Material.Wheat,
                Material.Wood, Material.Stone, null, Material.Sheep, Material.Wood,
                Material.Sheep, Material.Wood, Material.Wheat, Material.Stone,
                Material.Brick, Material.Sheep, Material.Wheat};
        int[] numbers = {11, 12, 9, 4, 3, 6, 10, 8, 11, 7, 5, 8, 10, 9, 4, 3, 5, 2, 6};

        List<List<Integer>> groups = List.of(
                List.of(1, 2, 3, 9, 10, 11),
                List.of(3, 4, 5, 11, 12, 13),
                List.of(5, 6, 7, 13, 14, 15),
                List.of(8, 9, 10, 18, 19, 20),
                List.of(10, 11, 12, 20, 21, 22),
                List.of(12, 13, 14, 22, 23, 24),
                List.of(14, 15, 16, 24, 25, 26),
                List.of(17, 18, 19, 53, 27, 28),
                List.of(19, 20, 21, 28, 29, 30),
                List.of(21, 22, 23, 30, 31, 32),
                List.of(23, 24, 25, 32, 33, 34),
                List.of(25, 26, 48, 34, 50, 49),
                List.of(27, 28, 29, 54, 35, 36),
                List.of(29, 30, 31, 36, 37, 38),
                List.of(31, 32, 33, 38, 39, 40),
                List.of(33, 34, 50, 40, 52, 51),
                List.of(35, 36, 37, 41, 42, 43),
                List.of(37, 38, 39, 43, 44, 45),
                List.of(39, 40, 52, 45, 46, 47)
        );
        for (int tileIndex = 0; tileIndex < numbers.length; tileIndex++) {
            Tile newTile = new Tile(groups.get(tileIndex), materials[tileIndex], numbers[tileIndex]);
            tilesList.add(newTile);

            if (!numbersToTiles.containsKey(numbers[tileIndex])) {
                numbersToTiles.put(numbers[tileIndex], new ArrayList<>());
            }
            numbersToTiles.get(numbers[tileIndex]).add(newTile);
        }
    }

    /**
     * Creates a road on the board.
     *
     * @param position      the position of the road
     * @param currentPlayer the player who owns the road
     */
    public void createRoad(Position position, int currentPlayer) {
        var road = new Road(position.start, position.end, currentPlayer);

        if (!roads.containsKey(position.start)) {
            roads.put(position.start, new ArrayList<>());
        }
        roads.get(position.start).add(road);
    }

    /**
     * Initializes the build sites with empty buildings.
     */
    private void createBuildSites() {
        buildings = new ArrayList<>();
        for (int i = 0; i <= 54; i++) {
            buildings.add(new Building(null, null));
        }
    }

    /**
     * Creates a village at the specified position.
     *
     * @param buildPosition the position to build the village
     * @param currentPlayer the player who owns the village
     */
    public void createVillage(int buildPosition, int currentPlayer) {
        var buildSpot = buildings.get(buildPosition);
        buildSpot.type = PawnType.Village;
        buildSpot.owner = currentPlayer;
    }

    /**
     * Upgrades a village to a town at the specified position.
     *
     * @param buildPosition the position to build the town
     */
    public void createTown(int buildPosition) {
        var buildSpot = buildings.get(buildPosition);
        buildSpot.type = PawnType.Town;
    }
}
