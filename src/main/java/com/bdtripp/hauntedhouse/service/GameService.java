package com.bdtripp.hauntedhouse.service;

import com.bdtripp.hauntedhouse.engine.Parser;
import com.bdtripp.hauntedhouse.engine.WorldBuilder;
import com.bdtripp.hauntedhouse.model.Command;
import com.bdtripp.hauntedhouse.api.GameRequest;
import com.bdtripp.hauntedhouse.api.GameResponse;
import com.bdtripp.hauntedhouse.engine.CommandProcessor;
import com.bdtripp.hauntedhouse.engine.GameEngine;
import com.bdtripp.hauntedhouse.model.GameStatus;
import com.bdtripp.hauntedhouse.model.World;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

/**
 * Provides the web-facing game service layer.
 *
 * Acts as the bridge between the controller and the game engine for the web version.
 *
 * @author Brian Tripp
 */
@SessionScope @Service
public class GameService {
    private GameEngine gameEngine;

    /**
     * Creates a new GameService instance.
     */
    public GameService() {
    }

    /**
     * Starts a new game session and returns the initial response.
     *
     * @return a response containing a welcome message and status of the game
     */
    public GameResponse startGame() {
        World world = new WorldBuilder().createWorld();
        GameEngine engine = new GameEngine(world);
        CommandProcessor processor = new CommandProcessor(world, engine);
        engine.setCommandProcessor(processor);
        this.gameEngine = engine;
        gameEngine.startGame();
        String output = gameEngine.buildWelcomeMessage() + "\n";
        return new GameResponse(output, GameStatus.RUNNING);
    }

    /**
     * Processes a player command and returns the resulting game output and status.
     *
     * @param request the request containing the player command
     * @return a response containing output and status of the game
     */
    public GameResponse execute(GameRequest request) {
        Parser parser = new Parser(request.getInput());
        Command command = parser.getCommand();

        String result = gameEngine.processCommand(command);
        String output = formatOutput(request.getInput(), result);

        GameStatus status = gameEngine.isGameOver() ? GameStatus.STOPPED : GameStatus.RUNNING;

        return new GameResponse(output, status);
    }

    /**
     * Formats the player's input and the resulting game output into a standardized output block for
     * the web interface.
     *
     * @param input the raw command entered by the player
     * @param result the game engine's response to the command
     * @return a formatted multi-line string combining the input and result
     */
    private String formatOutput(String input, String result) {
        return "> " + input + "\n" + result + "\n\n";
    }
}