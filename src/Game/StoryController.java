package Game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

public class StoryController {
	private Random randGene = new Random();
	private String currentStory = "";
	private String currentLine ="";
	private int storyState = 0; 
	private String name = "Jens";
    private String lokation = "Din mor";
    private String enemy = "Pikachu";
    ArrayList<String> firstPart = new ArrayList<String>(Arrays.asList("Der var engang en dreng som hed @%#", 
    																  "Nu skal i h�rer historien om @%# som var en papeg�je", 
    																  "Der var engang en moden kvinde kendt som @%#",
    																  "Der var engang en onkel som gik under aliaset @%#",
    																  "Der var engang en hemmelig agent kendt som @%#", 
    																  "Nu skal i h�rer om rotten @%#",
    																  "Nu skal i h�rer om n�rden @%#",
    																  "Der var engang en bj�rn den hed @%#"));
    ArrayList<String> secondPart = new ArrayList<String>(Arrays.asList("NAME elskede at spille @%#",
    																   "@%# var NAME's ynglingsting og den skulle altid med p� eventyr",
    																	"NAME kunne godt lide at have @%# med sig overalt det f�ltes betrykkende",
    																	"NAME havde jordens st�rste @%#",
    																	"NAME tog altid sit sv�rd med sig fordi den ofte manglede @%#",
    																	"NAME glemte altid sin @%# n�r NAME skulle p� tur",
    																	"NAME fik tit at vide NAME var god til at @%#"));
    ArrayList<String> thirdPart = new ArrayList<String>(Arrays.asList("NAME m�tte nu bev�ge sig mod @%#",
    																	"'Nu g�r jeg til @%#' sagde NAME",
    																	"NAME fl�j ud af vinduet mod @%#",
    																	"NAME hopped af sted p� 2 ben p� vej mod @%#",
    																	"Op af skorstenen gik det, nu var NAME p� vej mod @%#"));
    ArrayList<String> fourthPart = new ArrayList<String>(Arrays.asList("Da NAME ankom til LOKATION skete det ut�nkelige hans @%# eksploderede",
																		"NAME fl�j hen over LOKATION, da NAME s� hvad hvordan stedet var blevet @%# blev NAME chokeret",
																		"NAME opdagede hvor utroligt @%# LOKATION var blevet",
																		"NAME havde aldrig set noget s� @%#  som LOKATION, NAME begyndte at gr�de"));
    ArrayList<String> fifthPart = new ArrayList<String>(Arrays.asList("Pludeseligt opdagede NAME noget bag buskene bag tr�et det lignede en @%# ",
    																	"Der l�d et skrig, pludseligt fl�j @%# hen over hovede p� NAME",
    																	"Det buldrede kraftigt da @%# kom l�bende mod NAME",
    																	"Skyerne sortnede og med et stod der en @%# foran NAME", 
    																	"Det pudslede i gr�sset, og NAME s� en @%# komme krybende"));
    ArrayList<String> sixthPart = new ArrayList<String>(Arrays.asList("ENEMY trak en @%# og begyndte at t�ske NAME", 
    																	"NAME k�rtegnede blidt ENEMY, men pludseligt sprang ENEMY op og bed NAME",
    																	"NAME blev bange da ENEMY begyndte at @%# ham	"));
    ArrayList<String> seventhPart = new ArrayList<String>(Arrays.asList("NAME var nu blevet godt træt af ENEMY, så han @%#."));
    ArrayList<String> eightthPart = new ArrayList<String>(Arrays.asList("ENEMY blev @%# så han fløj væk"));
    ArrayList<String> ninethPart = new ArrayList<String>(Arrays.asList("NAME var nu helt alene i verden det gjorde ham @%#"));
    ArrayList<String> tenthPart = new ArrayList<String>(Arrays.asList("NAME kunne nu @%# til sine dages ende"));
    
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
    	returnString = returnString.replaceAll("@%#", "__________");
		returnString = returnString.replaceAll("NAME", name);
		returnString = returnString.replaceAll("LOKATION", lokation);
		returnString = returnString.replaceAll("ENEMY", enemy);
    	currentLine = returnString;
    	return returnString;
    }
    public String setVotedAnswer(String answer) {
    	currentLine = currentLine.replaceAll("__________",answer);
    	currentStory += currentLine;
    	switch(storyState) {
	    	case 1:
	    		name = answer;
	    		break;
	    	case 3:
	    		lokation = answer;
	    		break;
	    	case 5:
	    		enemy = answer;
	    		break;
    	}
		return currentLine;
    }
    public String getFullStory() {
    	return currentStory;
    }
}
