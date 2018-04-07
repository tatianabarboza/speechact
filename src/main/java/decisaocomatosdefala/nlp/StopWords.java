package decisaocomatosdefala.nlp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import opennlp.tools.tokenize.SimpleTokenizer;

public class StopWords {

	private static String[] defaultStopWords = {"#", "$", "%", "\"", "\'"};
    private static HashSet<String> stopWords = new HashSet<String>();
    static {
    		StopWords.stopWords.addAll(Arrays.asList(defaultStopWords));
    }
    
    public static String[] removeStopWords(String[] words) {
        ArrayList<String> tokens = new ArrayList<String>(Arrays.asList(words));
        for (int i = 0; i < tokens.size(); i++) {
            if (stopWords.contains(tokens.get(i))) {
                tokens.remove(i);
            }
        }
        return (String[]) tokens.toArray(
                new String[tokens.size()]);
    }
    
    public static String removendoCaracter(String paragraph) {
        SimpleTokenizer simpleTokenizer = SimpleTokenizer.INSTANCE;
        String tokens[] = simpleTokenizer.tokenize(paragraph);
        String list[] = StopWords.removeStopWords(tokens);
        paragraph = "";
        for (String word : list) {
            paragraph += word + " ";
        }
        return paragraph;
    }

	
}
