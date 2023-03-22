package assignment4.generator;

import java.io.*;

import assignment4.exception.UndefinedNonTerminalException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SentenceGenerator class is an abstract class that is responsible for all operations of generating
 * a sentence, such as finding the non-terminals, replacing them with a random element from its productions
 * in the grammar file and correcting the punctuations in the start string.
 *
 */
public abstract class SentenceGenerator {

    private static final String NON_TERMINAL_PATTERN = "<(.*?)>";
    private static final String PUNCTUATION_PATTERN = "[^a-zA-Z0-9<>]";
    private static final String ANGULAR = "[<>]";
    private static final String SPACE = " ";
    private static final String EMPTY = "";
    private static final String LEFT_ANGLE = "<";
    private static final String RIGHT_ANGLE = ">";
    private static final String EXTRA_SPACE_REGEX = "( )+";

    private List<String> nonTerminals;
    private List<String> punctuations;
    private List<String> tags;
    private Map<String, Object> jsonData;

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
    abstract String generate() throws UndefinedNonTerminalException;

    /**
     * readGrammar() reads the grammar JSON file's content for all the non-terminals in it and
     * stores all the non-terminal productions in a map.
     * @param grammarFile File that contains the productions of all the non-terminals
     */
    public void readGrammar(File grammarFile) {
        // Reads the already parsed grammar file from GrammarFileReader singleton class.
        JSONObject jsonObject = GrammarFileReader.getGrammarFileReader()
            .getFileJSONObjectMap()
            .get(grammarFile);

        List<String> tags = new ArrayList<>();
        for (Object key : jsonObject.keySet()) {
            tags.add(((String) key).toLowerCase());
        }

        Map<String, Object> jsonData = new HashMap<>();
        for (String tag : tags) {
            jsonData.put(tag.toLowerCase(), jsonObject.get(tag));
        }
        this.tags = new ArrayList<>(tags);
        this.jsonData = new HashMap<>(jsonData);
    }


    /**
     * findNonTerminalsInString() parses through the string passed, content looking for the specified
     * patterns indicating a non-terminal.
     * On finding the specified patterns, it removes the indicators and stores their values and forms a
     * collection of these pattern matches. In this case, the pattern matches we are looking for are
     * non-terminals specified by angle brackets in the string.
     *
     * @param content String that is parsed for patterns
     * @return returns all non-terminals
     */
    public List<String> findNonTerminalsInString(String content) {
        List<String> nonTerminals = new ArrayList<>();

        Matcher m = Pattern.compile(NON_TERMINAL_PATTERN).matcher(content);
        while (m.find()) {
            String tag = m.group().replaceAll(ANGULAR, EMPTY);
            nonTerminals.add(tag);
        }
        this.nonTerminals = new ArrayList<>(nonTerminals);
        findPunctuations(content);
        return this.nonTerminals;
    }

    /**
     * findPunctuations() parses through the string passed looking for the specified patterns
     * On finding the specified patterns, it removes the redundant space and forms a collection
     * of these pattern matches.
     * In this case, the pattern matches we are looking for is punctuations in the string.
     * @param content String that is parsed for patterns
     * @return returns all non-terminals
     */
    public List<String> findPunctuations(String content) {
        List<String> punctuationsFound = new ArrayList<>();
        Matcher n = Pattern.compile(PUNCTUATION_PATTERN).matcher(content);
        while (n.find()) {
            String ne = n.group().replaceAll(SPACE, EMPTY);
            if (!punctuationsFound.contains(ne) && !(ne.equals(EMPTY))) {
                punctuationsFound.add(ne);
            }
        }
        this.punctuations = new ArrayList<>(punctuationsFound);
        return punctuationsFound;
    }

    /**
     *
     * @return the non-terminals in the start statement
     */
    public List<String> getTags() {
        return this.tags;
    }


    /**
     * correctPunctuations() corrects the position of the punctuations in the string passed.
     * The string is split into a list and every string element that starts with a punctuation is
     * corrected.
     * @param content The string that is being parsed through in order to correct its punctuations.
     * @return String whose punctuation is corrected.
     */
    public String correctPunctuations(String content){
        //remove extra space
        String removeExtraSpace = content;
        removeExtraSpace = removeExtraSpace.replaceAll(EXTRA_SPACE_REGEX, SPACE);

        //split into array
        String[] seperated = removeExtraSpace.split(SPACE);
        //Convert array into list
        List<String> al = Arrays.asList(seperated);

        //List of all punctuations in the string
        List<String> checkList = this.findPunctuations(content);

        //Iterate through the list correcting positions of punctuations.
        StringBuilder statement = new StringBuilder(EMPTY);
        for (String word: al){
            if(checkList.contains(Character.toString(word.charAt(0)))){
                char punctuation = word.charAt(0);
                if (word.length()>1){
                    word = punctuation +SPACE+ word.substring(1);
                } else {
                    word = Character.toString(punctuation);
                }
                statement.append(word);
            } else {
                statement.append(SPACE).append(word);
            }
        }
        if(statement.length() >1) {
            statement = new StringBuilder(statement.substring(1));
        }
        return statement.toString();
    }


    /**
     * returnRandomElement() returns a random element from the production of the passed non-terminal
     * @param nonTerminal non-terminal whose random element is returned
     * @param seed Seed for the random value generator (used for establishing predictability while testing)
     * @return random element from the non-terminal's productions.
     * @throws UndefinedNonTerminalException when there is an undefined non-terminal used in the start statement
     */
    public String returnRandomElement(String nonTerminal, int seed)
            throws UndefinedNonTerminalException {
        if (tags.contains(nonTerminal.toLowerCase(Locale.ROOT))) {
            JSONArray values = (JSONArray) jsonData.get(nonTerminal.toLowerCase(Locale.ROOT));
            Random random = new Random(seed);
            Object element = null;
            if (!(values == null)) {
                int index = random.nextInt(values.size());
                element = values.get(index);
                //System.out.println("For "+nonTerminal+", index was: "+index);
            } else {
                element = "";
            }
            return (String) element;
        } else {
            throw new UndefinedNonTerminalException("The non-terminal: <" + nonTerminal + "> is not defined in the grammar file.");
        }

    }


    /**
     * replaceNonTerminals() replaces all the non-terminals with a random value from its production until all
     * the elements in the start statement are terminals.
     * @param content String in which the non-terminals must be replaced by one of its productions.
     * @param seed Seed for the random value generator (used for establishing predictability while testing)
     * @return Replaced string containing only terminals
     * @throws UndefinedNonTerminalException when there is an undefined non-terminal used in the start statement
     */
    public String replaceNonTerminals(String content, int seed)
            throws UndefinedNonTerminalException {
        String statement = content;
        while (!(this.findNonTerminalsInString(statement).isEmpty())) {
            for (String nonTerminal : this.nonTerminals) {
                String replacement = this.returnRandomElement(nonTerminal, seed);
                Pattern p = Pattern.compile(LEFT_ANGLE + nonTerminal + RIGHT_ANGLE, Pattern.CASE_INSENSITIVE);
                StringBuilder stringBuilder = new StringBuilder();
                Matcher matcher = p.matcher(statement);
                while (matcher.find()) {

                    matcher.appendReplacement(stringBuilder, replacement);
                }
                matcher.appendTail(stringBuilder);
                statement = stringBuilder.toString();
            }
        }
        return statement;
    }
}
