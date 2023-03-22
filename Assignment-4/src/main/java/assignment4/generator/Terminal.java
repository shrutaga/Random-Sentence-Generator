package assignment4.generator;

import java.io.File;
import java.util.List;
import java.util.Random;

import assignment4.exception.UndefinedNonTerminalException;

/**
 * Terminal class deals with all the operation on terminals in the grammar files.
 * 
 */
public class Terminal extends SentenceGenerator{
    private final File grammarFile;
    private final String start;
    private String punctuatedStart;

    /**
     * Terminal() constructor creates a new instance of Terminal class
     * @param start Start string that contains terminals and non-terminals that need to be replaced by
     *              elements from their respective productions.
     * @param grammarFile File that contains the productions of all the non-terminals that are a part
     *                    of a grammar
     */
    public Terminal(File grammarFile, String start) {
        this.grammarFile = grammarFile;
        this.start = start;

    }

    /**
     * generate() is an abstract method that returns a statement where all the non-terminals
     * are replaced by random values from their production
     *
     * @return The string resultant after replacing the non-terminals with random values from their
     * respective productions in the grammar file
     *
     * @throws UndefinedNonTerminalException when there is an undefined non-terminal used in the start
     *                                       statement
     */
    @Override
    public String generate() throws UndefinedNonTerminalException {
        Random random = new Random();
        int index = random.nextInt();
        return generate(index);
    }

    /**generate() is an abstract method that returns a statement where all the non-terminals
     * are replaced by random values from their production
     * @param seed Seed for the random value generator (used for establishing predictability while testing)
     *
     * @return The string resultant after replacing the non-terminals with random values from their
     * respective productions in the grammar file
     *
     * @throws UndefinedNonTerminalException when there is an undefined non-terminal used in the start statement
     */
    //Method Overloading
    protected String generate(int seed) throws UndefinedNonTerminalException {
        List<String> allMatches = this.findNonTerminalsInString(start);
        if(!(allMatches.isEmpty())) {
            punctuatedStart = correctPunctuations(start);
            NonTerminal generator = new NonTerminal(punctuatedStart,grammarFile);
            return generator.generate(seed);
       }
        return this.start;
    }
}
