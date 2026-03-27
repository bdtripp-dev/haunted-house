package com.bdtripp.hauntedhouse.engine;

import com.bdtripp.hauntedhouse.model.Command;

/**
 * The main entry point for the Haunted House CLI application.
 * 
 * This class creates a GameEngine, parses input, converts
 * it into a command, processes the command, and prints the output.
 *
 * @author Brian Tripp
 * @version 2026.02.09
 */
public class CliRunner {
    private Parser parser = new Parser(System.in);
    private GameEngine gameEngine = new GameEngine();

    /**
     * The main entry point. Creates a CliRunner and runs it.
     * 
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        CliRunner runner = new CliRunner();
        runner.run(args);
    }

    /**
     * Processes commands and print output until the game is over.
     * 
     * @param args command-line arguments
     */
    private void run(String[] args) {
        gameEngine.startGame();
        System.out.println(gameEngine.getWelcomeMessage());

        while (!gameEngine.isGameOver()) {
            System.out.print("> ");
            Command command = parser.getCommand();
            String output = gameEngine.processCommand(command);
            if (!output.isEmpty()) {
                System.out.println(output + "\n");
            }
        }
    }
}