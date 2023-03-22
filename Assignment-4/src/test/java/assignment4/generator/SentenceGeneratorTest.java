package assignment4.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import assignment4.exception.InvalidGrammarFileException;
import assignment4.exception.UndefinedNonTerminalException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import static generator.TestFileConstants.*;

class SentenceGeneratorTest {

  public static final String DIRECTORY = "src"+ File.separator + "test"+ File.separator + "resources";
  public static final String INSULT_GRAMMAR =
      DIRECTORY+ File.separator + "insult_grammar.json";
  public static final String POEM_GRAMMAR =
      DIRECTORY+ File.separator + "poem_grammar.json";
  public static final String TERM_PAPER_GRAMMAR =
      DIRECTORY+ File.separator + "term_paper_grammar.json";
  public static final String SECRET_GRAMMAR =
      DIRECTORY+ File.separator + "secret_grammar.json";
  public static final String REASON_GRAMMAR =
      DIRECTORY+ File.separator + "reason_grammar.json";

  private Terminal terminal1, terminal2;
  private GrammarFileReader reader ;
  private File grammarDirectory;

  @BeforeEach
  void setUp() throws InvalidGrammarFileException {
    grammarDirectory = new File(DIRECTORY);
    reader = GrammarFileReader.getGrammarFileReader(grammarDirectory);
    File grammar1 = new File(INSULT_GRAMMAR);
    File grammar2 = new File(POEM_GRAMMAR);

    String s1= "You are so <adj1> that even a <noun> would not want to <good_verb> you .";
    String s2= "With the <force> of <metaphor> , may <curse> .";
    String s3 = "The <object> <verb> tonight.";
    terminal1= new Terminal(grammar1,s1);
    terminal2= new Terminal(grammar2,s3);

  }

  @Test
  void findAllNonTerminals() {
    String s1= "You are so <adj1> that even a <noun> would not want to <good_verb> you .";
    String s2= "With the <force> of <metaphor> , may <curse> .";
    String s3 = "The <object> <verb> tonight.";

    List<String> testList = new ArrayList<>();
    testList.add("adj1");
    testList.add("noun");
    testList.add("good_verb");
    List<String> nonTerminals = terminal1.findNonTerminalsInString(s1);
    Assertions.assertEquals(testList, nonTerminals);

    testList.clear();
    testList.add("force");
    testList.add("metaphor");
    testList.add("curse");
    nonTerminals = terminal1.findNonTerminalsInString(s2);
    Assertions.assertEquals(testList, nonTerminals);

    testList.clear();
    testList.add("object");
    testList.add("verb");
    nonTerminals = terminal2.findNonTerminalsInString(s3);
    Assertions.assertEquals(testList, nonTerminals);
  }
  @Test
  void findPunctuation() {
    String s1= "You are so <adj1> that even a <noun> would not want to <good_verb> you .";
    String s2= "With the <force> of <metaphor> , may <curse> .";
    String s3 = "The <object> <verb> tonight.";

    /**List<String> testList = new ArrayList<>();
    testList.add(".");
    List<String> punctuations = terminal1.findPunctuations(s1);
    Assertions.assertEquals(testList, punctuations);

    testList.clear();
     */
    List<String> testList = new ArrayList<>();
    testList.add(",");
    testList.add(".");
    List<String> punctuations = terminal1.findPunctuations(s2);
    Assertions.assertEquals(testList, punctuations);

    testList.clear();
    testList.add(".");
    punctuations = terminal2.findPunctuations(s3);
    Assertions.assertEquals(testList, punctuations);
  }

  @Test
  void correctPunctuations() {
    String s1= "You are so <adj1> that even a <noun> would not want to <good_verb> you .";
    String s2= "With the <force> of <metaphor> , may <curse> .";
    String s3 = "The <object> <verb> tonight.";

    /*terminal1.findNonTerminalsInString(s2);
    String testString = "With the <force> of <metaphor>, may <curse>.";
    String corrected = terminal1.correctPunctuations(s2);
    Assertions.assertEquals(testString,corrected);

    terminal2.findNonTerminalsInString(s1);
    testString = "You are so <adj1> that even a <noun> would not want to <good_verb> you.";
    corrected = terminal2.correctPunctuations(s1);
    Assertions.assertEquals(testString,corrected);


    */String testString = "You deranged, deranged llama entrails for brains.";
      String corrected = terminal2.correctPunctuations("You deranged , deranged llama entrails for brains.");
      Assertions.assertEquals(testString, corrected);

  }

  @Test
  void readGrammar() {
    //File grammar1 = new File("C:\\Users\\srish\\IdeaProjects\\Group_shrutiagrawal10_hegdesrishti\\Assignment-4\\src\\test\\resources\\insult_grammar.json");
    File grammar1 = new File(POEM_GRAMMAR);
    terminal1.readGrammar(grammar1);
    List<String> testList = new ArrayList<>();
    testList.add("grammardesc");
    testList.add("start");
    testList.add("verb");
    testList.add("adverb");
    testList.add("grammartitle");
    testList.add("object");
    Assertions.assertEquals(testList, terminal1.getTags());
  }

  @Test
  void returnRandomElement() throws UndefinedNonTerminalException {
    File grammar1 = new File(POEM_GRAMMAR);
    terminal1.readGrammar(grammar1);
    String random1 = terminal1.returnRandomElement("object",0);
    Assertions.assertEquals("waves",random1);
    random1 = terminal1.returnRandomElement("verb",0);
    Assertions.assertEquals("sigh <adverb>",random1);
    random1 = terminal1.returnRandomElement("adverb",0);
    Assertions.assertEquals("grumpily",random1);
  }


  @Test
  void replaceTags() throws UndefinedNonTerminalException {
    File grammar1 = new File(POEM_GRAMMAR);
    terminal1.readGrammar(grammar1);
    String poem = terminal1.replaceNonTerminals("The <object> <verb> tonight.",0);
    Assertions.assertEquals("The waves sigh grumpily tonight.", poem);

    File grammar2=new File(TERM_PAPER_GRAMMAR);
    Terminal terminal4 = new Terminal(grammar2,"<body1> ");
    terminal4.readGrammar(grammar2);
    Assertions.assertThrows(UndefinedNonTerminalException.class, ()->terminal4.replaceNonTerminals("<body1> ",0));

    File grammar3 = new File(SECRET_GRAMMAR);
    Terminal terminal5 = new Terminal(grammar3,"Today's secret is <secret>.");
    terminal5.readGrammar(grammar3);
    String secret = terminal5.replaceNonTerminals("Today's secret is <secret>.",0);
    Assertions.assertEquals("Today's secret is .", secret);  }

  @Test
  void testGenerate() throws UndefinedNonTerminalException {
    File grammar2 = new File(POEM_GRAMMAR);
    terminal2.readGrammar(grammar2);
    String poem =terminal2.generate(0);
    System.out.println(poem);
    Assertions.assertEquals("The waves sigh grumpily tonight.", poem);

    File grammar3 = new File(REASON_GRAMMAR);
    Terminal terminal3= new Terminal(grammar3,"The reason it is this way here is unknown.");
    terminal3.readGrammar(grammar2);
    poem =terminal3.generate(0);
    System.out.println(poem);
    Assertions.assertEquals("The reason it is this way here is unknown.", poem);
  }
}