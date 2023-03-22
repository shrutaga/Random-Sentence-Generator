package assignment4.generator;

import java.io.File;

import assignment4.exception.InvalidGrammarFileException;
import assignment4.exception.UndefinedNonTerminalException;

/**
 * GrammarGenerator class is a handler which gets file information and passes to SentenceGenerator to generate sentences
 */
public class GrammarGenerator {
    private File grammarFile;

    /**
     * GrammarGenerator constructor that takes single parameter
     *
     * @param grammarFile File that contains the productions of all the terminals and non-terminals
     */
    public GrammarGenerator(File grammarFile) {
        this.grammarFile = grammarFile;
    }

    /**
     * Generates the random grammar sentence.
     *
     * @return generated statement
     * @throws UndefinedNonTerminalException when there is an undefined non-terminal used in the start statement
     * @throws InvalidGrammarFileException throws exception if the grammar file is invalid
     */
    public String generate() throws UndefinedNonTerminalException, InvalidGrammarFileException {
        GrammarFileReader fileReader = GrammarFileReader.getGrammarFileReader();
        String start = fileReader.getRandomizedStartOfGrammar(grammarFile);
        //calling terminal first
        SentenceGenerator generator = new Terminal(grammarFile, start);
        String statement = generator.generate();
        return generator.correctPunctuations(statement);
    }
}
