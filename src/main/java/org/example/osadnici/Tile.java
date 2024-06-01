package org.example.osadnici;

import java.util.List;

/**
 * Represents a tile on the game board.
 * Stores type of material it produces,
 * number on the tile that can be rolled
 * and indexes that creates it
 */
public record Tile(List<Integer> nodeGroup, Material material, int number) {
}
