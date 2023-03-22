package assignment4.generator;

import assignment4.exception.InvalidGrammarFileException;
import assignment4.exception.UndefinedNonTerminalException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static assignment4.generator.TestFileConstants.DIRECTORY;
import static assignment4.generator.TestFileConstants.POEM_GRAMMAR;

class GrammarGeneratorTest {
    private File grammarFile;
    private GrammarGenerator generator;
    private GrammarFileReader reader ;
    private File grammarDirectory;

    @BeforeEach
    void setup() throws InvalidGrammarFileException {
        grammarDirectory = new File(DIRECTORY);
        reader = GrammarFileReader.getGrammarFileReader(grammarDirectory);
        grammarFile = new File(POEM_GRAMMAR);
        generator = new GrammarGenerator(grammarFile);
    }

    @Test
    void generate() throws UndefinedNonTerminalException, InvalidGrammarFileException {
        Assertions.assertNotNull(generator.generate());
        Assertions.assertTrue(!(generator.generate().contains("<")));

    }
}