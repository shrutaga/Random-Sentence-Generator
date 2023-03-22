package assignment4.client;

import assignment4.generator.GrammarFileReader;
import assignment4.generator.GrammarGenerator;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Client class is responsible for interacting with the end user and interface operations.
 * It contains the driver code.
 */
public class Client {
    /**
     * main() contains the driver code that reads the command line arguments for the grammar directory
     * and reads user input on the grammar that needs to be generated
     *
     * @param args contains the name of the directory containing grammar files.
     */
    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                throw new IllegalArgumentException("Incorrect number of arguments.\nRun "
                        + "java -classpath %CLASSPATH% Client <absolut path of directory containing json grammar files>");
            }

            File grammarDirectory = new File(args[0]);
            Scanner scanner = new Scanner(System.in);
            String anotherOption;
            String finalStatement;

            System.out.println("Loading grammars...");
            GrammarFileReader grammarFileReader = GrammarFileReader.getGrammarFileReader(grammarDirectory);
            LinkedHashMap<String, File> grammarTitles = grammarFileReader.getGrammarTitles();

            List<String> titles = new ArrayList<>(grammarTitles.keySet());

            do {
                int option = ClientHelper.getUserSelection(titles, scanner);
                File grammarFile = grammarTitles.get(titles.get(option - 1));

                GrammarGenerator generator = new GrammarGenerator(grammarFile);
                finalStatement = generator.generate();
                System.out.println(finalStatement);

                System.out.println("*** Press 'Y' to continue or press any key to exit. ***");
                anotherOption = scanner.next();

            } while (anotherOption.equalsIgnoreCase(ClientHelper.YES_OPTION));

        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
    }
}
