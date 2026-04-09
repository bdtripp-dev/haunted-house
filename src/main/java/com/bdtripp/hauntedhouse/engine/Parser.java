package com.bdtripp.hauntedhouse.engine;

import java.util.Scanner;

import com.bdtripp.hauntedhouse.model.Command;
import com.bdtripp.hauntedhouse.model.CommandWord;

import java.io.InputStream;

/**
 * Parses raw player input into structured commands.
 *
 * The Parser tokenizes input from either the CLI or web client, extracts up to three command words,
 * and produces a Command object for processing.
 *
 * @author Michael Kölling, David J. Barnes, and Brian Tripp
 */
public class Parser {
    private Scanner reader;

    /**
     * For both CLI and web version of the game.
     * 
     * @param reader The scanner to tokenize input
     */
    private Parser(Scanner reader) {
        this.reader = reader;
    }

    /**
     * For the Web application
     * 
     * @param string The input to tokenize
     */
    public Parser(String string) {
        this(new Scanner(string));
    }

    /**
     * For the CLI application
     * 
     * @param inputStream The input stream to tokenize
     */
    public Parser(InputStream inputStream) {
        this(new Scanner(inputStream));
    }

    /**
     * Returns a tokenized version of the command that was entered by the player
     * 
     * @return The command
     */
    public Command getCommand() {
        String inputLine; // will hold the full input line
        CommandWord word1 = null;
        String word2 = null;
        String word3 = null;

        inputLine = reader.nextLine();

        // Find up to three words
        Scanner tokenizer = new Scanner(inputLine);
        if (tokenizer.hasNext()) {
            word1 = CommandWord.fromString(tokenizer.next());
            if (tokenizer.hasNext()) {
                word2 = tokenizer.next();
                if (tokenizer.hasNext()) {
                    word3 = tokenizer.next();
                }
            }
        }

        return new Command(word1, word2, word3);
    }
}
