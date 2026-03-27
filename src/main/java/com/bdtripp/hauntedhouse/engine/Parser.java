package com.bdtripp.hauntedhouse;

import java.util.Scanner;
import java.io.InputStream;

/**
 * Represents a parser in the Haunted House game. The parser tokenizes user input
 * into commands and arguments.
 *
 * @author  Michael Kölling, David J. Barnes, and Brian Tripp
 * @version 2026.02.06
 */
public class Parser
{
    private Scanner reader;

    /**
     * For both CLI and web version of the game.
     * @param reader The scanner to tokenize input
     */
    private Parser(Scanner reader) 
    {
        this.reader = reader;
    }

    /**
     * For the Web application
     * @param string The input to tokenize
     */
    public Parser(String string) { 
        this(new Scanner(string));
    }

    /**
     * For the CLI application
     * @param inputStream The input stream to tokenize
     */
    public Parser(InputStream inputStream) 
    {
        this(new Scanner(inputStream));
    }

    /** Returns a tokenized version of the command that was entered by the player 
     * @return The command
     */
    public Command getCommand()
    {
        String inputLine;   // will hold the full input line
        CommandWord word1 = null;
        String word2 = null;
        String word3 = null;

        inputLine = reader.nextLine();

        // Find up to three words
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = CommandWord.fromString(tokenizer.next());
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();
                if(tokenizer.hasNext()) {
                    word3 = tokenizer.next();
                }
            }
        }

        return new Command(word1, word2, word3);
    }
}
