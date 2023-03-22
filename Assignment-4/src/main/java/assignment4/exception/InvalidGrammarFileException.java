package assignment4.exception;

/**
 * Thrown for invalid or undefined grammar file
 */
public class InvalidGrammarFileException extends Exception{
    /**
     * Constructor of InvalidGrammarFileException
     * @param grammarFile which is invalid
     */
    public InvalidGrammarFileException(String grammarFile){
        super("Grammar file " + grammarFile + " is Invalid");
    }
}