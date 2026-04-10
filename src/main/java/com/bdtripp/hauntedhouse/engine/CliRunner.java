package com.bdtripp.hauntedhouse.engine;

import com.bdtripp.hauntedhouse.model.Command;

/**
 * Runs the game in a command-line environment.
 *
 * The CliRunner initializes the game engine, reads player input from the terminal, converts it into
 * commands, and prints the resulting output until the session ends.
 *
 * @author Brian Tripp
 */
public class CliRunner {
    private Parser parser = new Parser(System.in);
    private GameEngine gameEngine = new GameEngine();

    /**
     * Initializes the parser and game engine for cli play.
     */
    public CliRunner() {
    }

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
        System.out.println(gameEngine.buildWelcomeMessage());

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