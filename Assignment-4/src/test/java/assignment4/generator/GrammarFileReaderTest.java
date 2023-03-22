package assignment4.generator;

import assignment4.exception.InvalidGrammarFileException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class GrammarFileReaderTest {

    private GrammarFileReader reader ;
    private File grammarDirectory;
    private File grammarFile;

    @BeforeEach
    void setup() throws InvalidGrammarFileException {
        grammarDirectory = new File(TestFileConstants.DIRECTORY);
        grammarFile = new File(TestFileConstants.INSULT_GRAMMAR);
        reader = GrammarFileReader.getGrammarFileReader(grammarDirectory);

    }

    @Test
    void getGrammarTitles() {
        Assertions.assertNotNull(reader.getGrammarTitles());
        Assertions.assertEquals(this.reader.getGrammarTitles().get("Insult Generator").toString(),
                TestFileConstants.INSULT_GRAMMAR);

    }

    @Test
    void getRandomizedStartOfGrammarForInvalidFileThrowsException() {
        Assertions.assertThrows(InvalidGrammarFileException.class,
                ()-> this.reader.getRandomizedStartOfGrammar(grammarDirectory));

    }

    @Test
    void getRandomizedStartOfGrammarTest() throws InvalidGrammarFileException {
        Assertions.assertNotNull(this.reader.getRandomizedStartOfGrammar(grammarFile));
    }

    @Test
    void getFileJSONObjectMap() throws IOException, ParseException {
        Assertions.assertNotNull(this.reader.getFileJSONObjectMap());

        Map<File, JSONObject> testJsonMap = new HashMap<>();
        JSONParser jsonParser = new JSONParser();
        java.io.FileReader fileReader = new java.io.FileReader(grammarFile);
        JSONObject jsonObj = (JSONObject) jsonParser.parse(fileReader);
        testJsonMap.put(grammarFile, jsonObj);

        Assertions.assertEquals(this.reader.getFileJSONObjectMap().get(grammarFile),testJsonMap.get(grammarFile));

    }
}