package org.example.osadnici;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
        GameWindow.display(game);
    }
}