package decisaocomatosdefala.nlp;

import static opennlp.tools.util.Span.spansToStrings;

import java.util.ArrayList;
import java.util.List;

import opennlp.tools.util.Span;
import opennlp.tools.util.StringUtil;

public class Tokenizacao {
	
	 static class CharacterEnum {

	        static final CharacterEnum WHITESPACE = new CharacterEnum("whitespace");
	        static final CharacterEnum ALPHABETIC = new CharacterEnum("alphabetic");
	        static final CharacterEnum NUMERIC = new CharacterEnum("numeric");
	        static final CharacterEnum OTHER = new CharacterEnum("other");

	        private String name;

	        private CharacterEnum(String name) {
	            this.name = name;
	        }

	        @Override
	        public String toString() {
	            return name;
	        }
	    }
	
	public static String[] tokenize(String s) {
        return spansToStrings(tokenizePos(s), s);
    }

    public static Span[] tokenizePos(String s) {
        CharacterEnum charType = CharacterEnum.WHITESPACE;
        CharacterEnum state = charType;

        List<Span> tokens = new ArrayList<>();
        int sl = s.length();
        int start = -1;
        char pc = 0;
        for (int ci = 0; ci < sl; ci++) {
            char c = s.charAt(ci);
            if (StringUtil.isWhitespace(c)) {
                charType = CharacterEnum.WHITESPACE;
            } else if (Character.isLetter(c)) {
                charType = CharacterEnum.ALPHABETIC;
            } else if (Character.isDigit(c)) {
                charType = CharacterEnum.NUMERIC;
            } else {
                charType = CharacterEnum.OTHER;
            }
            if (state == CharacterEnum.WHITESPACE) {
                if (charType != CharacterEnum.WHITESPACE) {
                    start = ci;
                }
            } else {
                if (charType != state || charType == CharacterEnum.OTHER && c != pc) {
                    tokens.add(new Span(start, ci));
                    start = ci;
                }
            }
            state = charType;
            pc = c;
        }
        if (charType != CharacterEnum.WHITESPACE) {
            tokens.add(new Span(start, sl));
        }
        return tokens.toArray(new Span[tokens.size()]);

    }

}
