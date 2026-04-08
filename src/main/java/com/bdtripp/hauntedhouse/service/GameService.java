package com.bdtripp.hauntedhouse.service;

import com.bdtripp.hauntedhouse.engine.Parser;
import com.bdtripp.hauntedhouse.model.Command;
import com.bdtripp.hauntedhouse.api.GameRequest;
import com.bdtripp.hauntedhouse.api.GameResponse;
import com.bdtripp.hauntedhouse.engine.GameEngine;
import com.bdtripp.hauntedhouse.model.GameStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

/**
 * Service layer for the Haunted House web application.
 * 
 * The GameService acts as an interface between the GameController and the
 * GameEngine. This
 * class contains code that is unique to the web version of the game and is a
 * counterpart to
 * the CliRunner class. The CliRunner receives input and prints output to the
 * terminal,
 * whereas the GameService receives requests from the controller and returns
 * responses to
 * it and does not print anything.
 * 
 * @author Brian Tripp
 * @version 2026.02.09
 */
@SessionScope
@Service
public class GameService {
    private GameEngine gameEngine;

    /**
     * Starts a new game session and returns the initial response.
     *
     * @return A response containing a welcome message and status of the game
     */
    public GameResponse startGame() {
        gameEngine = new GameEngine();
        gameEngine.startGame();
        String output = gameEngine.buildWelcomeMessage() + "\n";
        return new GameResponse(output, GameStatus.RUNNING);
    }

    /**
     * Processes a player command and returns the resulting game output and status.
     *
     * @param request The request containing the player command
     * @return A response containing output and status of the game
     */
    public GameResponse execute(GameRequest request) {
        Parser parser = new Parser(request.getInput());
        Command command = parser.getCommand();

        String result = gameEngine.processCommand(command);
        String output = formatOutput(request.getInput(), result);

        GameStatus status = gameEngine.isGameOver()
                ? GameStatus.STOPPED
                : GameStatus.RUNNING;

        return new GameResponse(output, status);
    }

    /**
     * Formats the player's input and the resulting game output into a
     * standardized output block for the web interface.
     *
     * @param input  the raw command entered by the player
     * @param result the game engine's response to the command
     * @return a formatted multi-line string combining the input and result
     */
    private String formatOutput(String input, String result) {
        return "> " + input + "\n" + result + "\n\n";
    }
}