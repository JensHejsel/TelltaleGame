package GameManager;

import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

public class StoryController {
	private Random randGene = new Random();
	
	private int storyState = 0; 
	private String name = "Jens";
    private String lokation = "Din mor";
    
    ArrayList<String> firstPart = new ArrayList<String>(Arrays.asList("Der var engang en dreng som hed @%#", 
    																  "Nu skal i hører historien om @%# som var en papegøje", 
    																  "Der var engang en moden kvinde kendt som @%#",
    																  "Der var engang en onkel som gik under aliaset @%#",
    																  "Der var engang en hemmelig agent kendt som @%#", 
    																  "Nu skal i hører om rotten @%#",
    																  "Nu skal i hører om nørden @%#",
    																  "Der var engang en bjørn den hed @%#"));
    ArrayList<String> secondPart = new ArrayList<String>(Arrays.asList("NAME elskede at spille @%#",
    																   "@%# var NAME's ynglingsting og den skulle altid med på eventyr",
    																	"NAME kunne godt lide at have @%# med sig overalt det føltes betrykkende",
    																	"NAME havde jordens største @%#",
    																	"NAME tog altid sit sværd med sig fordi den ofte manglede @%#",
    																	"NAME glemte altid sin @%# når NAME skulle på tur",
    																	"NAME fik tit at vide NAME var god til at @%#"));
    ArrayList<String> thirdPart = new ArrayList<String>(Arrays.asList("NAME måtte nu bevæge sig mod @%#",
    																	"'Nu går jeg til @%#' sagde NAME",
    																	"NAME fløj ud af vinduet mod @%#",
    																	"NAME hopped af sted på 2 ben på vej mod @%#",
    																	"Op af skorstenen gik det, nu var NAME på vej mod @%#"));
    ArrayList<String> fourthPart = new ArrayList<String>(Arrays.asList("Da NAME ankom til LOKATION skete det utænkelige hans @%# eksploderede",
																		"NAME fløj hen over LOKATION, da NAME så hvad hvordan stedet var blevet @%# blev NAME chokeret",
																		"NAME opdagede hvor utroligt @%# LOKATION var blevet",
																		"",
																		""));
    ArrayList<String> fifthPart = new ArrayList<String>(Arrays.asList(""));
    ArrayList<String> sixthPart = new ArrayList<String>(Arrays.asList(""));
    ArrayList<String> seventhPart = new ArrayList<String>(Arrays.asList(""));
    ArrayList<String> eightthPart = new ArrayList<String>(Arrays.asList(""));
    ArrayList<String> ninethPart = new ArrayList<String>(Arrays.asList(""));
    ArrayList<String> tenthPart = new ArrayList<String>(Arrays.asList(""));
    
    public String getNextLine() {
    	switch(storyState) {
    		case 0:
    			storyState++;
    			return findNextLine(firstPart);
    		case 1:
    			storyState++;
    			return findNextLine(secondPart);
    		case 2:
    			storyState++;
    			return findNextLine(thirdPart);
    		case 3:
    			storyState++;
    			return findNextLine(fourthPart);
    		case 4:
    			storyState++;
    			return findNextLine(fifthPart);
    		case 5:
    			storyState++;
    			return findNextLine(sixthPart);
    		case 6:
    			storyState++;
    			return findNextLine(seventhPart);
    		case 7:
    			storyState++;
    			return findNextLine(eightthPart);
    		case 8:
    			storyState++;
    			return findNextLine(ninethPart);
    		case 9:
    			storyState = 0;
    			return findNextLine(tenthPart);
    		default:
    			return null;
    	}
    }
    private String findNextLine(ArrayList<String> inputList) {
    	String returnString = inputList.get(randGene.nextInt(0, inputList.size()));
    	returnString.replaceAll("@%#", "__________");
    	returnString.replaceAll("NAME", name);
    	returnString.replaceAll("LOKATION", lokation);
    	return returnString;
    }
}
