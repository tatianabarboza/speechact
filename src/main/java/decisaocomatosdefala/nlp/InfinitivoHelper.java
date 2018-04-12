package decisaocomatosdefala.nlp;

import java.io.File;

import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;
import edu.smu.tspell.wordnet.impl.file.Morphology;

public class InfinitivoHelper {

	 public static String convertendoParaInfinitivo(String verb) {
	        System.setProperty("wordnet.database.dir", System.getProperty("user.dir") + File.separator +  "test" + File.separator +  "dict"  );// "/home/tatiana/speechact/test/dict/");
	        WordNetDatabase database = WordNetDatabase.getFileInstance();
	        Morphology id = Morphology.getInstance();
	        String[] arr = id.getBaseFormCandidates(verb, SynsetType.VERB);
	        String verbo = "";
	        for (String a : arr) {
	            verbo = a;
	        }
	        return verbo;
	    }
	
}
