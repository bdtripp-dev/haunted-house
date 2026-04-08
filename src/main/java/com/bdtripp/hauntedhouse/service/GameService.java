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
    private Parser parser;

    /**
     * Creates a GameEngine and returns a welcome message.
     * 
     * @return A response containing a welcome message and status of the game
     */
    public GameResponse startGame() {
        gameEngine = new GameEngine();
        gameEngine.startGame();
        return new GameResponse(gameEngine.buildWelcomeMessage() + "\n", GameStatus.RUNNING);
    }

    /**
     * Parses the players input, converts it into a command, and executes it.
     * Returns output
     * to the GameController along with the game's status.
     * 
     * @param request The request that came from the GameController
     * @return A response containing output and status of the game that is returned
     *         to the GameController
     */
    public GameResponse execute(GameRequest request) {
        String input = request.getInput();
        parser = new Parser(input);
        Command command = parser.getCommand();
        String cmdResult = gameEngine.processCommand(command);
        String output = "> " + input + "\n" + cmdResult + "\n\n";
        if (gameEngine.isGameOver()) {
            return new GameResponse(output, GameStatus.STOPPED);
        }
        return new GameResponse(output, GameStatus.RUNNING);
    }
}