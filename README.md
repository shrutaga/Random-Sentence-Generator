
# Random Sentence Generator 
This code presesnts the user with a list of grammars in a specified directory and
generates a random sentence based on the grammar selected by the user.



## Entry Point to the code

The code execution starts with reading the directory provided by the user via command line for grammar files.
Based on the grammar files at this directory, the user is presented with the grammar options to choose from.

On choice, the code reads the grammar file looking for non-terminals in the starter 'Start' non-terminal.


## High-level description of classes and methods

### GrammarFileReader

GrammarFileReader is a singleTon class for reading the grammar JSON files. This class reads the file once, thereby avoiding multiple repetitive I/O operations.

getGrammarFileReader(): a static getter method that returns the single instance of GrammarFileReader.

getGrammarTitles() Reads the grammar directory and stores each grammar file's title and the file in a hashmap.

getRandomizedStartOfGrammar(): returns a random element from the start jsonArray.

### GrammarGenerator 
GrammarGenerator class is a handler which gets file information and passes to SentenceGenerator to generate sentences

generate(): a method that returns a statement where are terminals and non-terminals are replaced by random values from their production.


### Terminal
NonTerminal class deals with all the operation on non-terminals in the grammar files.

Terminal(): creates a new instance of Terminal class

generate(): an abstract method that returns a statement where are terminals and non-terminals 
are replaced by random values from their production

### NonTerminal
NonTerminal class deals with all the operation on non-terminals in the grammar files.

NonTerminal(): creates a new instance of NonTerminal class

generate(): an abstract method that returns a statement where are terminals and non-terminals 
are replaced by random values from their production

### SentenceGenerator
SentenceGenerator class is an abstract class that is responsible for all operations on the terminals, non-terminals such as finding them, replacing them with a random element from the respective json array in the grammar file and correcting the punctuations in the start string.

readGrammar(): reads the grammar JSON files for all the terminals and non-terminals in it and stores the content of all json arrays in a map.

generate(): an abstract method that returns a statement where are terminals and non-terminals are replaced by random values from their production.

findAllNonTerminalsInString(): parses through the string passed, content looking for the specified patterns. On finding the specified patterns, it does the required operations on them and forms a collection of these pattern matches.

findAllPunctuations():parses through the string passed, content looking for the specified patterns. On finding the specified patterns, it does the required operations on them and forms a collection of these pattern matches.

correctPunctuations(): corrects the position of the punctuations in the string passed. The string is split into a list and every string element that starts with a punctuation is corrected.

returnRandomElement(): returns a random element from the array of the passed tag

replaceTags(): replaces all the non-terminals with a random value from its production until all the elements in the start statement are terminals.


### Client
Client class is responsible for interacting with the end user and interface operations. It contains the driver code.

main(): main() contains the driver code that reads the command line arguments for the grammar directory and reads user input on the grammar that needs to be generated

### ClientHelper 
ClientHelper is a helper class for Client which has main method

getUserSelection(): displays the available options and gets the option selected by user

### UndefinedNonTerminalException
UndefinedNonTerminalException is defined to be thrown when a non-terminal is used, but not defined in the grammar file

### InvalidGrammarFileException
Thrown for invalid or undefined grammar file

## Assumptions about problem nature

All grammar files are syntactically correct and the non-terminal productions are not in an infinite cycle.

All JSON files have 2 keys, start and grammartitle.

## Running the code
Specify the absolute path of the directory containing the grammar files as the argument while running the code on command line.

Example: 

        javac -classpath src/main/assignment4 *.java

        java -classpath src/main/assignment4 assignment4.client.Client src/main/resouces

On passing the path, the code presents the user with multiple options of grammar based on the grammar files present in the directory, that can be generated.
The user is to enter their choice on command line and a production is generated for the entered grammar choice.
After this, the user can opt in for another grammar of the same type or opt a different one or quit.

## Steps taken to ensure correctness

1.The input directory passed is a valid, existing file and is a directory

2.If the non-terminal's production is null, the situation is handled with a blank expansion. 

3.If a non-terminal is undefined in the grammar file, an exception is thrown.

4.User input is validated at every step.

5.Code corrects punctuations in both the start statement and the resultant statement containing only terminals.


