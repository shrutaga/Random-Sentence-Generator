package assignment4.generator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import assignment4.exception.InvalidGrammarFileException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * GrammarFileReader is a singleTon class for reading the grammar JSON files.
 * This class reads the file once, thereby avoiding multiple repetitive I/O operations
 * 
 */
public class GrammarFileReader {
    private static GrammarFileReader grammarReaderInstance = null;
    private final File grammarDirectory;
    private final Map<File, JSONObject> fileJSONObjectMap;
    /**
     * GRAMMAR_TITLE constant for the key "grammarTitle"
     */
    public static final String GRAMMAR_TITLE = "grammarTitle";
    /**
     * GRAMMAR_START constant for the key "start"
     */
    public static final String GRAMMAR_START = "start";

    /**
     * Singleton constructor to initialize it with the directory
     * @param grammarDirectory Directory that contains the grammar files
     */
    private GrammarFileReader(File grammarDirectory) {
        this.grammarDirectory = grammarDirectory;
        this.fileJSONObjectMap = new HashMap<>();

        try {
            for (File file : Objects.requireNonNull(grammarDirectory.listFiles())) {

                JSONParser jsonParser = new JSONParser();
                java.io.FileReader fileReader = new java.io.FileReader(file);
                JSONObject jsonObj = (JSONObject) jsonParser.parse(fileReader);
                fileJSONObjectMap.put(file, jsonObj);
            }
        } catch (IOException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *
     * This method should only be called once the GrammarFileReader is initialized.
     * @return singleton instance of type GrammarFileReader
     */
    public static GrammarFileReader getGrammarFileReader() {
        return grammarReaderInstance;
    }

    /**
     * getGrammarFileReader() is a static getter method that returns the single instance of GrammarFileReader
     * @param grammarDirectory Directory that contains the grammar files
     * @return The single instance of GrammarFileReader
     * @throws InvalidGrammarFileException throws exception if the grammar file is invalid
     */
    public static GrammarFileReader getGrammarFileReader(File grammarDirectory) throws InvalidGrammarFileException {

        if (!(grammarDirectory.exists()) || !(grammarDirectory.isDirectory())){
            throw new InvalidGrammarFileException(grammarDirectory.getAbsolutePath());
        }

        if (grammarReaderInstance == null) {
            grammarReaderInstance = new GrammarFileReader(grammarDirectory);
        }

        return grammarReaderInstance;
    }


    /**
     * getGrammarTitles() Reads the grammar directory and stores each grammar file's title and the
     * file in a hashmap.
     * @return list of all grammar titles that exist in the provided directory
     * */
    public LinkedHashMap<String, File> getGrammarTitles() {
        LinkedHashMap<String, File> grammarTitles = new LinkedHashMap<>();
        try {
            for (File file : Objects.requireNonNull(grammarDirectory.listFiles())) {

                JSONObject jsonObject = fileJSONObjectMap.get(file);
                String grammarTitle = (String) jsonObject.get(GRAMMAR_TITLE);
                grammarTitles.put(grammarTitle, file);
            }
        } catch (SecurityException e) {
            System.out.println(e.getMessage());
        }
        return grammarTitles;
    }

    /**
     * getRandomizedStartOfGrammar() To get a random start in case a grammar file has multiple starts
     * @param grammarFile File that contains the productions of all the terminals and non-terminals
     * @return a random start as a String
     * @throws InvalidGrammarFileException throws exception if the grammar file is invalid
     */
    public String getRandomizedStartOfGrammar(File grammarFile) throws InvalidGrammarFileException {

        if(!fileJSONObjectMap.containsKey(grammarFile)) {
          throw new InvalidGrammarFileException(grammarFile.getAbsolutePath());
        }
        JSONObject jsonObject = fileJSONObjectMap.get(grammarFile);
        List<String> startList = (List) jsonObject.get(GRAMMAR_START);

        Random random = new Random();
        int index = random.nextInt(startList.size());
        return startList.get(index);
    }


    /**
     * getFileJSONObjectMap() is a getter method for fileJSONObjectMap map.
     * @return Map containing grammar json file as key and json object containing file's content as
     * its value.
     */
    public Map<File, JSONObject> getFileJSONObjectMap() {
        return fileJSONObjectMap;
    }
}
