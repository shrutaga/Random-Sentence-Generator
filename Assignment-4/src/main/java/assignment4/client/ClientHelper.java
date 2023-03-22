package assignment4.client;

import java.util.List;
import java.util.Scanner;

/**
 * ClientHelper is a helper class for Client which has main method
 */
public class ClientHelper {

    /**
     * YES_OPTION Option to select yes for next options
     */
    public static final String YES_OPTION ="y";
    /**
     * QUIT_OPTION Option to quit from the tool
     */
    public static final String QUIT_OPTION ="q";

    /**
     * getUserSelection() method displays the available options and gets the option selected by user
     * @param titles provides the titles of the grammar files that are present in given directory
     * @param scanner gets user's input
     * @return returns selected option as integer
     */
    public static int getUserSelection(List<String> titles, Scanner scanner) {
        System.out.println("The following titles are available:");
        for (int i = 0; i < titles.size(); i++) {
            System.out.println(i+1 + ". " + titles.get(i));
        }

        System.out.println("Which grammar content would you like to be generated? (Enter q to quit)");
        String input = scanner.next();
        int option = 0;
        if (input.equalsIgnoreCase(QUIT_OPTION)) {
            System.exit(0);
        } else {
            while(input.length() != 1){
                System.out.println("Please select the required grammar's number, please re-enter");
                input=scanner.next();
            }
            option = Character.getNumericValue(input.charAt(0));
        }
        return option;
    }
}
