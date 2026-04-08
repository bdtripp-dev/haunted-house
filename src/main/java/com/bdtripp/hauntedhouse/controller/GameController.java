package com.bdtripp.hauntedhouse.controller;

import com.bdtripp.hauntedhouse.service.GameService;
import com.bdtripp.hauntedhouse.api.GameRequest;
import com.bdtripp.hauntedhouse.api.GameResponse;
import com.bdtripp.hauntedhouse.model.GameStatus;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * The controller layer for the Haunted House web application.
 *
 * This class contains the API endpoints that connect the webpage
 * front end with the back end game engine.
 *
 * @author Brian Tripp
 * @version 2026.02.09
 */

@RestController
@RequestMapping("/api/game")
@Validated
public class GameController {
    private final GameService gameService;

    /**
     * Create the GameController and initialize the GameService. Since
     * this class is annotated with @RestController, Spring takes care
     * of instantiating it.
     * 
     * @param gameService The GameService that the Game Controller
     *                    routes request to and responses from.
     */
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Starts a new game session.
     *
     * <p>
     * This endpoint initializes the game world, creates a new player state,
     * and returns the initial output shown to the user. The client should call
     * this endpoint before sending any commands.
     * </p>
     *
     * @return A {@link GameResponse} containing the game's initial output
     *         and the current {@link GameStatus}.
     */
    @PostMapping("/start")
    public GameResponse start() {
        return gameService.startGame();
    }

    /**
     * API endpoint that executes a command.
     * 
     * @param request The request from the client.
     * @return The response that will be processed by the client.
     */
    @PostMapping(value = "/command", consumes = "application/json", produces = "application/json")
    public GameResponse executeCommand(@RequestBody GameRequest request) {
        return gameService.execute(request);
    }
}