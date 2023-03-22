package assignment4.generator;

import java.io.File;
import java.util.Random;

import assignment4.exception.UndefinedNonTerminalException;


/**
 * NonTerminal class deals with all the operation on non-terminals in the grammar files.
 * 
 */
public class NonTerminal extends SentenceGenerator {

    private final File grammarFile;
    private final String start;

    /**
     * NonTerminal() constructor creates a new instance of NonTerminal class
     *
     * @param start       Start string that contains terminals and non-terminals that are replaced by
     *                    their respective json array elements
     * @param grammarFile File that contains the productions of all the terminals and non-terminals
     *                    that are a part of a grammar
     */
    public NonTerminal(String start, File grammarFile) {
        this.start = start;
        this.grammarFile = grammarFile;
    }

    /**
     * generate() is an abstract method that returns a statement where all the non-terminals are
     * replaced by random values from their production
     * @return returns the generated random statement
     * @throws UndefinedNonTerminalException when there is an undefined non-terminal used in the start statement
     */
    public String generate() throws UndefinedNonTerminalException {
        Random random = new Random();
        int index = random.nextInt();
        return generate(index);
    }

    /**
     * generate() is an abstract method that returns a statement where all the non-terminals are
     * replaced by random values from their production
     * @param seed Adding this for testing purpose
     * @return Replaced string containing only terminals
     * @throws UndefinedNonTerminalException when there is an undefined non-terminal used in the start statement
     */
    protected String generate(int seed) throws UndefinedNonTerminalException {
        readGrammar(grammarFile);
        return replaceNonTerminals(start, seed);
    }
}
