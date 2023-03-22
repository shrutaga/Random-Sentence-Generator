package assignment4.exception;

/**
 * exception.UndefinedNonTerminalException is defined to be thrown when a non-terminal is used, but not defined
 * in the grammar file
 */
public class UndefinedNonTerminalException extends Exception{
    /**
     * exception.UndefinedNonTerminalException() creates this exception when a non-terminal is used but not defined
     * in the grammar file wih an explanatory error message
     * @param errorMessage Self-explanatory error message when an undefined non-terminal is used in the grammar
     */
    public UndefinedNonTerminalException(String errorMessage){
        super(errorMessage);
    }
}